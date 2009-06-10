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

package org.gaixie.micrite.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.CustomerSource;
import org.gaixie.micrite.crm.service.ICustomerService;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

public class CustomerServiceImplMock implements ICustomerService {
   
	private List<Customer> customers;
	private List<CustomerSource> customerSources;
	
	public CustomerServiceImplMock(){
		//	构建customerSource列表
		customerSources = new ArrayList<CustomerSource>();
		CustomerSource customerSource1 = new CustomerSource();
		customerSource1.setId(1);
		customerSource1.setName("Unfamiliar Visit");
		customerSources.add(customerSource1);
		CustomerSource customerSource2 = new CustomerSource();
		customerSource2.setId(2);
		customerSource2.setName("Familiar");
		customerSources.add(customerSource2);
		//	构建customer列表
		customers = new ArrayList<Customer>();
		Customer customer1 = new Customer();
		customer1.setId(1);
		customer1.setName("张三");
		customer1.setTelephone("10123456789");
		customer1.setCustomerSource(customerSource1);
		customers.add(customer1);
		Customer customer2 = new Customer();
		customer2.setId(2);
		customer2.setName("李四");
		customer2.setTelephone("20123456789");
		customer2.setCustomerSource(customerSource2);
		customers.add(customer2);
		Customer customer3 = new Customer();
		customer3.setId(3);
		customer3.setName("王五");
		customer3.setTelephone("30123456789");
		customer3.setCustomerSource(customerSource1);
		customers.add(customer3);
	}
	
	public void add(Customer customer, Integer customerSourceId) {
		//	根据Id查找customerSource
		CustomerSource wantedCustomerSource = null;
		for (CustomerSource customerSource:customerSources)
		{
			if (customerSource.getId() == customerSourceId)
			{
				wantedCustomerSource = customerSource;
			}
		}
		//	为customer设置customerSource
		customer.setCustomerSource(wantedCustomerSource);
		//	判断customer是否是已经保存过的数据决定更新还是新增
		Integer customerId = customer.getId();
		if (customerId != null)
		{
			Customer someCustomer = null;
			for (Customer customerExist:customers)
			{
				if (customerExist.getId() == customerId)
				{
					someCustomer = customerExist;
					break;
				}
			}
			customers.remove(someCustomer);
			customers.add(customer);
		}
		else
		{
			customer.setId(customers.size() + 1);
	     	customers.add(customer);
		}
	}
	public void update(Customer customer, Integer customerSourceId) {
		//	根据Id查找customerSource
		CustomerSource wantedCustomerSource = null;
		for (CustomerSource customerSource:customerSources)
		{
			if (customerSource.getId() == customerSourceId)
			{
				wantedCustomerSource = customerSource;
			}
		}
		//	为customer设置customerSource
		customer.setCustomerSource(wantedCustomerSource);
		//	判断customer是否是已经保存过的数据决定更新还是新增
		Integer customerId = customer.getId();
		if (customerId != null)
		{
			Customer someCustomer = null;
			for (Customer customerExist:customers)
			{
				if (customerExist.getId() == customerId)
				{
					someCustomer = customerExist;
					break;
				}
			}
			customers.remove(someCustomer);
			customers.add(customer);
		}
		else
		{
			customer.setId(customers.size() + 1);
	     	customers.add(customer);
		}
	}
	public List<CustomerSource> findALLCustomerSource() {
		return customerSources;
	}

	public Customer getCustomer(int id) {
		Customer wantedCustomer = null;
		for (Customer customer:customers)
		{
			if (customer.getId() == id)
			{
				wantedCustomer = customer;
			}
		}
		return wantedCustomer;
	}

	public List<Customer> findByTelExact(String telephone) {
		List<Customer> wantedCustomers = new ArrayList<Customer>();
		for (Customer customer:customers)
		{
			if (customer.getTelephone().equals(telephone))
			{
				wantedCustomers.add(customer);
			}
		}
		return wantedCustomers;
	}

	public List<Customer> findByTelVague(String telephone) {
		List<Customer> wantedCustomers = new ArrayList<Customer>();
		for (Customer customer:customers)
		{
			if (customer.getTelephone().equals(telephone))
			{
				wantedCustomers.add(customer);
			}
		}
		return wantedCustomers;
	}
	
	public int getNum() {
		return customers.size();
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public List<CustomerSource> getCustomerSources() {
		return customerSources;
	}

	public void setCustomerSources(List<CustomerSource> customerSources) {
		this.customerSources = customerSources;
	}
	
    public PieDataset getCustomerSourcePieDataset(){
        return null;
    }
    
    public CategoryDataset getCustomerSourceBarDataset(){
        return null;        
    }
}
