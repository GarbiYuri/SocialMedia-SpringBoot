package com.francisco.blog.repository;

import com.francisco.blog.entitys.ExcludePostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcludePostCommentRepository extends JpaRepository<ExcludePostComment, Long> {

}
