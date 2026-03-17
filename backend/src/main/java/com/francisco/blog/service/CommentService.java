package com.francisco.blog.service;

import com.francisco.blog.dto.response.CommentResponse;
import com.francisco.blog.dto.response.PostResponse;
import com.francisco.blog.entitys.ArchivePath;
import com.francisco.blog.entitys.Comment;
import com.francisco.blog.entitys.Post;
import com.francisco.blog.entitys.User;
import com.francisco.blog.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public CommentResponse publicComment(Long userId, Long postId, String comment){
        Post post = postService.showPostById(postId);
        User user = userService.showUserById(userId);
        Comment commentSave = new Comment();
        commentSave.setUser(user);
        commentSave.setPost(post);
        commentSave.setComment(comment);
        List<String> archives = post.getArchivePaths().stream()
                .map(ArchivePath::getArchivePath)
                .toList();
        PostResponse postResponse = new PostResponse(post.getTitle(),
                post.getDescription(),
                post.getIsActive(),
                archives,
                post.getUser().getUsername(),
                post.getUser().getPhotoPerfil());

        Comment commentEntity = commentRepository.saveAndFlush(commentSave);

        return new CommentResponse(commentEntity.getUser().getUsername(),
                postResponse,
                commentEntity.getComment(),
                commentEntity.getCommentedAt()
                );

    }

}
