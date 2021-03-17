/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

/**
 *
 * @author rober
 */
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.JOptionPane;
public class TelaUsuario extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    
    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conector();
    }
    
    private void consultar(){
        String sql = "select * from tbusuarios where idUser=?";
        
        try{
            pst= conexao.prepareStatement(sql);
            pst.setString(1,txtUsuId.getText());
            rs = pst.executeQuery();
            if(rs.next()){
                txtUsuNome.setText(rs.getString(2));
                txtUsuFone.setText(rs.getString(3));
                txtusuLogin.setText(rs.getString(4));
                txtUsuSenha.setText(rs.getString(5));
                cboUsuPerfil.setSelectedItem(rs.getString(6));        
            }else{
                JOptionPane.showMessageDialog(null,"Código não corresponde à nenhum" +"\n usuário cadastrado");
                //linhas para limpar campos
                txtUsuNome.setText(null);
                txtUsuFone.setText(null);
                txtusuLogin.setText(null);
                txtUsuSenha.setText(null);
                   
                    }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    //método para diciona usuário
    private void adicionar(){
        String sql = "insert into tbusuarios(idUser, usuario, fone, login, senha, perfil)values(?,?,?,?,?,?)";
        try{
            
            pst = conexao.prepareStatement(sql);
            pst.setString(1,txtUsuId.getText());
            pst.setString(2,txtUsuNome.getText());
            pst.setString(3,txtUsuFone.getText());
            pst.setString(4,txtusuLogin.getText());
            pst.setString(5,txtUsuSenha.getText());
            pst.setString(6,cboUsuPerfil.getSelectedItem().toString());
            
            //validação dos campos obrigatórios
            if(txtUsuId.getText().isEmpty()|| (txtUsuNome.getText().isEmpty())||
              (txtUsuFone.getText().isEmpty())|| (txtusuLogin.getText().isEmpty()) ||     
                    (txtUsuSenha.getText().isEmpty())
                    )
            
            {
                JOptionPane.showMessageDialog(null,"Campos Obrigatórios");
            }
            
            else{
            //atualizar tabela usuario c/ dados do formulário
            int adicionado = pst.executeUpdate();
            if(adicionado>0){
                JOptionPane.showMessageDialog(null,"Usuário adicionado com sucesso");
                //limpa os campos apos adicionar ao banco de dados
                txtUsuId.setText(null);
                txtUsuNome.setText(null);
                txtUsuFone.setText(null);
                txtusuLogin.setText(null);
                txtUsuSenha.setText(null);
                
            }
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    
    }
    
    private void alterar(){
        String sql = "update tbusuarios set usuario=?, fone=?, login=?,senha=?, perfil=? where idUser=?";
       try{
            
            pst = conexao.prepareStatement(sql);
            
            pst.setString(1,txtUsuNome.getText());
            pst.setString(2,txtUsuFone.getText());
            pst.setString(3,txtusuLogin.getText());
            pst.setString(4,txtUsuSenha.getText());
            pst.setString(5,cboUsuPerfil.getSelectedItem().toString());
            pst.setString(6,txtUsuId.getText());
            
            //validação dos campos obrigatórios
            if(txtUsuId.getText().isEmpty()|| (txtUsuNome.getText().isEmpty())||
              (txtUsuFone.getText().isEmpty())|| (txtusuLogin.getText().isEmpty()) ||     
                    (txtUsuSenha.getText().isEmpty())
                    )
            
            {
                JOptionPane.showMessageDialog(null,"Campos Obrigatórios");
            }
            
            else{
            //atualizar tabela usuario c/ dados do formulário
            int adicionado = pst.executeUpdate();
            if(adicionado>0){
                JOptionPane.showMessageDialog(null,"Dados so usuário alterados com sucesso");
                //limpa os campos apos adicionar ao banco de dados
                txtUsuId.setText(null);
                txtUsuNome.setText(null);
                txtUsuFone.setText(null);
                txtusuLogin.setText(null);
                txtUsuSenha.setText(null);
                
            }
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    
    }
    
    //método responsavel remoção de usuários
    private void apagar(){
        //estrutura para confirmar exclusão
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover esse usuário", "Atenção", JOptionPane.YES_NO_OPTION);
    
        if(confirma == JOptionPane.YES_OPTION){
            String sql ="delete from tbusuarios where  idUser= ?";
        try {
                pst = conexao.prepareStatement(sql);
                pst.setInt(1, Integer.parseInt(txtUsuId.getText()));
                int apagado = pst.executeUpdate();
                if(apagado > 0){
                    JOptionPane.showMessageDialog(null, "Usuário removido com sucesso !");
                    txtUsuId.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuFone.setText(null);
                    txtusuLogin.setText(null);
                    txtUsuSenha.setText(null);
                } 
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        
    
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtUsuNome = new javax.swing.JTextField();
        txtUsuFone = new javax.swing.JTextField();
        txtusuLogin = new javax.swing.JTextField();
        txtUsuSenha = new javax.swing.JTextField();
        cboUsuPerfil = new javax.swing.JComboBox<>();
        btnUsuCreate = new javax.swing.JButton();
        btnUsuDelete = new javax.swing.JButton();
        btnUsuRead = new javax.swing.JButton();
        btnUsuUpdate = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtUsuId = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuários");
        setPreferredSize(new java.awt.Dimension(642, 481));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("*NOME:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("*TELEFONE:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("*LOGIN:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("*SENHA:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("*PERFIL:");

        txtUsuNome.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtUsuFone.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtUsuFone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuFoneActionPerformed(evt);
            }
        });

        txtusuLogin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtUsuSenha.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        cboUsuPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admistrador", "Funcionário" }));

        btnUsuCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnUsuCreate.setToolTipText("Adicionar");
        btnUsuCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuCreate.setPreferredSize(new java.awt.Dimension(60, 60));
        btnUsuCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuCreateActionPerformed(evt);
            }
        });

        btnUsuDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        btnUsuDelete.setToolTipText("Apagar");
        btnUsuDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuDelete.setPreferredSize(new java.awt.Dimension(60, 60));
        btnUsuDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuDeleteActionPerformed(evt);
            }
        });

        btnUsuRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/read.png"))); // NOI18N
        btnUsuRead.setToolTipText("Pesquisar");
        btnUsuRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuRead.setPreferredSize(new java.awt.Dimension(60, 60));
        btnUsuRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuReadActionPerformed(evt);
            }
        });

        btnUsuUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update.png"))); // NOI18N
        btnUsuUpdate.setToolTipText("Alterar");
        btnUsuUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuUpdate.setPreferredSize(new java.awt.Dimension(60, 60));
        btnUsuUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuUpdateActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("*CÓDIGO:");

        txtUsuId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtUsuId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuIdActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("* Campos Obrigatórios");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtusuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtUsuFone, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(158, 158, 158)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(57, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(btnUsuCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(btnUsuRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(btnUsuUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89)
                .addComponent(btnUsuDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsuFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtusuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cboUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUsuCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46))
        );

        setBounds(0, 0, 642, 481);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsuFoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuFoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuFoneActionPerformed

    private void btnUsuReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuReadActionPerformed
        // metodo consultar
        consultar();
    }//GEN-LAST:event_btnUsuReadActionPerformed

    private void btnUsuCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuCreateActionPerformed
        //metodo adicionar usuário
        adicionar();
    }//GEN-LAST:event_btnUsuCreateActionPerformed

    private void txtUsuIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuIdActionPerformed

    private void btnUsuUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuUpdateActionPerformed
        // chamando o método alterar
        alterar();
    }//GEN-LAST:event_btnUsuUpdateActionPerformed

    private void btnUsuDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuDeleteActionPerformed
        // excluindo usuario no sistema
        apagar();
    }//GEN-LAST:event_btnUsuDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUsuCreate;
    private javax.swing.JButton btnUsuDelete;
    private javax.swing.JButton btnUsuRead;
    private javax.swing.JButton btnUsuUpdate;
    private javax.swing.JComboBox<String> cboUsuPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtUsuFone;
    private javax.swing.JTextField txtUsuId;
    private javax.swing.JTextField txtUsuNome;
    private javax.swing.JTextField txtUsuSenha;
    private javax.swing.JTextField txtusuLogin;
    // End of variables declaration//GEN-END:variables
}
