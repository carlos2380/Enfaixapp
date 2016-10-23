CREATE DATABASE enfaixappDB CHARSET 'UTF-8';

CREATE USER 'enfaixapp_server'@'localhost' IDENTIFIED BY '';

GRANT ALL ON enfaixappDB.* TO 'enfaixapp_server'@'localhost';