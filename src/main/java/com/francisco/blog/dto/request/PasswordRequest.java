package com.francisco.blog.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record PasswordRequest(@NotEmpty(message = "O Email não pode estar em Branco") String email,
                              @NotEmpty(message = "A senha antiga não pode estar em Branco") String oldPassword,
                              @NotEmpty(message = "A senha não pode estar em Branco") String password){
}
