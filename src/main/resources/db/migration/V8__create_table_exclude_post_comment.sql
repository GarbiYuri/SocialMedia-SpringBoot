create table exclude_post_comment(
    id bigserial primary key,
    excluded_by bigint not null,
    excluded_post bigint,
    excluded_comment bigint,
    exclude_reason varchar(255),
    excluded_at timestamp with time zone default CURRENT_TIMESTAMP,
    constraint fk_user_id foreign key (excluded_by) references users(id),
    constraint fk_post_id foreign key (excluded_post) references posts(id),
    constraint fk_comment_id foreign key (excluded_comment) references comments(id),
    constraint check_origin check ( (excluded_post is not null and excluded_comment is null ) or (excluded_post is null and excluded_comment is not null ))
);
create index idx_excluded_by on exclude_post_comment(excluded_by);
create index idx_excluded_post on exclude_post_comment(excluded_post);
create index idx_excluded_comment on exclude_post_comment(excluded_comment);