package com.francisco.blog.repository;

import com.francisco.blog.entitys.ExcludeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcludeUserRepository extends JpaRepository<ExcludeUser, Long> {

}
