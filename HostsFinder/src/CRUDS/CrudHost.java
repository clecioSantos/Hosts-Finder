/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDS;

import Conect.ConectaSqlite;
import Models.Conexao;
import Models.Host;
import hostsfinder.Util;
import java.awt.List;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Clecio
 */
public class CrudHost {
    public boolean NewHost(Host host) throws ClassNotFoundException, SQLException, IOException{
        String sql = "insert into Host (mac,fabricante) values(?,?)";
        PreparedStatement pstmt = ConectaSqlite.getConnection().prepareStatement(sql);
        pstmt.setString(1, host.getMac());
        pstmt.setString(2, host.getDesenvolvedor());
        int registros = pstmt.executeUpdate();
        pstmt.close();
        if(registros == 1){
            setFirstConexao(host);
            return true;
        }
        return false;
    }
    public Host getHost(int id) throws ClassNotFoundException, SQLException, IOException{
        String sql = "select * from Host where id = ?;";
        PreparedStatement pstmt = ConectaSqlite.getConnection().prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            Host host = new Host();
            host.setMac(rs.getString("mac"));
            host.setDesenvolvedor(rs.getString("fabricante"));
            host.setId(rs.getInt("id"));
            host.setNome(rs.getString("nome"));
            return host;
        }
        pstmt.close();
        return null;
    }
    public Host getHost(String mac) throws ClassNotFoundException, SQLException, IOException{
        String sql = "select * from Host where mac = ?;";
        PreparedStatement pstmt = ConectaSqlite.getConnection().prepareStatement(sql);
        pstmt.setString(1, mac);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            Host host = new Host();
            host.setMac(rs.getString("mac"));
            host.setDesenvolvedor(rs.getString("fabricante"));
            host.setId(rs.getInt("id"));
            host.setNome(rs.getString("nome"));
            return host;
        }
        pstmt.close();
        return null;
    }
    public ArrayList<Host> listHost() throws SQLException, ClassNotFoundException, IOException{
        ArrayList hosts = new ArrayList<>();
        String sql = "select * from Host;";
        PreparedStatement pstmt = ConectaSqlite.getConnection().prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            Host host = new Host();
            host.setMac(rs.getString("mac"));
            host.setDesenvolvedor(rs.getString("fabricante"));
            host.setId(rs.getInt("id"));
            host.setNome(rs.getString("nome"));
            hosts.add(host);
        }
        pstmt.close();
        return hosts;
    }
    public void updateHost(Host host) throws ClassNotFoundException, SQLException, IOException{
        String sql = "update Host set mac = ?, fabricante = ?, nome = ? where id = ?;";
        PreparedStatement pstmt = ConectaSqlite.getConnection().prepareStatement(sql);
        pstmt.setString(1, host.getMac());
        pstmt.setString(2, host.getDesenvolvedor());
        pstmt.setInt(3, host.getId());
        pstmt.executeUpdate();
        pstmt.close();
    }
    public void deleteHost(int id) throws ClassNotFoundException, SQLException, IOException{
        String sql = "delete from Host where id = ?;";
        PreparedStatement pstmt = ConectaSqlite.getConnection().prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        pstmt.close();
    }
    private void setFirstConexao(Host host) throws ClassNotFoundException, SQLException, IOException{
        Conexao conexao = new Conexao();
        conexao.setId_host(host.getId());
        conexao.setStatus("PrimeiroAcesso");
        conexao.setHora_inicio(Util.getHora());
        conexao.setHora_fim("");
        conexao.setIp(host.getIp());
        CrudConexao cc = new CrudConexao();
        cc.NewConexao(conexao);
    }
}
