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
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.BorderArrangement;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RectangleEdge;

/**
 * 饼图样式
 */
public class PieStyle {

    /**
     * 默认饼图样式
     * <p>
     * 设置micrite统一的背景色
     * @param chart JFreeChart对象
     */
    public static void styleDefault(JFreeChart chart){
        PieStyle.setBackground(chart);
                
    }
    
    /**
     * 饼图样式One，如果大量信息无法在饼图全部显示，可以放在底部的block显示
     * <p>
     * 含百分比，底部显示 LegendTitle
     * @param chart JFreeChart
     */
    public static void styleOne(JFreeChart chart){
        PiePlot plot = (PiePlot)chart.getPlot();
        
        PieStyle.setBackground(chart);
        
        // 创建用于容纳LegendTitle的block，并设置block的边框
        BlockContainer block = new BlockContainer(new BorderArrangement());
        block.setBorder(new BlockBorder(1.0D, 1.0D, 1.0D, 1.0D));
        
        // 得到LegendTitle的block
        LegendTitle legend = new LegendTitle(plot);        
        BlockContainer legendBlock = legend.getItemContainer();
        
        // 将legendBlock放入父block，并位于底部
        block.add(legendBlock);
        legend.setWrapper(block);
        legend.setPosition(RectangleEdge.BOTTOM);

        chart.addSubtitle(legend);
        //label设置
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})"));
        
    }
    
    /**
     * 设置默认图片背景色
     * <p>
     * 目前默认图片背景色为#EEE,也是前端窗口的背景色  
     * @param chart JFreeChart对象
     */    
    public static void setBackground(JFreeChart chart){
    }       
}
