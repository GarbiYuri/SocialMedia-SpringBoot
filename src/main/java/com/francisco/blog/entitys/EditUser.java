package com.francisco.blog.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_edit_id"))
    @Enumerated(EnumType.STRING)
    @Column( name = "role")
    private Set<UserRole> oldRole = new LinkedHashSet<>();

    @Column(name = "old_about")
    private String oldAbout;

    @Column(name = "old_photo_perfil")
    private String oldPhotoPerfil;
}
