package org.huyhieu.config;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.crypto.spec.SecretKeySpec;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${jwt.signer-key}")
    private String signerKey;

    /*
     * endpoints will be public: auth/token, auth/introspect
     * */
    private static final List<Pair<String, String>> PUBLIC_END_POINTS
            = Lists.newArrayList(Pair.of("/auth/token", HttpMethod.POST.name()),
                                 Pair.of("/auth/introspect", HttpMethod.POST.name()),
                                 Pair.of("/api/create/user", HttpMethod.POST.name()));

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        RequestMatcher[] requestMatchers =
                PUBLIC_END_POINTS.stream()
                                 .map(pulicRequestPair -> new AntPathRequestMatcher(pulicRequestPair.getKey(), pulicRequestPair.getValue()))
                                 .toArray(RequestMatcher[]::new);

        httpSecurity.authorizeHttpRequests(
                requests -> requests.requestMatchers(requestMatchers).permitAll()
                                    .requestMatchers(new AntPathRequestMatcher("/api/users", HttpMethod.GET.name())).hasAuthority("SCOPE_ADMIN")
                                    .anyRequest().authenticated());

        httpSecurity.oauth2ResourceServer(
                oAuth2Configurer -> oAuth2Configurer.jwt(
                        jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
                ));

        // Temporarily disable CSRF
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");

        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
