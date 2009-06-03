package org.gaixie.micrite.jfreechart;

import java.util.List;

import org.gaixie.micrite.jfreechart.chart.BarChart;
import org.gaixie.micrite.jfreechart.chart.PieChart;
import org.gaixie.micrite.jfreechart.data.DefindDefaultCategoryDataset;
import org.gaixie.micrite.jfreechart.data.DefindPieDataset;
import org.gaixie.micrite.jfreechart.template.BarTemplate;
import org.gaixie.micrite.jfreechart.template.Pietemplate;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class DefindChartFactory {
    /**
     * 2D饼图
     * 
     * @param co
     * @return
     */
    public JFreeChart getPieChart(PieChart co) {
        String title = co.getTitle();
        PieDataset pd = pieOfDataset(co.getDataset());
        JFreeChart jfreechart = ChartFactory.createPieChart(title,
                pd, false, true, true);
        //调用模版1
        if("PIE1".equals(co.getPieTemplate())){
            new Pietemplate().pie1Template(jfreechart,pd,co);
        }
        else{
            new Pietemplate().pie1Template(jfreechart,pd,co);
        }
        return jfreechart;
    }
    
    /**
     * 饼图结果集
     * 
     * @param list
     * @return
     */
    private PieDataset pieOfDataset(List<DefindPieDataset> list) {
        DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                defaultpiedataset.setValue(list.get(i).getKey(), Integer
                        .parseInt(list.get(i).getValue()));
            }
        } else {
            return null;
        }
        return defaultpiedataset;
    }
    /**
     * 2D柱状图
     * @param bco
     * @return
     */
    public JFreeChart getBar2DChart(BarChart bco) {
        CategoryDataset dca = bar2DOfdaataset(bco.getList());
        JFreeChart jfreechart = ChartFactory.createBarChart(
                bco.getTitle(), bco.getCategoryAxisLabel(), bco.getValueAxisLabel(),
                dca, PlotOrientation.VERTICAL,
                false, false, false);
        if("BAR1".equals(bco.getBarTemplate())){
            new BarTemplate().bar1Template(jfreechart, dca, bco);
        }
        else{
            new BarTemplate().bar1Template(jfreechart, dca, bco);
        }
        return jfreechart;
    }
    /**
     * 柱状图结果集
     * @param list
     * @return
     */
    public CategoryDataset bar2DOfdaataset(List<DefindDefaultCategoryDataset> list ){
        DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                defaultCategoryDataset.setValue(Integer.parseInt(list.get(i).getValue()),list.get(i).getRowKey(),list.get(i).getColumnKey());
            }
        } else {
            return null;
        }
        return defaultCategoryDataset;

    }}
