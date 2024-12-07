package com.example.securitydemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Profile("!prd")
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.sessionManagement(smc -> smc.invalidSessionUrl("/invalidSession").maximumSessions(1).maxSessionsPreventsLogin(true));

        http.requiresChannel(requestMatcherRegistry -> requestMatcherRegistry.anyRequest().requiresInsecure());

        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), UsernamePasswordAuthenticationFilter.class);  //모든 곳에 cqrs token 발행하게 + 쿠키를 csrf 저장소로 쓰기위한 코드

        http.authorizeHttpRequests( requests ->
                requests.requestMatchers("/members/**", "/method").authenticated()
                        .requestMatchers("/login/**", "/join", "/invalidSession").permitAll()
                        .requestMatchers("/admin").hasAuthority("USER"));

        http.formLogin(formLoginConfigurer -> formLoginConfigurer.loginPage("/login").defaultSuccessUrl("/members").failureUrl("/login?error=true"));
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
