package com.carboncell.assessment.SecurityConfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthenticatonConfig {

    //authentication manager manages the different types of authentication providers.
    //there are different types of authentication provider its manages them
    //we have authenticationmanger in spring security here we are using that one
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return   configuration.getAuthenticationManager();
    }

    //authentication provider authenticates the requests
    // authentication provider uses 2 things userdetailservice and password encoder
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        // most commelny used authentication provider will be used here that is DAO
        DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }
    @Bean
    public PasswordEncoder bCryptpasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


}

