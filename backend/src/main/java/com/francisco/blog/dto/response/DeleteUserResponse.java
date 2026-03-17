package com.francisco.blog.dto.response;

import jakarta.validation.constraints.NotEmpty;

public record DeleteUserResponse(@NotEmpty(message = "Nome é Obrigatório") String username,
                                 @NotEmpty(message = "Email é Obrigatório") String email,
                                 @NotEmpty(message = "Ativo deve ter respota(boolean)") Boolean is_Active,
                                 @NotEmpty(message = "Precisa da razão") String reason,
                                 @NotEmpty(message = "Precisa do tempo") String time) {
}
