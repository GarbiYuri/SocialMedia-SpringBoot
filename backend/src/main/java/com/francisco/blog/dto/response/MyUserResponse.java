package com.francisco.blog.dto.response;

public record MyUserResponse(Long id,
                             String email,
                             String username,
                             String about,
                             String photoPerfil) {
}
