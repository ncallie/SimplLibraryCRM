create extension if not exists pgcrypto;

update person set password = crypt(password, gen_salt('bf', 8))