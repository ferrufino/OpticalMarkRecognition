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
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

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
    
    public Report getReport(List<Map<String, String>> data) {
        
        Map<String, String[]> questions = new HashMap<>();
        
        questions.put("iluminacion", new String[] { "gas", "veladora", "electrica" });
        questions.put("calienta", new String[] { "lena", "butano", "natural" });
        questions.put("techo", new String[] { "carton", "madera", "lamina", "placa" });
        questions.put("excretas", new String[] { "fecalismo", "letrina", "septica", "publica" });
        questions.put("agua", new String[] { "deposito", "pozo", "rios", "pipa", "llave", "garrafon", "entubada" });
        questions.put("piso", new String[] { "tierra", "spulir", "pulido", "madera", "mosaico" });
        questions.put("parroquia", new String[] { "si", "no" });
        questions.put("transporte", new String[] { "si", "no" });
        questions.put("salud", new String[] { "si", "no" });
        questions.put("mercados", new String[] { "si", "no" });
        questions.put("comerciales", new String[] { "si", "no" });
        questions.put("paredes", new String[] { "carton", "madera", "adobe", "cemento" });
        questions.put("ayuda", new String[] { "monetaria", "alimentaria", "no" });
        questions.put("poblacion", new String[] { "rural", "semirural", "urbana" });
        questions.put("educacion", new String[] { "kinder", "primaria", "secundaria" });

        Map<String, List<Double>> percentages = new HashMap<>();
        
        questions.entrySet().stream().forEach((question) -> {
            List<Double> result = new ArrayList<>();
            for(String answer: question.getValue()) {
                double filterCount = data.stream().filter((row) -> row.get(question.getKey()).equals(answer)).count();
                result.add(filterCount / data.size());
            }
            percentages.put(question.getKey(), result);
        });
        
        return new Report(data.size(), percentages);
    }
    
    public Map<String, Report> generateReports(List<String> dataFiles) throws FileNotFoundException {
        List<Map<String, String>> data = new ArrayList<>();
        for(String fileName: dataFiles) {
            data.add(readFile(fileName));
        }
        
        Map<String, Report> reports = new HashMap<>();
        reports.put("General", getReport(data));
        reports.put("Rural", getReport(data.stream().filter((row) -> row.get("poblacion").equals("rural")).collect(Collectors.toList())));
        reports.put("Semirural", getReport(data.stream().filter((row) -> row.get("poblacion").equals("semirural")).collect(Collectors.toList())));
        reports.put("Urbana", getReport(data.stream().filter((row) -> row.get("poblacion").equals("urbana")).collect(Collectors.toList())));
        
        return reports;
    }
    
}
