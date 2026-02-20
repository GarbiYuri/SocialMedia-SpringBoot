package com.francisco.blog.repository;

import com.francisco.blog.entitys.ArchivePath;
import com.francisco.blog.entitys.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArchivePathRepository extends JpaRepository<ArchivePath, Long> {

    List<ArchivePath> findAllByPost(Post post);
}
