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
package org.gaixie.micrite.crm.service;

import java.util.Date;

import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.CustomerSource;
import org.gaixie.micrite.crm.dao.ICustomerDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(locations={"classpath:applicationContext-crm-bean.xml","classpath:databaseResource-hibernate.xml"})
public class CustomerServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ICustomerService customerService;
   
    @Autowired
    private ICustomerDAO customerDAO;
   
    private Customer customer;
    
    private SearchBean[] searchBean = new SearchBean[1];
    
    @Before //准备测试数据
    public void prepareTestData() {
        customer = new Customer();
        customer.setName("tommy");
        customer.setTelephone("1234");
        customer.setCreation_ts(new Date());
        CustomerSource cs = customerService.findALLCustomerSource().get(0);
        customer.setCustomerSource(cs);
        customerDAO.save(customer);
        //封装高级查询测试数据
        searchBean[0] = new SearchBean ("telephone",customer.getTelephone(),"=");
    }
    
    @Test
    public void advancedFindCount(){
        int before = customerService.advancedFindCount(searchBean);
        customer.setTelephone("5678");
        customerDAO.update(customer);
        int after = customerService.advancedFindCount(searchBean);
        Assert.assertEquals(1, before - after);
    }
}
