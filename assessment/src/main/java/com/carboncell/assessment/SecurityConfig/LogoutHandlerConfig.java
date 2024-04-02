package com.carboncell.assessment.SecurityConfig;


import com.carboncell.assessment.Model.Token;
import com.carboncell.assessment.Repo.TokenRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class LogoutHandlerConfig implements LogoutHandler {
    private final TokenRepo tokenRepo;

    public LogoutHandlerConfig(TokenRepo tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//this is the same as the method of jwtauthenticationfilter
        String authHeader= request.getHeader(HttpHeaders.AUTHORIZATION);

        if(!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {

            return;
        }
        String token=authHeader.substring(7);
        Token storedToken = tokenRepo.findByToken(token).orElse(null);

        if(storedToken!=null){
            storedToken.setLoggedOut(true);
            tokenRepo.save(storedToken);
        }

    }
}

