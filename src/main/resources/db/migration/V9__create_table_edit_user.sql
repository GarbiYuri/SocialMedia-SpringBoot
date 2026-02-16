create table edit_user(
    id bigserial primary key,
    edited_by bigint,
    edited_user bigint,
    edited_at timestamp with time zone default current_timestamp,
    old_username varchar(255) ,
    old_email varchar(255),
    old_password varchar(255) ,
    old_role user_role,
    old_about varchar(255),
    old_photo_perfil varchar(255),

    constraint fk_edited_by foreign key (edited_by) references users(id),
    constraint fk_edited_user foreign key (edited_user) references users(id)
);
create index idx_edit_user_id on edit_user(edited_by);
create index idx_edit_user_edited_id on edit_user(edited_user);
