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

    public HostsFinder(JTable jTable) {
        this.jTable = jTable;
    }
    
    public static ArrayList<Host> getHosts(){
        ArrayList<Host> hosts = new ArrayList<>();
        try {
                // Host Windows
                String osName = System.getProperty("os.name");
                if (osName.contains("Windows")) {
                        Process child = Runtime.getRuntime().exec("arp -a");
                        InputStream in = child.getInputStream();
                        String output = "";
                        int c;
                        while ((c = in.read()) != -1) {
                                output += (char) c;
                        }
                        in.close();
                        String line = null;
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

                }else{
                    System.out.println("SO não suportado");
                }
        } catch (IOException ex) {
                System.err.println(ex.getMessage());
        }
        return hosts;
    }
    @Override
    public void run(){
        String[] coluns = {"IP","MAC","Fabricante","Tipo Host"};
        while(true){
            ArrayList<Host> hosts = HostsFinder.getHosts();
            DefaultTableModel dtm = new DefaultTableModel(coluns, 0);
            for(int i = 0 ; i < hosts.size() ; i++){
                String[] rowData = new String[4];
                rowData[0] = hosts.get(i).getIp();
                rowData[1] = hosts.get(i).getMac();
                rowData[2] = hosts.get(i).getDesenvolvedor();
                rowData[3] = hosts.get(i).getTipoHost();
                dtm.addRow(rowData);
            }
            jTable.setModel(dtm);
            try {
                Thread.sleep(300);
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
    

