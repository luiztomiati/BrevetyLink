package com.brevitylink.api.controller;

import com.brevitylink.api.dto.DadosToken;
import com.brevitylink.api.dto.Login;
import com.brevitylink.api.dto.RefreshToken;
import com.brevitylink.api.model.Users;
import com.brevitylink.api.repository.UserRepository;
import com.brevitylink.api.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class AutenticationController {


    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final UserRepository userRepository;

    public AutenticationController(AuthenticationManager authenticationManager, TokenService tokenService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<DadosToken> loginUser(@Valid @RequestBody Login data) {
       var authenticationToken =  new UsernamePasswordAuthenticationToken(data.email(), data.password());
       var authentication = authenticationManager.authenticate(authenticationToken);

       String tokenAccess = tokenService.generateToken((Users) authentication.getPrincipal());
       String refreshToken = tokenService.generateRefreshToken((Users) authentication.getPrincipal());
       return ResponseEntity.ok(new DadosToken(tokenAccess, refreshToken));
    }

    @PostMapping("/atttoken")
    public ResponseEntity<DadosToken> attToken(@Valid @RequestBody RefreshToken data) {
        var refreshToken = data.refreshToken();
        UUID userId = UUID.fromString(tokenService.checkToken(refreshToken));
        Users user = userRepository.findById(userId).orElseThrow();

        String tokenAccess = tokenService.generateToken(user);
        String refreshTokenAtt = tokenService.generateRefreshToken(user);
        return ResponseEntity.ok(new DadosToken(tokenAccess, refreshTokenAtt));


    }
}
