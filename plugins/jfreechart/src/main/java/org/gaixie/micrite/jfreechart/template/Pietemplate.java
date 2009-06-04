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
import java.awt.GradientPaint;

import org.gaixie.micrite.jfreechart.chart.PieChart;
import org.gaixie.micrite.jfreechart.template.color.ColorPaint;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.BorderArrangement;
import org.jfree.chart.block.EmptyBlock;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.CompositeTitle;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RectangleEdge;
/**
 * 饼图模版
 */
public class Pietemplate {
    /**
     * 饼图模版1<p>
     * 设置图片底色，画板色，每个饼片的色彩，饼片数据显示的字体等图形渲染
     * @param jfreechart JFreeChart
     * @param pd PieDataset（JFreeChart饼图的结果集对象）
     * @param pc PieChart饼图的实体bean
     */
    public void pie1Template(JFreeChart jfreechart,PieDataset pd,PieChart pc){
        PiePlot pieplot = (PiePlot)jfreechart.getPlot();
        //图片底色
        jfreechart.setBackgroundPaint(new GradientPaint(0F, 0.0F, Color.decode("#CAD0C6"), 500, 400F, Color.white, false));
        
        //画板底色
        pieplot.setBackgroundPaint(new GradientPaint(0F, 0.0F, Color.decode("#CAD0C6"), 500F, 400F, Color.white, false));
        
        //legend设置
        LegendTitle legendtitle = new LegendTitle(pieplot);
        BlockContainer blockcontainer = new BlockContainer(new BorderArrangement());
        blockcontainer.add(legendtitle, RectangleEdge.BOTTOM);
        blockcontainer.add(new EmptyBlock(0D, 0.0D));
        CompositeTitle compositetitle = new CompositeTitle(blockcontainer);
        compositetitle.setPosition(RectangleEdge.BOTTOM);
        jfreechart.addSubtitle(compositetitle);
        //label设置
        pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2} percent)"));
        pieplot.setLabelBackgroundPaint(new Color(220, 220, 220));
        pieplot.setSimpleLabels(true);
        pieplot.setInteriorGap(0.0D);
        
        //设置饼片色彩
        if(pd != null){
            //循环为每个饼片设置颜色
            for (int i = 0; i < pd.getItemCount(); i++) {
                // 循环从色彩配置中读取颜色
                int color = i % ColorPaint.colors.length;
                pieplot.setSectionPaint(pd.getKey(i), ColorPaint.colors[color]);
            }
        }
        else{
            //无数据
            pieplot.setNoDataMessage("NO DATA");
        }
        
    }
}
