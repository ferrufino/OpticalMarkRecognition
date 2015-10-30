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

    public static void main(String args[]) {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter: ");
        String filename = "test/";
        filename += reader.next();
        System.out.println(filename);

        Gray8Image grayimage = ImageUtil.readImage(filename);
        
        ImageManipulation image = new ImageManipulation(grayimage);
        image.locateConcentricCircles();
        image.locateMarks();
        
        image.writeAscTemplate(filename + ".asc");
        image.writeConfig(filename + ".config");
    }    
}
