package cuka.martin.netbeans.gui;

/**
 * Vygenerovane modalne okno cez netbeans, vyskoci pri pokuse o zatvorenie hry,
 * vyzve uzivatela o potvrdenie volby
 *
 * @author Martin Čuka
 */
public class ExitDialog extends javax.swing.JDialog {

    /**
     * Creates new form ExitDialog
     */
    public ExitDialog() {
        initComponents();
        setModal(true);
        javatarIcon1.startThread();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javatarIcon1 = new cuka.martin.netbeans.gui.JavatarIcon();
        labelMassage = new javax.swing.JLabel();
        buttonYes = new javax.swing.JButton();
        buttonNo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout javatarIcon1Layout = new javax.swing.GroupLayout(javatarIcon1);
        javatarIcon1.setLayout(javatarIcon1Layout);
        javatarIcon1Layout.setHorizontalGroup(
            javatarIcon1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        javatarIcon1Layout.setVerticalGroup(
            javatarIcon1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        labelMassage.setText("Exit the application?");

        buttonYes.setText("Yes");
        buttonYes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonYesActionPerformed(evt);
            }
        });

        buttonNo.setText("No");
        buttonNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(javatarIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelMassage)
                .addContainerGap(16, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(buttonYes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonNo)
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(javatarIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(labelMassage)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonYes)
                    .addComponent(buttonNo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Vykona akciu po kliknuti na "no" button - zrusi okno
     *
     * @param evt - parameter vygenerovany netbeansom (nepotrebny)
     */
    private void buttonNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNoActionPerformed
        setVisible(false);
        dispose();
    }//GEN-LAST:event_buttonNoActionPerformed

    /**
     * Vykona akciu po kliknuti na "yes" button - vypne celu aplikaciu
     *
     * @param evt - parameter vygenerovany netbeansom (nepotrebny)
     */
    private void buttonYesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonYesActionPerformed
        System.out.println("Thanks for playing");
        Runtime.getRuntime().halt(0);
    }//GEN-LAST:event_buttonYesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonNo;
    private javax.swing.JButton buttonYes;
    private cuka.martin.netbeans.gui.JavatarIcon javatarIcon1;
    private javax.swing.JLabel labelMassage;
    // End of variables declaration//GEN-END:variables
}
