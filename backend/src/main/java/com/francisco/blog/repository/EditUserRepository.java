package com.francisco.blog.repository;

import com.francisco.blog.entitys.EditUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditUserRepository extends JpaRepository<EditUser, Long> {
}
