package br.mycompany.poliedroamilhao.telas;

import br.mycompany.poliedroamilhao.persistencia.PontuacaoDAO;
import br.mycompany.poliedroamilhao.persistencia.UsuarioDAO;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TelaFimJogo extends javax.swing.JFrame {

    private TelaJogo telaJogo;

    public TelaFimJogo(TelaJogo telaJogo) {
        initComponents();
        this.telaJogo = telaJogo;
        this.setExtendedState(MAXIMIZED_BOTH);
        configurarPontuacao();
    }

    private void configurarPontuacao() {
        try {
            int reinicios = telaJogo.getContadorReinicios();
            int ganho = PontuacaoDAO.calcularPontuacao(reinicios);
            ganhoLabel.setText("Você ganhou " + ganho + " pontos!");
        } catch (Exception e) {
            e.printStackTrace();
            ganhoLabel.setText("Erro ao atualizar pontuação.");
        }
    }

    private void reiniciarAjudas() {
        TelaJogo.ajudaUniversitariaUsada = false;
        TelaJogo.ajudaCortarUsada = false;
        TelaJogo.ajudaPularUsada = false;
        TelaJogo.ajudaTelefoneUsada = false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jogarnovamenteBotao = new javax.swing.JButton();
        sairBotao = new javax.swing.JButton();
        poliedroIcone = new javax.swing.JLabel();
        fimDeJogoPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        ganhoLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(163, 236, 255));

        jogarnovamenteBotao.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jogarnovamenteBotao.setText("Jogar Novamente");
        jogarnovamenteBotao.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        jogarnovamenteBotao.setFocusPainted(false);
        jogarnovamenteBotao.setFocusable(false);
        jogarnovamenteBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jogarnovamenteBotaoActionPerformed(evt);
            }
        });

        sairBotao.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        sairBotao.setText("Sair");
        sairBotao.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        sairBotao.setFocusPainted(false);
        sairBotao.setFocusable(false);
        sairBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sairBotaoActionPerformed(evt);
            }
        });

        poliedroIcone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/LogoPoliedroAMIlhao-removebg-500png.png"))); // NOI18N

        fimDeJogoPanel.setBackground(new java.awt.Color(173, 230, 244));
        fimDeJogoPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("FIM DE JOGO");

        jPanel3.setBackground(new java.awt.Color(135, 217, 237));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        ganhoLabel.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        ganhoLabel.setForeground(new java.awt.Color(255, 255, 255));
        ganhoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ganhoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/POLIEDROMOEDA-removebg-preview.png"))); // NOI18N
        ganhoLabel.setText("+ (GANHO)");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(144, Short.MAX_VALUE)
                .addComponent(ganhoLabel)
                .addContainerGap(132, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(ganhoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout fimDeJogoPanelLayout = new javax.swing.GroupLayout(fimDeJogoPanel);
        fimDeJogoPanel.setLayout(fimDeJogoPanelLayout);
        fimDeJogoPanelLayout.setHorizontalGroup(
            fimDeJogoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fimDeJogoPanelLayout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
            .addGroup(fimDeJogoPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        fimDeJogoPanelLayout.setVerticalGroup(
            fimDeJogoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fimDeJogoPanelLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(280, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sairBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jogarnovamenteBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(280, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(poliedroIcone, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(fimDeJogoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(97, Short.MAX_VALUE)
                .addComponent(poliedroIcone)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(fimDeJogoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jogarnovamenteBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(sairBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jogarnovamenteBotaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jogarnovamenteBotaoActionPerformed
        try {
            reiniciarAjudas();
            String nome = telaJogo.getNome();
            int reinicios = telaJogo.getContadorReinicios();
            int pontosGanhos = PontuacaoDAO.calcularPontuacao(reinicios);

            UsuarioDAO dao = new UsuarioDAO();
            dao.garantirPremiacaoExiste(pontosGanhos, pontosGanhos);
            dao.atualizarPontuacaoTotalSomando(nome, pontosGanhos);
            dao.migrarPontuacaoTotalParaPontuacao();

            TelaModos telaModos = new TelaModos(nome);
            telaModos.setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(TelaFimJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jogarnovamenteBotaoActionPerformed

    private void sairBotaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sairBotaoActionPerformed
        try {
            String nome = telaJogo.getNome();
            int reinicios = telaJogo.getContadorReinicios();
            int pontosGanhos = PontuacaoDAO.calcularPontuacao(reinicios);

            UsuarioDAO dao = new UsuarioDAO();
            dao.garantirPremiacaoExiste(pontosGanhos, pontosGanhos);
            dao.atualizarPontuacaoTotalSomando(nome, pontosGanhos);
            dao.migrarPontuacaoTotalParaPontuacao();

            System.exit(0);
        } catch (Exception ex) {
            Logger.getLogger(TelaFimJogo.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_sairBotaoActionPerformed

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
            java.util.logging.Logger.getLogger(TelaFimJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaFimJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaFimJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaFimJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel fimDeJogoPanel;
    private javax.swing.JLabel ganhoLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton jogarnovamenteBotao;
    private javax.swing.JLabel poliedroIcone;
    private javax.swing.JButton sairBotao;
    // End of variables declaration//GEN-END:variables
}
