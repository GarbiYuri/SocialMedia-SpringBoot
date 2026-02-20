package com.francisco.blog.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record MyPostRequest(String title,
                            String description,
                            Boolean isActive,
                            List<String> path) {
}
