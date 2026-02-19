package com.francisco.blog.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record DeleteUserRequest(@NotEmpty(message = "Email Obrigatório") String email,
                                @NotEmpty(message = "Senha Obrigatório") String password,
                                @NotEmpty(message = "Razão de Delete Obrigatório") String reason,
                                Integer time) {
}
