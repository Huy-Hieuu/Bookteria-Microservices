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
import org.huyhieu.entity.User;
import org.huyhieu.exception.custom.UserAPIException;
import org.huyhieu.repository.UserRepository;
import org.huyhieu.service.AuthenticationService;
import org.huyhieu.utils.enums.APIStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signer-key}")
    protected String signerKey;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                                  .orElseThrow(() -> new UserAPIException(APIStatus.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isAuthenticated) {
            throw new UserAPIException(APIStatus.UNAUTHENTICATED);
        }

        String token = generateToken(request.getUsername());

        return AuthenticationResponse.builder()
                                     .token(token)
                                     .isAuthenticated(true)
                                     .build();
    }

    private String generateToken(String username) {
        // Header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Payload, body's data is called claim
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("huyhieu")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("customClaim", "custom")
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
            throw new RuntimeException(e);
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
}
