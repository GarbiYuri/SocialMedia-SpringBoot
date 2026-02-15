create table edit_post_comment(
    id bigserial primary key,
    edited_by bigint not null ,
    edited_post bigint,
    edited_comment bigint,
    edited_at timestamp with time zone default CURRENT_TIMESTAMP,
    constraint fk_edited_by foreign key (edited_by) references users(id),
    constraint fk_edited_post foreign key (edited_post) references posts(id),
    constraint fk_edited_comment foreign key (edited_comment) references comments(id),
    constraint check_origin check ( (edited_post is not null and edited_comment is null ) or (edited_post is null and edited_comment is not null))
);

create index idx_edit_post_comment_post_id on edit_post_comment(edited_post);
create index idx_edit_post_comment_comment_id on edit_post_comment(edited_comment);