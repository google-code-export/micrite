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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.crm.service.ICustomerService;
import org.gaixie.micrite.jfreechart.style.BarStyle;
import org.gaixie.micrite.jfreechart.style.PieStyle;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.servlet.ServletUtilities;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 用户来源图形报表
 */
public class CustomerChartAction extends ActionSupport {
    private static final long serialVersionUID = -8118104364113464883L;
    @Autowired
    private ICustomerService customerService;
    private JFreeChart chart;
    private Map<String,Object> resultMap = new HashMap<String,Object>();
    private Customer customer;

    /**
     * 2D柱图
     * @return
     */
    public String getBarChart(){
        chart = ChartFactory.createBarChart("用户来源分析", 
                                            "来源", 
                                            "数量",
                                            customerService.getCustomerSourceBarDataset(customer.getTelephone()), 
                                            PlotOrientation.VERTICAL,
                                            false, false, false);
        BarStyle.styleDefault(chart);
        StandardEntityCollection entityCollection = new StandardEntityCollection();
        ChartRenderingInfo info = new ChartRenderingInfo(entityCollection);
        String filename = "";
        try {
            filename = ServletUtilities.saveChartAsPNG(chart, 600, 450, info, null);
            resultMap.put("success", true);
            resultMap.put("filename", filename);
        } catch (IOException e) {
            resultMap.put("success", false);
        }
        return SUCCESS ;
    }
    
    /**
     * 2D饼图
     * @return
     */
    public String getPieChart(){
        chart = ChartFactory.createPieChart("用户来源分析",
                                            customerService.getCustomerSourcePieDataset(customer.getTelephone()),
                                            false,
                                            true,
                                            false);
        PieStyle.styleDefault(chart);
        StandardEntityCollection entityCollection = new StandardEntityCollection();
        ChartRenderingInfo info = new ChartRenderingInfo(entityCollection);
        String filename = "";
        try {
            filename = ServletUtilities.saveChartAsPNG(chart, 600, 450, info, null);
            resultMap.put("success", true);
            resultMap.put("filename", filename);
        } catch (IOException e) {
            resultMap.put("success", false);
        }
        return SUCCESS ;
    }
    
    
    public JFreeChart getChart() {
        return chart;
    }
    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


}
