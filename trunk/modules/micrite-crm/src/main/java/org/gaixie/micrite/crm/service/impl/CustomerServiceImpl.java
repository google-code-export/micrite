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

import java.util.Date;
import java.util.List;

import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.CustomerSource;
import org.gaixie.micrite.crm.dao.ICustomerDAO;
import org.gaixie.micrite.crm.service.ICustomerService;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * 客户管理功能实现
 * @see org.gaixie.micrite.crm.service.ICustomerService
 */
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerDAO customerDAO;

    public void add(Customer customer) {
        customerDAO.save(customer);
    }
    
    public void update(Customer c) {
        Customer customer = customerDAO.get(c.getId());
        customer.setCustomerSource(c.getCustomerSource());
        customer.setName(c.getName());
        customer.setTelephone(c.getTelephone());
        customerDAO.update(customer);
    }

    public List<CustomerSource> findALLCustomerSource() {
        List<CustomerSource> customerSource = customerDAO.findAllCustomerSource();
        return customerSource;
    }

    
    public CategoryDataset getCustomerSourceBarDataset(String tel) {
        List list = customerDAO.findCSGroupByTelVague(tel);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                Object[] obj = (Object[]) list.get(i);
                dataset.setValue(Integer.parseInt(obj[0].toString()),"",obj[1].toString());
            }
        } else {
            return null;
        }
        return dataset;
    }
    
    public PieDataset getCustomerSourcePieDataset(String telphone) {
        List list = customerDAO.findCSGroupByTelVague(telphone);
        DefaultPieDataset dataset = new DefaultPieDataset();
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                Object[] obj = (Object[]) list.get(i);
                dataset.setValue( obj[1].toString(),Integer.parseInt(obj[0].toString()));
            }
        } else {
            return null;
        }
        return dataset;
        
    }

    public List<Customer> findByTelVaguePerPage(String telephone, int start,
            int limit) {
        List<Customer> list = customerDAO.findByTelVaguePerPage(telephone,start,limit);
        return list;
    }

    public int findByTelVagueCount(String telephone) {
        return customerDAO.findByTelVagueCount(telephone); 
    }

    public void delete(int[] customerIds) {
        for (int i = 0; i < customerIds.length; i++) { 
            Customer customer = customerDAO.get(customerIds[i]);
            customerDAO.delete(customer);
        }
    }

    public int findByCreateDateSpacingCount(Date startDate, Date endDate,int customerSourceType) {
        return customerDAO.findByCreateDateSpacingCount(startDate, endDate,customerSourceType);
    }

    public List<Customer> findByCreateDateSpacingPerPage(Date startDate,
            Date endDate, int start, int limit,int coustomerSourceType) {
        List<Customer> list = customerDAO.findByCreateDateSpacingPerPage(startDate, endDate, start, limit,coustomerSourceType);
        return list;
    }
}
