CREATE TABLE login_aluno (
    id_login INT PRIMARY KEY AUTO_INCREMENT,
    id_aluno INT NOT NULL,
    email VARCHAR(50) NOT NULL,
    senha VARCHAR(50) NOT NULL,
    RA CHAR(6),
    FOREIGN KEY (id_aluno) REFERENCES Aluno(Id_Aluno)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Professor (
    Id_Professor INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(45),
    Email VARCHAR(50)
);

CREATE TABLE login_professor (
    id_login INT PRIMARY KEY AUTO_INCREMENT,
    id_professor INT NOT NULL,
    email VARCHAR(50) NOT NULL,
    senha VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_professor) REFERENCES Professor(Id_Professor)
        ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE login_aluno ADD CONSTRAINT unico_email_login_aluno UNIQUE (email);
ALTER TABLE login_professor ADD CONSTRAINT unico_email_login_professor UNIQUE (email);
ALTER TABLE Professor DROP COLUMN Email;
DESCRIBE Professor;
ALTER TABLE Aluno DROP COLUMN Turno;
ALTER TABLE Aluno DROP COLUMN Classe;
ALTER TABLE Materias DROP COLUMN Material_Físico;
DESCRIBE login_aluno;
DESCRIBE Perguntas;
ALTER TABLE Premiacoes DROP COLUMN Tipo_Premiacao;
ALTER TABLE Respostas DROP COLUMN Texto;
ALTER TABLE Aluno DROP COLUMN Email;
ALTER TABLE login_aluno MODIFY COLUMN id_aluno INT NOT NULL AUTO_INCREMENT;
ALTER TABLE login_aluno DROP PRIMARY KEY, MODIFY COLUMN id_aluno INT NOT NULL AUTO_INCREMENT PRIMARY KEY;
DROP TABLE IF EXISTS login_aluno;
CREATE TABLE login_aluno (
    id_aluno INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(50) NOT NULL,
    estado VARCHAR(10) NOT NULL CHECK (estado IN ('Ativo', 'Inativo')),
    FOREIGN KEY (id_aluno) REFERENCES Aluno(Id_Aluno)
        ON DELETE RESTRICT ON UPDATE CASCADE
);
ALTER TABLE login_aluno MODIFY COLUMN estado VARCHAR(10) NOT NULL DEFAULT 'Ativo' CHECK (estado IN ('Ativo', 'Inativo'));
SELECT * FROM login_aluno;
ALTER TABLE login_aluno RENAME COLUMN id_aluno TO Id_Aluno;
DESCRIBE login_aluno;
DROP TABLE IF EXISTS login_aluno;
CREATE TABLE login_aluno (
    id_aluno INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(50) NOT NULL,
    estado VARCHAR(10) NOT NULL CHECK (estado IN ('Ativo', 'Inativo')),
    FOREIGN KEY (Id_Aluno) REFERENCES Aluno(Id_Aluno)
        ON DELETE RESTRICT ON UPDATE CASCADE
);
