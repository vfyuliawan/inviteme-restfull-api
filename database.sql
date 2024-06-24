CREATE DATABASE invite_me


USE invite_me


CREATE TABLE users
(
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    token VARCHAR(100),
    token_expired_at BIGINT,
    PRIMARY KEY(username),
    UNIQUE (token)
)ENGINE InnoDB;


SELECT * FROM users

DESC users

DROP TABLE IF EXISTS Projects;


CREATE TABLE projects (
    id_project VARCHAR(100) PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    title VARCHAR(100) NOT NULL,
    FOREIGN KEY fk_users_projects (username) REFERENCES users(username)
)ENGINE InnoDB;

SELECT * FROM projects

DESC projects

DROP TABLE IF EXISTS Hero;


CREATE TABLE hero (
    id VARCHAR(255) PRIMARY KEY,
    id_project VARCHAR(255),
    title VARCHAR(255) NOT NULL,
    img VARCHAR(255),
    date DATETIME,
    is_show BOOLEAN NOT NULL,
    FOREIGN KEY fk_projects_hero (id_project) REFERENCES projects(id_project)
)ENGINE InnoDB;


SELECT * FROM Hero

DESC Hero


CREATE TABLE home (
    id VARCHAR(255) PRIMARY KEY,
    id_project VARCHAR(255),
    title VARCHAR(255) NOT NULL,
    quotes VARCHAR(255) NOT NULL,
    img VARCHAR(255),
    is_show BOOLEAN NOT NULL,
    FOREIGN KEY fk_projects_home (id_project) REFERENCES projects(id_project)
)ENGINE InnoDB;


SELECT * FROM home

DESC home

CREATE TABLE cover (
    id VARCHAR(255) PRIMARY KEY,
    id_project VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    img VARCHAR(255),
    date DATETIME,
    is_show BOOLEAN NOT NULL,
    FOREIGN KEY fk_projects_cover (id_project) REFERENCES projects(id_project)
)ENGINE InnoDB;


SELECT * FROM cover

DESC cover


CREATE TABLE theme (
    id VARCHAR(255) PRIMARY KEY,
    id_project VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL,
    alamat VARCHAR(255) NOT NULL,
    embeded VARCHAR(255) NOT NULL,
    theme VARCHAR(255) NOT NULL,
    music VARCHAR(255) NOT NULL,
    FOREIGN KEY fk_projects_theme (id_project) REFERENCES projects(id_project)
)ENGINE InnoDB;


SELECT * FROM theme

DESC theme

DROP TABLE IF EXISTS acara;



CREATE TABLE acara (
    id VARCHAR(255) PRIMARY KEY,
    id_project VARCHAR(255) NOT NULL,
    title_akad VARCHAR(255) NOT NULL,
    map_akad VARCHAR(255),
    lokasi_akad VARCHAR(255),
    img_akad VARCHAR(255),
    date_akad DATETIME,
    title_resepsi VARCHAR(255),
    map_resepsi VARCHAR(255),
    lokasi_resepsi VARCHAR(255),
    img_resepsi VARCHAR(255),
    date_resepsi DATETIME,
    FOREIGN KEY fk_projects_acara (id_project) REFERENCES projects(id_project)
)ENGINE InnoDB;


SELECT * FROM acara

DESC acara



-- ////////////////////////////////////////////////////////////////////////




CREATE TABLE contacts(
    id VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100),
    phone VARCHAR(100),
    email VARCHAR(100),
    PRIMARY KEY(id),
    FOREIGN KEY fk_users_contacts (username) REFERENCES users(username)
)ENGINE InnoDB;


SELECT * FROM contacts

DESC contacts



CREATE TABLE addresses(
    id VARCHAR(100) NOT NULL,
    contact_id VARCHAR(100) NOT NULL,
    street VARCHAR(100),
    city VARCHAR(100),
    province VARCHAR(100),
    country VARCHAR(100) NOT NULL,
    postal_code VARCHAR(10),
    PRIMARY KEY (id),
    FOREIGN KEY fk_contacts_addresses (contact_id) REFERENCES contacts(id)
)ENGINE InnoDB;


SELECT * FROM addresses

DESC addresses


