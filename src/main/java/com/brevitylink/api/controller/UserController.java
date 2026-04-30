package com.brevitylink.api.controller;

import com.brevitylink.api.dto.*;
import com.brevitylink.api.model.Users;
import com.brevitylink.api.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@Tag(name = "Users", description = "Gerenciamento de usuários")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Operation(summary = "Criar usuário", description = "Realiza o cadastro de um novo usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    })
    @PostMapping()
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest user){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usersService.createUser(user));
    }

    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados do usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping()
    public ResponseEntity<Void> editUser(@RequestBody @Valid UserEdit data, @AuthenticationPrincipal Users userLogado) {
        usersService.updateUser(userLogado.getId(), data);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletar usuário", description = "Remove o usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Removido com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping()
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal Users userLogado) {
        usersService.deleteUser(userLogado.getId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Alterar senha", description = "Permite ao usuário autenticado alterar sua senha")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Senha inválida"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal Users userLogado, @RequestBody @Valid ResetPassword user) {
        usersService.resetPassword(userLogado.getId(), user);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Solicitar recuperação de senha", description = "Envia instruções para recuperação de senha")
    @ApiResponse(responseCode = "200", description = "Solicitação processada")
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotPassword email) {
        usersService.forgotPassword(email);
        return ResponseEntity.ok("Se o email existir, enviaremos instruções para a recuperação.");
    }

    @Operation(summary = "Resetar senha com token", description = "Define uma nova senha utilizando um token de recuperação")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Senha redefinida"),
            @ApiResponse(responseCode = "400", description = "Token inválido ou expirado")
    })
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid ResetPasswordToken reset) {

        usersService.resetPasswordToken(reset.token(), reset.password());
        return ResponseEntity.noContent().build();
    }
}
