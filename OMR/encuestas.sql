DROP DATABASE encuestas;
CREATE DATABASE encuestas;

USE encuestas;

CREATE TABLE encuestas1 (
  id INT NOT NULL AUTO_INCREMENT,
  educacion VARCHAR(255),
  poblacion VARCHAR(255),
  ayuda VARCHAR(255),
  paredes VARCHAR(255),
  comerciales VARCHAR(255),
  mercados VARCHAR(255),
  salud VARCHAR(255),
  transporte VARCHAR(255),
  parroquia VARCHAR(255),
  piso VARCHAR(255),
  agua VARCHAR(255),
  excretas VARCHAR(255),
  techo VARCHAR(255),
  calienta VARCHAR(255),
  iluminacion VARCHAR(255),
  PRIMARY KEY(id),
  UNIQUE(id)
)