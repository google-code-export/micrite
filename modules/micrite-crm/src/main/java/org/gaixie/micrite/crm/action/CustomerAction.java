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

import java.util.List;

import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.CustomerSource;
import org.gaixie.micrite.crm.service.ICustomerService;

import com.opensymphony.xwork2.ActionSupport;

public class CustomerAction extends ActionSupport{ 
	
	private ICustomerService customerService;

	public CustomerAction(ICustomerService customerService) {
		this.customerService = customerService;
	}

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
	 * 
	 * @return
	 */
	public String index() {
		customerNum = customerService.getCustomerNum();
		customerSource = customerService.findALLCustomerSource();
        return SUCCESS;
	}
	/**
	 * 新增/修改事件
	 * 
	 * @return
	 */
	public String save() {
		customerService.addOrUpdateCustomer(customer, customerSourceId);
		customer = null;
        return index();
	}
	/**
	 * 查找用户事件
	 * 
	 * @return
	 */
	public String find() {
		customers = customerService.findByTelExact(telephone);
        return index();
	}
	/**
	 * 获取修改实体
	 * 
	 * @return
	 */
	public String edit() {
		customer = customerService.findByIdExact(customerId);
		customerSourceId = customer.getCustomerSource().getId();
        return index();
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
