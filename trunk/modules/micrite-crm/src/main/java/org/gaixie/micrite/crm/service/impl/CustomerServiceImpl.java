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
import org.gaixie.micrite.crm.dao.ICustomerDao;
import org.gaixie.micrite.crm.service.ICustomerService;
import org.gaixie.micrite.jfreechart.DefindChartFactory;
import org.gaixie.micrite.jfreechart.chart.BarChart;
import org.gaixie.micrite.jfreechart.chart.PieChart;
import org.gaixie.micrite.jfreechart.data.DefindDefaultCategoryDataset;
import org.gaixie.micrite.jfreechart.data.DefindPieDataset;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * 客户管理功能实现
 * @see org.gaixie.micrite.crm.service.ICustomerService
 */
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerDao customerDao;

    public void add(Customer customer, Integer customerSourceId) {
        CustomerSource cs = customerDao.getCustomerSource(customerSourceId);
        customer.setCustomerSource(cs);
        customerDao.save(customer);
    }
    
    public void update(Customer customer, Integer customerSourceId) {
        CustomerSource cs = customerDao.getCustomerSource(customerSourceId);
        customer.setCustomerSource(cs);
        customerDao.update(customer);
    }

    public List<CustomerSource> findALLCustomerSource() {
        List<CustomerSource> customerSource = customerDao
                .findAllCustomerSource();
        return customerSource;
    }

    public Customer getCustomer(int id) {
        return customerDao.getCustomer(id);
    }

    public List<Customer> findByTelExact(String telephone) {
        List<Customer> list = customerDao.findByTelExact(telephone);
        return list;
    }

    public List<Customer> findByTelVague(String telephone) {
        List<Customer> list = customerDao.findByTelVague(telephone);
        return list;
    }
    
    public int getNum() {
        int count = customerDao.getCount();
        return  count;
    }
    public JFreeChart barCustomerSource() {
        DefindChartFactory mcf = new DefindChartFactory();
        BarChart bco = new BarChart();
        bco.setTitle("用户来源分析");
        bco.setCategoryAxisLabel("来源");
        bco.setValueAxisLabel("数量");
        bco.setList(createDateset());
        return mcf.getBar2DChart(bco);
    }
    
    public List<DefindDefaultCategoryDataset> createDateset(){
        List<DefindDefaultCategoryDataset> barList = new ArrayList<DefindDefaultCategoryDataset>();
        List list = customerDao.groupByCustomerSource();
        for(int i =0 ;i<list.size();i++){
            Object[] obj = (Object[]) list.get(i);
            barList.add(new DefindDefaultCategoryDataset(obj[0].toString(),"",obj[1].toString()));
        }
        return barList;
    }

    public JFreeChart pieCustomerSource() {
        DefindChartFactory mcf = new DefindChartFactory();
        PieChart pc = new PieChart();
        pc.setTitle("用户来源分析");
        pc.setDataset(pieDataset());
        return mcf.getPieChart(pc);
       }
    
    public List<DefindPieDataset> pieDataset(){
        List<DefindPieDataset> pieList = new ArrayList<DefindPieDataset>();
        List list = customerDao.groupByCustomerSource();
        for(int i =0 ;i<list.size();i++){
            Object[] obj = (Object[]) list.get(i);
            pieList.add(new DefindPieDataset(obj[1].toString(),obj[0].toString()));
        }
        return pieList;
    }

}
