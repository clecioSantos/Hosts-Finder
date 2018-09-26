/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDS;

import Conect.ConectaSqlite;
import Models.Conexao;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Clecio
 */
public class CrudConexao {
    public boolean NewConexao(Conexao conexao) throws ClassNotFoundException, SQLException, IOException{
        String sql = "insert into Conexao (id_host,ip,status,hora_inicio,hora_fim) values(?,?,?,?,?)";
        PreparedStatement pstmt = ConectaSqlite.getConnection().prepareStatement(sql);
        pstmt.setInt(1, conexao.getId_host());
        pstmt.setString(2, conexao.getIp());
        pstmt.setString(3, conexao.getStatus());
        pstmt.setString(4, conexao.getHora_inicio());
        pstmt.setString(5, conexao.getHora_fim());
        int registros = pstmt.executeUpdate();
        pstmt.close();
        if(registros == 1){
            //criar primeira conexao
            return true;
        }
        return false;
    }
    public Conexao getConexao(int id) throws ClassNotFoundException, SQLException, IOException{
        String sql = "select * from Conexao where id = ?;";
        PreparedStatement pstmt = ConectaSqlite.getConnection().prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            Conexao conexao = new Conexao();
            conexao.setIp(rs.getString("ip"));
            conexao.setHora_inicio(rs.getString("hora_inicio"));
            conexao.setHora_fim(rs.getString("hora_fim"));
            conexao.setId(rs.getInt("id"));
            conexao.setId_host(rs.getInt("id_host"));
            conexao.setStatus(rs.getString("status"));
            return conexao;
        }
        pstmt.close();
        return null;
    }
    public ArrayList<Conexao> listConexao() throws SQLException, ClassNotFoundException, IOException{
        ArrayList conexoes = new ArrayList<>();
        String sql = "select * from conexao;";
        PreparedStatement pstmt = ConectaSqlite.getConnection().prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            Conexao conexao = new Conexao();
            conexao.setIp(rs.getString("ip"));
            conexao.setHora_inicio(rs.getString("hora_inicio"));
            conexao.setHora_fim(rs.getString("hora_fim"));
            conexao.setId(rs.getInt("id"));
            conexao.setId_host(rs.getInt("id_host"));
            conexao.setStatus(rs.getString("status"));
            conexoes.add(conexao);
        }
        pstmt.close();
        return conexoes;
    }
    public void updateConexao(Conexao conexao) throws ClassNotFoundException, SQLException, IOException{
        String sql = "update Conexao set ip = ?, hora_inicio = ?, hora_fim = ?, id_host = ?, status = ? where id = ?;";
        PreparedStatement pstmt = ConectaSqlite.getConnection().prepareStatement(sql);
        pstmt.setString(1, conexao.getIp());
        pstmt.setString(2, conexao.getHora_inicio());
        pstmt.setString(3, conexao.getHora_fim());
        pstmt.setInt(4, conexao.getId_host());
        pstmt.setString(5, conexao.getStatus());
        pstmt.setInt(6, conexao.getId());
        pstmt.executeUpdate();
        pstmt.close();
    }
    public void deleteHost(int id) throws ClassNotFoundException, SQLException, IOException{
        String sql = "delete from Conexao where id = ?;";
        PreparedStatement pstmt = ConectaSqlite.getConnection().prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        pstmt.close();
    }
}
