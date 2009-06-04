/* ===========================================================
 * $Id$
 * This file is part of Micrite
 * ===========================================================
 *
 * (C) Copyright 2009, by Gaixie.org and Contributors.
 * 
 * Project Info:  http://micrite.gaixie.org/
 *
 * Micrite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Micrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Micrite.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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
/**
 * 图形工厂类
 * <p>
 * 解析结果集，生成图形并调用模版渲染
 */
public class DefindChartFactory {
    /**
     * 生成2D饼图<p>
     * 解析PieChart的dataset结果集，生成图形，并调用模版进行渲染
     * @param pc PieChart饼图的实体bean
     * @return JFreeChart
     */
    public JFreeChart getPieChart(PieChart pc) {
        String title = pc.getTitle();
        PieDataset pd = pieOfDataset(pc.getDataset());
        JFreeChart jfreechart = ChartFactory.createPieChart(title,
                pd, false, true, true);
        //调用模版1
        if("PIE1".equals(pc.getPieTemplate())){
            new Pietemplate().pie1Template(jfreechart,pd,pc);
        }
        else{
            new Pietemplate().pie1Template(jfreechart,pd,pc);
        }
        return jfreechart;
    }
    
    /**
     * 饼图结果集<p>
     * 把DefindPieDataset对象结果集转成JFreeChart的PieDataset结果集
     * @param list List<DefindPieDataset>
     * @return PieDataset
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
     * 生成2D柱状图<p>
     * 解析BarChart的list结果集，生成图形，并调用模版进行渲染
     * @param bc BarChart柱图的实体bean
     * @return JFreeChart
     */
    public JFreeChart getBar2DChart(BarChart bc) {
        CategoryDataset dca = bar2DOfDataset(bc.getList());
        JFreeChart jfreechart = ChartFactory.createBarChart(
                bc.getTitle(), bc.getCategoryAxisLabel(), bc.getValueAxisLabel(),
                dca, PlotOrientation.VERTICAL,
                false, false, false);
        if("BAR1".equals(bc.getBarTemplate())){
            new BarTemplate().bar1Template(jfreechart, dca, bc);
        }
        else{
            new BarTemplate().bar1Template(jfreechart, dca, bc);
        }
        return jfreechart;
    }
    /**
     * 柱状图结果集
     * 把DefindDefaultCategoryDataset对象结果集转成JFreeChart的CategoryDataset结果集
     * @param list
     * @return CategoryDataset
     */
    private CategoryDataset bar2DOfDataset(List<DefindDefaultCategoryDataset> list ){
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
