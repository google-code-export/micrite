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
package org.gaixie.micrite.jfreechart.template;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;

import org.gaixie.micrite.jfreechart.chart.BarChart;
import org.gaixie.micrite.jfreechart.template.color.ColorPaint;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.TextAnchor;
/**
 * 柱状图模版
 */
public class BarTemplate {
   /**
    * 柱状图模版1<p>
    * 设置图片底色，画板色，每个柱图列的色彩，数据显示的字体，样式，X轴，Y轴等图形渲染
    * @param jfreechart JFreeChart对象
    * @param dca CategoryDataset(JFreeChart柱图的一种结果集对象)
    * @param bc BarChart柱图实体bean
    */
    public void bar1Template(JFreeChart jfreechart,CategoryDataset dca,BarChart bc){
        CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
        // 图片底色
        jfreechart.setBackgroundPaint(new GradientPaint(0F, 0.0F, Color
                .decode("#CAD0C6"), 500, 400F, Color.white, false));
        // 画板底色
        categoryplot.setBackgroundPaint(new GradientPaint(0F, 0.0F, Color
                .decode("#CAD0C6"), 500F, 400F, Color.white, false));

        if(dca!=null){
            CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
            for(int i = 0 ;i<dca.getRowCount();i++){
                int colorIdx = i % ColorPaint.colors.length;
                categoryitemrenderer.setSeriesPaint(i, ColorPaint.colors[colorIdx]);
            }
        }
        else{
            //无数据
            categoryplot.setNoDataMessage("NO DATA");
        }
        // 显示每个柱的数值，并修改该数值的字体属性
        categoryplot.getRenderer().setBaseItemLabelGenerator(
                new StandardCategoryItemLabelGenerator());
        categoryplot.getRenderer().setBaseItemLabelFont(
                new Font("黑体", Font.PLAIN, 15));
        categoryplot.getRenderer().setBaseItemLabelsVisible(true);
        categoryplot.getRenderer().setBaseItemURLGenerator(null);
        categoryplot.getRenderer().setBasePositiveItemLabelPosition(
                new ItemLabelPosition(ItemLabelAnchor.CENTER,
                        TextAnchor.CENTER, TextAnchor.CENTER, 300D));
        //Y轴设置
        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        numberaxis.setUpperMargin(0.14999999999999999D);
        //X轴设置
        CategoryAxis categoryaxis = categoryplot.getDomainAxis();
        categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
    }
}
