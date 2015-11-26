/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omrproj;

import java.util.List;
import java.util.Map;

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
    
    public void saveAsPDF() {
        
    }
    
}
