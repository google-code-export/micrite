package org.gaixie.micrite.jfreechart.chart;

import java.util.List;

import org.gaixie.micrite.jfreechart.data.DefindDefaultCategoryDataset;

public class BarChart {
    //标题
    private String title="";
    //X轴下标
    private String categoryAxisLabel="";
    //Y轴下标
    private String valueAxisLabel="";
    //样式
    private String barTemplate="";
    //结果集
    private List<DefindDefaultCategoryDataset> list = null;

    public String getBarTemplate() {
        return barTemplate;
    }
    public void setBarTemplate(String barTemplate) {
        this.barTemplate = barTemplate;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCategoryAxisLabel() {
        return categoryAxisLabel;
    }
    public void setCategoryAxisLabel(String categoryAxisLabel) {
        this.categoryAxisLabel = categoryAxisLabel;
    }
    public String getValueAxisLabel() {
        return valueAxisLabel;
    }
    public void setValueAxisLabel(String valueAxisLabel) {
        this.valueAxisLabel = valueAxisLabel;
    }
    public List<DefindDefaultCategoryDataset> getList() {
        return list;
    }
    public void setList(List<DefindDefaultCategoryDataset> list) {
        this.list = list;
    }
    
}
