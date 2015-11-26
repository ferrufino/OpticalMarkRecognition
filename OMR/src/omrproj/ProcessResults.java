/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omrproj;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lalo
 */
public class ProcessResults {
    
    private static Connection con;
    
    public static void addResult(String filename) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url ="jdbc:mysql://localhost/encuestas";
            con = DriverManager.getConnection(url, "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(ProcessResults.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProcessResults.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(filename);
        
        try {
            ArrayList<String> list = processFile(filename);
            
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO encuestas1 " 
                    + "(educacion, poblacion, ayuda, paredes, comerciales, mercados, "
                    + "salud, transporte, parroquia, piso, agua, excretas, techo, " 
                    + "calienta, iluminacion) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            
            for (int i = 0; i < 15; i++) {
                pstmt.setString(i+1, list.get(i));
            }
            
            pstmt.executeUpdate();
            
        } catch (IOException ex) {
            Logger.getLogger(ProcessResults.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProcessResults.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static ArrayList processFile(String filename) throws IOException {
        FileReader fr = null;
        try {
            fr = new FileReader(filename);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProcessResults.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader br = new BufferedReader(fr);
        String line;
        ArrayList list= new ArrayList();
        while((line = br.readLine()) != null) {
            String[] parts = line.split("=");
            list.add(parts[1]);
        }
        return list;
    }
}
