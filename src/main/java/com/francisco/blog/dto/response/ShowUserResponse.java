package com.francisco.blog.dto.response;

import jakarta.annotation.Nullable;
import org.springframework.data.domain.Pageable;

public record ShowUserResponse(String username,
                               String photoPerfil,
                               Boolean isActive) {
}
