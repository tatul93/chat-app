create table if not exists users (
	id bigserial not null constraint user_pkey primary key,
	user_name varchar(255) not null,
	full_name varchar(255) not null,
	password varchar(255) not null
);

create sequence if not exists USERS_ID_SEC;


INSERT INTO public.users (id, user_name, full_name, password) VALUES (1, 'john1', 'John Smith', '$2a$12$tTfUgMjSY./Jp0JbrQIKA.Axdiul9kTBsFiyh4SLGcEdlWfsSft1u'); -- password: test1
INSERT INTO public.users (id, user_name, full_name, password) VALUES (2, 'user1', 'Joe Doe', '$2a$12$tTfUgMjSY./Jp0JbrQIKA.Axdiul9kTBsFiyh4SLGcEdlWfsSft1u'); -- password: test1
INSERT INTO public.users (id, user_name, full_name, password) VALUES (3, 'user2', 'Jack Miller', '$2a$12$tTfUgMjSY./Jp0JbrQIKA.Axdiul9kTBsFiyh4SLGcEdlWfsSft1u'); -- password: test1
