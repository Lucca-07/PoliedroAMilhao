
package com.mycompany.TelaAluno.telas;

import com.mycompany.TelaAluno.modelo.ControleJogo;
import com.mycompany.TelaAluno.modelo.Materias;
import com.mycompany.TelaAluno.modelo.Pergunta;
import com.mycompany.TelaAluno.modelo.Respostas;    
import com.mycompany.TelaAluno.persistencia.AlunoDAO;
import com.mycompany.TelaAluno.persistencia.ConnectionFactory;
import com.mycompany.TelaAluno.persistencia.PerguntasDificilDAO;
import com.mycompany.TelaAluno.persistencia.PerguntasMediaDAO;
import com.mycompany.TelaAluno.persistencia.PerguntasFacilDAO;
import com.mycompany.TelaAluno.persistencia.RespostasDAO;
import java.util.List;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TelaJogo extends javax.swing.JFrame {

    public int contadorReinicios = ControleJogo.idsUsadas.size() + 1;
    private Timer timer;
    private int segundos = 45;
    private int idPerguntaAtual;
    private static int idAluno;
    private Connection obterConexao;
    private Respostas respostaSelecionadaA;
    private Respostas respostaSelecionadaB;
    private Respostas respostaSelecionadaC;
    private Respostas respostaSelecionadaD;
    private Respostas respostaSelecionadaE;
    public static boolean ajudaUniversitariaUsada = false;
    public static boolean ajudaPularUsada = false;
    public static boolean ajudaCortarUsada = false;
    public static boolean ajudaTelefoneUsada = false;
    

    public TelaJogo(int idAluno, int reinicios, Connection obterConexao) throws Exception {
        initComponents();
        ControleJogo controle = new ControleJogo();
        controle.iniciarNovaPontuacaoParaAluno(idAluno, obterConexao);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.idAluno = idAluno;
        this.obterConexao = obterConexao;
        this.contadorReinicios = reinicios;

        atualizarPontuacao(contadorReinicios);
        moedasTotais();
   
        
        try {
            // Nome do aluno
            var daoAluno = new AlunoDAO();
            nomeLabel.setText(daoAluno.buscarNomePorId(idAluno));
            
            //Guarda a pergunta atual
            Pergunta perguntaAtual = null;
            
            //Pega a materia
            int idMateria = Materias.getIdMateriaSelecionada();
            
            // Nível de dificuldade
            if (contadorReinicios <= 4) {
                PerguntasFacilDAO daoFacil = new PerguntasFacilDAO();
                perguntaAtual = daoFacil.buscarPerguntaFacil(idMateria);
  
            } else if (contadorReinicios <= 8) {
                PerguntasMediaDAO daoMedia = new PerguntasMediaDAO();
                perguntaAtual = daoMedia.buscarPerguntaMedia(idMateria);
            
            } else if (contadorReinicios <= 12) {
                PerguntasDificilDAO daoDificil = new PerguntasDificilDAO();
                perguntaAtual = daoDificil.buscarPerguntaDificil(idMateria);         
            }

            if (perguntaAtual != null) {
                
                // Pega a pergunta que caiu
                idPerguntaAtual = perguntaAtual.getId_pergunta();
                
                // Mostra o enunciado
                perguntaLabel.setText(" " + perguntaAtual.getEnunciado());

                // Exibe o número da pergunta
                contadorLabel.setText("Pergunta de número: " + contadorReinicios);

                // Buscar alternativas com o ID correto da pergunta
                int idPergunta = perguntaAtual.getId_pergunta();
                var daoresposta = new RespostasDAO();

                Respostas altA = daoresposta.AlternativaPorLetra(idPergunta, "A");
                Respostas altB = daoresposta.AlternativaPorLetra(idPergunta, "B");
                Respostas altC = daoresposta.AlternativaPorLetra(idPergunta, "C");
                Respostas altD = daoresposta.AlternativaPorLetra(idPergunta, "D");
                Respostas altE = daoresposta.AlternativaPorLetra(idPergunta, "E");
                
                respostaSelecionadaA = altA;
                respostaSelecionadaB = altB;
                respostaSelecionadaC = altC;    
                respostaSelecionadaD = altD;
                respostaSelecionadaE = altE;

                // Define o texto nos botões 
                Alternativa_1.setText(altA.getTexto());
                Alternativa_2.setText(altB.getTexto());
                Alternativa_3.setText(altC.getTexto());
                Alternativa_4.setText(altD.getTexto());
                Alternativa_5.setText(altE.getTexto());
                
                // Retira as ajudas da  tela quando forem utilizadas
                if (ajudaUniversitariaUsada) {
                    estudantesAjudaBotao.setVisible(false);
                }
                if (ajudaPularUsada) {
                    pularAjudaBotao.setVisible(false);
                }
                
                if (ajudaCortarUsada) {
                    cortarAjudaBotao.setVisible(false);
                }
                
                if (ajudaTelefoneUsada) {
                    telefoneAjudaBotao.setVisible(false);
                }
                
                  
            }

        } catch (Exception ex) {
            ex.printStackTrace();
          
        }
    }
    
    private void moedasTotais() throws Exception {
        AlunoDAO dao = new AlunoDAO();

        int idPremiacao = dao.buscarPontuacaoPorId(idAluno);

        int moedas = dao.buscarPontuacaoPorId(idPremiacao);

        moedasLabel.setText("Moedas: " + moedas);

    }
    
    private void mostrarPontuacaoTotal() {
        try {
            AlunoDAO dao = new AlunoDAO();
            int pontuacaoTotal = dao.buscarPontuacaoPorId(idAluno);
            moedasLabel.setText("Moedas Totais: " + pontuacaoTotal);
        } catch (Exception ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getContadorReinicios() {       
        return this.contadorReinicios;
    }
    public int getIdAluno() {
        return idAluno;
    }
    
    public void atualizarPontuacao(int reinicios) {
        int pontuacaoAtual = 0;

        switch (reinicios) {
            case 2:
                pontuacaoAtual = 1250;
                break;
            case 3:
                pontuacaoAtual = 2500;
                break;
            case 4:
                pontuacaoAtual = 3750;
                break;
            case 5:
                pontuacaoAtual = 5000;
                break;
            case 6:
                pontuacaoAtual = 10000;
                break;
            case 7:
                pontuacaoAtual = 25000;
                break;
            case 8:
                pontuacaoAtual = 50000;
                break;
            case 9:
                pontuacaoAtual = 100000;
                break;
            case 10:
                pontuacaoAtual = 250000;
                break;
            case 11:
                pontuacaoAtual = 500000;
                break;
            case 12:
                pontuacaoAtual = 750000;
                break;
            case 13:
                pontuacaoAtual = 1000000;
                break;
            default:
                pontuacaoAtual = 0;
        }
        // mostra na tela
        pontuacaoLabel.setText("Pontuação: " + pontuacaoAtual);
       
    }

    

    public void proximaPergunta() throws Exception {
        atualizarPontuacao(contadorReinicios);
        contadorReinicios++;
        ControleJogo.idsUsadas.add(idPerguntaAtual);
        this.dispose();
        if (contadorReinicios == 13) {
            TelaVitoriaJogo telaVitoriaJogo = new TelaVitoriaJogo(this);
            telaVitoriaJogo.setVisible(true);
        } else {
            TelaJogo novaTela = new TelaJogo(idAluno, contadorReinicios, obterConexao);
            novaTela.setVisible(true);
        }
    }

    public void cortarTresAlternativasErradas() {
        List<Respostas> alternativas = List.of(
                respostaSelecionadaA,
                respostaSelecionadaB,
                respostaSelecionadaC,
                respostaSelecionadaD,
                respostaSelecionadaE );
        List<JButton> botoes = List.of(
                Alternativa_1,
                Alternativa_2,
                Alternativa_3,
                Alternativa_4,
                Alternativa_5 );
        int removidas = 0;

        for (int i = 0; i < alternativas.size() && removidas < 3; i++) {
            if (!alternativas.get(i).getCorreta()) {
                botoes.get(i).setVisible(false);
                removidas++;
            }
        }

        // Desabilitar botão da ajuda cortar para evitar repetir
        cortarAjudaBotao.setEnabled(false);
    }
    
    public void removerAlternativa(String letra) {
        switch (letra) {
            case "A":
                Alternativa_1.setVisible(false);
                break;
            case "B":
                Alternativa_2.setVisible(false);
                break;
            case "C":
                Alternativa_3.setVisible(false);
                break;
            case "D":
                Alternativa_4.setVisible(false);
                break;
            case "E":
                Alternativa_5.setVisible(false);
                break;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelFUNDO = new javax.swing.JPanel();
        PanelContador = new javax.swing.JPanel();
        Contador = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        PanelPergunta = new javax.swing.JPanel();
        perguntaLabel = new javax.swing.JLabel();
        Alternativa_5 = new javax.swing.JButton();
        Alternativa_4 = new javax.swing.JButton();
        Alternativa_3 = new javax.swing.JButton();
        Alternativa_2 = new javax.swing.JButton();
        Alternativa_1 = new javax.swing.JButton();
        ajudasPanel = new javax.swing.JPanel();
        cortarAjudaBotao = new javax.swing.JButton();
        estudantesAjudaBotao = new javax.swing.JButton();
        pularAjudaBotao = new javax.swing.JButton();
        telefoneAjudaBotao = new javax.swing.JButton();
        moedaicon = new javax.swing.JLabel();
        PanelMoedas = new javax.swing.JPanel();
        moedasLabel = new javax.swing.JLabel();
        nomePanel = new javax.swing.JPanel();
        nomeLabel = new javax.swing.JLabel();
        pessoaicon = new javax.swing.JLabel();
        numeroperguntaPanel = new javax.swing.JPanel();
        contadorLabel = new javax.swing.JLabel();
        pontuacaoPanel = new javax.swing.JPanel();
        pontuacaoLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        PanelFUNDO.setBackground(new java.awt.Color(204, 255, 255));
        PanelFUNDO.setPreferredSize(new java.awt.Dimension(1360, 768));

        PanelContador.setBackground(new java.awt.Color(255, 255, 255));
        PanelContador.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 204), 1, true));

        Contador.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Contador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Contador.setText("45");

        javax.swing.GroupLayout PanelContadorLayout = new javax.swing.GroupLayout(PanelContador);
        PanelContador.setLayout(PanelContadorLayout);
        PanelContadorLayout.setHorizontalGroup(
            PanelContadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelContadorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Contador, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelContadorLayout.setVerticalGroup(
            PanelContadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelContadorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Contador, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/LogoPoliedroAMIlhao-removebg-300png.png"))); // NOI18N

        PanelPergunta.setBackground(new java.awt.Color(255, 255, 255));
        PanelPergunta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204)));

        perguntaLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        perguntaLabel.setText("PERGUNTA");

        javax.swing.GroupLayout PanelPerguntaLayout = new javax.swing.GroupLayout(PanelPergunta);
        PanelPergunta.setLayout(PanelPerguntaLayout);
        PanelPerguntaLayout.setHorizontalGroup(
            PanelPerguntaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPerguntaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(perguntaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 939, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanelPerguntaLayout.setVerticalGroup(
            PanelPerguntaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPerguntaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(perguntaLabel)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        Alternativa_5.setBackground(new java.awt.Color(255, 0, 0));
        Alternativa_5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        Alternativa_5.setText("Opção 5");
        Alternativa_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Alternativa_5ActionPerformed(evt);
            }
        });

        Alternativa_4.setBackground(new java.awt.Color(255, 102, 0));
        Alternativa_4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        Alternativa_4.setText("Opção 4");
        Alternativa_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Alternativa_4ActionPerformed(evt);
            }
        });

        Alternativa_3.setBackground(new java.awt.Color(102, 255, 102));
        Alternativa_3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        Alternativa_3.setText("Opção 3");
        Alternativa_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Alternativa_3ActionPerformed(evt);
            }
        });

        Alternativa_2.setBackground(new java.awt.Color(255, 153, 0));
        Alternativa_2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        Alternativa_2.setText("Opção 2");
        Alternativa_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Alternativa_2ActionPerformed(evt);
            }
        });

        Alternativa_1.setBackground(new java.awt.Color(51, 255, 255));
        Alternativa_1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        Alternativa_1.setText("Opção 1");
        Alternativa_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Alternativa_1ActionPerformed(evt);
            }
        });

        ajudasPanel.setBackground(new java.awt.Color(0, 204, 204));
        ajudasPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204)));

        cortarAjudaBotao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cortar.png"))); // NOI18N
        cortarAjudaBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cortarAjudaBotaoActionPerformed(evt);
            }
        });

        estudantesAjudaBotao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/universitarioss.png"))); // NOI18N
        estudantesAjudaBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estudantesAjudaBotaoActionPerformed(evt);
            }
        });

        pularAjudaBotao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pular.png"))); // NOI18N
        pularAjudaBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pularAjudaBotaoActionPerformed(evt);
            }
        });

        telefoneAjudaBotao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/telefone.png"))); // NOI18N
        telefoneAjudaBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefoneAjudaBotaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ajudasPanelLayout = new javax.swing.GroupLayout(ajudasPanel);
        ajudasPanel.setLayout(ajudasPanelLayout);
        ajudasPanelLayout.setHorizontalGroup(
            ajudasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ajudasPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(ajudasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ajudasPanelLayout.createSequentialGroup()
                        .addComponent(telefoneAjudaBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(estudantesAjudaBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ajudasPanelLayout.createSequentialGroup()
                        .addComponent(pularAjudaBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cortarAjudaBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        ajudasPanelLayout.setVerticalGroup(
            ajudasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ajudasPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(ajudasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(telefoneAjudaBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(estudantesAjudaBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(ajudasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pularAjudaBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cortarAjudaBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        moedaicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/POLIEDROMOEDA-removebg-preview.png"))); // NOI18N

        PanelMoedas.setBackground(new java.awt.Color(255, 255, 255));
        PanelMoedas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        moedasLabel.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        moedasLabel.setText("Moedas Totais: 0");

        javax.swing.GroupLayout PanelMoedasLayout = new javax.swing.GroupLayout(PanelMoedas);
        PanelMoedas.setLayout(PanelMoedasLayout);
        PanelMoedasLayout.setHorizontalGroup(
            PanelMoedasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMoedasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(moedasLabel)
                .addContainerGap())
        );
        PanelMoedasLayout.setVerticalGroup(
            PanelMoedasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMoedasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(moedasLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        nomePanel.setBackground(new java.awt.Color(255, 255, 255));
        nomePanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        nomeLabel.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        nomeLabel.setText("Nome");

        javax.swing.GroupLayout nomePanelLayout = new javax.swing.GroupLayout(nomePanel);
        nomePanel.setLayout(nomePanelLayout);
        nomePanelLayout.setHorizontalGroup(
            nomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nomePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nomeLabel)
                .addContainerGap())
        );
        nomePanelLayout.setVerticalGroup(
            nomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nomePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nomeLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pessoaicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pessoaicon52.png"))); // NOI18N

        numeroperguntaPanel.setBackground(new java.awt.Color(255, 255, 255));
        numeroperguntaPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204)));

        javax.swing.GroupLayout numeroperguntaPanelLayout = new javax.swing.GroupLayout(numeroperguntaPanel);
        numeroperguntaPanel.setLayout(numeroperguntaPanelLayout);
        numeroperguntaPanelLayout.setHorizontalGroup(
            numeroperguntaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, numeroperguntaPanelLayout.createSequentialGroup()
                .addComponent(contadorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addContainerGap())
        );
        numeroperguntaPanelLayout.setVerticalGroup(
            numeroperguntaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, numeroperguntaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contadorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 11, Short.MAX_VALUE)
                .addContainerGap())
        );

        pontuacaoPanel.setBackground(new java.awt.Color(255, 255, 255));
        pontuacaoPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204)));

        pontuacaoLabel.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 24)); // NOI18N
        pontuacaoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pontuacaoLabel.setText("Pontuação: 0");
        pontuacaoLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pontuacaoPanelLayout = new javax.swing.GroupLayout(pontuacaoPanel);
        pontuacaoPanel.setLayout(pontuacaoPanelLayout);
        pontuacaoPanelLayout.setHorizontalGroup(
            pontuacaoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pontuacaoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pontuacaoLabel)
                .addContainerGap(103, Short.MAX_VALUE))
        );
        pontuacaoPanelLayout.setVerticalGroup(
            pontuacaoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pontuacaoPanelLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(pontuacaoLabel)
                .addContainerGap())
        );

        javax.swing.GroupLayout PanelFUNDOLayout = new javax.swing.GroupLayout(PanelFUNDO);
        PanelFUNDO.setLayout(PanelFUNDOLayout);
        PanelFUNDOLayout.setHorizontalGroup(
            PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFUNDOLayout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelFUNDOLayout.createSequentialGroup()
                        .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelFUNDOLayout.createSequentialGroup()
                                .addComponent(moedaicon, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PanelMoedas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pessoaicon)
                                .addGap(12, 12, 12)
                                .addComponent(nomePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(numeroperguntaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addGroup(PanelFUNDOLayout.createSequentialGroup()
                        .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PanelPergunta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Alternativa_3, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Alternativa_4, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Alternativa_5, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Alternativa_1, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Alternativa_2, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelFUNDOLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                                .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ajudasPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pontuacaoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 49, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelFUNDOLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(PanelContador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(110, 110, 110))))))
        );
        PanelFUNDOLayout.setVerticalGroup(
            PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFUNDOLayout.createSequentialGroup()
                .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(PanelFUNDOLayout.createSequentialGroup()
                        .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(moedaicon, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PanelFUNDOLayout.createSequentialGroup()
                                .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(pessoaicon)
                                    .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(nomePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(PanelMoedas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(7, 7, 7)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numeroperguntaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelFUNDOLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addComponent(PanelPergunta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelFUNDOLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PanelContador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)))
                .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelFUNDOLayout.createSequentialGroup()
                        .addComponent(Alternativa_1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(Alternativa_2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(Alternativa_3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(Alternativa_4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 20, Short.MAX_VALUE)
                        .addComponent(Alternativa_5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(44, Short.MAX_VALUE))
                    .addGroup(PanelFUNDOLayout.createSequentialGroup()
                        .addComponent(pontuacaoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ajudasPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(151, 151, 151))))
        );

        getContentPane().add(PanelFUNDO, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void iniciarTemporizador() {
        segundos = 45; // reinicia o tempo
        Contador.setText(String.valueOf(segundos));
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                segundos--;
                Contador.setText(String.valueOf(segundos));
                if (segundos <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "Tempo esgotado!");
                    ControleJogo.idsUsadas.clear();
                    TelaJogo.ajudaUniversitariaUsada = false;

                    TelaFimJogo telaFimJogo = new TelaFimJogo(TelaJogo.this);
                    telaFimJogo.setVisible(true);
                    dispose();
                }
            }
        });

        timer.start();
    }

    public void pararTemporizador() {
        timer.stop();
    }


    private void Alternativa_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Alternativa_1ActionPerformed
        RespostasDAO dao = new RespostasDAO();
        try {
            Respostas correta = dao.AlternativaCorreta(idPerguntaAtual);

            if (respostaSelecionadaA.getCorreta() == true) {
                new TelaConfirmacaoCorreta(this).setVisible(true);
         
                
            } else {
                TelaConfirmacaoErrada confirmacao = new TelaConfirmacaoErrada(this);
                confirmacao.setVisible(true);
            }

        } catch (Exception ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    

    }//GEN-LAST:event_Alternativa_1ActionPerformed


    private void Alternativa_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Alternativa_2ActionPerformed
        RespostasDAO dao = new RespostasDAO();
        try {
            Respostas correta = dao.AlternativaCorreta(idPerguntaAtual);

            if (respostaSelecionadaB.getCorreta() == true) {
                new TelaConfirmacaoCorreta(this).setVisible(true);
            
            } else {
                TelaConfirmacaoErrada confirmacao = new TelaConfirmacaoErrada(this);
                confirmacao.setVisible(true);
            }

        } catch (Exception ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_Alternativa_2ActionPerformed

    private void Alternativa_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Alternativa_3ActionPerformed

        RespostasDAO dao = new RespostasDAO();
        try {
            Respostas correta = dao.AlternativaCorreta(idPerguntaAtual);

            if (respostaSelecionadaC.getCorreta() == true) {
                new TelaConfirmacaoCorreta(this).setVisible(true);
          
            } else {
                TelaConfirmacaoErrada confirmacao = new TelaConfirmacaoErrada(this);
                confirmacao.setVisible(true);
            }

        } catch (Exception ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_Alternativa_3ActionPerformed

    private void Alternativa_4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Alternativa_4ActionPerformed
        // TODO add your handling code here:
        RespostasDAO dao = new RespostasDAO();
        try {
            Respostas correta = dao.AlternativaCorreta(idPerguntaAtual);

            if (respostaSelecionadaD.getCorreta() == true) {
                new TelaConfirmacaoCorreta(this).setVisible(true);
                
            } else {
                TelaConfirmacaoErrada confirmacao = new TelaConfirmacaoErrada(this);
                confirmacao.setVisible(true);
            }

        } catch (Exception ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_Alternativa_4ActionPerformed

    private void Alternativa_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Alternativa_5ActionPerformed
        RespostasDAO dao = new RespostasDAO();
        try {
            Respostas correta = dao.AlternativaCorreta(idPerguntaAtual);

            if (respostaSelecionadaE.getCorreta() == true) {
                new TelaConfirmacaoCorreta(this).setVisible(true);
                
            } else {
                TelaConfirmacaoErrada confirmacao = new TelaConfirmacaoErrada(this);
                confirmacao.setVisible(true);
            }

        } catch (Exception ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }



    }//GEN-LAST:event_Alternativa_5ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        iniciarTemporizador();
    }//GEN-LAST:event_formWindowOpened

    private void telefoneAjudaBotaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefoneAjudaBotaoActionPerformed
        // TODO add your handling code here:
        TelaAjudaTelefone tela = new TelaAjudaTelefone(
        respostaSelecionadaA,
        respostaSelecionadaB,        
        respostaSelecionadaC,
        respostaSelecionadaD,
        respostaSelecionadaE
       );
       tela.setVisible(true);
       ajudaTelefoneUsada = true;
       telefoneAjudaBotao.setVisible(false);

    }//GEN-LAST:event_telefoneAjudaBotaoActionPerformed

    private void pularAjudaBotaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pularAjudaBotaoActionPerformed
        // TODO add your handling code here:
        RespostasDAO dao = new RespostasDAO();

        TelaAjudarPular pularajudaTela = new TelaAjudarPular(this);
        pularajudaTela.setVisible(true);
        
        ajudaPularUsada = true;
        pularAjudaBotao.setVisible(false);
    }//GEN-LAST:event_pularAjudaBotaoActionPerformed

    private void estudantesAjudaBotaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estudantesAjudaBotaoActionPerformed
        // TODO add your handling code here:
        try {
            RespostasDAO dao = new RespostasDAO();
            Respostas respostaCorreta = dao.AlternativaCorreta(idPerguntaAtual);

            TelaAjudaEstudantes tela = new TelaAjudaEstudantes(respostaCorreta);
            tela.setVisible(true);


            ajudaUniversitariaUsada = true;
            estudantesAjudaBotao.setVisible(false);

        } catch (Exception ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_estudantesAjudaBotaoActionPerformed

    private void cortarAjudaBotaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cortarAjudaBotaoActionPerformed
        // TODO add your handling code here:
        TelaAjudaCortar telaAjuda = new TelaAjudaCortar(this);
        telaAjuda.setVisible(true);

        ajudaCortarUsada = true;
        cortarAjudaBotao.setVisible(false);

    }//GEN-LAST:event_cortarAjudaBotaoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    int contadorReinicios = 0;
                    ConnectionFactory connectionFactory = new ConnectionFactory();
                    
                    Connection conexao = connectionFactory.obterConexao();

                    new TelaJogo(idAluno, contadorReinicios, conexao).setVisible(true);

                } catch (Exception ex) {
                    Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, "Erro ao iniciar aplicação", ex);
                    JOptionPane.showMessageDialog(null, "Erro fatal: " + ex.getMessage());
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Alternativa_1;
    private javax.swing.JButton Alternativa_2;
    private javax.swing.JButton Alternativa_3;
    private javax.swing.JButton Alternativa_4;
    private javax.swing.JButton Alternativa_5;
    private javax.swing.JLabel Contador;
    private javax.swing.JPanel PanelContador;
    private javax.swing.JPanel PanelFUNDO;
    private javax.swing.JPanel PanelMoedas;
    private javax.swing.JPanel PanelPergunta;
    private javax.swing.JPanel ajudasPanel;
    private javax.swing.JLabel contadorLabel;
    private javax.swing.JButton cortarAjudaBotao;
    private javax.swing.JButton estudantesAjudaBotao;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel moedaicon;
    private javax.swing.JLabel moedasLabel;
    private javax.swing.JLabel nomeLabel;
    private javax.swing.JPanel nomePanel;
    private javax.swing.JPanel numeroperguntaPanel;
    private javax.swing.JLabel perguntaLabel;
    private javax.swing.JLabel pessoaicon;
    private javax.swing.JLabel pontuacaoLabel;
    private javax.swing.JPanel pontuacaoPanel;
    private javax.swing.JButton pularAjudaBotao;
    private javax.swing.JButton telefoneAjudaBotao;
    // End of variables declaration//GEN-END:variables
}
