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

package org.gaixie.micrite.crm.service.impl;

import java.util.List;

import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.CustomerSource;
import org.gaixie.micrite.crm.dao.ICustomerDao;
import org.gaixie.micrite.crm.service.ICustomerService;

/**
 * 客户管理功能实现
 * @author Maven Yu
 * @see org.gaixie.micrite.crm.service.ICustomerService
 */
public class CustomerServiceImpl implements ICustomerService {

	private ICustomerDao customerDao;

	/**
	 * @param customerDao the customerDao to set
	 */
	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gaixie.micrite.crm.service.ICustomerService#addOrUpdateCustomer(org
	 * .gaixie.micrite.beans.Customer)
	 */
	public void addOrUpdateCustomer(Customer customer, Integer customerSourceId) {
		CustomerSource cs = (CustomerSource) customerDao.getEntity(
				CustomerSource.class, customerSourceId);
		customer.setCustomerSource(cs);
		if (customer.getId() == null) {
			customerDao.save(customer);
		} else {
			customerDao.update(customer);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gaixie.micrite.crm.service.ICustomerService#findALLCustomerSource()
	 */
	public List<CustomerSource> findALLCustomerSource() {
		List<CustomerSource> customerSource = customerDao
				.findAll(CustomerSource.class);
		return customerSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gaixie.micrite.crm.service.ICustomerService#findByIdExact(int)
	 */
	public Customer findByIdExact(int id) {
		return (Customer) customerDao.getEntity(Customer.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gaixie.micrite.crm.service.ICustomerService#findByTelExact(java.lang
	 * .String)
	 */
	public List<Customer> findByTelExact(String telephone) {
		List<Customer> list = customerDao.findExact(Customer.class,
				"telephone", telephone);
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gaixie.micrite.crm.service.ICustomerService#getCustomerNum()
	 */
	public int getCustomerNum() {
		List<Customer> customers = customerDao.findAll(Customer.class);
		return customers == null ? 0 : customers.size();
	}

}
