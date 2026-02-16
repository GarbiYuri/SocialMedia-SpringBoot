create table archive_path(
  id bigserial primary key ,
  archive_path varchar(255),
  post_id bigint,
  comment_id bigint,
  constraint fk_post_id foreign key (post_id) references posts(id),
  constraint fk_comment_id foreign key (comment_id) references comments(id),
  constraint check_origin CHECK ( (post_id is not null and comment_id is null ) or  (post_id is null and comment_id is not null ))

);

create index idx_archive_path_post_id on archive_path(post_id);
create index idx_archive_path_comment_id on archive_path(comment_id);