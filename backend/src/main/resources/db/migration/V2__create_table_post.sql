create table posts(
    id bigserial primary key,
    create_time timestamp with time zone default CURRENT_TIMESTAMP,
    posted_by bigint not null ,
    title varchar(45) not null ,
    description varchar(255) not null ,
    is_active boolean DEFAULT TRUE,
    constraint fk_posted_by foreign key (posted_by) references users(id)
);

CREATE INDEX idx_posts_posted_by ON posts(posted_by);
