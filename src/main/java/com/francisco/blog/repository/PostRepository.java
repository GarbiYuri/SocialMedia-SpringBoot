package com.francisco.blog.repository;

import com.francisco.blog.entitys.Post;
import com.francisco.blog.entitys.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByIsActiveTrue(Pageable pageable);

    Page<Post> findAllByUser(User user, Pageable pageable);

}
