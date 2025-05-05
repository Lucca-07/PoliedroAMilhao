CREATE DATABASE PoliedroAMilhao;
USE PoliedroAMilhao;

-- CRIANDO AS TABELAS DO DIAGRAMA --
CREATE TABLE Aluno (
    Id_Aluno INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(45),
    Email VARCHAR(50),
    Turno CHAR(1),
    Série CHAR(1),
    Classe CHAR(1));

CREATE TABLE Materias (
    Id_Materia INT PRIMARY KEY AUTO_INCREMENT,
    Nome_Materia VARCHAR(20),
    Material_Físico CHAR(1),
    Aluno_Id_Aluno INT,
    FOREIGN KEY (Aluno_Id_Aluno) REFERENCES Aluno(Id_Aluno));

CREATE TABLE Premiacoes (
    Id_Premiacao INT PRIMARY KEY AUTO_INCREMENT,
    Quantidade TINYINT,
    Tipo_Premiacao VARCHAR(25));

CREATE TABLE Perguntas_predefinidas (
    Id_Pergunta INT PRIMARY KEY AUTO_INCREMENT,
    Nivel_Dificuldade VARCHAR(20),
    Tipo_Pergunta VARCHAR(45),
    Pergunta TEXT,
    Materias_Id_Materia INT,
    Premiacao_Id_Premiacao INT,
    FOREIGN KEY (Materias_Id_Materia) REFERENCES Materias(Id_Materia), 
    FOREIGN KEY (Premiacao_Id_Premiacao) REFERENCES Premiacoes(Id_Premiacao) );

CREATE TABLE Aluno_Materia (
    Id_Aluno_Materia INT PRIMARY KEY AUTO_INCREMENT,
    Aluno_Id_Aluno INT,
    Materias_Id_Materia INT,
    FOREIGN KEY (Aluno_Id_Aluno) REFERENCES Aluno(Id_Aluno) ,
    FOREIGN KEY (Materias_Id_Materia) REFERENCES Materias(Id_Materia));
    
DESCRIBE perguntas_predefinidas;

CREATE TABLE perguntas_criadas (Id_Pergunta INT PRIMARY KEY AUTO_INCREMENT,
    Nivel_Dificuldade VARCHAR(20),
    Tipo_Pergunta VARCHAR(45),
    Pergunta TEXT,
    Materias_Id_Materia INT,
    Premiacao_Id_Premiacao INT,
    FOREIGN KEY (Materias_Id_Materia) REFERENCES Materias(Id_Materia), 
<<<<<<< HEAD
    FOREIGN KEY (Premiacao_Id_Premiacao) REFERENCES Premiacoes(Id_Premiacao) ON DELETE CASCADE ON UPDATE CASCADE);
    
     ALTER TABLE Premiacoes RENAME COLUMN Quantidade TO Quantidade_Dinheiro;
     ALTER TABLE Aluno ADD CONSTRAINT fk_pontuacao_aluno FOREIGN KEY (Pontuacao) REFERENCES Premiacoes(Id_Premiacao) ON DELETE RESTRICT ON UPDATE CASCADE;
	 ALTER TABLE aluno ADD COLUMN Pontuacao INT;
     
   
=======
    FOREIGN KEY (Premiacao_Id_Premiacao) REFERENCES Premiacoes(Id_Premiacao) ON DELETE CASCADE ON UPDATE CASCADE);
>>>>>>> 749714002d304dcfc29b227c3bd8d35e58194576
