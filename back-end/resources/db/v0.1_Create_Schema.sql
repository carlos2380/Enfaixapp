CREATE TABLE users (
  id       INTEGER AUTO_INCREMENT,
  name     VARCHAR(20)        NOT NULL,
  surnames VARCHAR(100)       NOT NULL,
  email    VARCHAR(50) UNIQUE NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE colles (
  id       INTEGER AUTO_INCREMENT,
  name     VARCHAR(20) NOT NULL,
  uni      BOOLEAN     NOT NULL,
  color    NVARCHAR(7),
  img_path VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE belongsTo (
  id_user  INTEGER NOT NULL,
  id_colla INTEGER NOT NULL,
  PRIMARY KEY (id_user, id_colla),
  CONSTRAINT fk_belongsTo_users FOREIGN KEY (id_user) REFERENCES users (id),
  CONSTRAINT fk_belongTo_colles FOREIGN KEY (id_colla) REFERENCES colles (id)
);

CREATE TABLE follows (
  id_user  INTEGER NOT NULL,
  id_colla INTEGER NOT NULL,
  PRIMARY KEY (id_user, id_colla),
  CONSTRAINT fk_follows_users FOREIGN KEY (id_user) REFERENCES users (id),
  CONSTRAINT fk_follows_colles FOREIGN KEY (id_colla) REFERENCES colles (id)
);