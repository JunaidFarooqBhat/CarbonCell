package com.carboncell.assessment.Controller;

import com.carboncell.assessment.DTO.AuthRequestDTO;
import com.carboncell.assessment.DTO.AuthResponseDTO;
import com.carboncell.assessment.DTO.AuthStatus;
import com.carboncell.assessment.DTO.LoginDto;
import com.carboncell.assessment.Model.Role;
import com.carboncell.assessment.Repo.TokenRepo;
import com.carboncell.assessment.Service.Auth.AuthServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Manager", description = "This manager contains the apis related to registration,login and logout")
public class AuthController {

    private final AuthServiceImp authService;
    private final TokenRepo tokenRepo;
    // login api
    @Operation(summary = "The api used for user to login. It authenticates user based on the provided credentials and returns jwt token."
    ,responses  ={
            @ApiResponse(
                    description = "Success login",
                    responseCode = "200"
            ),
            @ApiResponse(responseCode = "401", description = "Incorrect credentials")
    }
    )

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto authRequestDto){
        var token= authService.login(authRequestDto.username(),authRequestDto.password());
        var authresp= new AuthResponseDTO(token, AuthStatus.LOGIN_SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(authresp);
    }
    @Operation(
            summary = "The api registers the user based on the provided details like name,username,password and returns the jwt token"
            ,responses  ={
            @ApiResponse(
                    description = "Success registration",
                    responseCode = "200"
            ),
            @ApiResponse(responseCode = "403", description = "Internal server error")
    }
    )
    // signup api
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody AuthRequestDTO authRequestDto){
        try {
            var token = authService.register(authRequestDto.name(), authRequestDto.username(), authRequestDto.password(), Role.USER);
            var authresponsedto = new AuthResponseDTO(token, AuthStatus.USER_CREATED_SUCCESSFULLY);
            return ResponseEntity.status(HttpStatus.OK).body(authresponsedto);
        }catch (Exception e){
            var authresponsedto = new AuthResponseDTO(null, AuthStatus.USER_NOT_CREATED);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(authresponsedto);
        }
    }
    @Operation(
            summary = "The api invalidated the jwt token of user and logging out the user, we have to provide the token for that. user have to login to access the protected apis "
            ,responses  ={
            @ApiResponse(
                    description = "Success logout",
                    responseCode = "200"
            ),
            @ApiResponse(responseCode = "403", description = "Internal server error")
    }
    )
    /// the api is only created for swagger ui otherwise we can access logout with this thing
    @GetMapping("/logout")
    public ResponseEntity<String>LogoutUser( String authToken){
        var token= tokenRepo.findByToken(authToken).orElseThrow();
        token.setLoggedOut(true);
        tokenRepo.save(token);
        return new ResponseEntity<>("User logged out successfully",HttpStatus.ACCEPTED);
    }

}
