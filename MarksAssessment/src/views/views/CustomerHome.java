/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.views;

import models.Customer;
import models.DBManager;

/**
 *
 * @author 30251094
 */
public class CustomerHome extends javax.swing.JFrame {
    private Customer loggedInCustomer;
    /**
     * Creates new form CustomerHome
     */
    public CustomerHome(Customer customer) {
        loggedInCustomer = customer;
        initComponents();
        //displays a greeting to the customer
        lblMessage.setText(loggedInCustomer.displayGreeting());
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BtnProducts = new javax.swing.JButton();
        BtnMyOrder = new javax.swing.JButton();
        BtnEdit = new javax.swing.JButton();
        btnUnregister = new javax.swing.JButton();
        BtnLogout = new javax.swing.JButton();
        lblMessage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BtnProducts.setText("Browse Products");
        BtnProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnProductsActionPerformed(evt);
            }
        });

        BtnMyOrder.setText("View my Orders");
        BtnMyOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMyOrderActionPerformed(evt);
            }
        });

        BtnEdit.setText("Edit Details");
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });

        btnUnregister.setText("Unregister from Shop");
        btnUnregister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnregisterActionPerformed(evt);
            }
        });

        BtnLogout.setText("Logout");
        BtnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BtnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUnregister, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnLogout, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnMyOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnProducts, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(122, 122, 122))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMessage)
                .addGap(181, 181, 181))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblMessage)
                .addGap(18, 18, 18)
                .addComponent(BtnProducts)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnMyOrder)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUnregister)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnLogout)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //sends the user to the view products page when clicked
    private void BtnProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnProductsActionPerformed
        ViewProducts viewProducts = new ViewProducts(loggedInCustomer);
        this.dispose();
        viewProducts.setVisible(true);
        
    }//GEN-LAST:event_BtnProductsActionPerformed
    //sends the user to the main menu page when clicked
    private void BtnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLogoutActionPerformed
        MainMenu mainmenu = new MainMenu();
        this.dispose();
        mainmenu.setVisible(true);
    }//GEN-LAST:event_BtnLogoutActionPerformed

    //sends the user to the edit customer page when clicked
    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        EditCustomer editCustomer = new EditCustomer(loggedInCustomer);
        this.dispose();
        editCustomer.setVisible(true);
    }//GEN-LAST:event_BtnEditActionPerformed
    //unregisters the logged in customer from the database
    private void btnUnregisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnregisterActionPerformed
        DBManager db = new DBManager();
        db.deleteCustomer(loggedInCustomer);
        MainMenu mainMenu = new MainMenu();
        this.dispose();
        mainMenu.setVisible(true);
    }//GEN-LAST:event_btnUnregisterActionPerformed
    //sends the user to the view orders page when clicked
    private void BtnMyOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMyOrderActionPerformed
        CustomerViewOrders viewOrders = new CustomerViewOrders(loggedInCustomer);
        this.dispose();
        viewOrders.setVisible(true);
    }//GEN-LAST:event_BtnMyOrderActionPerformed

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
            java.util.logging.Logger.getLogger(CustomerHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new CustomerHome().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnEdit;
    private javax.swing.JButton BtnLogout;
    private javax.swing.JButton BtnMyOrder;
    private javax.swing.JButton BtnProducts;
    private javax.swing.JButton btnUnregister;
    private javax.swing.JLabel lblMessage;
    // End of variables declaration//GEN-END:variables
}
