package com.brevitylink.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserEdit(@NotNull String nickName,
                       @Email @NotNull String userEmail,
                       @NotNull String name)
{
}
