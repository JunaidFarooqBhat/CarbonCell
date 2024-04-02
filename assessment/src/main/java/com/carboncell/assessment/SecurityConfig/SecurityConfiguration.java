package com.carboncell.assessment.SecurityConfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {


    private final   AuthenticationEntryPoint authenticationEntryPoint;
    private final LogoutHandlerConfig logoutHandler;

    public SecurityConfiguration(AuthenticationEntryPoint authenticationEntryPoint, LogoutHandlerConfig logoutHandler, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.logoutHandler = logoutHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //disable cors
        httpSecurity.cors(corsConfig->corsConfig.disable());
        //disable csrf
        httpSecurity.csrf(csrfConfig->csrfConfig.disable());
        // filter the request
        httpSecurity.authorizeHttpRequests(requestMatcher->requestMatcher.requestMatchers("/api/auth/login/**",
                                "/api/auth/register/**",
                                "/v2/api-docs/**",
                                "/swagger-resources/**",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/v3/api-docs/**")
                        .permitAll()
                       .requestMatchers("/h2-console/**").
                        permitAll()
                        .anyRequest().authenticated()
                //this headers thing is for h2 db console becz the ui was blocked by local host
        ).headers(headers->headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()));
        //authentication entry point for-> exception handling in authentication
        httpSecurity.exceptionHandling(authConfig->authConfig.authenticationEntryPoint(authenticationEntryPoint));

        //Session policy should be stateless in jwt so i will make session policy stateless
        httpSecurity.sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Adding jwt authentication filter to filter the jwt token which comes with any request
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // adding logout functionality to logout form application
        httpSecurity.logout(logout->logout.logoutUrl("/logout").addLogoutHandler(logoutHandler).logoutSuccessHandler(
                ((request, response, authentication) -> SecurityContextHolder.clearContext())
        ));

        return httpSecurity.build();


    }

}

