CREATE DATABASE locadora DEFAULT CHARACTER SET utf8 ;

USE locadora;

CREATE TABLE usuario (
  id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(60) NOT NULL,
  senha VARCHAR(255) NOT NULL,
  nome VARCHAR(60) NOT NULL,
  acess_token VARCHAR(255) 
);

CREATE TABLE filme (
  id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(60) NOT NULL,
  diretor VARCHAR(60) NOT NULL,
  quantidade INT(3),
  quantidade_disponivel INT(3)
);

CREATE TABLE locacao (
  id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  id_filme INT(10) UNSIGNED NOT NULL,
  id_usuario INT(10) UNSIGNED NOT NULL,
  situacao VARCHAR(1) NOT NULL -- Locado, Devolvido  
);

ALTER TABLE locacao ADD CONSTRAINT fk_filme FOREIGN KEY (id_filme) REFERENCES filme (id) ;
ALTER TABLE locacao ADD CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id) ;