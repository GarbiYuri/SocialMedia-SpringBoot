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
@Table(name = "edit_user")
public class EditUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edited_by")
    private User EditorUser;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edited_user")
    private User EditedUser;

    @Column(name = "edited_at", insertable = false)
    private ZonedDateTime editedAt;

    @Column(name = "old_username")
    private String oldUsername;

    @Column(name = "old_email")
    private String OldEmail;

    @Column(name = "old_password")
    private String oldPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_role")
    private UserRole oldRole;

    @Column(name = "old_about")
    private String oldAbout;

    @Column(name = "old_photo_perfil")
    private String oldPhotoPerfil;
}
