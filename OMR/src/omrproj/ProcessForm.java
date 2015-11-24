/*
 * ProcessImage.java
 *
 * Created on June 29, 2007, 10:11 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package omrproj;

import net.sourceforge.jiu.codecs.*;
import net.sourceforge.jiu.data.*;
import net.sourceforge.jiu.color.reduction.*;
import net.sourceforge.jiu.filters.*;

/**
 *
 * @author Aaditeshwar Seth
 */
public class ProcessForm {
    
    public static String procesarFormaEncuesta(String forma, String template) {
        String templatefilename = "test/" + template;
        
        Gray8Image grayimage = ImageUtil.readImage(forma);

        ImageManipulation image = new ImageManipulation(grayimage);
        image.locateConcentricCircles();
        
        image.readConfig(templatefilename + ".config");
        image.readFields(templatefilename + ".fields");
        image.readAscTemplate(templatefilename + ".asc");
        image.searchMarks();
        String aux = image.saveData(forma + ".dat");
        
        return aux;
    }
}