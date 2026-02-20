package com.francisco.blog.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PostRequest (@NotEmpty(message = "O post deve ter titulo") String title,
                           @NotEmpty(message = "O post deve ter Descrição") String description,
                           List<String> path){
}
