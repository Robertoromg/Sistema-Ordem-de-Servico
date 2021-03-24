package br.com.infox.telas;
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
//importando recurso r2xml
import net.proteanit.sql.DbUtils;
/**
 *
 * @author rober
 */
public class TelaRelatorio extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    
    public TelaRelatorio() {
        initComponents();
        conexao= ModuloConexao.conector();
    }
    
     
      
     //metodo filtrar pesquisa por nome
      
      private void pesquisar_clientes(){
          String sql = "select * from tbclientes where nomecli like ?";
          try{
              
              pst = conexao.prepareStatement(sql);
              pst.setString(1,txtCliPesquisar.getText()+"%");
              rs= pst.executeQuery();
              
             // usando biblioteca para pesquisar tabelas
             
             DefaultTableModel modelo = new DefaultTableModel();
            tblClientes.setModel(modelo);
            
            modelo.addColumn("Cod");
            modelo.addColumn("Nome");
            modelo.addColumn("Endereço");
            modelo.addColumn("Fone");
            
            
            while(rs.next()){
               
                int cod = rs.getInt("idcli");
                String nome = rs.getString("nomecli");
                String endereco =  rs.getString("endcli");
                String fone = rs.getString("fonecli");
                
                
                modelo.addRow(new Object[] {cod, nome, endereco, fone});
            } 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }   
      }
      
       private void pesquisarRelOs(){
          String codCliente = txtRelOs.getText();
          String sql = "select * from tbos where idcli=  "+codCliente;
          try{
              
              pst = conexao.prepareStatement(sql);
             
              rs= pst.executeQuery();
              
             // usando biblioteca para pesquisar tabelas
             
             DefaultTableModel modelo = new DefaultTableModel();
            tblRelOs.setModel(modelo);
            
            modelo.addColumn("Num da OS");
            modelo.addColumn("Data da OS");
            modelo.addColumn("Tipo");
            modelo.addColumn("Situação");
            modelo.addColumn("Serviço");
            
            
            while(rs.next()){
               
                int os = rs.getInt("os");
                String data_os = rs.getString("data_os");
                String tipo_os =  rs.getString("tipo");
                String situacao_os = rs.getString("situacao");
                String servico_os = rs.getString("servico");
                
                
                modelo.addRow(new Object[] {os, data_os, tipo_os,
                situacao_os, servico_os});
            } 
            
        } catch (java.sql.SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "Código inválido, digite apenas números");
              System.out.println(e);
        } catch (SQLException ex) {
            Logger.getLogger(TelaRelatorio.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtCliPesquisar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRelOs = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtRelOs = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Relatórios");

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Código ", "Nome:", "Endereço", "Telefone:"
            }
        ));
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/busca.png"))); // NOI18N

        txtCliPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliPesquisarActionPerformed(evt);
            }
        });
        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Buscar cliente ");

        tblRelOs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Num. da OS", "Data da OS", "Tipo", "Situação", "Serviço"
            }
        ));
        tblRelOs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRelOsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblRelOs);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Digite o código do cliente");

        txtRelOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRelOsActionPerformed(evt);
            }
        });
        txtRelOs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRelOsKeyReleased(evt);
            }
        });

        jButton1.setText("Pesquisar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addGap(0, 9, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRelOs, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jButton1)
                    .addComponent(txtRelOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addContainerGap())
        );

        setBounds(0, 0, 642, 481);
    }// </editor-fold>//GEN-END:initComponents

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // meto setar campos setarCampos
        
    }//GEN-LAST:event_tblClientesMouseClicked

    private void txtCliPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliPesquisarActionPerformed

    }//GEN-LAST:event_txtCliPesquisarActionPerformed

    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // chamar metodo pesquisar_clientes
        pesquisar_clientes();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    private void tblRelOsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRelOsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblRelOsMouseClicked

    private void txtRelOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRelOsActionPerformed
        pesquisarRelOs();
    }//GEN-LAST:event_txtRelOsActionPerformed

    private void txtRelOsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRelOsKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRelOsKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        pesquisarRelOs();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTable tblRelOs;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txtRelOs;
    // End of variables declaration//GEN-END:variables
}
