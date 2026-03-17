package com.francisco.blog.dto.response;

import jakarta.validation.constraints.NotEmpty;

public record RegisterResponse(@NotEmpty(message = "Nome é Obrigatório") String username,
                               @NotEmpty(message = "Email é Obrigatório") String email) {
}
