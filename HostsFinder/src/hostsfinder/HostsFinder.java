/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hostsfinder;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
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
    
    public static ArrayList<Host> getHosts(ArrayList<Host> hostsOld){
        ArrayList<Host> hosts = new ArrayList<>();
        try {
                // Host Windows
                //String osName = System.getProperty("os.name");
                //if (osName.contains("Windows")) {
            String output = runArp("-a");
            String[] splitted = output.split("\n");
            for (String portion : splitted) {
                String[] splittedLine = portion.split(" ");
                ArrayList<String> camposLine = new ArrayList<>();
                for (String split : splittedLine) {
                    if(split.length()>1){
                        camposLine.add(split);
                    }
                }
                if(camposLine.size() == 3){
                    Host h = new Host(camposLine.get(0),camposLine.get(1),camposLine.get(2));
                    hosts.add(h);
                   // System.out.println("IP > "+camposLine.get(0) + "   MAC > "+camposLine.get(1));
                }
            }
            if(hostsOld != null)
                hosts = AtualizaHosts(hosts, hostsOld);
        } catch (IOException ex) {
                System.err.println(ex.getMessage());
        }
        return hosts;
    }
    
    private static String runArp(String flag) throws IOException{
        Process child = Runtime.getRuntime().exec("arp " + flag);
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
        for(Host host : hostsNew){
            for(Host h : hostsOld){
                if(h.getMac().equals(host.getMac())){
                    host.setTimeOn(h.getTimeOn()+timeSleep);
                }
            }
        }   
        return hostsNew;
    }
    
    
    
    @Override
    public void run(){
        String[] coluns = {"Num","IP","MAC","Fabricante","Time on"};
        while(true){
            hosts = HostsFinder.getHosts(hosts);
            DefaultTableModel dtm = new DefaultTableModel(coluns, 0);
            int num = 0;
            for(int i = 0 ; i < hosts.size() ; i++){
                num++;
                String[] rowData = new String[5];
                rowData[0] = num + "";
                rowData[1] = hosts.get(i).getIp();
                rowData[2] = hosts.get(i).getMac();
                rowData[3] = hosts.get(i).getDesenvolvedor();
                rowData[4] = (hosts.get(i).getTimeOn()/1000)+" Segundos";
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

    public JTable getjTable() {
        return jTable;
    }

    public void setjTable(JTable jTable) {
        this.jTable = jTable;
    }
}
    

