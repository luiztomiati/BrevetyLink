package com.brevitylink.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPassword(@Email @NotBlank String email) {
}
