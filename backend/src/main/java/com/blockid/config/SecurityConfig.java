//package com.blockid.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//@Configuration
//public class SecurityConfig {
//
//    private final JwtFilter jwtFilter;
//
//    public SecurityConfig(JwtFilter jwtFilter) {
//        this.jwtFilter = jwtFilter;
//    }
////this was getting 403 forbidden later u can delete below this code
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                .cors(cors -> {}) // ✅ IMPORTANT (CORS enabled)
////                .csrf(csrf -> csrf.disable())
////                .authorizeHttpRequests(auth -> auth
////                        .requestMatchers("/api/auth/**").permitAll()
////                        .requestMatchers("/api/identity/submit").authenticated() // ✅ USER allowed
//////                        .requestMatchers("/api/identity/all").hasRole("ADMIN")
//////                        .requestMatchers("/api/identity/approve/**").hasRole("ADMIN")
////
////                                .requestMatchers("/api/identity/all").permitAll()
////                                .requestMatchers("/api/identity/approve/**").permitAll()
////
////                        .anyRequest().authenticated()
////                )
////                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
////
////        return http.build();
////    }
//
//@Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http
//            .csrf(csrf -> csrf.disable())
//            .cors(cors -> {})
//            .authorizeHttpRequests(auth -> auth
//                    .anyRequest().permitAll() // 🔥 allow EVERYTHING
//            );
//
//    return http.build();
//}
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}


package com.blockid.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        // ✅ ADDED: Allows anyone to use the Verify page (no login required)
                        .requestMatchers("/api/identity/verify").permitAll()
                        // ✅ CHANGED: Allows both USER and ADMIN to see the Explorer (all identities)
                        .requestMatchers("/api/identity/all").hasAnyRole("USER", "ADMIN")

                        .requestMatchers("/api/identity/approve/**").hasRole("ADMIN")
                        .requestMatchers("/api/identity/submit").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}