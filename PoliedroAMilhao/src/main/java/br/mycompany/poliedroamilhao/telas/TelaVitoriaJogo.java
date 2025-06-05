
package br.mycompany.poliedroamilhao.telas;

import br.mycompany.poliedroamilhao.persistencia.PontuacaoDAO;
import br.mycompany.poliedroamilhao.persistencia.UsuarioDAO;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diego
 */
public class TelaVitoriaJogo extends javax.swing.JFrame {

   private TelaJogo telaJogo;
   
    public TelaVitoriaJogo() {
        initComponents();
    }
    public TelaVitoriaJogo(TelaJogo telaJogo) {
        initComponents();
        this.telaJogo = telaJogo;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fundoPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jogarnovamenteBotao = new javax.swing.JButton();
        sairBotao = new javax.swing.JButton();
        parabensPanel = new javax.swing.JPanel();
        parabensLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        fundoPanel.setBackground(new java.awt.Color(163, 236, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/LogoPoliedroAMIlhao-removebg-500png.png"))); // NOI18N

        jogarnovamenteBotao.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jogarnovamenteBotao.setText("Jogar Novamente");
        jogarnovamenteBotao.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        jogarnovamenteBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jogarnovamenteBotaoActionPerformed(evt);
            }
        });

        sairBotao.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        sairBotao.setText("Sair");
        sairBotao.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        sairBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sairBotaoActionPerformed(evt);
            }
        });

        parabensPanel.setBackground(new java.awt.Color(173, 230, 244));
        parabensPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        parabensPanel.setForeground(new java.awt.Color(255, 255, 255));

        parabensLabel.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        parabensLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        parabensLabel.setText("PARABÉNS, VOCÊ COMPLETOU O POLIEDRO A MILHÃO!");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/moedaPOLIEDRO-removebg-150.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Showcard Gothic", 0, 48)); // NOI18N
        jLabel2.setText("+ 1.000.000");

        javax.swing.GroupLayout parabensPanelLayout = new javax.swing.GroupLayout(parabensPanel);
        parabensPanel.setLayout(parabensPanelLayout);
        parabensPanelLayout.setHorizontalGroup(
            parabensPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parabensPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parabensPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(parabensLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1157, Short.MAX_VALUE)
                    .addGroup(parabensPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        parabensPanelLayout.setVerticalGroup(
            parabensPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parabensPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(parabensLabel)
                .addGroup(parabensPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(parabensPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addGroup(parabensPanelLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel2)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout fundoPanelLayout = new javax.swing.GroupLayout(fundoPanel);
        fundoPanel.setLayout(fundoPanelLayout);
        fundoPanelLayout.setHorizontalGroup(
            fundoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fundoPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(fundoPanelLayout.createSequentialGroup()
                .addContainerGap(93, Short.MAX_VALUE)
                .addComponent(parabensPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fundoPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(fundoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sairBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jogarnovamenteBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(263, 263, 263))
        );
        fundoPanelLayout.setVerticalGroup(
            fundoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fundoPanelLayout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(parabensPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jogarnovamenteBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(sairBotao, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fundoPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fundoPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void sairBotaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sairBotaoActionPerformed
        try {
            String nome = telaJogo.getNome();
            int reinicios = telaJogo.getContadorReinicios();

            // Calcula os pontos ganhos com base nos reinícios
            int pontosGanhos = PontuacaoDAO.calcularPontuacao(reinicios);

            UsuarioDAO dao = new UsuarioDAO();

            // Garante que há uma premiação com esse valor
            dao.garantirPremiacaoExiste(pontosGanhos, pontosGanhos);

            // Atualiza o campo PontuacaoTotal SOMANDO os pontos ganhos
            dao.atualizarPontuacaoTotalSomando(nome, pontosGanhos);

            //  SINCRONIZA OS CAMPOS PONTUACAO COM PONTUACAOTOTAL 
            dao.migrarPontuacaoTotalParaPontuacao();

            System.exit(0);
        } catch (Exception ex) {
            Logger.getLogger(TelaFimJogo.class.getName()).log(Level.SEVERE, null, ex);
        }



    }//GEN-LAST:event_sairBotaoActionPerformed

    private void jogarnovamenteBotaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jogarnovamenteBotaoActionPerformed
        // TODO add your handling code here:
        try {
            TelaJogo.ajudaUniversitariaUsada = false;
            TelaJogo.ajudaCortarUsada = false;
            TelaJogo.ajudaPularUsada = false;
            TelaJogo.ajudaTelefoneUsada = false;

            String nome = telaJogo.getNome();
            int reinicios = telaJogo.getContadorReinicios();

            // Calcula os pontos ganhos com base nos reinícios
            int pontosGanhos = PontuacaoDAO.calcularPontuacao(reinicios);

            UsuarioDAO dao = new UsuarioDAO();

            // Garante a premiação existir com esse valor
            dao.garantirPremiacaoExiste(pontosGanhos, pontosGanhos);

            // Soma esses pontos à pontuação total
            dao.atualizarPontuacaoTotalSomando(nome, pontosGanhos);

            // SINCRONIZA OS CAMPOS PONTUACAO COM PONTUACAOTOTAL 
            dao.migrarPontuacaoTotalParaPontuacao();

            // Próxima tela
            TelaModos telaModos = new TelaModos(nome);
            telaModos.setVisible(true);
            this.dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(TelaFimJogo.class.getName()).log(Level.SEVERE, null, ex);
        }


    
    }//GEN-LAST:event_jogarnovamenteBotaoActionPerformed

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
            java.util.logging.Logger.getLogger(TelaVitoriaJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaVitoriaJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaVitoriaJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaVitoriaJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaVitoriaJogo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel fundoPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton jogarnovamenteBotao;
    private javax.swing.JLabel parabensLabel;
    private javax.swing.JPanel parabensPanel;
    private javax.swing.JButton sairBotao;
    // End of variables declaration//GEN-END:variables
}
