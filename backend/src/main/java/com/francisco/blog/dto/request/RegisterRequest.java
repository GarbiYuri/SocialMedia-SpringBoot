package com.francisco.blog.dto.request;

import com.francisco.blog.entitys.UserRole;
import jakarta.validation.constraints.NotEmpty;

public record RegisterRequest(@NotEmpty(message = "Username Obrigatório") String username,
                              @NotEmpty(message = "Email Obrigatório") String email,
                              @NotEmpty(message = "Senha Obrigatório") String password) {
}
