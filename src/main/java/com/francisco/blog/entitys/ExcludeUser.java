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
@Table(name = "exclude_user")
public class ExcludeUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excluded_by")
    private User exluderUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excluded_user")
    private User excludedUser;

    @Column(name = "excluded_at", insertable = false)
    private ZonedDateTime excludedAt;

    @Column(name = "exclude_for_time")
    private ZonedDateTime excludeForTime;

    @Column(name = "exclude_reason")
    private  String excludeReason;

}
