/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
//importando recurso r2xml
import net.proteanit.sql.DbUtils;
/**
 *
 * @author rober
 */
public class TelaClientes extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    
    public TelaClientes() {
        initComponents();
        conexao= ModuloConexao.conector();
    }
    
      private void adicionar(){
        String sql = "insert into tbclientes(nomecli, endcli, fonecli, emailcli,cidade, bairro, numcli)values(?,?,?,?,?,?,?)";
        try{
            
            pst = conexao.prepareStatement(sql);
            pst.setString(1,txtCliNome.getText());
            pst.setString(2,txtCliend.getText());
            pst.setString(3,txtCliFone.getText());
            pst.setString(4,txtCliEmail.getText());
            pst.setString(5,txtCliCidade.getText());
            pst.setString(6,txtCliBairro.getText());
            pst.setString(7,txtCliNum.getText());
           
            
            //validação dos campos obrigatórios
            if(txtCliNome.getText().isEmpty()|| (txtCliend.getText().isEmpty())||
              (txtCliFone.getText().isEmpty())|| (txtCliCidade.getText().isEmpty()) ||     
                    (txtCliBairro.getText().isEmpty())|| (txtCliNum.getText().isEmpty())
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
                txtCliNome.setText(null);
                txtCliend.setText(null);
                txtCliFone.setText(null);
                txtCliEmail.setText(null);
                txtCliCidade.setText(null);
                txtCliBairro.setText(null);
                txtCliNum.setText(null);
                
            }
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    
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
            modelo.addColumn("E-mail");
            modelo.addColumn("Cidade");
            modelo.addColumn("Bairro");
            modelo.addColumn("Num");
            
            while(rs.next()){
               
                int cod = rs.getInt("idcli");
                String nome = rs.getString("nomecli");
                String endereco =  rs.getString("endcli");
                String fone = rs.getString("fonecli");
                String email = rs.getString("emailcli");
                String cidade = rs.getString("cidade");
                String bairro = rs.getString("bairro");
                String num = rs.getString("numcli");
                
                modelo.addRow(new Object[] {cod, nome, endereco, fone, email,cidade,bairro, num});
            } 
            btnCliNovo.setEnabled(true);
            txtCliNome.setText(null);
            txtCliend.setText(null);
            txtCliFone.setText(null);
            txtCliEmail.setText(null);
            txtCliCidade.setText(null);
            txtCliBairro.setText(null);
            txtCliNum.setText(null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }   
    }
      
      //metodo para setar campos do formulario 
    public void setarCampos(){
        int setar = tblClientes.getSelectedRow();
                
                lblCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
                txtCliNome.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
                txtCliend.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
                txtCliFone.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
                txtCliEmail.setText(tblClientes.getModel().getValueAt(setar, 4).toString());
                txtCliCidade.setText(tblClientes.getModel().getValueAt(setar, 5).toString());
                txtCliBairro.setText(tblClientes.getModel().getValueAt(setar, 6).toString());
                txtCliNum.setText(tblClientes.getModel().getValueAt(setar, 7).toString());
     
        // desabilitando o botão adicionar na pesquisa para evitar duplicidade
        btnCliNovo.setEnabled(false);
    }
    
    private void alterarCliente(){
        int clicado = tblClientes.getSelectedRow();
        String id = tblClientes.getModel().getValueAt(clicado, 0).toString();
        String sql = "update tbclientes set nomecli=?, endcli=?, fonecli=?, emailcli=?,cidade=?, bairro=?, numcli=? where idcli=?";
       try{
            
            pst = conexao.prepareStatement(sql);
            
            pst.setString(1,txtCliNome.getText());
            pst.setString(2,txtCliend.getText());
            pst.setString(3,txtCliFone.getText());
            pst.setString(4,txtCliEmail.getText());
            pst.setString(5,txtCliCidade.getText());
            pst.setString(6,txtCliBairro.getText());
            pst.setString(7,txtCliNum.getText());
            pst.setInt(8, Integer.parseInt(id));
            
            //validação dos campos obrigatórios
            if(txtCliNome.getText().isEmpty()|| (txtCliend.getText().isEmpty())||
              (txtCliFone.getText().isEmpty())|| (txtCliCidade.getText().isEmpty()) ||     
                    (txtCliBairro.getText().isEmpty())|| (txtCliNum.getText().isEmpty())
                    )
            
            {
                JOptionPane.showMessageDialog(null,"Campos Obrigatórios");
            }
            
            else{
            //atualizar tabela usuario c/ dados do formulário
            int adicionado = pst.executeUpdate();
            if(adicionado>0){
                JOptionPane.showMessageDialog(null,"Dados alterados com sucesso");
                //limpa os campos apos adicionar ao banco de dados
                txtCliNome.setText(null);
                txtCliend.setText(null);
                txtCliFone.setText(null);
                txtCliEmail.setText(null);
                txtCliCidade.setText(null);
                txtCliBairro.setText(null);
                txtCliNum.setText(null);
                 btnCliNovo.setEnabled(true);
            }
            pesquisar_clientes();
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    
    }
    
    private void deletarCliente(){
        //estrutura para confirmar exclusão
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este Cliente", "Atenção", JOptionPane.YES_NO_OPTION);
    
        if(confirma == JOptionPane.YES_OPTION){
            String sql ="delete from tbclientes where  idcli= ?";
        try {
                pst = conexao.prepareStatement(sql);
                pst.setInt(1, Integer.parseInt(lblCliId.getText()));
                int apagado = pst.executeUpdate();
                if(apagado > 0){
                    JOptionPane.showMessageDialog(null, "Cliente removido com sucesso !");
                    txtCliNome.setText(null);
                    txtCliend.setText(null);
                    txtCliFone.setText(null);
                    txtCliEmail.setText(null);
                    txtCliCidade.setText(null);
                    txtCliBairro.setText(null);
                    txtCliNum.setText(null);
                 
                } 
               pesquisar_clientes(); 
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCliNome = new javax.swing.JTextField();
        txtCliFone = new javax.swing.JTextField();
        txtCliend = new javax.swing.JTextField();
        txtCliEmail = new javax.swing.JTextField();
        txtCliCidade = new javax.swing.JTextField();
        txtCliBairro = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtCliPesquisar = new javax.swing.JTextField();
        btnCliEditar = new javax.swing.JButton();
        btnCliDeletar = new javax.swing.JButton();
        btnCliNovo = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtCliNum = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        lblCliId = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Clientes");
        setPreferredSize(new java.awt.Dimension(642, 481));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("*NOME:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("*ENDEREÇO:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("*TELEFONE:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("EMAIL:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("*BAIRRO:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("*CIDADE:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel7.setText("*Campos Obrigatórios");

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
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

        btnCliEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update.png"))); // NOI18N
        btnCliEditar.setToolTipText("editar cliente");
        btnCliEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCliEditar.setPreferredSize(new java.awt.Dimension(50, 50));
        btnCliEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliEditarActionPerformed(evt);
            }
        });

        btnCliDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        btnCliDeletar.setToolTipText("excluir cliente");
        btnCliDeletar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCliDeletar.setPreferredSize(new java.awt.Dimension(50, 50));
        btnCliDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliDeletarActionPerformed(evt);
            }
        });

        btnCliNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnCliNovo.setToolTipText("adicionar clente");
        btnCliNovo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCliNovo.setPreferredSize(new java.awt.Dimension(50, 50));
        btnCliNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliNovoActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("*NUM:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("CÓDIGO:");

        lblCliId.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(btnCliNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(119, 119, 119)
                        .addComponent(btnCliEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCliDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtCliNome)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtCliFone)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtCliEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtCliBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtCliCidade))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtCliend, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCliNum, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(19, 19, 19))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblCliId)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtCliNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCliFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtCliEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCliBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(txtCliCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCliNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCliEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCliDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );

        setBounds(0, 0, 642, 481);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCliNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliNovoActionPerformed
        // ação chamnado método de adicionar clientes
        adicionar();
    }//GEN-LAST:event_btnCliNovoActionPerformed

    //em tempo real vai pesquisando o que for digitado
    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // chamar metodo pesquisar_clientes
        pesquisar_clientes();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    //evento setar tabela
    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // meto setar campos setarCampos
        setarCampos();
    }//GEN-LAST:event_tblClientesMouseClicked
    
    //metodo para alterar dados do cliente
    private void btnCliEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliEditarActionPerformed
        // chamndo metodo alterarCliente
        alterarCliente();
    }//GEN-LAST:event_btnCliEditarActionPerformed

    //metodo utilizado para excluir cliente da base de dados
    private void btnCliDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliDeletarActionPerformed
        // chamndo mo metodo deletarCliente();
        deletarCliente();
    }//GEN-LAST:event_btnCliDeletarActionPerformed

    private void txtCliPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliPesquisarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCliDeletar;
    private javax.swing.JButton btnCliEditar;
    private javax.swing.JButton btnCliNovo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lblCliId;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliBairro;
    private javax.swing.JTextField txtCliCidade;
    private javax.swing.JTextField txtCliEmail;
    private javax.swing.JTextField txtCliFone;
    private javax.swing.JTextField txtCliNome;
    private javax.swing.JTextField txtCliNum;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txtCliend;
    // End of variables declaration//GEN-END:variables
}
