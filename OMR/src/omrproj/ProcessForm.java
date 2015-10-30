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
import java.util.Scanner;


/**
 *
 * @author Aaditeshwar Seth
 */
public class ProcessForm {
    
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter Form File: ");
        String imgfilename = "test/";
        imgfilename += reader.next();
        System.out.println(imgfilename);
        System.out.println("Enter Template File: ");
        String templatefilename = "test/";
        templatefilename += reader.next();
        System.out.println(templatefilename);

        Gray8Image grayimage = ImageUtil.readImage(imgfilename);

        ImageManipulation image = new ImageManipulation(grayimage);
        image.locateConcentricCircles();

        image.readConfig(templatefilename + ".config");
        image.readFields(templatefilename + ".fields");
        image.readAscTemplate(templatefilename + ".asc");
        image.searchMarks();
        image.saveData(imgfilename + ".dat");
    }
}