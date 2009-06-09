package org.gaixie.micrite.crm.action;

import org.gaixie.micrite.crm.service.ICustomerService;
import org.jfree.chart.JFreeChart;
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
    /**
     * 2D柱图
     * @return
     */
    public String getBarChart(){
        chart =  customerService.barCustomerSource();
        return SUCCESS ;
    }
    /**
     * 2D饼图
     * @return
     */
    public String getPieChart(){
        chart =  customerService.pieCustomerSource();
        return SUCCESS;
    }
    public JFreeChart getChart() {
        return chart;
    }
}
