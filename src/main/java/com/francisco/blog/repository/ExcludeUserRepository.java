package com.francisco.blog.repository;

import com.francisco.blog.entitys.ExcludeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExcludeUserRepository extends JpaRepository<ExcludeUser, Long> {

    Optional<ExcludeUser> findTopByExcludedUser_IdOrderByIdDesc(Long id);
}
