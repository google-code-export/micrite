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
package org.gaixie.micrite.jfreechart.style;

import java.awt.Color;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;

/**
 * 柱状图样式
 * <p>
 * 
 */
public class BarStyle {

    /**
     * 默认柱状图样式
     * <p>
     * 设置micrite统一的背景色
     * @param chart JFreeChart对象
     */
    public static void styleDefault(JFreeChart chart){
        BarStyle.setBackground(chart);
                
    }
    
    /**
     * 柱状图样式，适用于X轴 lable密集的chart
     * <p>
     * Y轴数据为整数，X轴的 lable 向上倾斜45度
     * @param chart JFreeChart对象
     */
    public static void styleOne(JFreeChart chart){
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        
        BarStyle.setBackground(chart);
                
        //Y轴设置
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        //X轴设置
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
    }

    /**
     * 设置默认图片背景色和柱图背景色
     * <p>
     * 目前默认图片背景色为#EEE,也是前端窗口的背景色  
     * @param chart JFreeChart对象
     */    
    public static void setBackground(JFreeChart chart){
        chart.setBackgroundPaint(null);
                
    }    
}
