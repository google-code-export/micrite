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

import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.CustomerSource;
import org.gaixie.micrite.crm.dao.ICustomerDAO;
import org.gaixie.micrite.crm.dao.ICustomerSourceDAO;
import org.gaixie.micrite.crm.service.ICRMDataPrepareService;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * CRM数据初始化接口实现
 * @see org.gaixie.micrite.crm.service.ICRMDataPrepareService
 */
public class CRMDataPrepareServiceImpl implements ICRMDataPrepareService { 

    @Autowired
    private ICustomerSourceDAO customerSourceDAO;

    @Autowired
    private ICustomerDAO customerDAO;
    
    public void initDataForRun() {
        if(customerSourceDAO.get(1)!=null) return;
        
        //insert  into customer_source(name) values('Unfamiliar Visit');
        //insert  into customer_source(name) values('Familiar'); 
        CustomerSource cs = new CustomerSource("Unfamiliar Visit");
        customerSourceDAO.save(cs);
        cs = new CustomerSource("Familiar");
        customerSourceDAO.save(cs);
        
        Customer customer = new Customer("Bob Yu","139000000",new Date(),cs);
        customerDAO.save(customer);
    }
}
