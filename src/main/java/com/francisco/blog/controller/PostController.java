package com.francisco.blog.controller;

import com.francisco.blog.config.JWTUserData;
import com.francisco.blog.dto.request.MyPostRequest;
import com.francisco.blog.dto.request.PostRequest;
import com.francisco.blog.dto.response.MyPostResponse;
import com.francisco.blog.dto.response.PostResponse;
import com.francisco.blog.entitys.Post;
import com.francisco.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/showAll")
    public ResponseEntity<Page<PostResponse>> showAll(@PageableDefault(size = 10) Pageable pageable, Authentication authentication,@RequestParam(defaultValue = "false") Boolean showDesactive){
        JWTUserData userData = (JWTUserData) authentication.getPrincipal();
        return ResponseEntity.ok(postService.showAll(pageable,  userData.userId(), showDesactive));
    }
    @GetMapping("/showMyPost")
    public ResponseEntity<Page<MyPostResponse>> showMyPosts(Authentication authentication, @PageableDefault(size = 10) Pageable pageable){
        JWTUserData userData = (JWTUserData) authentication.getPrincipal();

        return ResponseEntity.ok(postService.showMyPosts(userData.userId(), pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<PostResponse> createPost(Authentication authentication, @RequestBody PostRequest post){
        JWTUserData userData = (JWTUserData) authentication.getPrincipal();

        return ResponseEntity.ok(postService.createPost(userData.userId(), post));
    }
    @PutMapping("/editMyPost")
    public ResponseEntity<MyPostResponse> editMyPost(@RequestBody MyPostRequest postRequest, @RequestParam Long idPost, Authentication authentication){
        JWTUserData userData = (JWTUserData) authentication.getPrincipal();

        return ResponseEntity.ok(postService.editPost(postRequest, idPost, userData.userId()));
    }
    @PutMapping("/deletePost")
    public ResponseEntity<MyPostResponse> deletePost(@RequestParam Long id,@RequestBody String reason, Authentication authentication){
        JWTUserData userData = (JWTUserData) authentication.getPrincipal();

        return ResponseEntity.ok(postService.softDeletePostById(userData.userId(), id, reason));
    }


}
