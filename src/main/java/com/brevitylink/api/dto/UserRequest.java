package com.brevitylink.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(@NotBlank String name,
                          @NotBlank String nickname,
                          @NotBlank @Email String email,
                          @NotBlank(message = "A senha é obrigatória")
                             @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
                             @Pattern(
                                     regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
                                     message = "A senha deve conter pelo menos uma letra maiúscula, um número e um caractere especial"
                             ) String password) {
}
