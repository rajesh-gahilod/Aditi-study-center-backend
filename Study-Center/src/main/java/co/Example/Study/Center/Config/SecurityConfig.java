package co.Example.Study.Center.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final DaoAuthenticationProvider authenticationProvider;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(JwtFilter jwtFilter,
                          DaoAuthenticationProvider authenticationProvider,
                          CorsConfigurationSource corsConfigurationSource) {
        this.jwtFilter = jwtFilter;
        this.authenticationProvider = authenticationProvider;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/api/users/**",
                                "/api/users/register",
                                "/api/users/getAllStudents",
                                "/api/users/forgot-password",
                                "/api/users/reset-password",


                                "/loginapi/adminlogin",
                                "/loginapi/studentLogin",

                                "/adminApi/create-admin",
                                "/adminApi/studentCount",
                                "/adminApi/totalFee",

                                // ✅ ADD THESE 👇
                                "/batchApi/**",
                                "/batchApi/getAllBatches",
                                "/batchApi/createBatch",
                                "/batchApi/updateBatch",
                                "/batchApi/Batchcount",
//                                "/batchApi/batches/schedule",

                                "/api/payments**",
                                "/api/payments/create-order",
                                "/api/payments/verify-payment",

                                "/api/machine/attendance"

                        ).permitAll()
                        .anyRequest().authenticated()   // ⚠️ ALWAYS LAST
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable());

        return http.build();
    }
}

