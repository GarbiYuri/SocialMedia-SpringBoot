package com.francisco.blog.service;

import com.francisco.blog.dto.request.MyPostRequest;
import com.francisco.blog.dto.request.PostRequest;
import com.francisco.blog.dto.response.MyPostResponse;
import com.francisco.blog.dto.response.PostResponse;
import com.francisco.blog.entitys.*;
import com.francisco.blog.exception.PermissionDeniedException;
import com.francisco.blog.exception.ResourceNotFoundException;
import com.francisco.blog.repository.EditPostCommentRepository;
import com.francisco.blog.repository.ExcludePostCommentRepository;
import com.francisco.blog.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final EditPostCommentRepository editPostCommentRepository;
    private final ExcludePostCommentRepository excludePostCommentRepository;

    public Page<PostResponse> showAll(Pageable pageable, Long idUser, Boolean showDesactive){
        User userEntity = userService.showUserById(idUser);
        Page<Post> page = userEntity.getUserRole().contains(UserRole.ROLE_ADMIN) && showDesactive ? postRepository.findAll(pageable) :
                postRepository.findAllByIsActiveTrue(pageable);
        return page.map(
                p -> {
                    User user = p.getUser();
                    List<String> paths = p.getArchivePaths().stream()
                            .map(ArchivePath::getArchivePath)
                            .toList();
                    return new PostResponse(
                            p.getTitle(),
                            p.getDescription(),
                            p.getIsActive(),
                            paths,
                            user.getUsername(),
                            user.getPhotoPerfil()
                    );
                });
    }

    public Page<MyPostResponse> showMyPosts(Long id, Pageable pageable){
        User user = userService.showUserById(id);
        Page<Post> post = postRepository.findAllByUser(user, pageable);
        return post.map(
                p -> {
                    List<String> paths = p.getArchivePaths().stream()
                            .map(ArchivePath::getArchivePath)
                            .toList();
                    return new MyPostResponse(
                            p.getTitle(),
                            p.getDescription(),
                            p.getIsActive(),
                            paths
                    );
                }
        );
    }

    @Transactional
    public PostResponse createPost(Long id, PostRequest postRequest){
        Post post = new Post();
        post.setTitle(postRequest.title());
        post.setDescription(postRequest.description());
        User user =  userService.showUserById(id);
        post.setUser(user);
        post.setIsActive(true);
        if (postRequest.path() != null && !postRequest.path().isEmpty()){
            List<ArchivePath> archives = postRequest.path().stream().map(p -> {
                ArchivePath archive = new ArchivePath();
                archive.setArchivePath(p);
                archive.setPost(post);
                return archive;
            }).toList();
            post.setArchivePaths(archives);
        }
        postRepository.save(post);
        return new PostResponse(post.getTitle(), post.getDescription(),post.getIsActive(),postRequest.path(), post.getUser().getUsername(), post.getUser().getPhotoPerfil());
    }

        public MyPostResponse editPost(MyPostRequest postRequest, Long id, Long idEditor){
            Post post = postRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Post não encontrado")
            );
            User user = userService.showUserById(idEditor);
            if (!idEditor.equals(post.getUser().getId()) && !user.getUserRole().contains(UserRole.ROLE_ADMIN)) {
                throw new PermissionDeniedException(("Você não tem permissão para este Post!" + "Ele pertence a " + post.getUser().getUsername()));
            }
                EditPostComment editPostComment = new EditPostComment();

                editPostComment.setUser(user);
                editPostComment.setPost(post);

                if (postRequest.title() != null){
                    editPostComment.setOldTitle(post.getTitle());
                    post.setTitle(postRequest.title());
                }
                if (postRequest.description() != null){
                    editPostComment.setOldDescription(post.getDescription());
                    post.setDescription(postRequest.description());
                }
                if (postRequest.isActive() != null){
                    post.setIsActive(postRequest.isActive());
                }
                editPostCommentRepository.save(editPostComment);
                if (postRequest.path() != null && !postRequest.path().isEmpty()){
                    List<ArchivePath> archives = postRequest.path().stream().map(p -> {
                        ArchivePath archiveNew = new ArchivePath();
                        archiveNew.setArchivePath(p);
                        archiveNew.setPost(post);
                        return archiveNew;
                    }).toList();
                    post.getArchivePaths().forEach(p ->{
                                p.setOldPostId(editPostComment);
                                p.setPost(null);
                            });
                    post.getArchivePaths().addAll(archives);
                }
            postRepository.save(post);
            List<String> paths = post.getArchivePaths().stream()
                    .map(ArchivePath::getArchivePath)
                    .toList();
            return new MyPostResponse(
                    post.getTitle(),
                    post.getDescription(),
                    post.getIsActive(),
                    paths
            );
        }
        public MyPostResponse softDeletePostById(Long excludorId,Long id, String reason){
            Post post = postRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Post não encontrado")
            );
            User user = userService.showUserById(excludorId);
            ExcludePostComment excludePostComment = new ExcludePostComment();
            if (post.getUser().getId().equals(excludorId)  && !user.getUserRole().contains(UserRole.ROLE_ADMIN)){
                throw new PermissionDeniedException("Você não pode excluir o Post de: " + post.getUser().getUsername());
            }
            excludePostComment.setUser(user);
            excludePostComment.setPost(post);
            if (reason != null && !reason.isEmpty()){
                excludePostComment.setExcludeReason(reason);
            }
            post.setIsActive(false);
            excludePostCommentRepository.save(excludePostComment);
            postRepository.save(post);

            List<String> archives = post.getArchivePaths().stream()
                    .map(ArchivePath::getArchivePath)
                    .toList();

            return new MyPostResponse(
                    post.getTitle(),
                    post.getDescription(),
                    post.getIsActive(),
                    archives
            );

        }


















}
