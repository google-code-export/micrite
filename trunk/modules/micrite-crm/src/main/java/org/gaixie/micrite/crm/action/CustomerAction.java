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

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.CustomerSource;
import org.gaixie.micrite.crm.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

/**
 * CustomerAction用来响应用户对Customer基本信息维护时的操作，并调用相关的Service。
 * <p>
 * 通过调用相关的Service类，完成对Customer基本信息的增加，删除，修改，查询。
 */
public class CustomerAction extends ActionSupport{ 
	private static final long serialVersionUID = 3072131320220662398L;

	@Autowired
	private ICustomerService customerService;

    //以Map格式存放操作的结果，然后由struts2-json插件转换为json对象
    private Map<String,Object> resultMap = new HashMap<String,Object>();

    private List<CustomerSource> customerSource;
    //获取的页面参数
    private Customer customer;
    private int[] customerIds;
    private Date startDate;
    private Date endDate;
   
    //起始索引
    private int start;
    //限制数
    private int limit;
    //记录总数（分页中改变页码时，会传递该参数过来）
    private int totalCount;

    // ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    /**
     * 保存客户信息
     * @return "success"
     */
    public String add() {
        customer.setCreation_ts(new Date());
        customerService.add(customer);
        resultMap.put("message", getText("save.success"));
        resultMap.put("success", true);
        return SUCCESS;
    }
    /**
     * 电话查找客户信息
     * @return "success"
     */
    public String findByTelVague() {
        if (totalCount == 0) {
            //  初次查询时，要从数据库中读取总记录数
            Integer count = customerService.findByTelVagueCount(customer.getTelephone());
            setTotalCount(count);
        }         
        //  得到分页查询结果
        List<Customer> customers = customerService.findByTelVaguePerPage(customer.getTelephone(), start, limit);
        resultMap.put("totalCount", totalCount);
        resultMap.put("success", true);
        resultMap.put("data", customers);
        return SUCCESS;
    }
    /**
     * 日期间隔查询客户 
     * @return
     */
    public String findByDateSpacing(){
        if (totalCount == 0) {
            //  初次查询时，要从数据库中读取总记录数
            Integer count = customerService.findByCreateDateSpacingCount(startDate, endDate);
            setTotalCount(count);
        }         
        //  得到分页查询结果
        List<Customer> customers = customerService.findByCreateDateSpacingPerPage(startDate, endDate, start, limit);
        resultMap.put("totalCount", totalCount);
        resultMap.put("success", true);
        resultMap.put("data", customers);
        return SUCCESS;
    }
    /**
     * 获取修改的客户信息
     * @return "success"
     */
    public String update() {
        customerService.update(customer);
        resultMap.put("message", getText("save.success"));
        resultMap.put("success", true);
        return SUCCESS;
        
    }
    /**
     * 删除客户
     * @return "success"
     */
    public String delete(){
        customerService.delete(customerIds);
        resultMap.put("message", getText("delete.success"));
        resultMap.put("success", true);
        return SUCCESS;
    }
    /**
     * 获得用户来源
     * @return "success"
     */
    public String getPartner(){
        customerSource = customerService.findALLCustomerSource();
        return SUCCESS;
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }
    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    /**
     * @return the customerSource
     */
    public List<CustomerSource> getCustomerSource() {
        return customerSource;
    }

    /**
     * @param customerSource the customerSource to set
     */
    public void setCustomerSource(List<CustomerSource> customerSource) {
        this.customerSource = customerSource;
    }
    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }
    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }
    public int[] getCustomerIds() {
        return customerIds;
    }
    public void setCustomerIds(int[] customerIds) {
        this.customerIds = customerIds;
    }
    public void setStartDate(String startDate) throws ParseException {
        this.startDate = DateUtils.parseDate(startDate + ":00", new String[]{"yyyy-MM-dd hh:mm:ss"});
    }
    public void setEndDate(String endDate) throws ParseException {
        this.endDate = DateUtils.parseDate(endDate + ":00", new String[]{"yyyy-MM-dd hh:mm:ss"});
    }
}
