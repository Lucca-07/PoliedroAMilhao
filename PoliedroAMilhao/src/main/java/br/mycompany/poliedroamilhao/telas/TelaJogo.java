
package br.mycompany.poliedroamilhao.telas;

import br.mycompany.poliedroamilhao.modelo.Perguntas;
import br.mycompany.poliedroamilhao.modelo.Respostas;
import br.mycompany.poliedroamilhao.persistencia.ConnectionFactory;
import br.mycompany.poliedroamilhao.persistencia.PerguntasDificilDAO;
import br.mycompany.poliedroamilhao.persistencia.PerguntasFacilDAO;
import br.mycompany.poliedroamilhao.persistencia.PerguntasMediaDAO;
import br.mycompany.poliedroamilhao.persistencia.PontuacaoDAO;
import br.mycompany.poliedroamilhao.persistencia.RespostaDAO;
import br.mycompany.poliedroamilhao.persistencia.UsuarioDAO;
import java.util.List;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TelaJogo extends javax.swing.JFrame {

    public int contadorReinicios = PontuacaoDAO.idsUsadas.size() + 1;
    private Timer timer;
    private int segundos = 45;
    private int idPerguntaAtual;
    private static String nome;
    private static int idMateria;
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
    

    public TelaJogo(int reinicios, Connection obterConexao, String nome, int idMateria) throws Exception {
        initComponents();
        PontuacaoDAO controle = new PontuacaoDAO();
        controle.iniciarNovaPontuacaoParaAluno(nome, obterConexao);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.obterConexao = obterConexao;
        this.contadorReinicios = reinicios;
        TelaJogo.nome = nome;
        TelaJogo.idMateria = idMateria;
        nomeLabel.setText(nome);
        atualizarPontuacao(contadorReinicios);
        moedasTotais();
   
        
        try {
            //Guarda a pergunta atual
            Perguntas perguntaAtual = null;
            
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
                var daoresposta = new RespostaDAO();

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
        UsuarioDAO dao = new UsuarioDAO();

        int idPremiacao = dao.buscarPontuacaoPorNome(nome);

        int moedas = dao.buscarPontuacaoPorId(idPremiacao);

        moedasLabel.setText("Moedas: " + moedas);

    }
    
    private void mostrarPontuacaoTotal() {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            int pontuacaoTotal = dao.buscarPontuacaoPorNome(nome);
            moedasLabel.setText("Moedas Totais: " + pontuacaoTotal);
        } catch (Exception ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getContadorReinicios() {       
        return this.contadorReinicios;
    }
    public String getNome() {
        return this.nome;
    }
    
    public void atualizarPontuacao(int reinicios) {
        int pontuacaoAtual;

        pontuacaoAtual = switch (reinicios) {
            case 2 -> 1250;
            case 3 -> 2500;
            case 4 -> 3750;
            case 5 -> 5000;
            case 6 -> 10000;
            case 7 -> 25000;
            case 8 -> 50000;
            case 9 -> 100000;
            case 10 -> 250000;
            case 11 -> 500000;
            case 12 -> 750000;
            case 13 -> 1000000;
            default -> 0;
        };
        // mostra na tela
        pontuacaoLabel.setText("Pontuação: " + pontuacaoAtual);
       
    }

    

    public void proximaPergunta(String nome) throws Exception {
        atualizarPontuacao(contadorReinicios);
        contadorReinicios++;
        PontuacaoDAO.idsUsadas.add(idPerguntaAtual);
        if (contadorReinicios == 13) {
            TelaVitoriaJogo telaVitoriaJogo = new TelaVitoriaJogo(this);
            telaVitoriaJogo.setVisible(true);
        } else {
            TelaJogo novaTela = new TelaJogo(contadorReinicios, obterConexao, nome, idMateria);
            novaTela.setVisible(true);
        }
        this.dispose();
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
            case "A" -> Alternativa_1.setVisible(false);
            case "B" -> Alternativa_2.setVisible(false);
            case "C" -> Alternativa_3.setVisible(false);
            case "D" -> Alternativa_4.setVisible(false);
            case "E" -> Alternativa_5.setVisible(false);
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
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        PanelFUNDO.setBackground(new java.awt.Color(163, 236, 255));
        PanelFUNDO.setPreferredSize(new java.awt.Dimension(1360, 768));

        PanelContador.setBackground(new java.awt.Color(255, 255, 255));
        PanelContador.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        Contador.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Contador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Contador.setText("45 segundos");

        javax.swing.GroupLayout PanelContadorLayout = new javax.swing.GroupLayout(PanelContador);
        PanelContador.setLayout(PanelContadorLayout);
        PanelContadorLayout.setHorizontalGroup(
            PanelContadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelContadorLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(Contador)
                .addContainerGap(23, Short.MAX_VALUE))
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
        PanelPergunta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        perguntaLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        perguntaLabel.setText("PERGUNTA");

        javax.swing.GroupLayout PanelPerguntaLayout = new javax.swing.GroupLayout(PanelPergunta);
        PanelPergunta.setLayout(PanelPerguntaLayout);
        PanelPerguntaLayout.setHorizontalGroup(
            PanelPerguntaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPerguntaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(perguntaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanelPerguntaLayout.setVerticalGroup(
            PanelPerguntaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPerguntaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(perguntaLabel)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        Alternativa_5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        Alternativa_5.setText("Opção 5");
        Alternativa_5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        Alternativa_5.setFocusPainted(false);
        Alternativa_5.setFocusable(false);
        Alternativa_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Alternativa_5ActionPerformed(evt);
            }
        });

        Alternativa_4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        Alternativa_4.setText("Opção 4");
        Alternativa_4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        Alternativa_4.setFocusPainted(false);
        Alternativa_4.setFocusable(false);
        Alternativa_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Alternativa_4ActionPerformed(evt);
            }
        });

        Alternativa_3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        Alternativa_3.setText("Opção 3");
        Alternativa_3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        Alternativa_3.setFocusPainted(false);
        Alternativa_3.setFocusable(false);
        Alternativa_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Alternativa_3ActionPerformed(evt);
            }
        });

        Alternativa_2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        Alternativa_2.setText("Opção 2");
        Alternativa_2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        Alternativa_2.setFocusPainted(false);
        Alternativa_2.setFocusable(false);
        Alternativa_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Alternativa_2ActionPerformed(evt);
            }
        });

        Alternativa_1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        Alternativa_1.setText("Opção 1");
        Alternativa_1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        Alternativa_1.setFocusPainted(false);
        Alternativa_1.setFocusable(false);
        Alternativa_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Alternativa_1ActionPerformed(evt);
            }
        });

        ajudasPanel.setBackground(new java.awt.Color(147, 232, 255));
        ajudasPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(70, 213, 251), 2, true));

        cortarAjudaBotao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cortar.png"))); // NOI18N
        cortarAjudaBotao.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        cortarAjudaBotao.setFocusPainted(false);
        cortarAjudaBotao.setFocusable(false);
        cortarAjudaBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cortarAjudaBotaoActionPerformed(evt);
            }
        });

        estudantesAjudaBotao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/universitarioss.png"))); // NOI18N
        estudantesAjudaBotao.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        estudantesAjudaBotao.setFocusPainted(false);
        estudantesAjudaBotao.setFocusable(false);
        estudantesAjudaBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estudantesAjudaBotaoActionPerformed(evt);
            }
        });

        pularAjudaBotao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pular.png"))); // NOI18N
        pularAjudaBotao.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        pularAjudaBotao.setFocusPainted(false);
        pularAjudaBotao.setFocusable(false);
        pularAjudaBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pularAjudaBotaoActionPerformed(evt);
            }
        });

        telefoneAjudaBotao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/telefone.png"))); // NOI18N
        telefoneAjudaBotao.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        telefoneAjudaBotao.setFocusPainted(false);
        telefoneAjudaBotao.setFocusable(false);
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
                .addGap(0, 51, Short.MAX_VALUE)
                .addGroup(ajudasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ajudasPanelLayout.createSequentialGroup()
                        .addComponent(telefoneAjudaBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 48, Short.MAX_VALUE)
                        .addComponent(estudantesAjudaBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ajudasPanelLayout.createSequentialGroup()
                        .addComponent(pularAjudaBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(cortarAjudaBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 48, Short.MAX_VALUE))
        );
        ajudasPanelLayout.setVerticalGroup(
            ajudasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ajudasPanelLayout.createSequentialGroup()
                .addGap(0, 29, Short.MAX_VALUE)
                .addGroup(ajudasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(telefoneAjudaBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(estudantesAjudaBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 25, Short.MAX_VALUE)
                .addGroup(ajudasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cortarAjudaBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pularAjudaBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 22, Short.MAX_VALUE))
        );

        moedaicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/POLIEDROMOEDA-removebg-preview.png"))); // NOI18N

        PanelMoedas.setBackground(new java.awt.Color(255, 255, 255));
        PanelMoedas.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

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
        nomePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

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
        numeroperguntaPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        contadorLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        contadorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout numeroperguntaPanelLayout = new javax.swing.GroupLayout(numeroperguntaPanel);
        numeroperguntaPanel.setLayout(numeroperguntaPanelLayout);
        numeroperguntaPanelLayout.setHorizontalGroup(
            numeroperguntaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, numeroperguntaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contadorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                .addContainerGap())
        );
        numeroperguntaPanelLayout.setVerticalGroup(
            numeroperguntaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, numeroperguntaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contadorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addContainerGap())
        );

        pontuacaoPanel.setBackground(new java.awt.Color(255, 255, 255));
        pontuacaoPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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
                .addComponent(pontuacaoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addContainerGap())
        );
        pontuacaoPanelLayout.setVerticalGroup(
            pontuacaoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pontuacaoPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pontuacaoLabel)
                .addContainerGap())
        );

        jButton2.setBackground(new java.awt.Color(204, 255, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Sair.png"))); // NOI18N
        jButton2.setText("Sair");
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setFocusPainted(false);
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("TIMER:");

        javax.swing.GroupLayout PanelFUNDOLayout = new javax.swing.GroupLayout(PanelFUNDO);
        PanelFUNDO.setLayout(PanelFUNDOLayout);
        PanelFUNDOLayout.setHorizontalGroup(
            PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFUNDOLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Alternativa_3, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                    .addComponent(Alternativa_4, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                    .addComponent(Alternativa_5, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                    .addComponent(Alternativa_1, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                    .addComponent(Alternativa_2, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                    .addGroup(PanelFUNDOLayout.createSequentialGroup()
                        .addComponent(moedaicon, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PanelMoedas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pessoaicon)
                        .addGap(12, 12, 12)
                        .addComponent(nomePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 162, Short.MAX_VALUE)
                        .addComponent(numeroperguntaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(PanelPergunta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelFUNDOLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelFUNDOLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PanelFUNDOLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelFUNDOLayout.createSequentialGroup()
                                .addComponent(PanelContador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pontuacaoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(29, Short.MAX_VALUE))
                            .addGroup(PanelFUNDOLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelFUNDOLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ajudasPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        PanelFUNDOLayout.setVerticalGroup(
            PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFUNDOLayout.createSequentialGroup()
                .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(moedaicon, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(PanelFUNDOLayout.createSequentialGroup()
                            .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(pessoaicon)
                                .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(nomePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(PanelMoedas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(7, 7, 7)))
                    .addGroup(PanelFUNDOLayout.createSequentialGroup()
                        .addContainerGap(18, Short.MAX_VALUE)
                        .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(numeroperguntaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelPergunta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelFUNDOLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(PanelContador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pontuacaoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelFUNDOLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addComponent(Alternativa_1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(Alternativa_2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(Alternativa_3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE))
                    .addGroup(PanelFUNDOLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ajudasPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(PanelFUNDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelFUNDOLayout.createSequentialGroup()
                        .addComponent(Alternativa_4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(Alternativa_5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelFUNDOLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelFUNDO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelFUNDO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void iniciarTemporizador() {
        segundos = 45; // reinicia o tempo
        Contador.setText(String.valueOf(segundos) + "segundos");
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                segundos--;
                Contador.setText(String.valueOf(segundos));
                if (segundos <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "Tempo esgotado!");
                    PontuacaoDAO.idsUsadas.clear();
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
        RespostaDAO dao = new RespostaDAO();
        try {
            Respostas correta = dao.AlternativaCorreta(idPerguntaAtual);

            if (respostaSelecionadaA.getCorreta() == true) {
                new TelaConfirmacaoCorreta(this, nome).setVisible(true);
         
                
            } else {
                TelaConfirmacaoErrada confirmacao = new TelaConfirmacaoErrada(this);
                confirmacao.setVisible(true);
            }

        } catch (Exception ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    

    }//GEN-LAST:event_Alternativa_1ActionPerformed


    private void Alternativa_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Alternativa_2ActionPerformed
        RespostaDAO dao = new RespostaDAO();
        try {
            Respostas correta = dao.AlternativaCorreta(idPerguntaAtual);

            if (respostaSelecionadaB.getCorreta() == true) {
                new TelaConfirmacaoCorreta(this, nome).setVisible(true);
            
            } else {
                TelaConfirmacaoErrada confirmacao = new TelaConfirmacaoErrada(this);
                confirmacao.setVisible(true);
            }

        } catch (Exception ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_Alternativa_2ActionPerformed

    private void Alternativa_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Alternativa_3ActionPerformed

        RespostaDAO dao = new RespostaDAO();
        try {
            Respostas correta = dao.AlternativaCorreta(idPerguntaAtual);

            if (respostaSelecionadaC.getCorreta() == true) {
                new TelaConfirmacaoCorreta(this, nome).setVisible(true);
          
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
        RespostaDAO dao = new RespostaDAO();
        try {
            Respostas correta = dao.AlternativaCorreta(idPerguntaAtual);

            if (respostaSelecionadaD.getCorreta() == true) {
                TelaConfirmacaoCorreta tcc = new TelaConfirmacaoCorreta(this, nome);
                tcc.setVisible(true);
                
            } else {
                TelaConfirmacaoErrada confirmacao = new TelaConfirmacaoErrada(this);
                confirmacao.setVisible(true);
            }

        } catch (Exception ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_Alternativa_4ActionPerformed

    private void Alternativa_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Alternativa_5ActionPerformed
        RespostaDAO dao = new RespostaDAO();
        try {
            Respostas correta = dao.AlternativaCorreta(idPerguntaAtual);

            if (respostaSelecionadaE.getCorreta() == true) {
                new TelaConfirmacaoCorreta(this, nome).setVisible(true);
                
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
        RespostaDAO dao = new RespostaDAO();

        TelaAjudarPular pularajudaTela = new TelaAjudarPular(this);
        pularajudaTela.setVisible(true);
        
        ajudaPularUsada = true;
        pularAjudaBotao.setVisible(false);
    }//GEN-LAST:event_pularAjudaBotaoActionPerformed

    private void estudantesAjudaBotaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estudantesAjudaBotaoActionPerformed
        // TODO add your handling code here:
        try {
            RespostaDAO dao = new RespostaDAO();
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        SairTelaAluno sta = new SairTelaAluno(this);
        sta.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

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

                    new TelaJogo(contadorReinicios, conexao, nome, idMateria).setVisible(true);

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
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
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
