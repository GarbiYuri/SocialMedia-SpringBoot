create table exclude_user(
    id bigserial primary key,
    excluded_by bigint not null ,
    excluded_user bigint not null ,
    excluded_at timestamp with time zone default CURRENT_TIMESTAMP,
    exclude_for_time timestamp with time zone default (CURRENT_TIMESTAMP + INTERVAL  '30 days') ,
    exclude_reason varchar(50),
    constraint fk_excluded_by foreign key (excluded_by) references users(id),
    constraint fk_excluded_user foreign key (excluded_user) references users(id)
);
create index idx_exclude_user_id on exclude_user(excluded_by);
create index idx_user_excluded_id on exclude_user(excluded_user);