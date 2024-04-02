package com.carboncell.assessment.SecurityConfig.Utils;



import com.carboncell.assessment.Repo.TokenRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
@Component

public class JwtUtils {
    //make private constructor becz not want to create the object out of it
    private JwtUtils(TokenRepo tokenRepo){
        this.tokenRepo = tokenRepo;
    }
    private static final SecretKey Secretkey= Jwts.SIG.HS256.key().build();
    private static final String ISSUER ="JUNAIDBHAT";

    private static TokenRepo tokenRepo;
    public static boolean validateToken(String jwttoken) {
        boolean isValidtoken= tokenRepo.findByToken(jwttoken).map(t->!t.isLoggedOut()).orElse(false);
        return parseToken(jwttoken).isPresent() && isValidtoken;
    }

    private static Optional<Claims> parseToken(String jwttoken) {
        var jwtparser=
                Jwts.parser().verifyWith(Secretkey).build();
        try {
            return  Optional.of(jwtparser.parseSignedClaims(jwttoken).getPayload());
        }catch (JwtException | IllegalArgumentException e){
            System.out.print("jwt exception occur"+ e.getMessage());
        }
        return Optional.empty();
    }

    public static Optional<String> getUserNameFromToken(String token) {
        var claimsOpt= parseToken(token);
        if(claimsOpt.isPresent()){
            return Optional.of(claimsOpt.get().getSubject());
        }
        return Optional.empty();

    }

    public static String generateToken(String username) {
        var currentDate= new Date();
        var exp= DateUtils.addMinutes(currentDate,20);
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuer(ISSUER)
                .subject(username)
                .signWith(Secretkey)
                .issuedAt(currentDate)
                .expiration(exp)
                .compact();
    }
}
