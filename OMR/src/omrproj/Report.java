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
    
    public void saveAsPDF(String reportName) throws FileNotFoundException, TransformerException, FOPException, IOException, ZipException {
        long time = System.currentTimeMillis();
        
        try (FileWriter writer = new FileWriter(new File("Template/Plantilla/word/document.xml"))) {
            String content = String.join("\n", Files.readAllLines(Paths.get("document.xml"), StandardCharsets.UTF_8).toArray(new String[] {}));
            writer.append(content);
        }
        
        ZipFile zipFile = new ZipFile(reportName+"_reporte_" + System.currentTimeMillis() + ".zip");
        ZipParameters parameters = new ZipParameters();

        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        
        zipFile.addFolder("Template/Plantilla", parameters);
    }
    
}
