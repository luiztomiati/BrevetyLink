package com.brevitylink.api.controller;

import com.brevitylink.api.dto.DadosToken;
import com.brevitylink.api.dto.Login;
import com.brevitylink.api.dto.RefreshToken;
import com.brevitylink.api.model.Users;
import com.brevitylink.api.repository.UserRepository;
import com.brevitylink.api.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@Tag(name = "Auth", description = "Autenticação e geração de tokens JWT")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final UserRepository userRepository;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }
    @Operation(
            summary = "Realizar login",
            description = "Autentica o usuário com email e senha e retorna access token e refresh token"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<DadosToken> loginUser(@Valid @RequestBody Login data) {
       var authenticationToken =  new UsernamePasswordAuthenticationToken(data.email(), data.password());
       var authentication = authenticationManager.authenticate(authenticationToken);

       String tokenAccess = tokenService.generateToken((Users) authentication.getPrincipal());
       String refreshToken = tokenService.generateRefreshToken((Users) authentication.getPrincipal());
       return ResponseEntity.ok(new DadosToken(tokenAccess, refreshToken));
    }
    @Operation(
            summary = "Atualizar token",
            description = "Gera um novo access token a partir de um refresh token válido"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Refresh token inválido"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping("/att-token")
    public ResponseEntity<DadosToken> attToken(@Valid @RequestBody RefreshToken data) {
        var refreshToken = data.refreshToken();
        UUID userId = UUID.fromString(tokenService.checkToken(refreshToken));
        Users user = userRepository.findById(userId).orElseThrow();

        String tokenAccess = tokenService.generateToken(user);
        String refreshTokenAtt = tokenService.generateRefreshToken(user);
        return ResponseEntity.ok(new DadosToken(tokenAccess, refreshTokenAtt));
    }
}
