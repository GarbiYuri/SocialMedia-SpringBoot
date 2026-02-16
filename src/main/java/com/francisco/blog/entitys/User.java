package com.francisco.blog.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "create_time", insertable = false)
    private ZonedDateTime createTime;

    @Column( nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole  role;

    @Column(name = "photo_perfil")
    private String photoPerfil;

    @Column(name = "is_Active")
    private boolean isActive = true;





}


