package com.francisco.blog.dto.response;

import com.francisco.blog.entitys.Post;
import jakarta.validation.constraints.NotEmpty;

import java.time.ZonedDateTime;

public record CommentResponse(String username,
                              PostResponse postResponse,
                              String comment,
                              ZonedDateTime commentedAt) {
}
