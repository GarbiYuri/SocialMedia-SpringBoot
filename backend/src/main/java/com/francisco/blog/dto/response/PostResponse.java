package com.francisco.blog.dto.response;

import java.util.List;

public record PostResponse(String title,
                           String description,
                           Boolean isActive,
                           List<String> path,
                           String username,
                           String photoPerfil) {
}
