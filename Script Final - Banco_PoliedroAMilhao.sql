
-- Tabela Premiacoes
CREATE TABLE Premiacoes (
    Id_Premiacao INT PRIMARY KEY AUTO_INCREMENT,
    Quantidade_Dinheiro TINYINT
);

-- Tabela Aluno
CREATE TABLE Aluno (
    Id_Aluno INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(45),
    Serie CHAR(8),
    Pontuacao INT,
    CONSTRAINT fk_pontuacao_aluno FOREIGN KEY (Pontuacao) 
        REFERENCES Premiacoes(Id_Premiacao) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Tabela Materias
CREATE TABLE Materias (
    Id_Materia INT PRIMARY KEY AUTO_INCREMENT,
    Nome_Materia VARCHAR(20)
);

-- Tabela Aluno_Materia
CREATE TABLE Aluno_Materia (
    Id_Aluno_Materia INT PRIMARY KEY AUTO_INCREMENT,
    Aluno_Id_Aluno INT,
    Materias_Id_Materia INT,
    FOREIGN KEY (Aluno_Id_Aluno) REFERENCES Aluno(Id_Aluno) 
        ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (Materias_Id_Materia) REFERENCES Materias(Id_Materia) 
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Tabela Professor
CREATE TABLE Professor (
    Id_Professor INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(45)
);

-- Tabela Perguntas (modelagem original)
CREATE TABLE Perguntas (
    Id_Pergunta INT PRIMARY KEY AUTO_INCREMENT,
    Enunciado TEXT NOT NULL,
    Id_Materia INT NOT NULL,
    Id_Premiacao INT,
    Tipo VARCHAR(20) NOT NULL DEFAULT 'Sistema',
    Dificuldade ENUM('Fácil', 'Média', 'Difícil') NOT NULL DEFAULT 'Fácil',
    FOREIGN KEY (Id_Materia) REFERENCES Materias(Id_Materia),
    FOREIGN KEY (Id_Premiacao) REFERENCES Premiacoes(Id_Premiacao),
    CONSTRAINT chk_tipo_pergunta CHECK (Tipo IN ('Professor', 'Sistema'))
);

-- Tabela Respostas (modelagem original)
CREATE TABLE Respostas (
    Id_Resposta INT PRIMARY KEY AUTO_INCREMENT,
    Texto TEXT NOT NULL,
    Letra CHAR(1) NOT NULL CHECK (Letra IN ('A', 'B', 'C', 'D', 'E')),
    Correta BOOLEAN NOT NULL DEFAULT FALSE
);

-- Tabela Pergunta_Resposta (modelagem original)
CREATE TABLE Pergunta_Resposta (
    Id_Pergunta INT NOT NULL,
    Id_Resposta INT NOT NULL,
    PRIMARY KEY (Id_Pergunta, Id_Resposta),
    FOREIGN KEY (Id_Pergunta) REFERENCES Perguntas(Id_Pergunta),
    FOREIGN KEY (Id_Resposta) REFERENCES Respostas(Id_Resposta)
);

-- Tabela login_aluno
CREATE TABLE login_aluno (
    Id_Aluno INT PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(50) NOT NULL,
    estado VARCHAR(10) NOT NULL DEFAULT 'Ativo' CHECK (estado IN ('Ativo', 'Inativo')),
    FOREIGN KEY (Id_Aluno) REFERENCES Aluno(Id_Aluno)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Tabela login_professor
CREATE TABLE login_professor (
    id_professor INT PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_professor) REFERENCES Professor(Id_Professor)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- 3. INSERIR DADOS BÁSICOS
INSERT INTO Materias (Nome_Materia) VALUES ('Matemática');
SET @id_matematica = LAST_INSERT_ID();

INSERT INTO Premiacoes (Quantidade_Dinheiro) VALUES (10), (20), (30);

-- 4. PROCEDURE PARA INSERIR PERGUNTAS E RESPOSTAS (modelagem original)
DELIMITER //
CREATE PROCEDURE InserirPerguntaComRespostas(
    IN p_enunciado TEXT,
    IN p_dificuldade ENUM('Fácil', 'Média', 'Difícil'),
    IN p_alternativa_A TEXT,
    IN p_alternativa_B TEXT,
    IN p_alternativa_C TEXT,
    IN p_alternativa_D TEXT,
    IN p_alternativa_E TEXT,
    IN p_letra_correta CHAR(1))
BEGIN
    DECLARE v_id_pergunta INT;
    DECLARE v_id_resposta_A INT;
    DECLARE v_id_resposta_B INT;
    DECLARE v_id_resposta_C INT;
    DECLARE v_id_resposta_D INT;
    DECLARE v_id_resposta_E INT;
    
    -- Inserir pergunta
    INSERT INTO Perguntas (Enunciado, Id_Materia, Dificuldade)
    VALUES (p_enunciado, @id_matematica, p_dificuldade);
    
    SET v_id_pergunta = LAST_INSERT_ID();
    
    -- Inserir alternativas
    INSERT INTO Respostas (Texto, Letra, Correta) VALUES
    (p_alternativa_A, 'A', p_letra_correta = 'A');
    SET v_id_resposta_A = LAST_INSERT_ID();
    
    INSERT INTO Respostas (Texto, Letra, Correta) VALUES
    (p_alternativa_B, 'B', p_letra_correta = 'B');
    SET v_id_resposta_B = LAST_INSERT_ID();
    
    INSERT INTO Respostas (Texto, Letra, Correta) VALUES
    (p_alternativa_C, 'C', p_letra_correta = 'C');
    SET v_id_resposta_C = LAST_INSERT_ID();
    
    INSERT INTO Respostas (Texto, Letra, Correta) VALUES
    (p_alternativa_D, 'D', p_letra_correta = 'D');
    SET v_id_resposta_D = LAST_INSERT_ID();
    
    INSERT INTO Respostas (Texto, Letra, Correta) VALUES
    (p_alternativa_E, 'E', p_letra_correta = 'E');
    SET v_id_resposta_E = LAST_INSERT_ID();
    
    -- Relacionar pergunta com respostas
    INSERT INTO Pergunta_Resposta (Id_Pergunta, Id_Resposta) VALUES
    (v_id_pergunta, v_id_resposta_A),
    (v_id_pergunta, v_id_resposta_B),
    (v_id_pergunta, v_id_resposta_C),
    (v_id_pergunta, v_id_resposta_D),
    (v_id_pergunta, v_id_resposta_E);
END //
DELIMITER ;

-- 5. INSERIR TODAS AS PERGUNTAS E RESPOSTAS
-- Perguntas Fáceis
CALL InserirPerguntaComRespostas('Qual o valor de 5²?', 'Fácil', '10', '15', '20', '25', '30', 'D');
CALL InserirPerguntaComRespostas('Qual é o valor de 3 × (4 + 2)?', 'Fácil', '24', '18', '12', '9', '18', 'B');
CALL InserirPerguntaComRespostas('Qual é o número primo entre os seguintes?', 'Fácil', '9', '15', '17', '21', '25', 'C');
CALL InserirPerguntaComRespostas('Quanto é 1/2 + 1/4?', 'Fácil', '3/4', '1/2', '1', '2/4', '2/3', 'A');
CALL InserirPerguntaComRespostas('O triplo de 7 é:', 'Fácil', '21', '14', '10', '24', '17', 'A');
CALL InserirPerguntaComRespostas('Qual o valor de 100 ÷ 4?', 'Fácil', '20', '25', '30', '40', '22', 'B');
CALL InserirPerguntaComRespostas('Quantos graus há em um ângulo reto?', 'Fácil', '60º', '90º', '100º', '45º', '120º', 'B');
CALL InserirPerguntaComRespostas('Um quadrado tem 4 lados de 5 cm. Qual é o seu perímetro?', 'Fácil', '10 cm', '20 cm', '25 cm', '15 cm', '30 cm', 'B');

-- Perguntas Médias
CALL InserirPerguntaComRespostas('Qual é a fração equivalente a 2/4?', 'Média', '1/2', '3/4', '1/3', '2/3', '3/2', 'A');
CALL InserirPerguntaComRespostas('João tem R$ 80 e gasta 25%. Quanto ele gastou?', 'Média', 'R$ 15', 'R$ 20', 'R$ 25', 'R$ 30', 'R$ 18', 'B');
CALL InserirPerguntaComRespostas('Qual é a área de um triângulo com base 8 cm e altura 5 cm?', 'Média', '40 cm²', '20 cm²', '30 cm²', '15 cm²', '25 cm²', 'B');
CALL InserirPerguntaComRespostas('Qual é o resultado de -3 + 7?', 'Média', '-10', '-4', '4', '10', '-2', 'C');
CALL InserirPerguntaComRespostas('Em uma sala há 12 meninas e 8 meninos. Qual a razão entre meninas e o total de alunos?', 'Média', '3/5', '2/3', '1/2', '2/5', '1/3', 'A');
CALL InserirPerguntaComRespostas('A soma dos ângulos internos de um triângulo é:', 'Média', '90º', '180º', '360º', '270º', '100º', 'B');
CALL InserirPerguntaComRespostas('Um número menos 9 é igual a 31. Qual é o número?', 'Média', '40', '30', '42', '38', '39', 'A');
CALL InserirPerguntaComRespostas('Se uma peça custa R$ 32, quanto custam 4 peças?', 'Média', 'R$ 128', 'R$ 120', 'R$ 132', 'R$ 124', 'R$ 140', 'A');

-- Perguntas Difíceis
CALL InserirPerguntaComRespostas('Qual é o valor de -5 × (-3) + 4 × (-2)?', 'Difícil', '-7', '1', '0', '-1', '7', 'E');
CALL InserirPerguntaComRespostas('Qual é o número inteiro mais próximo da raiz quadrada de 90?', 'Difícil', '9', '10', '8', '9,5', '11', 'B');
CALL InserirPerguntaComRespostas('Um carro faz 12 km por litro de combustível. Quantos litros são necessários para percorrer 192 km?', 'Difícil', '14', '16', '18', '20', '22', 'B');
CALL InserirPerguntaComRespostas('Se um número é aumentado em 20% e o resultado é 60, qual era o número original?', 'Difícil', '48', '50', '55', '58', '45', 'B');
CALL InserirPerguntaComRespostas('Quanto é a raiz quadrada de 64 mais 5 ao quadrado menos 3 vezes 4?', 'Difícil', '17', '21', '25', '29', '33', 'B');
CALL InserirPerguntaComRespostas('Um aluno acertou 20 questões. A razão entre acertos e erros foi 4 para 1. Quantas questões ele errou?', 'Difícil', '4', '5', '6', '8', '10', 'B');
CALL InserirPerguntaComRespostas('Quanto é -2 ao cubo mais -3 ao quadrado?', 'Difícil', '-17', '-1', '1', '17', '7', 'C');
CALL InserirPerguntaComRespostas('Uma loja vendeu 120 camisetas no total. Desse total: 40% eram brancas, 1/3 das restantes eram pretas, as demais eram coloridas. Quantas camisetas coloridas foram vendidas?', 'Difícil', '36', '40', '48', '52', '60', 'C');


SELECT p.Id_Pergunta, p.Enunciado, p.Dificuldade, 
       r.Letra, r.Texto, r.Correta
FROM Perguntas p
JOIN Pergunta_Resposta pr ON p.Id_Pergunta = pr.Id_Pergunta
JOIN Respostas r ON pr.Id_Resposta = r.Id_Resposta
ORDER BY p.Id_Pergunta, r.Letra;