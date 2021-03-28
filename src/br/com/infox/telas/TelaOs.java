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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import net.proteanit.sql.DbUtils;

public class TelaOs extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    //linha para variavel armazenar texto radio button
    private String tipo ;
    
    public TelaOs() {
        initComponents();
        conexao = ModuloConexao.conector();
    }
    private void pesquisarClienteOs(){
        String sql = "select idcli, nomecli, fonecli from tbclientes where nomecli like? ";
        try{
            pst = conexao.prepareStatement(sql);
            pst.setString(1,txtCliPesquisar.getText()+"%");
            rs= pst.executeQuery();
          
            
        DefaultTableModel modelo = new DefaultTableModel();
            tbOs.setModel(modelo);
            
            modelo.addColumn("Código");
            modelo.addColumn("Nome");
            modelo.addColumn("Fone");
            
            
            while(rs.next()){
               
                int codigo = rs.getInt("idcli");
                String nome = rs.getString("nomecli");
                String fone = rs.getString("fonecli");
                
                
                modelo.addRow(new Object[] {codigo, nome,fone});
            } 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }   
    }
    
    private void setarCampos(){
        int setar = tbOs.getSelectedRow();
        txtCodOs.setText(tbOs.getModel().getValueAt(setar, 0).toString());
    }

    //metodo cadastrar ordm de serviço
    
    private void emitirOs(){
        String sql = "insert into tbos (tipo,situacao,equipamento,defeito,servico,tecnico,valor,idcli) values (?,?,?,?,?,?,?,?)";
        try{
            pst= conexao.prepareStatement(sql);
            pst.setString(1,tipo);
            pst.setString(2,cmbOs.getSelectedItem().toString());
            pst.setString(3,txtEqui.getText());
            pst.setString(4,txtDef.getText());
            pst.setString(5,txtServ.getText());
            pst.setString(6,txtTec.getText());
            pst.setString(7,txtValor.getText().replaceAll(",", "."));
            pst.setString(8,txtCodOs.getText());
            
            // validado camppos obrigatorios
            
            if((txtCodOs.getText().isEmpty())|| (txtEqui.getText().isEmpty())||(txtDef.getText().isEmpty())
                    ||(txtServ.getText().isEmpty()) ||(txtTec.getText().isEmpty())
                    ){
                        JOptionPane.showMessageDialog(null, "*Campos Obrigatórios");
            }else{
                int adicionado = pst.executeUpdate();
                if(adicionado>0){
                    JOptionPane.showMessageDialog(null, "OS emitida com sucesso");
                    txtEqui.setText(null);
                    txtDef.setText(null);
                    txtServ.setText(null);
                    txtTec.setText(null);
                    txtValor.setText(null);
                    txtCodOs.setText(null);
                    
                }
            }
        }catch (com.mysql.cj.jdbc.exceptions.MysqlDataTruncation e) {
            JOptionPane.showMessageDialog(null,"Valor Inválido");
            System.out.println(e);
        } catch (SQLException ex) {
            Logger.getLogger(TelaOs.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch(Exception e2){
            JOptionPane.showMessageDialog( null, e2);
        }
    }
    
    //metodo para pesquisar uma Os
    private void pesquisarOs(){
        String numOs=JOptionPane.showInputDialog("Número da Os");
        String sql="select * from tbos where os=  "+numOs;
        try{
            pst= conexao.prepareStatement(sql);
            rs = pst.executeQuery();
           
            if(rs.next()){
                
                String data = rs.getString("data_os");
                txtOs.setText(rs.getString(1));
                txtOsData.setText(data);
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                String dateInString = data;
                sdf.parse(dateInString);
         
                System.out.println(sdf);
                //setar radios buttons
                
                String rbtTipo= rs.getString(3);
                if (rbtTipo.equals("Ordem de serviço")) {
                    rdOrdem.setSelected(true);
                } else {
                   rdOrca.setSelected(true);
                   tipo = "Orçamento";
                }
                cmbOs.setSelectedItem(rs.getString(4));
                txtEqui.setText(rs.getString(5));
                txtDef.setText(rs.getString(6));
                txtServ.setText(rs.getString(7));
                txtTec.setText(rs.getString(8));
                txtValor.setText(rs.getString(9));
                txtCodOs.setText(rs.getString(10));
                // evitar duplicidade de OS
                btnOsAdd.setEnabled(false);
                txtCliPesquisar.setEnabled(false);
                tbOs.setVisible(false);
            }else{
                JOptionPane.showMessageDialog(null, "OS não cadastrada");
            }
        }catch (java.sql.SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "Digite apenas números");
            System.out.println(e);
        } catch (SQLException ex) {
            Logger.getLogger(TelaOs.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception e2){
            JOptionPane.showMessageDialog(null, e2);
        }
                
    }
    
    private void alterarOs(){
        
        String sql = "update tbos set tipo = ?, situacao = ?, equipamento = ?, defeito = ?, servico = ?, tecnico = ?, valor = ? where os = ?";
        try{
            pst= conexao.prepareStatement(sql);
            pst.setString(1,tipo);
            pst.setString(2,cmbOs.getSelectedItem().toString());
            pst.setString(3,txtEqui.getText());
            pst.setString(4,txtDef.getText());
            pst.setString(5,txtServ.getText());
            pst.setString(6,txtTec.getText());
            pst.setString(7,txtValor.getText().replaceAll(",", "."));
            pst.setString(8,txtOs.getText());
            
            // validado camppos obrigatorios
            
            if((txtCodOs.getText().isEmpty())|| (txtEqui.getText().isEmpty())||(txtDef.getText().isEmpty())
                    ||(txtServ.getText().isEmpty()) ||(txtTec.getText().isEmpty())
                    ){
                        JOptionPane.showMessageDialog(null, "*Campos Obrigatórios");
            }else{
                int adicionado = pst.executeUpdate();
                if(adicionado>0){
                    JOptionPane.showMessageDialog(null, "Dados da OS alterados com sucesso");
                    txtOs.setText(null);
                    txtOsData.setText(null);
                    txtEqui.setText(null);
                    txtDef.setText(null);
                    txtServ.setText(null);
                    txtTec.setText(null);
                    txtValor.setText(null);
                    txtCodOs.setText(null);
                    
                   //habilitando objetos novamente apos consulta
                   btnOsAdd.setEnabled(true);
                   txtCliPesquisar.setEnabled(true);
                   tbOs.setVisible(true);
                }
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }   
    
    }
     //método de eclusão de Os
    private void excluirOs(){
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir essa OS ?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbos where os = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtOs.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "OS excluida com sucesso !");
                    txtOs.setText(null);
                    txtOsData.setText(null);
                    txtEqui.setText(null);
                    txtDef.setText(null);
                    txtServ.setText(null);
                    txtTec.setText(null);
                    txtValor.setText(null);
                    txtCodOs.setText(null);
                    
                    btnOsAdd.setEnabled(true);
                    txtCliPesquisar.setEnabled(true);
                    tbOs.setVisible(true);
                  

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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtOs = new javax.swing.JTextField();
        txtOsData = new javax.swing.JTextField();
        rdOrdem = new javax.swing.JRadioButton();
        rdOrca = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cmbOs = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtCliPesquisar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCodOs = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbOs = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtEqui = new javax.swing.JTextField();
        txtDef = new javax.swing.JTextField();
        txtServ = new javax.swing.JTextField();
        txtTec = new javax.swing.JTextField();
        txtValor = new javax.swing.JTextField();
        btnOsAdd = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Ordem de Serviço");
        setPreferredSize(new java.awt.Dimension(642, 481));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("DATA:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("N: OS");

        txtOs.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtOs.setEnabled(false);

        txtOsData.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtOsData.setEnabled(false);

        buttonGroup1.add(rdOrdem);
        rdOrdem.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rdOrdem.setText("Ordem de Serviço");
        rdOrdem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdOrdemActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdOrca);
        rdOrca.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rdOrca.setText("Orçamento");
        rdOrca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdOrcaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rdOrca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rdOrdem))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOsData)
                            .addComponent(txtOs))))
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtOsData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdOrca)
                    .addComponent(rdOrdem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Situação:");

        cmbOs.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Na bancada", "Entregue", "Orçamento reprovado", "Aguardando Aprovação", "Aguardando peças", "Retorno pela garantia", "Abandonado pelo cliente" }));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));
        jPanel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

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

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/busca.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("*Código:");

        txtCodOs.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCodOs.setEnabled(false);

        tbOs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Código", "Nome", "Fone"
            }
        ));
        tbOs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbOsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbOs);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCliPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCodOs, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCodOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("*Equipamento:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("*Defeito:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Valor Total:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("*Serviço:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("*Técnico");

        txtValor.setText("00");

        btnOsAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnOsAdd.setToolTipText("Adicionar OS");
        btnOsAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsAdd.setPreferredSize(new java.awt.Dimension(40, 40));
        btnOsAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsAddActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/read.png"))); // NOI18N
        jButton3.setToolTipText("Pesquisar OS");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setPreferredSize(new java.awt.Dimension(40, 40));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update.png"))); // NOI18N
        jButton4.setToolTipText("Editar OS");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setPreferredSize(new java.awt.Dimension(40, 40));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        jButton5.setToolTipText("Excluir OS");
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.setPreferredSize(new java.awt.Dimension(40, 40));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(cmbOs, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtTec)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtServ)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDef)
                            .addComponent(txtEqui))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOsAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cmbOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtEqui, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtServ, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel8)
                        .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOsAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        setBounds(0, 0, 642, 481);
    }// </editor-fold>//GEN-END:initComponents
    
    
    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // chamando metodo pesquisar clientes
        pesquisarClienteOs();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    private void tbOsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbOsMouseClicked
        // chamndo meto setarCampos
        setarCampos();
    }//GEN-LAST:event_tbOsMouseClicked

    private void rdOrcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdOrcaActionPerformed
        // atribuindo um texto variavel tipo selecionada
        tipo = "Orçamento";
    }//GEN-LAST:event_rdOrcaActionPerformed

    private void rdOrdemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdOrdemActionPerformed
        // atribuindo um texto variavel tipo selecionada
        tipo = "Ordem de serviço";
    }//GEN-LAST:event_rdOrdemActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // ao abrir form orçamento fica coomo opção marcada
        rdOrca.setSelected(true);
        tipo= "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnOsAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsAddActionPerformed
        // chamando evento emitir os
        emitirOs();
    }//GEN-LAST:event_btnOsAddActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // chamando metodo pesquisar os
        pesquisarOs();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // método alterar Os
        alterarOs();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // chamando meto excluir
        excluirOs();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtCliPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliPesquisarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOsAdd;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbOs;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdOrca;
    private javax.swing.JRadioButton rdOrdem;
    private javax.swing.JTable tbOs;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txtCodOs;
    private javax.swing.JTextField txtDef;
    private javax.swing.JTextField txtEqui;
    private javax.swing.JTextField txtOs;
    private javax.swing.JTextField txtOsData;
    private javax.swing.JTextField txtServ;
    private javax.swing.JTextField txtTec;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
