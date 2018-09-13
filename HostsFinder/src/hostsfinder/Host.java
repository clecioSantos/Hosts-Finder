/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hostsfinder;

/**
 *
 * @author Clecio
 */
public class Host {
    private String ip;
    private String mac;
    private String desenvolvedor;
    private String tipoHost;

    public Host(String ip, String mac, String tipoHost) {
        this.ip = ip;
        this.mac = mac;
        this.tipoHost = tipoHost;
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
    
}
