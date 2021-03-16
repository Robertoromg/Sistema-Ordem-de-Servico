/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.dal;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author rober
 */
public class ModuloConexao {

    //metodo responsavel por estabelecer a conexao com o banco
    public static Connection conector() {
        java.sql.Connection conexao = null;
        //a linha abaixo chama o driver 
        String driver = "com.mysql.cj.jdbc.Driver";
        // armazenando informações referente ao banco
        String url = "jdbc:mysql://127.0.0.1:3306/dbinfox?useTimezone=true&serverTimezone=UTC";
        String user = "root";
        String password = "123";
        // Estabelecendo a conexao com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            return null;
      
        }
    }
}
