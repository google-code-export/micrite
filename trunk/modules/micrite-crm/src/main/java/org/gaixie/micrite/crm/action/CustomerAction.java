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
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Micrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
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

import com.opensymphony.xwork2.ActionSupport;

/**
 * CustomerAction用来响应用户对Customer基本信息维护时的操作，并调用相关的Service。
 * <p>
 * 通过调用相关的Service类，完成对Customer基本信息的增加，删除，修改，查询。
 * @see org.gaixie.micrite.crm.service.ICustomerService
 */
public class CustomerAction extends ActionSupport{ 
	
	private ICustomerService customerService;

    //以Map格式存放操作的结果，然后由struts2-json插件转换为json对象

	private Map<String,String> result = new HashMap<String,String>();

	//输出到页面的数据
	private int customerNum = 0;
	private List<Customer> customers;
	private List<CustomerSource> customerSource;

	//获取的页面参数
	private Integer customerId;
	private Customer customer;
	private String telephone;
	private Integer customerSourceId;

	/**
	 * 带参数构造函数，实例化对象，并通过参数初始化<strong>customerService</strong>
	 * @param customerService ICustomerService接口，通过Ioc模式注入业务实例
	 * @see org.gaixie.micrite.crm.service.ICustomerService
	 */
	public CustomerAction(ICustomerService customerService) {
		this.customerService = customerService;
	}
	/**
	 * 默认起始事件，获得显示数据，包含总客户数、客户来源
	 * @return "success"
	 */
	public String index() {
		customerNum = customerService.getCustomerNum();
		
        return SUCCESS;
	}
	/**
	 * 保存客户信息
	 * @return "success"
	 */
	public String save() {
		customer.setId(null);
		customerService.addOrUpdateCustomer(customer, customerSourceId);
		result.put("success", "true");
        return SUCCESS;
	}
	/**
	 * 查找客户信息
	 * @return "success"
	 */
	public String find() {
		customers = customerService.findByTelExact(telephone);
        return SUCCESS;
	}
	/**
	 * 获取修改的客户信息
	 * @return "success"
	 */
	public String edit() {
		customerService.addOrUpdateCustomer(customer, customerSourceId);
		result.put("success", "true");
        return SUCCESS;
	}
	
	public String getPartner(){
		customerSource = customerService.findALLCustomerSource();
		return SUCCESS;
	}
	
	/**********************getting setting method**************************/
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
	public void setCustomerId(Integer customerId) {
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
	public void setCustomerSourceId(Integer customerSourceId) {
		this.customerSourceId = customerSourceId;
	}
	/**
	 * @return the customerId
	 */
	public Integer getCustomerId() {
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
	public Integer getCustomerSourceId() {
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
	

}
