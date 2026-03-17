
create table comments(
    id BIGSERIAL primary key,
    commented_by bigint not null ,
    comment_in_post bigint not null ,
    comment varchar(255) not null ,
    commented_at timestamp with time zone default CURRENT_TIMESTAMP,
    is_active boolean DEFAULT TRUE,
    constraint fk_commented_by foreign key (commented_by) references users(id),
    constraint fk_comment_in_post foreign key (comment_in_post) references posts(id)
);
create index idx_comment_commented_by on comments(commented_by);
create index idx_comment_in_post on comments(comment_in_post);