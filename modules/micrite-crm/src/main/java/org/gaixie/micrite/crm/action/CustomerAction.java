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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.gaixie.micrite.action.GenericAction;
import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.CustomerSource;
import org.gaixie.micrite.crm.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * CustomerAction用来响应用户对Customer基本信息维护时的操作，并调用相关的Service。
 * <p>
 * 通过调用相关的Service类，完成对Customer基本信息的增加，删除，修改，查询。
 */
public class CustomerAction extends GenericAction{ 
	private static final long serialVersionUID = 3072131320220662398L;

	@Autowired
	private ICustomerService customerService;

    private List<CustomerSource> customerSource;
    //获取的页面参数
    private Customer customer;
    private int[] customerIds;
    private Date startDate;
    private Date endDate;

    // ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    /**
     * 保存客户信息
     * @return "success"
     */
    public String add() {
        customer.setCreation_ts(new Date());
        customerService.add(customer);
        this.getResultMap().put("message", getText("save.success"));
        this.getResultMap().put("success", true);
        return SUCCESS;
    }
    /**
     * 高级查询客户信息
     * @return "success"
     */
    public String advancedFind(){
        if (isFirstSearch()) {
            //  初次查询时，要从数据库中读取总记录数
            Integer count = customerService.advancedFindCount(getQueryBean());
            setTotalCount(count);
        }         
        //  得到分页查询结果
        List<Customer> customers = customerService.advancedFindByPerPage(getQueryBean(), this.getStart(), this.getLimit());
        this.putResultList(customers);
        return SUCCESS;
    }
    /**
     * 日期间隔及customerSourceType普通查询客户 
     * @return
     */
    public String findByDateSpacing(){
        if (isFirstSearch()) {
            //  初次查询时，要从数据库中读取总记录数
            Integer count = customerService.findByCreateDateSpacingCount(startDate, endDate,customer.getCustomerSource().getId());
            setTotalCount(count);
        }         
        //  得到分页查询结果
        List<Customer> customers = customerService.findByCreateDateSpacingPerPage(startDate, endDate, this.getStart(), this.getLimit(),customer.getCustomerSource().getId());
        this.putResultList(customers);
        return SUCCESS;
    }
    /**
     * 获取修改的客户信息
     * @return "success"
     */
    public String update() {
        customerService.update(customer);
        this.getResultMap().put("message", getText("save.success"));
        this.getResultMap().put("success", true);
        return SUCCESS;
        
    }
    /**
     * 删除客户
     * @return "success"
     */
    public String delete(){
        customerService.delete(customerIds);
        this.getResultMap().put("message", getText("delete.success"));
        this.getResultMap().put("success", true);
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
    /**
     * 获得用户来源(含ALL)
     * @return "success"
     */
    public String getPartnerByAll(){
        customerSource = customerService.findALLCustomerSource();
        //在结果集中添加ALL
        CustomerSource tempSource = new CustomerSource();
        tempSource.setId(0);
        tempSource.setName("ALL");
        customerSource.add(tempSource);
        //按ID排序，把ALL放到List的最前面，用于下拉框显示时ALL在最上面
        Collections.sort(customerSource, new Comparator<CustomerSource>(){
            public int compare(CustomerSource arg0, CustomerSource arg1){
                return arg0.getId().compareTo(arg1.getId());
            }
        });

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
