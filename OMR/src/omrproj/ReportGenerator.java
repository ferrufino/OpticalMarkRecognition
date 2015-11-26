/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omrproj;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author alfredo_altamirano
 */
public class ReportGenerator {
    
    private Map<String, String> readFile(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(filename));
        Map<String, String> rows = new HashMap<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.length() != 0) {
                String parts[] = line.split("=");
                rows.put(parts[0], parts[2]);
            }
        }
        
        return rows;
    }
    
    public Report generateReport(List<String> dataFiles) throws FileNotFoundException {
        List<Map<String, String> > rawData = new ArrayList<>();
        for(String fileName: dataFiles) {
            rawData.add(readFile(fileName));
        }
        
        return new Report();
    }
    
}
