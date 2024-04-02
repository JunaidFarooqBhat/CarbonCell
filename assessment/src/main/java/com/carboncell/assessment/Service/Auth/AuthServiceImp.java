package com.carboncell.assessment.Service.Auth;



import com.carboncell.assessment.Model.Role;
import com.carboncell.assessment.Model.Token;
import com.carboncell.assessment.Model.User;
import com.carboncell.assessment.Repo.TokenRepo;
import com.carboncell.assessment.Repo.UserRepository;
import com.carboncell.assessment.SecurityConfig.Utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepo tokenRepo;

    @Override
    public String login(String username, String password) {
        var authenticationToken= new UsernamePasswordAuthenticationToken(username,password);
        var authenticate=authenticationManager.authenticate(authenticationToken);
        var details= ((User)(authenticate.getPrincipal()));
/// this thing is for logout bexz we are storing the token value in db as well for login
        tokenRepo.deleteAllTokenById(details.getId());
        String token = JwtUtils.generateToken(details.getUsername());

        tokenSave(details,token);
        return token;
    }

    @Override
    public String register(String name, String username, String password, Role role) {
        //check weather user exists or not
        if (userRepository.existsByUsername(username)){
            throw new RuntimeException("User already exists");
        }
// encode the password first
        var encodedPassword= passwordEncoder.encode(password);

        //CREATE USER
        var user= User.builder().
                name(name).username(username)
                .password(encodedPassword).role(role).build();
        // save user in db
        userRepository.save(user);

        // generate the jwt token using jwtutils
        String token=JwtUtils.generateToken(username);
        tokenSave(user, token);
        return token;
    }

    private void tokenSave(User user, String token) {
        Token tok= new Token();
        tok.setToken(token);
        tok.setUser(user);
        tok.setLoggedOut(false);
        tokenRepo.save(tok);
    }
}

