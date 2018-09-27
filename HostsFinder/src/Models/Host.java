/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import hostsfinder.HostsFinder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Clecio
 */
public class Host {
    private String ip; //ip atual
    private String mac;
    private String desenvolvedor;
    private String tipoHost;
    private String timeOn;
    private int id;
    private String nome;

    public Host(String ip, String mac, String tipoHost) {
        this.ip = ip;
        setMac(mac);
        this.tipoHost = tipoHost;
        setTimeOn();
    }
    public Host(){
        setTimeOn();
    }
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
        setDesenvolvedor(HostsFinder.LeituraCSV(mac));
    }

    public String getDesenvolvedor() {
        return desenvolvedor;
    }

    public void setDesenvolvedor(String desenvolvedor) {
        this.desenvolvedor = desenvolvedor;
    }

    public String getTipoHost() {
        return tipoHost;
    }

    public void setTipoHost(String tipoHost) {
        this.tipoHost = tipoHost;
    }

    public String getTimeOn() {
        return timeOn;
    }

    public void setTimeOn(String timeOn) {
        this.timeOn = timeOn;
    }
    public void setTimeOn() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        this.timeOn = sdf.format(new Date());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
