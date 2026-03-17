package com.francisco.blog.repository;

import com.francisco.blog.entitys.EditPostComment;
import com.francisco.blog.entitys.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EditPostCommentRepository extends JpaRepository<EditPostComment,Long> {
}
