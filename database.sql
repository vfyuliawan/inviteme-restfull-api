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

ALTER TABLE users
MODIFY token VARCHAR(512);

ALTER TABLE users
ADD COLUMN role VARCHAR(50) NOT NULL;


SELECT * FROM users

DESC users

DROP TABLE IF EXISTS Projects;


CREATE TABLE projects (
    id_project VARCHAR(100) PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    title VARCHAR(100) NOT NULL,
    FOREIGN KEY fk_users_projects (username) REFERENCES users(username)
)ENGINE InnoDB;

ALTER TABLE projects
ADD COLUMN countdown DATETIME AFTER title;

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

ALTER TABLE hero MODIFY img LONGTEXT;



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


ALTER TABLE home MODIFY img LONGTEXT;


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

ALTER TABLE cover MODIFY img LONGTEXT;



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

ALTER TABLE acara MODIFY img_akad LONGTEXT;
ALTER TABLE acara MODIFY img_resepsi LONGTEXT;



SELECT * FROM acara

DESC acara

CREATE TABLE braid_info (
    id VARCHAR(255) PRIMARY KEY,
    id_project VARCHAR(255) NOT NULL,
    male_name VARCHAR(255),
    male_mom VARCHAR(255),
    male_dad VARCHAR(255),
    male_img VARCHAR(255),
    female_name VARCHAR(255),
    female_mom VARCHAR(255),
    female_dad VARCHAR(255),
    female_img VARCHAR(255),
    is_show BOOLEAN NOT NULL,
    FOREIGN KEY fk_projects_braid_info (id_project) REFERENCES projects(id_project)
)ENGINE InnoDB;

ALTER TABLE braid_info MODIFY male_img LONGTEXT;
ALTER TABLE braid_info MODIFY female_img LONGTEXT;



SELECT * FROM braid_info

DESC braid_info


CREATE TABLE story (
    id_story VARCHAR(255) PRIMARY KEY,
    id_project VARCHAR(255) NOT NULL,
    is_show BOOLEAN,
    FOREIGN KEY fk_projects_story (id_project) REFERENCES projects(id_project)
)ENGINE InnoDB;


SELECT * FROM story

DESC story

CREATE TABLE stories (
    id VARCHAR(255) PRIMARY KEY,
    id_story VARCHAR(255),
    title VARCHAR(255),
    text TEXT,
    img LONGTEXT,
    date TIMESTAMP,
    CONSTRAINT fk_story
        FOREIGN KEY (id_story)
        REFERENCES story(id_story)
)ENGINE InnoDB;



SELECT * FROM stories

DESC stories




CREATE TABLE galery (
    id_galery VARCHAR(255) PRIMARY KEY,
    id_project VARCHAR(255) NOT NULL,
    is_show BOOLEAN,
    FOREIGN KEY fk_projects_galery (id_project) REFERENCES projects(id_project)
)ENGINE InnoDB;



SELECT * FROM galery

DESC galery


CREATE TABLE galeries (
    id VARCHAR(255) PRIMARY KEY,
    id_galery VARCHAR(255),
    img LONGTEXT,
    CONSTRAINT fk_galery
        FOREIGN KEY (id_galery)
        REFERENCES galery(id_galery)
)ENGINE InnoDB;


SELECT * FROM galeries

DESC galeries




CREATE TABLE gift (
    id_gift VARCHAR(255) PRIMARY KEY,
    id_project VARCHAR(255) NOT NULL,
    is_show BOOLEAN,
    FOREIGN KEY fk_projects_gift (id_project) REFERENCES projects(id_project)
)ENGINE InnoDB;


SELECT * FROM gift

DESC gift

CREATE TABLE gifts (
    id VARCHAR(255) PRIMARY KEY,
    id_gift VARCHAR(255),
    image LONGTEXT,
    name VARCHAR(255),
    norek VARCHAR(255),
    CONSTRAINT fk_gift FOREIGN KEY (id_gift) REFERENCES gift(id_gift)
)ENGINE InnoDB;

ALTER TABLE gifts CHANGE COLUMN norek no_rek VARCHAR(255);


SELECT * FROM gifts

DESC gifts



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




