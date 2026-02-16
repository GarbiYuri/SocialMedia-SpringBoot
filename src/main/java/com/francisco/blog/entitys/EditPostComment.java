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
@Table(name = "edit_post_comment")
public class EditPostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edited_by")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edited_post", updatable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edited_comment", updatable = false)
    private Comment comment;

    @Column(name = "edited_at", insertable = false)
    private ZonedDateTime editedAt;

    @Column(name = "old_title")
    private String oldTitle;

    @Column(name = "old_description")
    private String oldDescription;
}
