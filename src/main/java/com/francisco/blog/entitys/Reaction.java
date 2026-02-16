package com.francisco.blog.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reactions")
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name = "reaction_post")
    private ReactionPost reactionPost;

    @Enumerated(EnumType.STRING)
    @Column(name = "reaction_comment")
    private ReactionComment reactionComment;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reacted_by")
    private User user;

    @Column(name = "reacted_at", insertable = false)
    private ZonedDateTime reactedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_post")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comment")
    private Comment comment;


}
