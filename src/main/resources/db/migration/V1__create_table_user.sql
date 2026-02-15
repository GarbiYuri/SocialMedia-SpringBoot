CREATE TYPE user_role AS ENUM ('ADMIN', 'USER');

CREATE TABLE users(
    id bigserial primary key,
    username varchar(255) not null,
    email varchar(255) unique not null ,
    password varchar(255)not null ,
    create_time timestamp with time zone default CURRENT_TIMESTAMP,
    role user_role DEFAULT 'USER',
    about varchar(255),
    photo_perfil varchar(255),
    is_active boolean DEFAULT TRUE
);

CREATE INDEX idx_users_username ON users(username);