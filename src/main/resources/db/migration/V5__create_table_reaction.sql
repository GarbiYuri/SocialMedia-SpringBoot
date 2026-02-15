create type reaction_post as enum ('LIKE', 'LOVE', 'HAHA', 'HATE');
create type reaction_comment as enum('LIKE');

create table reactions(
    id bigserial primary key,
    reaction_post reaction_post,
    reaction_comment reaction_comment,
    reacted_by bigint not null ,
    reacted_at timestamp with time zone default CURRENT_TIMESTAMP,
    id_post bigint,
    id_comment bigint,
    constraint fk_react_by foreign key (reacted_by) references users(id),
    constraint fk_id_post foreign key (id_post) references posts(id),
    constraint fk_id_comment foreign key (id_comment) references comments(id),
    constraint check_origin check ( (id_post is not null and id_comment is null ) or (id_post is null and id_comment is not null ) )
);
create index idx_reactions_id_user on reactions(reacted_by);
create index idx_reactions_id_post on reactions(id_post);
create index idx_reactions_id_comment on reactions(id_comment);
