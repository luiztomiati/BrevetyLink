package com.brevitylink.api.controller;

import com.brevitylink.api.dto.*;
import com.brevitylink.api.model.Users;
import com.brevitylink.api.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequest user){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(usersService.createUser(user));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity<Void> editUser(@RequestBody @Valid UserEdit data, @AuthenticationPrincipal Users userLogado) {
        usersService.updateUser(userLogado.getId(), data);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal Users userLogado) {
        usersService.deleteUser(userLogado.getId());
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/change-password")
    public ResponseEntity<?> resetPassword(@AuthenticationPrincipal Users userLogado, @RequestBody @Valid ResetPassword user ) {
        try {
            usersService.resetPassword(userLogado.getId(),user);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody @Valid ForgotPassword email) {
        usersService.forgotPassword(email);
        return ResponseEntity.ok("Se o email existir, enviaremos instruções para a recuperação.");
    }
    @PostMapping("/reset-password-token")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordToken reset) {
        try{
            usersService.resetPasswordToken(reset.token(), reset.password());
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
