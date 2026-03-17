create table reposts(
    id bigserial primary key,
    reposted_at timestamp with time zone default CURRENT_TIMESTAMP,
    id_post bigint not null,
    reposted_by bigint not null,
    comment varchar(255),
    constraint fk_reposted_by foreign key (reposted_by) references users(id),
    constraint fk_id_post foreign key (id_post) references posts(id),
    constraint uq_user_repost unique (id_post, reposted_by)
);
create index idx_reposts_user_id on reposts(reposted_by);
create index idx_reposts_post_id on reposts(id_post);