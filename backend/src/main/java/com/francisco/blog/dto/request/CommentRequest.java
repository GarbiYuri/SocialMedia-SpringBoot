package com.francisco.blog.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CommentRequest (@NotEmpty(message = "Post não pode estar Vazio") Long postId,
                              @NotEmpty(message = "Commentario não pode estar Vazio") String comment){
}
