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

package org.gaixie.micrite.crm.action;

import org.gaixie.micrite.action.GenericAction;
import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.crm.service.ICustomerService;
import org.gaixie.micrite.jfreechart.style.BarStyle;
import org.gaixie.micrite.jfreechart.style.LineStyle;
import org.gaixie.micrite.jfreechart.style.PieStyle;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * 用户来源图形报表
 */
public class CustomerChartAction extends GenericAction {
    private static final long serialVersionUID = -8118104364113464883L;
    @Autowired
    private ICustomerService customerService;
    private JFreeChart chart;
//    private Map<String,Object> resultMap = new HashMap<String,Object>();
    private Customer customer;

    /**
     * 2D柱图
     * @return "success"
     */
    public String getBarChart(){
        CategoryDataset dca = customerService.getCustomerSourceBarDataset(this.getQueryBean());
        chart = ChartFactory.createBarChart(
                    getText("customer.source.chart.bar.title"), 
                    getText("customer.source.chart.bar.y"), 
                    getText("customer.source.chart.bar.x"),
                    dca, 
                    PlotOrientation.VERTICAL,
                    true, false, false);
        BarStyle.styleOne(chart);
        this.putChartResultList(chart);
        return SUCCESS ;
    }
    
    /**
     * 2D饼图
     * @return "success"
     */
    public String getPieChart(){
        PieDataset pd = customerService.getCustomerSourcePieDataset(this.getQueryBean());
        chart = ChartFactory.createPieChart(
                    getText("customer.source.chart.pie.title"),
                    pd,
                    true,
                    true,
                    false);
        PieStyle.styleOne(chart);
        this.putChartResultList(chart);
        return SUCCESS ;
    }
    /**
     * 测试折线图
     * @return
     */
    public String getLineChart(){
        chart = ChartFactory.createTimeSeriesChart("Time Series Demo 10",
                "Time", 
                "Value",
                createDataset(),
                true,
                true,
                false);
        LineStyle.styleOne(chart);
        this.putChartResultList(chart);
        return SUCCESS ;
    }
    /**
     * 折线图测试数据
     * @return
     */
    private static XYDataset createDataset()
    {
        TimeSeries timeseries = new TimeSeries("Per Minute Data", org.jfree.data.time.Minute.class);
        Hour hour = new Hour();
        timeseries.add(new Minute(1, hour), 10.199999999999999D);
        timeseries.add(new Minute(3, hour), 17.300000000000001D);
        timeseries.add(new Minute(9, hour), 14.6D);
        timeseries.add(new Minute(11, hour), 11.9D);
        timeseries.add(new Minute(15, hour), 13.5D);
        timeseries.add(new Minute(19, hour), 10.9D);
        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection(timeseries);
        return timeseriescollection;
    }
    
    public JFreeChart getChart() {
        return chart;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
