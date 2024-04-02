package com.carboncell.assessment.SecurityConfig;


import com.carboncell.assessment.SecurityConfig.Utils.JwtUtils;
import com.carboncell.assessment.Service.Userdetails.UserDetailsServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImp userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //first get the token from request
        var tokenOp=getTokenFromRequest(request);



        tokenOp.ifPresent(token->{
            // NOw validationg the jwt token using jwt utils that class has to made
            if(JwtUtils.validateToken(token)) {
                // now get the username from token
                var userNameOp = JwtUtils.getUserNameFromToken(token);
                //now i will fetch the userDetails by this username by using UserDetailsService interface;
                userNameOp.ifPresent(username->{

                    var userDetails = userDetailsService.loadUserByUsername(username);

                    //create the jwttoken
                    var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                    //now set the web details in token like ip address etc from request
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //set authentication token in security context which contains the information of authenticated users
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                });
            }

        });
        // pass request and response to next filter
        filterChain.doFilter(request,response);

    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        //get the jwt authentication header from request
        String authHeader= request.getHeader(HttpHeaders.AUTHORIZATION);
        //first we will check wheather the authheader is empty if not then return only jwttoken
        //here using stingutils
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            // jwt is in this form Bearer <jwt token>
            // we will get only jwttoken for that we will use substring
            return Optional.of(authHeader.substring(7));
        }
        return Optional.empty();
    }
}

