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

import java.util.List;
import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.CustomerSource;

/**
 * 客户管理持久化接口
 */
public interface ICustomerDao {
    /**
     * 保存Customer
     * @param  customer customer对象
     */
    public void save(Customer customer);

    /**
     * 更新Customer
     * @param  customer customer对象
     */
    public void update(Customer customer);

    /**
     * 删除Customer
     * @param  customer customer对象
     */
    public void delete(Customer customer);

    /**
     * 根据ID获得Customer对象
     * @param  id Customer对象id
     * @return Customer对象
     */
    public Customer getCustomer(int id);

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
     * 获得Customer记录数
     * @return Customer对象数目
     */
    public int getCount();
    
    /**
     * 根据电话精确查找Customer
     * @param  telephone 字段名
     * @return Customer对象集合
     */
    public List<Customer> findByTelExact(String telephone);

    /**
     * 根据电话模糊查找Customer
     * @param  column 字段名
     * @param  value 字段值
     * @return Customer对象集合
     */
    public List<Customer> findByTelVague(String telephone);

}
