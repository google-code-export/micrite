package org.gaixie.micrite.jfreechart.chart;

import java.util.List;

import org.gaixie.micrite.jfreechart.data.DefindPieDataset;


public class PieChart {
    //标题
    private String title = "";
    //结果集
    private List<DefindPieDataset> dataset = null;
    //样式
    private String pieTemplate = "";
   
    public String getPieTemplate() {
        return pieTemplate;
    }
    public void setPieTemplate(String pieTemplate) {
        this.pieTemplate = pieTemplate;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<DefindPieDataset> getDataset() {
        return dataset;
    }
    public void setDataset(List<DefindPieDataset> dataset) {
        this.dataset = dataset;
    }
    
    
}
