/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omrproj;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.MimeConstants;
import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.model.fields.FieldUpdater;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.xml.sax.SAXException;

/**
 *
 * @author alfredo_altamirano
 */
public class Report {

    private int surveyCount;
    private Map<String, List<Double>> percentages;

    Report(int surveyCount, Map<String, List<Double>> percentages) {
        this.surveyCount = surveyCount;
        this.percentages = percentages;
    }
    
    public void saveAsPDF(String reportName) throws FileNotFoundException, TransformerException, FOPException, IOException, ZipException, Docx4JException, Exception {
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
        
        try (FileWriter writer = new FileWriter(new File("Template/Plantilla/word/document.xml"))) {
            String content = String.join("\n", Files.readAllLines(Paths.get("Template/document.xml"), StandardCharsets.UTF_8).toArray(new String[] {}));
            
            content = content.replace("ytituloy", reportName);
            content = content.replace("yctdy", Integer.toString(surveyCount));
            for(Map.Entry<String, List<Double>> entry : percentages.entrySet()) {
                for(int i = 0; i < entry.getValue().size(); i++) {
                    String varName = "y" + entry.getKey().substring(0, 2) + questions.get(entry.getKey())[i].substring(0, 2) + "y";
                    content = content.replace(varName, Math.round(entry.getValue().get(i) * 100) + "");
                }
            }
            
            writer.append(content);
        }
        
        ZipFile zipFile = new ZipFile("Reports/" + reportName + ".docx");
        ZipParameters parameters = new ZipParameters();

        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        
        zipFile.addFolder("Template/Plantilla/_rels", parameters);
        zipFile.addFolder("Template/Plantilla/docProps", parameters);
        zipFile.addFolder("Template/Plantilla/word", parameters);
        zipFile.addFile(new File("Template/Plantilla/[Content_Types].xml"), parameters);
        
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new java.io.File("Reports/" + reportName + ".docx"));
        
        FieldUpdater updater = new FieldUpdater(wordMLPackage);
	updater.update(true);
		
	Mapper fontMapper = new IdentityPlusMapper();
	wordMLPackage.setFontMapper(fontMapper);
        
        FOSettings foSettings = Docx4J.createFOSettings();
        
        foSettings.setWmlPackage(wordMLPackage);
        String outputfilepath = "Reports/" + reportName + ".pdf";
        OutputStream os = new java.io.FileOutputStream(outputfilepath);
        Docx4J.toFO(foSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
        
    }
    
}
