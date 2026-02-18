package com.francisco.blog.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(@NotEmpty(message = "Email Obrigatório") String email,
                           @NotEmpty(message = "Senha Obrigatório") String password) {
}
