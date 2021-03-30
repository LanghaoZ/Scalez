CREATE DATABASE scalezdb IF NOT EXISTS;

CREATE USER 'scalez'@'%' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON scalezdb.* TO 'scalez'@'%';

FLUSH PRIVILEGES;