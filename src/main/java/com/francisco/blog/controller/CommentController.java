package com.francisco.blog.controller;

import com.francisco.blog.config.JWTUserData;
import com.francisco.blog.dto.request.CommentRequest;
import com.francisco.blog.dto.response.CommentResponse;
import com.francisco.blog.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/publicComment")
    public ResponseEntity<CommentResponse> publicComment(@RequestBody CommentRequest comment, Authentication authentication){
        JWTUserData userData = (JWTUserData) authentication.getPrincipal();

        return ResponseEntity.ok(commentService.publicComment(userData.userId(), comment.postId(), comment.comment()));
    }

}
