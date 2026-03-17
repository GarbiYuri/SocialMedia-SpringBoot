create table user_roles(
    user_id bigint null ,
    user_edit_id bigint null ,
    create_at timestamp with time zone default current_timestamp,
    role varchar(50),
    constraint fk_user foreign key (user_id) references users (id) on delete cascade,
    constraint fk_user_edit foreign key (user_edit_id) references edit_user (id) on delete cascade
);
create index idx_user_role_id on user_roles(user_id);
create index idx_user_edit_role_id on user_roles(user_edit_id);