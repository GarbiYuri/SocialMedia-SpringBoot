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
@Table(name = "exclude_post_comment")
public class ExcludePostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excluded_by")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excluded_post")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excluded_comment")
    private Comment comment;

    @Column(name = "exclude_reason")
    private String excludeReason;

    @Column(name = "excluded_at", insertable = false)
    private ZonedDateTime excludedAt;
}
