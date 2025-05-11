CREATE DATABASE final_PoliedroAMilhao;
USE final_PoliedroAMilhao;

-- Mantida a tabela aluno (Adição: FK de premiações) --
CREATE TABLE Aluno (
    Id_Aluno INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(45),
    Email VARCHAR(50),
    Turno CHAR(1),
    Série CHAR(1),
    Classe CHAR(1),
    Pontuacao INT,
    CONSTRAINT fk_pontuacao_aluno FOREIGN KEY (Pontuacao) REFERENCES Premiacoes(Id_Premiacao) 
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Mantida tabela de Premiações --
CREATE TABLE Premiacoes (
    Id_Premiacao INT PRIMARY KEY AUTO_INCREMENT,
    Quantidade_Dinheiro TINYINT,
    Tipo_Premiacao VARCHAR(25)
);

-- Mantida a tabela de Matérias --
CREATE TABLE Materias (
    Id_Materia INT PRIMARY KEY AUTO_INCREMENT,
    Nome_Materia VARCHAR(20),
    Material_Físico CHAR(1),
    Aluno_Id_Aluno INT,
    FOREIGN KEY (Aluno_Id_Aluno) REFERENCES Aluno(Id_Aluno) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Tabela de relacionamento N:N entre Aluno e Matéria (Mantida tabela para ranking) --
CREATE TABLE Aluno_Materia (
    Id_Aluno_Materia INT PRIMARY KEY AUTO_INCREMENT,
    Aluno_Id_Aluno INT,
    Materias_Id_Materia INT,
    FOREIGN KEY (Aluno_Id_Aluno) REFERENCES Aluno(Id_Aluno) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (Materias_Id_Materia) REFERENCES Materias(Id_Materia) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Criando tabela de Perguntas --
CREATE TABLE Perguntas (
    Id_Pergunta INT PRIMARY KEY AUTO_INCREMENT,
    Enunciado TEXT NOT NULL,
    Id_Materia INT,
    Id_Premiacao INT,
    Tipo VARCHAR(20), -- Pergunta Criada ou Prefefinida  --
    FOREIGN KEY (Id_Materia) REFERENCES Materias(Id_Materia) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (Id_Premiacao) REFERENCES Premiacoes(Id_Premiacao) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT chk_tipo_pergunta CHECK (Tipo IN ('Professor', 'Sistema'))
);

-- Criando tabela de Respostas --
CREATE TABLE Respostas (
    Id_Resposta INT PRIMARY KEY AUTO_INCREMENT,
    Texto TEXT NOT NULL,
    Correta BOOLEAN
);

-- Tabela de relacionamento N:N entre Perguntas e Respostas --
CREATE TABLE Pergunta_Resposta (
    Id_Pergunta INT,
    Id_Resposta INT,
    PRIMARY KEY (Id_Pergunta, Id_Resposta),
    FOREIGN KEY (Id_Pergunta) REFERENCES Perguntas(Id_Pergunta) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (Id_Resposta) REFERENCES Respostas(Id_Resposta) ON DELETE RESTRICT ON UPDATE CASCADE
);

