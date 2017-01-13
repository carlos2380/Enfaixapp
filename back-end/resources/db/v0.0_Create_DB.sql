CREATE DATABASE enfaixappDB CHARACTER SET 'UTF8';

CREATE USER 'enfaixapp_server'@'localhost' IDENTIFIED BY '';

GRANT ALL ON enfaixappDB.* TO 'enfaixapp_server'@'localhost';