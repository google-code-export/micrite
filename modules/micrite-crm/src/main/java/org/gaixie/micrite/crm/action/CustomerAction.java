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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String,String> result = new HashMap<String,String>();
    private Map<String,Object> resultMap = new HashMap<String,Object>();

    //输出到页面的数据
    private int customerNum = 0;
    private List<Customer> customers;
    private List<CustomerSource> customerSource;
    //获取的页面参数
    private int customerId;
    private Customer customer;
    private String telephone;
    private int customerSourceId;
    private int[] customerIds;
    
    //起始索引
    private int start;
    //限制数
    private int limit;
    //记录总数（分页中改变页码时，会传递该参数过来）
    private int totalCount;

    // ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    /**
     * 默认起始事件，获得显示数据，包含总客户数、客户来源
     * @return "success"
     */
    public String index() {
        customerNum = customerService.getNum();
        
        return SUCCESS;
    }
    /**
     * 保存客户信息
     * @return "success"
     */
    public String save() {
        customerService.add(customer, customerSourceId);
        result.put("success", "true");
        return SUCCESS;
    }
    /**
     * 查找客户信息
     * @return "success"
     */
    public String find() {
        if (totalCount == 0) {
            //  初次查询时，要从数据库中读取总记录数
            Integer count = customerService.findByTelVague(telephone).size();
            setTotalCount(count);
        }         
        //  得到分页查询结果
        customers = customerService.findByTelPerPage(telephone, start, limit);
        resultMap.put("totalCount", totalCount);
        resultMap.put("success", true);
        resultMap.put("data", customers);
        return SUCCESS;
    }
    /**
     * 获取修改的客户信息
     * @return "success"
     */
    public String edit() {
        try {
            customerService.update(customer, customerSourceId);
            resultMap.put("message", getText("save.success"));
            resultMap.put("success", true);
        } catch(SecurityException e) {
            resultMap.put("message", getText(e.getMessage()));
            resultMap.put("success", false);
        }
        return SUCCESS;
        
    }
    /**
     * 删除客户
     * @return
     */
    public String delete(){
        try {
            customerService.delete(customerIds);
            resultMap.put("message", getText("save.success"));
            resultMap.put("success", true);
        } catch(SecurityException e) {
            resultMap.put("message", getText(e.getMessage()));
            resultMap.put("success", false);
        }
        return SUCCESS;
    }
    
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
     * @return the customerNum
     */
    public int getCustomerNum() {
        return customerNum;
    }
    /**
     * @return the customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }
    /**
     * @return the customerSource
     */
    public List<CustomerSource> getCustomerSource() {
        return customerSource;
    }
    /**
     * @param id the id to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    /**
     * @param customerSourceId the customerSourceId to set
     */
    public void setCustomerSourceId(int customerSourceId) {
        this.customerSourceId = customerSourceId;
    }
    /**
     * @return the customerId
     */
    public int getCustomerId() {
        return customerId;
    }
    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }
    /**
     * @return the customerSourceId
     */
    public int getCustomerSourceId() {
        return customerSourceId;
    }
    /**
     * @param customerNum the customerNum to set
     */
    public void setCustomerNum(int customerNum) {
        this.customerNum = customerNum;
    }
    /**
     * @param customers the customers to set
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
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
}
