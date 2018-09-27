/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hostsfinder;
import Models.Host;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Clecio
 */
public class HostsFinder extends Thread{

    private JTable jTable;
    private static int timeSleep = 5000;
    private static ArrayList<Host> hosts = null;
    public HostsFinder(JTable jTable) {
        this.jTable = jTable;
    }
    
    //Busca os Hosts da rede e os organiza em uma List<Hosts>
    public static ArrayList<Host> getHosts(ArrayList<Host> hostsOld){
        ArrayList<Host> hosts = new ArrayList<>();
        try {
            String output = runCMD("arp -a");
            String[] splitted = output.split("\n");
            String gateway = getDefaultGateway();
            int numLinha = 0;
            for (String portion : splitted) {
                String[] splittedLine = portion.split(" ");
                ArrayList<String> camposLine = new ArrayList<>();
                for (String split : splittedLine) {
                    if(split.length()>1){
                        camposLine.add(split);
                    }
                }
                if(numLinha >= 2){
                    if(camposLine.get(2).contains("din")){
                        String tipo="Host";
                        if((" "+camposLine.get(0)).equals(gateway))
                            tipo = "Router";
                        Host h = new Host(camposLine.get(0),camposLine.get(1),tipo);
                        hosts.add(h);
                    }
                }
                numLinha++;
            }
        } catch (IOException ex) {
                System.err.println(ex.getMessage());
        }
        return AtualizaHosts(hosts, hostsOld);
    }
    //executa o comando arp com a flag especificada e retorna uma String com a resposta do protocolo
    private static String runCMD(String flag) throws IOException{
        Process child = Runtime.getRuntime().exec(flag);
        InputStream in = child.getInputStream();
        String output = "";
        int c;
        while ((c = in.read()) != -1) {
                output += (char) c;
        }
        in.close();
        return output;
    }
    
    private static ArrayList<Host> AtualizaHosts(ArrayList<Host> hostsNew, ArrayList<Host> hostsOld){
        
        if(hostsOld != null){
            for(Host h : hostsOld){
                h.setStatus("Offline");
            }
            for(Host host : hostsNew){
                boolean exists = false;
                for(Host h : hostsOld){
                    if(h.getMac().equals(host.getMac())){
                        exists = true;
                        h.setStatus("Online");
                    }
                }
                if(!exists){
                    hostsOld.add(host);
                }
            }   
            return hostsOld;
        }else{
            for(Host host : hostsNew){
                host.setStatus("Online");
            }
        }
        return hostsNew;
    }
public static String LeituraCSV(String mac){
    String fabricante = "Indefinido";
    mac = mac.substring(0,8);
    try {
    BufferedReader StrR = new BufferedReader(new FileReader("src\\mac-vendor.csv"));

    String Str;
    String[] TableLine;

    while((Str = StrR.readLine())!= null){
        TableLine = Str.split(",");
        if(mac.equalsIgnoreCase(TableLine[0])){
                fabricante = TableLine[1];
                return fabricante;
            }
    }
    StrR.close();
    }
    catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    catch (IOException ex){
        ex.printStackTrace();
    }
    return fabricante;
}
    
    
    @Override
    public void run(){
        String[] coluns = {"Num","IP","MAC","Fabricante","Time on","Type"};
        while(true){
            hosts = HostsFinder.getHosts(hosts);
            DefaultTableModel dtm = (DefaultTableModel) jTable.getModel();
            int num = 0;
            int i = 0;
            while(dtm.getRowCount()>0){
                dtm.removeRow(0);
            }
            for(i = 0 ; i < hosts.size() ; i++){
                num++;
                String[] rowData = new String[7];
                rowData[0] = num + "";
                rowData[1] = hosts.get(i).getIp();
                rowData[2] = hosts.get(i).getMac();
                rowData[3] = hosts.get(i).getDesenvolvedor();
                rowData[4] = (hosts.get(i).getTimeOn());
                rowData[5] = hosts.get(i).getTipoHost();
                rowData[6] = hosts.get(i).getStatus();
                dtm.addRow(rowData);
            }
            jTable.setModel(dtm);
            try {
                Thread.sleep(timeSleep);
            } catch (InterruptedException ex) {
                Logger.getLogger(HostsFinder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static String getDefaultGateway() throws IOException {
        String[] cmd = { "cmd", "/c", "ipconfig | findstr /i \"Gateway\"" };

        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        Process process = processBuilder.start();

        BufferedReader saida = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String linhaSaida = saida.readLine();
        StringTokenizer st = new StringTokenizer(linhaSaida, ":");
        st.nextToken(); // o endereco esta depois do ":"
        return st.nextToken();
    }

    public JTable getjTable() {
        return jTable;
    }

    public void setjTable(JTable jTable) {
        this.jTable = jTable;
    }
}
    

