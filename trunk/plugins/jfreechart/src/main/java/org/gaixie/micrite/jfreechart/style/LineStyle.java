package org.gaixie.micrite.jfreechart.style;

import java.awt.Color;
import java.awt.Paint;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;

public class LineStyle {

    /**
     * 默认折线图样式
     * <p>
     * 设置micrite统一的背景色
     * @param chart JFreeChart对象
     */
    public static void styleDefault(JFreeChart chart){
        LineStyle.setBackground(chart);
    }
    public static void styleOne(JFreeChart chart){
        XYPlot xyplot = (XYPlot)chart.getPlot();
        XYDataset xyd = xyplot.getDataset();
        //画板底色
        LineStyle.setBackground(chart);
        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)xyplot.getRenderer();
        if(xyd!=null){
            //显示数据结点
            xylineandshaperenderer.setBaseShapesVisible(true);
            //在结点上显示数值
            xylineandshaperenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
            xylineandshaperenderer.setBaseItemLabelsVisible(true);
            xyplot.setRenderer(xylineandshaperenderer);
            //tooltip
//            xyplot.getRenderer().setBaseToolTipGenerator(new StandardXYToolTipGenerator("{0}: ({1}, {2})",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),new DecimalFormat()));
            //循环取色彩
            for(int i = 0 ;i<xyd.getSeriesCount();i++){
                int colorIdx = i % colors.length;
                xylineandshaperenderer.setSeriesPaint(i, colors[colorIdx]);
            }
        }
        else{
            xyplot.setNoDataMessage("NO DATA");
        }
        
    }
    /**
     * 设置默认图片背景色
     * <p>
     * 目前默认图片背景色为#EEE,也是前端窗口的背景色  
     * @param chart JFreeChart对象
     */    
    public static void setBackground(JFreeChart chart){
        chart.setBackgroundPaint(null);
    }  
    /**
     * 色彩方案
     */
    public static Paint colors[] = { 
        Color.decode("#999933"), Color.decode("#88AACC"),
        Color.decode("#666699"), Color.decode("#CC9933"),
        Color.decode("#006666"), Color.decode("#3399FF"),
        Color.decode("#993300"), Color.decode("#AAAA77"),
        Color.decode("#666666"), Color.decode("#FFCC66"),
        Color.decode("#6699CC"), Color.decode("#663366"),
        Color.decode("#9999CC"), Color.decode("#AAAAAA"),
        Color.decode("#669999"), Color.decode("#BBBB55"),
        Color.decode("#CC6600"), Color.decode("#9999FF"),
        Color.decode("#0066CC"), Color.decode("#99CCCC"),
        Color.decode("#999999"), Color.decode("#FFCC00"),
        Color.decode("#009999"), Color.decode("#99CC33"),
        Color.decode("#FF9900"), Color.decode("#999966"),
        Color.decode("#66CCCC"), Color.decode("#339966"),
        Color.decode("#CCCC33") 
    };

}
