package com.francisco.blog.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "archive_path")
public class ArchivePath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "archive_path")
    private String archivePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    //Olds (para SoftDelete)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "old_post_id")
    private EditPostComment oldPostId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "old_comment_id")
    private EditPostComment oLdCommentId;
}
