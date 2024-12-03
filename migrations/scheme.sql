create table if not exists users (
    id uuid primary key default gen_random_uuid(),
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    email varchar(255) not null unique,
    phone_number varchar(255) not null unique,
    about_me text,
    password varchar(255) not null,
    role varchar(255) not null,
    date_of_birth timestamp with time zone,
    photo text,
    barbershop_id uuid,
    grade text
);

insert into users (first_name, last_name, email, phone_number, password, role, date_of_birth, photo, barbershop_id,
                   grade)
values ('admin',
        'admin',
        '-',
        '-',
        '$2a$12$Cnb94Jz4OBZLOod8RdL8EuCyLxyqpRSpRmHIRgQt/CtvlvWGrow1u',
        'ADMIN',
        null,
        null,
        null,
        null
       );