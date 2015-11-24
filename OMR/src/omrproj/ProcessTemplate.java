/*
 * ProcessTemplate.java
 *
 * Created on June 30, 2007, 12:12 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package omrproj;

import net.sourceforge.jiu.codecs.*;
import net.sourceforge.jiu.data.*;
import net.sourceforge.jiu.color.reduction.*;
import net.sourceforge.jiu.filters.*;
import net.sourceforge.jiu.geometry.*;
import java.util.Scanner;

/**
 *
 * @author Aaditeshwar Seth
 */
public class ProcessTemplate {   
    public static String procesarTemplateEncuesta(String template) {
        Gray8Image grayimage = ImageUtil.readImage(template);
        
        ImageManipulation image = new ImageManipulation(grayimage);
        image.locateConcentricCircles();
        image.locateMarks();
        
        image.writeAscTemplate(template + ".asc");
        image.writeConfig(template + ".config");
        
        return "Template escaneado correctamente";
    }
}
