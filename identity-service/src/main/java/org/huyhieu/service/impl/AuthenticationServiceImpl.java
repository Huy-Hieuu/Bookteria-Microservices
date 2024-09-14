package org.huyhieu.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.huyhieu.dto.request.AuthenticationRequest;
import org.huyhieu.dto.request.IntrospectionRequest;
import org.huyhieu.dto.response.AuthenticationResponse;
import org.huyhieu.dto.response.IntrospectionResponse;
import org.huyhieu.entity.IdentityRole;
import org.huyhieu.entity.IdentityUser;
import org.huyhieu.enums.APIStatus;
import org.huyhieu.exception.custom.APIException;
import org.huyhieu.repository.UserRepository;
import org.huyhieu.service.AuthenticationService;
import org.huyhieu.utils.ConstantUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    @NonFinal
    @Value("${jwt.signer-key}")
    protected String signerKey;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        IdentityUser identityUser = userRepository.findByUsername(request.getUsername())
                                                  .orElseThrow(() -> new APIException(APIStatus.USER_NOT_EXISTED));

        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), identityUser.getPassword());
        if (!isAuthenticated) {
            throw new APIException(APIStatus.UNAUTHENTICATED);
        }

        String token = generateToken(identityUser);

        return AuthenticationResponse.builder()
                                     .token(token)
                                     .isAuthenticated(true)
                                     .build();
    }

    /*
    * Need to add "role" to payload's claim => This need for spring security "auto" detect role
    * (Role is mentioned as "Scope" in default Prefix Authorities in Spring Security)
    * */
    private String generateToken(IdentityUser identityUser) {
        // Header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Payload, body's data is called claim
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(identityUser.getUsername())
                .issuer("huyhieu")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("customClaim", "custom")
                .claim("scope", buildRoles(identityUser.getIdentityRoles()))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        // Build header & payload
        JWSObject jwsObject = new JWSObject(header, payload);

        // Sign the token by SIGNATURE
        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Can not generate token", e);
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public IntrospectionResponse introspect(IntrospectionRequest request) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(request.getToken());
        Date expiredTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean isVerified = signedJWT.verify(verifier);
        boolean isTokenValid = isVerified && expiredTime.after(new Date());

        return IntrospectionResponse.builder().isTokenValid(isTokenValid).build();
    }

    /*
    * Need to add space (" ") between these roles because it is convention in Spring Security
    * */
    private String buildRoles(Set<IdentityRole> identityRoles) {
        StringJoiner stringJoiner = new StringJoiner(ConstantUtils.SPACE);

        identityRoles.stream().map(role -> role.getType().name())
                     .forEach(roleType -> stringJoiner.add(ConstantUtils.ROLE_PREFIX + roleType));

        identityRoles.stream()
                     .flatMap(role -> role.getIdentityPermissions().stream())
                     .map(permission -> permission.getType().name())
                     .distinct()
                     .forEach(stringJoiner::add);

        return stringJoiner.toString();
    }
}
