-- DROP DE TODAS AS TABELAS (em ordem inversa para evitar problemas de FK)
DROP TABLE IF EXISTS Pergunta_Resposta;
DROP TABLE IF EXISTS Respostas;
DROP TABLE IF EXISTS Perguntas;
DROP TABLE IF EXISTS login_aluno;
DROP TABLE IF EXISTS login_professor;
DROP TABLE IF EXISTS Aluno_Materia;
DROP TABLE IF EXISTS Aluno;
DROP TABLE IF EXISTS Professor;
DROP TABLE IF EXISTS Materias;
DROP TABLE IF EXISTS Premiacoes;

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

-- Tabela Perguntas (MODELAGEM AJUSTADA - com Id_Materia)
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

-- Tabela Respostas (MODELAGEM AJUSTADA - sem coluna Correta)
CREATE TABLE Respostas (
    Id_Resposta INT PRIMARY KEY AUTO_INCREMENT,
    Texto TEXT NOT NULL,
    Letra CHAR(1) NOT NULL CHECK (Letra IN ('A', 'B', 'C', 'D', 'E'))
);

-- Tabela Pergunta_Resposta (MODELAGEM AJUSTADA - com coluna Correta)
CREATE TABLE Pergunta_Resposta (
    Id_Pergunta INT NOT NULL,
    Id_Resposta INT NOT NULL,
    Correta BOOLEAN NOT NULL DEFAULT FALSE,
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

-- INSERIR DADOS BÁSICOS
INSERT INTO Materias (Nome_Materia) VALUES ('Matemática');
SET @id_matematica = LAST_INSERT_ID();

INSERT INTO Premiacoes (Quantidade_Dinheiro) VALUES (10), (20), (30);

-- PROCEDURE PARA INSERIR PERGUNTAS E RESPOSTAS (MODELAGEM AJUSTADA E COM NOVO NOME)
DELIMITER //
DROP PROCEDURE IF EXISTS InserirPerguntaResposta//
CREATE PROCEDURE InserirPerguntaResposta(
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
    
    -- Inserir pergunta (com Id_Materia)
    INSERT INTO Perguntas (Enunciado, Id_Materia, Dificuldade)
    VALUES (p_enunciado, @id_matematica, p_dificuldade);
    
    SET v_id_pergunta = LAST_INSERT_ID();
    
    -- Inserir alternativas (sem informação de correta)
    INSERT INTO Respostas (Texto, Letra) VALUES (p_alternativa_A, 'A');
    SET v_id_resposta_A = LAST_INSERT_ID();
    
    INSERT INTO Respostas (Texto, Letra) VALUES (p_alternativa_B, 'B');
    SET v_id_resposta_B = LAST_INSERT_ID();
    
    INSERT INTO Respostas (Texto, Letra) VALUES (p_alternativa_C, 'C');
    SET v_id_resposta_C = LAST_INSERT_ID();
    
    INSERT INTO Respostas (Texto, Letra) VALUES (p_alternativa_D, 'D');
    SET v_id_resposta_D = LAST_INSERT_ID();
    
    INSERT INTO Respostas (Texto, Letra) VALUES (p_alternativa_E, 'E');
    SET v_id_resposta_E = LAST_INSERT_ID();
    
    -- Relacionar pergunta com respostas e marcar a correta (na tabela Pergunta_Resposta)
    INSERT INTO Pergunta_Resposta (Id_Pergunta, Id_Resposta, Correta) VALUES
    (v_id_pergunta, v_id_resposta_A, p_letra_correta = 'A'),
    (v_id_pergunta, v_id_resposta_B, p_letra_correta = 'B'),
    (v_id_pergunta, v_id_resposta_C, p_letra_correta = 'C'),
    (v_id_pergunta, v_id_resposta_D, p_letra_correta = 'D'),
    (v_id_pergunta, v_id_resposta_E, p_letra_correta = 'E');
END //
DELIMITER ;

-- INSERIR TODAS AS PERGUNTAS E RESPOSTAS
-- Perguntas Fáceis (Matemática)
CALL InserirPerguntaResposta('Qual o valor de 5²?', 'Fácil', '10', '15', '20', '25', '30', 'D');
CALL InserirPerguntaResposta('Qual é o valor de 3 × (4 + 2)?', 'Fácil', '24', '18', '12', '9', '18', 'B');
CALL InserirPerguntaResposta('Qual é o número primo entre os seguintes?', 'Fácil', '9', '15', '17', '21', '25', 'C');
CALL InserirPerguntaResposta('Quanto é 1/2 + 1/4?', 'Fácil', '3/4', '1/2', '1', '2/4', '2/3', 'A');
CALL InserirPerguntaResposta('O triplo de 7 é:', 'Fácil', '21', '14', '10', '24', '17', 'A');
CALL InserirPerguntaResposta('Qual o valor de 100 ÷ 4?', 'Fácil', '20', '25', '30', '40', '22', 'B');
CALL InserirPerguntaResposta('Quantos graus há em um ângulo reto?', 'Fácil', '60º', '90º', '100º', '45º', '120º', 'B');
CALL InserirPerguntaResposta('Um quadrado tem 4 lados de 5 cm. Qual é o seu perímetro?', 'Fácil', '10 cm', '20 cm', '25 cm', '15 cm', '30 cm', 'B');

-- Perguntas Médias (Matemática)
CALL InserirPerguntaResposta('Qual é a fração equivalente a 2/4?', 'Média', '1/2', '3/4', '1/3', '2/3', '3/2', 'A');
CALL InserirPerguntaResposta('João tem R$ 80 e gasta 25%. Quanto ele gastou?', 'Média', 'R$ 15', 'R$ 20', 'R$ 25', 'R$ 30', 'R$ 18', 'B');
CALL InserirPerguntaResposta('Qual é a área de um triângulo com base 8 cm e altura 5 cm?', 'Média', '40 cm²', '20 cm²', '30 cm²', '15 cm²', '25 cm²', 'B');
CALL InserirPerguntaResposta('Qual é o resultado de -3 + 7?', 'Média', '-10', '-4', '4', '10', '-2', 'C');
CALL InserirPerguntaResposta('Em uma sala há 12 meninas e 8 meninos. Qual a razão entre meninas e o total de alunos?', 'Média', '3/5', '2/3', '1/2', '2/5', '1/3', 'A');
CALL InserirPerguntaResposta('A soma dos ângulos internos de um triângulo é:', 'Média', '90º', '180º', '360º', '270º', '100º', 'B');
CALL InserirPerguntaResposta('Um número menos 9 é igual a 31. Qual é o número?', 'Média', '40', '30', '42', '38', '39', 'A');
CALL InserirPerguntaResposta('Se uma peça custa R$ 32, quanto custam 4 peças?', 'Média', 'R$ 128', 'R$ 120', 'R$ 132', 'R$ 124', 'R$ 140', 'A');

-- Perguntas Difíceis (Matemática)
CALL InserirPerguntaResposta('Qual é o valor de -5 × (-3) + 4 × (-2)?', 'Difícil', '-7', '1', '0', '-1', '7', 'E');
CALL InserirPerguntaResposta('Qual é o número inteiro mais próximo da raiz quadrada de 90?', 'Difícil', '9', '10', '8', '9,5', '11', 'B');
CALL InserirPerguntaResposta('Um carro faz 12 km por litro de combustível. Quantos litros são necessários para percorrer 192 km?', 'Difícil', '14', '16', '18', '20', '22', 'B');
CALL InserirPerguntaResposta('Se um número é aumentado em 20% e o resultado é 60, qual era o número original?', 'Difícil', '48', '50', '55', '58', '45', 'B');
CALL InserirPerguntaResposta('Quanto é a raiz quadrada de 64 mais 5 ao quadrado menos 3 vezes 4?', 'Difícil', '17', '21', '25', '29', '33', 'B');
CALL InserirPerguntaResposta('Um aluno acertou 20 questões. A razão entre acertos e erros foi 4 para 1. Quantas questões ele errou?', 'Difícil', '4', '5', '6', '8', '10', 'B');
CALL InserirPerguntaResposta('Quanto é -2 ao cubo mais -3 ao quadrado?', 'Difícil', '-17', '-1', '1', '17', '7', 'C');
CALL InserirPerguntaResposta('Uma loja vendeu 120 camisetas no total. Desse total: 40% eram brancas, 1/3 das restantes eram pretas, as demais eram coloridas. Quantas camisetas coloridas foram vendidas?', 'Difícil', '36', '40', '48', '52', '60', 'C');

SELECT p.Id_Pergunta, p.Enunciado, p.Dificuldade, 
       r.Letra, r.Texto, pr.Correta
FROM Perguntas p
JOIN Pergunta_Resposta pr ON p.Id_Pergunta = pr.Id_Pergunta
JOIN Respostas r ON pr.Id_Resposta = r.Id_Resposta
WHERE p.Id_Materia = 2
ORDER BY p.Dificuldade, p.Id_Pergunta, r.Letra;

INSERT INTO Materias (Nome_Materia) VALUES ('Português');
SET @id_portugues = LAST_INSERT_ID();

-- 2. PROCEDURE PARA INSERIR PERGUNTAS E RESPOSTAS (MODELAGEM AJUSTADA)
DELIMITER //
DROP PROCEDURE IF EXISTS InserirPerguntaResposta//
CREATE PROCEDURE InserirPerguntaResposta(
    IN p_enunciado TEXT,
    IN p_materia_id INT,
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
    
    -- Inserir pergunta (com Id_Materia)
    INSERT INTO Perguntas (Enunciado, Id_Materia, Dificuldade)
    VALUES (p_enunciado, p_materia_id, p_dificuldade);
    
    SET v_id_pergunta = LAST_INSERT_ID();
    
    -- Inserir alternativas (sem informação de correta)
    INSERT INTO Respostas (Texto, Letra) VALUES (p_alternativa_A, 'A');
    SET v_id_resposta_A = LAST_INSERT_ID();
    
    INSERT INTO Respostas (Texto, Letra) VALUES (p_alternativa_B, 'B');
    SET v_id_resposta_B = LAST_INSERT_ID();
    
    INSERT INTO Respostas (Texto, Letra) VALUES (p_alternativa_C, 'C');
    SET v_id_resposta_C = LAST_INSERT_ID();
    
    INSERT INTO Respostas (Texto, Letra) VALUES (p_alternativa_D, 'D');
    SET v_id_resposta_D = LAST_INSERT_ID();
    
    INSERT INTO Respostas (Texto, Letra) VALUES (p_alternativa_E, 'E');
    SET v_id_resposta_E = LAST_INSERT_ID();
    
    -- Relacionar pergunta com respostas e marcar a correta (na tabela Pergunta_Resposta)
    INSERT INTO Pergunta_Resposta (Id_Pergunta, Id_Resposta, Correta) VALUES
    (v_id_pergunta, v_id_resposta_A, p_letra_correta = 'A'),
    (v_id_pergunta, v_id_resposta_B, p_letra_correta = 'B'),
    (v_id_pergunta, v_id_resposta_C, p_letra_correta = 'C'),
    (v_id_pergunta, v_id_resposta_D, p_letra_correta = 'D'),
    (v_id_pergunta, v_id_resposta_E, p_letra_correta = 'E');
END //
DELIMITER ;

-- 3. INSERIR PERGUNTAS DE PORTUGUÊS
-- Português - Fácil
CALL InserirPerguntaResposta('Qual o sujeito da frase: "As crianças brincam no parque."', @id_portugues, 'Fácil', 'no parque', 'brincam', 'As crianças', 'o parque', 'Nenhuma das opções', 'C');
CALL InserirPerguntaResposta('Qual a palavra proparoxítona:', @id_portugues, 'Fácil', 'cadeira', 'lâmpada', 'livro', 'mesa', 'caneta', 'B');
CALL InserirPerguntaResposta('Complete: "___ muitos anos atrás, ___ vida era diferente."', @id_portugues, 'Fácil', 'Há, a', 'A, há', 'A, a', 'Há, há', 'As, a', 'A');
CALL InserirPerguntaResposta('Qual o Plural de "chapéu":', @id_portugues, 'Fácil', 'chapéus', 'chapéis', 'chapéiz', 'chapéies', 'chapéusos', 'A');
CALL InserirPerguntaResposta('Verbo em: "O pássaro canta lindamente."', @id_portugues, 'Fácil', 'pássaro', 'canta', 'lindamente', 'o', 'Nenhuma', 'B');
CALL InserirPerguntaResposta('Qual é o antônimo de "alegre":', @id_portugues, 'Fácil', 'feliz', 'triste', 'animado', 'contente', 'radiante', 'B');
CALL InserirPerguntaResposta('Frase com sentido figurado:', @id_portugues, 'Fácil', 'O sol brilha no céu.', 'Ela tem um coração de pedra.', 'O gato mia alto.', 'A chuva molhou a rua.', 'A planta cresceu.', 'B');
CALL InserirPerguntaResposta('Como se escreve a palavra a baixo?', @id_portugues, 'Fácil', 'exceção', 'excessão', 'exseção', 'excesão', 'ecseção', 'A');

-- Português - Médio
CALL InserirPerguntaResposta('Qual objeto direto em: "Eu comprei um livro novo."', @id_portugues, 'Média', 'Eu', 'comprei', 'um livro novo', 'novo', 'Nenhuma', 'C');
CALL InserirPerguntaResposta('Diga o sujeito oculto em: "Estudamos para a prova."', @id_portugues, 'Média', 'Nós', 'Eles', 'Vocês', 'Elas', 'Vós', 'A');
CALL InserirPerguntaResposta('Qual das frases a seguir se trata de uma oração subordinada adverbial?', @id_portugues, 'Média', '"Quando cheguei, ela saiu."', '"O livro que li era ótimo."', '"Quero que você venha."', '"Ela sorriu porque ficou feliz."', '"Não sei se ele virá."', 'A');
CALL InserirPerguntaResposta('Qual é o sinônimo de "magnânimo":', @id_portugues, 'Média', 'generoso', 'fraco', 'pequeno', 'egoísta', 'cruel', 'A');
CALL InserirPerguntaResposta('Como fica a transformação para voz passiva de: "O artista pintou o quadro."', @id_portugues, 'Média', 'O quadro foi pintado pelo artista.', 'O quadro pintou o artista.', 'O artista foi pintado pelo quadro.', 'Pintou-se o quadro pelo artista.', 'O artista tinha pintado o quadro.', 'A');
CALL InserirPerguntaResposta('Qual das palavras a seguir é um advérbio:', @id_portugues, 'Média', 'feliz', 'rapidamente', 'coragem', 'beleza', 'inteligente', 'B');
CALL InserirPerguntaResposta('Figura de linguagem em: "O tempo voa."', @id_portugues, 'Média', 'metáfora', 'comparação', 'hipérbole', 'personificação', 'ironia', 'A');
CALL InserirPerguntaResposta('Como fica a concordância correta em: "Faz muitos anos que não nos vemos."', @id_portugues, 'Média', 'Faz', 'Fazem', 'Fez', 'Fizeram', 'Fazendo', 'A');

-- Português - Difícil
CALL InserirPerguntaResposta('Considerando a construção da frase "Subir para cima é cansativo", qual elemento caracteriza um vício de linguagem por redundância?', @id_portugues, 'Difícil', 'O uso do verbo "subir" sem complemento', 'A expressão "para cima" após o verbo "subir"', 'A ausência de sujeito definido', 'O emprego do adjetivo "cansativo"', 'A estrutura sintática da oração', 'B');
CALL InserirPerguntaResposta('Na oração "Espero que você entenda", qual é a função sintática desempenhada pela palavra "que"?', @id_portugues, 'Difícil', 'Pronome relativo que retoma o termo anterior', 'Conjunção integrante que introduz conteúdo do verbo "esperar"', 'Advérbio de intensidade modificando "entenda"', 'Partícula expletiva sem função sintática', 'Pronome interrogativo implícito', 'B');
CALL InserirPerguntaResposta('Analisando a construção "Aspiro a um futuro melhor", qual comentário sobre regência verbal está correto?', @id_portugues, 'Difícil', 'O verbo "aspirar" exige complemento com preposição "a"', 'Deveria ser usado "aspirar por" por se tratar de desejo', 'A preposição "a" está incorreta pois o verbo é direto', 'O correto seria "aspirar para" seguido de infinitivo', 'Trata-se de caso facultativo de regência', 'A');
CALL InserirPerguntaResposta('Na metáfora "O vento sussurrava segredos", qual figura de linguagem predomina?', @id_portugues, 'Difícil', 'Personificação, por atribuir ação humana ao vento', 'Hipérbole, por exagerar a ação do vento', 'Metonímia, pela substituição de conceitos', 'Antítese, pelo contraste entre elementos', 'Eufemismo, por suavizar a ação', 'A');
CALL InserirPerguntaResposta('Sobre o uso da crase na frase "Refiro-me à professora", qual análise está correta?', @id_portugues, 'Difícil', 'O acento indica contração de preposição com artigo feminino', 'A crase poderia ser omitida sem prejuízo gramatical', 'Deveria ser usado "à" mesmo com substantivo masculino', 'O correto seria usar apenas a preposição "a"', 'Trata-se de caso facultativo de crase', 'A');
CALL InserirPerguntaResposta('Na construção "Fazem dois dias que cheguei", qual é o erro de concordância?', @id_portugues, 'Difícil', 'O verbo "fazer" no plural quando deveria ser singular', 'A ausência de vírgula após "dias"', 'O uso do conectivo "que" em vez de "desde que"', 'O tempo verbal do verbo "chegar"', 'A estrutura da oração subordinada', 'A');
CALL InserirPerguntaResposta('Em "Ela se machucou", qual é a função do pronome "se"?', @id_portugues, 'Difícil', 'Indicar ação reflexiva (quem machuca e é machucado é a mesma pessoa)', 'Exercer função de sujeito oculto da oração', 'Servir como partícula apassivadora', 'Atuar como índice de indeterminação do sujeito', 'Funcionar como objeto direto pleonástico', 'A');
CALL InserirPerguntaResposta('Qual das alternativas apresenta claramente uma variante linguística não padrão?', @id_portugues, 'Difícil', '"Eles foi embora" (erro de concordância verbal)', '"Eles foram embora cedo" (padrão culto)', '"Eles estão indo agora" (padrão formal)', '"Eles saíram há pouco" (variação regular)', '"Eles decidiram partir" (estrutura correta)', 'A');

SELECT 
    p.Id_Pergunta,
    p.Id_Materia,
    m.Nome_Materia AS Materia,
    p.Enunciado,
    r.Letra,
    r.Texto AS Resposta,
    IF(pr.Correta = 1, '1', '0') AS Correta
FROM 
    Perguntas p
JOIN 
    Pergunta_Resposta pr ON p.Id_Pergunta = pr.Id_Pergunta
JOIN 
    Respostas r ON pr.Id_Resposta = r.Id_Resposta
JOIN
    Materias m ON p.Id_Materia = m.Id_Materia
ORDER BY 
    p.Id_Pergunta, r.Letra;
    

SELECT p.Id_Pergunta, p.Enunciado, p.Dificuldade, 
       r.Letra, r.Texto, pr.Correta
FROM Perguntas p
JOIN Pergunta_Resposta pr ON p.Id_Pergunta = pr.Id_Pergunta
JOIN Respostas r ON pr.Id_Resposta = r.Id_Resposta
WHERE p.Id_Materia = 2
ORDER BY p.Dificuldade, p.Id_Pergunta, r.Letra;

-- 1. ADICIONAR MATÉRIA DE CIÊNCIAS
INSERT INTO Materias (Nome_Materia) VALUES ('Ciências');
SET @id_ciencias = LAST_INSERT_ID();

-- 2. INSERIR PERGUNTAS DE CIÊNCIAS USANDO A PROCEDURE EXISTENTE
-- Ciências - Fácil
CALL InserirPerguntaResposta('Qual desses organismos é considerado um produtor em uma cadeia alimentar?', @id_ciencias, 'Fácil', 'Gato', 'Cogumelo', 'Planta', 'Bactéria', 'Minhoca', 'C');
CALL InserirPerguntaResposta('O que acontece com a água quando é colocada no congelador?', @id_ciencias, 'Fácil', 'Evapora', 'Condensa', 'Solidifica', 'Dissolve', 'Ferve', 'C');
CALL InserirPerguntaResposta('Qual desses é um exemplo de animal vertebrado?', @id_ciencias, 'Fácil', 'Aranha', 'Minhoca', 'Peixe', 'Água-viva', 'Caracol', 'C');
CALL InserirPerguntaResposta('Para que serve a clorofila nas plantas?', @id_ciencias, 'Fácil', 'Absorver água', 'Captar luz solar para a fotossíntese', 'Atrair polinizadores', 'Armazenar nutrientes', 'Proteger contra pragas', 'B');
CALL InserirPerguntaResposta('Qual fenômeno natural é responsável pelas estações do ano?', @id_ciencias, 'Fácil', 'Rotação da Terra', 'Translação da Terra e inclinação do eixo', 'Movimento das marés', 'Variação da luz solar', 'Atividade vulcânica', 'B');
CALL InserirPerguntaResposta('O que é necessário para que ocorra a combustão?', @id_ciencias, 'Fácil', 'Água, luz e calor', 'Oxigênio, combustível e calor', 'Gás carbônico e energia', 'Nitrogênio e vento', 'Eletricidade e umidade', 'B');
CALL InserirPerguntaResposta('Qual destes materiais é obtido diretamente da natureza sem transformação industrial significativa?', @id_ciencias, 'Fácil', 'Isopor', 'Cimento', 'Lã', 'Fibra de carbono', 'Nylon', 'C');
CALL InserirPerguntaResposta('O que forma o sistema solar?', @id_ciencias, 'Fácil', 'Estrelas, planetas e satélites naturais', 'Asteroides e cometas apenas', 'Galáxias e nebulosas', 'Buracos negros e quasares', 'Planetas anões e meteoritos', 'A');

-- Ciências - Médio
CALL InserirPerguntaResposta('Durante um experimento, um aluno aquece um béquer com água e observa a formação de bolhas. Qual fenômeno está ocorrendo quando a água atinge 100°C ao nível do mar?', @id_ciencias, 'Média', 'Solidificação – as moléculas se organizam em estrutura rígida', 'Ebulição – mudança de estado líquido para gasoso', 'Sublimação – passagem direta do estado sólido para gasoso', 'Condensação – formação de gotículas no vidro', 'Fusão – transformação de gelo em água líquida', 'B');
CALL InserirPerguntaResposta('Qual órgão é responsável pela filtragem do sangue no corpo humano?', @id_ciencias, 'Média', 'Coração', 'Pulmão', 'Rim', 'Fígado', 'Estômago', 'C');
CALL InserirPerguntaResposta('O que representa a fórmula H₂O?', @id_ciencias, 'Média', 'Dióxido de carbono', 'Água', 'Sal de cozinha', 'Gás oxigênio', 'Ácido sulfúrico', 'B');
CALL InserirPerguntaResposta('Qual processo as plantas realizam para produzir seu próprio alimento?', @id_ciencias, 'Média', 'Respiração', 'Fotossíntese', 'Transpiração', 'Digestão', 'Fermentação', 'B');
CALL InserirPerguntaResposta('O que acontece quando o gelo derrete?', @id_ciencias, 'Média', 'Mudança de sólido para líquido', 'Formação de novo material', 'Liberação de gás', 'Mudança de cor', 'Dissolução química', 'A');
CALL InserirPerguntaResposta('Qual gás é liberado na fotossíntese?', @id_ciencias, 'Média', 'Gás carbônico', 'Oxigênio', 'Nitrogênio', 'Hidrogênio', 'Metano', 'B');
CALL InserirPerguntaResposta('O que são seres autótrofos?', @id_ciencias, 'Média', 'Organismos que produzem seu alimento', 'Seres que dependem de outros para nutrição', 'Animais que comem plantas', 'Bactérias decompositoras', 'Fungos parasitas', 'A');
CALL InserirPerguntaResposta('O que aconteceria se todos os decompositores desaparecessem?', @id_ciencias, 'Média', 'As plantas cresceriam mais', 'O acúmulo de matéria orgânica aumentaria', 'Os predadores se multiplicariam', 'O solo ficaria mais fértil', 'Nada mudaria', 'B');

-- Ciências - Difícil
CALL InserirPerguntaResposta('Qual conceito explica a sobrevivência dos organismos mais adaptados?', @id_ciencias, 'Difícil', 'Mutação aleatória', 'Seleção natural', 'Especiação', 'Deriva genética', 'Hibridização', 'B');
CALL InserirPerguntaResposta('Qual organela é responsável pela produção de ATP em células eucarióticas?', @id_ciencias, 'Difícil', 'Lisossomo', 'Mitocôndria', 'Ribossomo', 'Complexo golgiense', 'Retículo endoplasmático', 'B');
CALL InserirPerguntaResposta('Em que parte do sistema digestório ocorre a maior absorção de nutrientes?', @id_ciencias, 'Difícil', 'Estômago', 'Intestino delgado', 'Esôfago', 'Intestino grosso', 'Boca', 'B');
CALL InserirPerguntaResposta('O que diferencia os vírus de seres vivos?', @id_ciencias, 'Difícil', 'Possuem DNA ou RNA', 'Não têm estrutura celular', 'Podem se reproduzir', 'Sofrem mutações', 'Interagem com outros organismos', 'B');
CALL InserirPerguntaResposta('Qual glândula produz insulina no corpo humano?', @id_ciencias, 'Difícil', 'Tireoide', 'Hipófise', 'Pâncreas', 'Suprarrenal', 'Timo', 'C');
CALL InserirPerguntaResposta('Por que a biodiversidade é importante para um ecossistema?', @id_ciencias, 'Difícil', 'Aumenta a resiliência ambiental', 'Reduz a competição entre espécies', 'Diminui a taxa de fotossíntese', 'Elimina os decompositores', 'Nenhuma das alternativas', 'A');
CALL InserirPerguntaResposta('Qual estrutura cerebral é responsável pelo equilíbrio e coordenação motora?', @id_ciencias, 'Difícil', 'Hipotálamo', 'Cerebelo', 'Tálamo', 'Córtex cerebral', 'Bulbo raquidiano', 'B');
CALL InserirPerguntaResposta('Qual é a principal função dos estômatos nas folhas das plantas?', @id_ciencias, 'Difícil', 'Produção de clorofila', 'Troca gasosa e transpiração', 'Absorção de nutrientes do solo', 'Proteção contra herbívoros', 'Armazenamento de água', 'B');

SELECT p.Id_Pergunta, p.Enunciado, p.Dificuldade, 
       r.Letra, r.Texto, pr.Correta
FROM Perguntas p
JOIN Pergunta_Resposta pr ON p.Id_Pergunta = pr.Id_Pergunta
JOIN Respostas r ON pr.Id_Resposta = r.Id_Resposta
WHERE p.Id_Materia = 3
ORDER BY p.Dificuldade, p.Id_Pergunta, r.Letra;




