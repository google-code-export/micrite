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

package org.gaixie.micrite.crm.dao;

import java.util.Date;
import java.util.List;

import org.gaixie.micirte.common.search.SearchBean;
import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.CustomerSource;
import org.gaixie.micrite.dao.IGenericDAO;
/**
 * 客户管理持久化接口
 */
public interface ICustomerDAO extends IGenericDAO<Customer, Integer> {

    /**
     * 根据ID获得CustomerSource对象
     * @param  id CustomerSource对象id
     * @return CustomerSource对象
     */
    public CustomerSource getCustomerSource(int id);

    /**
     * 获得所有CustomerSource
     * @return CustomerSource对象集合
     */
    public List<CustomerSource> findAllCustomerSource();


    /**
     * 高级查询并按用户来源进行分组
     * @param telphone
     * @return 分组数据集合
     */
    public List findCSGroupByTelVague(SearchBean[] queryBean);
    /**
     * 高级查询客户
     * @param telephone
     * @param start
     * @param limit
     * @return 客户集合
     */
    public List<Customer> advancedFindByPerPage(SearchBean[] queryBean, int start, int limit);
    /**
     * 高级查询客户总记录数
     * @param telephone
     * @return 客户数量
     */
    public int advancedFindCount(SearchBean[] queryBean);
    /**
     *根据创建日期及customerSourceType普通查询客户
     * @param createDate
     * @param start
     * @param limit
     * @return
     */
    public List<Customer> findByCreateDateSpacingPerPage(Date startDate,Date endDate, int start, int limit,int customerSourceType);
    /**
     * 根据创建日期及customerSourceType普通查询客户总记录数
     * @param createDate
     * @return
     */
    public int findByCreateDateSpacingCount(Date startDate,Date endDatem,int customerSourceType);

}
