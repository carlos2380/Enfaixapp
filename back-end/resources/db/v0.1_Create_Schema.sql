CREATE TABLE users (
  id       INTEGER AUTO_INCREMENT,
  name     VARCHAR(20)        NOT NULL,
  surnames VARCHAR(100)       NOT NULL,
  email    VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(256) 	  NOT NULL,
  PRIMARY KEY (id)
) CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE colles (
  id       INTEGER AUTO_INCREMENT,
  name     VARCHAR(100) NOT NULL,
  uni      BOOLEAN     NOT NULL,
  description TEXT,
  phoneNumber VARCHAR(13),
  email VARCHAR(100),
  web VARCHAR(100),
  address VARCHAR(512),
  color    NVARCHAR(7),
  img_path VARCHAR(255),
  PRIMARY KEY (id)
) CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE belongsTo (
  id_user  INTEGER NOT NULL,
  id_colla INTEGER NOT NULL,
  PRIMARY KEY (id_user, id_colla),
  CONSTRAINT fk_belongsTo_users FOREIGN KEY (id_user) REFERENCES users (id),
  CONSTRAINT fk_belongTo_colles FOREIGN KEY (id_colla) REFERENCES colles (id)
) CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE follows (
  id_user  INTEGER NOT NULL,
  id_colla INTEGER NOT NULL,
  PRIMARY KEY (id_user, id_colla),
  CONSTRAINT fk_follows_users FOREIGN KEY (id_user) REFERENCES users (id),
  CONSTRAINT fk_follows_colles FOREIGN KEY (id_colla) REFERENCES colles (id)
) CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE tokens (
  id_user INTEGER,
  token VARCHAR(256) UNIQUE,
	PRIMARY KEY (id_user),
	CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES users (id)
) CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE events (
  id INTEGER AUTO_INCREMENT,
  title VARCHAR(256) NOT NULL,
  description TEXT,
  path VARCHAR(256),
  date DATE NOT NULL,
  address VARCHAR(512),
  id_user INTEGER NOT NULL,
  id_colla INTEGER NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_events_users FOREIGN KEY (id_user) REFERENCES users (id),
  CONSTRAINT fk_events_colles FOREIGN KEY (id_colla) REFERENCES colles (id)
) CHARACTER SET utf8 COLLATE utf8_general_ci;


CREATE TABLE practice (
  id INTEGER AUTO_INCREMENT,
  date DATETIME NOT NULL,
  description text,
  address VARCHAR(256),
  id_colla INTEGER,
  PRIMARY KEY(id),
  CONSTRAINT fk_practice_colla FOREIGN KEY (id_colla) REFERENCES colles (id)
) CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE attendants (
  id_user INTEGER,
  id_practice INTEGER,
  PRIMARY KEY (id_user, id_practice),
  CONSTRAINT fk_attendants_practices FOREIGN KEY (id_practice) REFERENCES practice (id),
  CONSTRAINT fk_attendants_user FOREIGN KEY (id_user) REFERENCES users (id)
) CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE admin_colles(
  id_user INTEGER NOT NULL,
  id_colla INTEGER NOT NULL,
  PRIMARY KEY (id_user, id_colla),
  CONSTRAINT fk_admin_colles_user FOREIGN KEY (id_user) REFERENCES users(id),
  CONSTRAINT fk_admin_colles_colla FOREIGN KEY (id_colla) REFERENCES colles(id)
) CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE performances(
  id INTEGER AUTO_INCREMENT,
  title VARCHAR(512) NOT NULL,
  date DATETIME NOT NULL,
  PRIMARY KEY (id)
) CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE results(
  id_performance INTEGER NOT NULL,
  id_colla INTEGER NOT NULL,
  round INTEGER NOT NULL,
  try INTEGER,
  castell VARCHAR(256),
  result ENUM('Intent', 'Intent Desmuntat', 'Carregat', 'Descarregat'),
  PRIMARY KEY (id_performance, id_colla, round),
  CONSTRAINT fk_admin_colla FOREIGN KEY (id_colla) REFERENCES colles(id),
  CONSTRAINT fk_admin_performances FOREIGN KEY (id_performance) REFERENCES performances(id)
) CHARACTER SET utf8 COLLATE utf8_general_ci;
