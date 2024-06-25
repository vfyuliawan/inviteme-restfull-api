// package inviteme.restfull.security;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.oauth2.jwt.JwtDecoder;
// import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
// import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
// import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
// import org.springframework.security.web.SecurityFilterChain;

// import inviteme.restfull.utility.JwtTokenUtil;

// @Configuration
// @EnableWebSecurity
// @EnableMethodSecurity
// public class SecurityConfig {

//     @Autowired
//     private JwtTokenUtil jwtTokenUtil;

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
//             throws Exception {
//         httpSecurity
//                 .authorizeHttpRequests(authRequest -> {
//                     authRequest
//                             .requestMatchers("/api/auth/login", "/swagger-ui/**", "/v3/api-docs/**",
//                                     "/swagger-resources/**", "/webjars/**")
//                             .permitAll()
//                             .anyRequest().authenticated();
//                 })
//                 .oauth2ResourceServer(
//                         oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
//         return httpSecurity.build();
//     }

//     @Bean
//     public JwtDecoder jwtDecoder() {
//         return NimbusJwtDecoder.withSecretKey(jwtTokenUtil.getKey()).build();
//     }

//     @Bean
//     public JwtAuthenticationConverter jwtAuthenticationConverter() {
//         JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//         grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
//         grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");

//         JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//         jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
//         return jwtAuthenticationConverter;
//     }

// }
