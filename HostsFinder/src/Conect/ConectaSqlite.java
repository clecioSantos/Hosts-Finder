/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conect;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Clecio
 */
public class ConectaSqlite {
    
    public static Connection conexao;

    public static Connection getConnection() throws ClassNotFoundException, SQLException, IOException{
        if(conexao == null){
            String caminho = new File(".").getCanonicalPath()+"\\db\\SQLiteHostFinder.db";
            Class.forName("org.sqlite.JDBC");
            String url ="jdbc:sqlite:/"+caminho;
            conexao = DriverManager.getConnection(url);
        }
        return conexao;

    }
}