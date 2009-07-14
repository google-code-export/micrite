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
     * 按用户来源进行分组
     * @return
     */
//    public List findCSGroup(String telphone);
    /**
     * 根据电话模糊查询并按用户来源进行分组
     * @param tel
     * @return
     */
    public List findCSGroupByTelVague(String telphone);
    /**
     * 分页查询客户
     * @param telephone
     * @param start
     * @param limit
     * @return
     */
    public List<Customer> findByTelVaguePerPage(String telephone, int start, int limit);
    /**
     * 根据电话模糊查询总记录数
     * @param telephone
     * @return
     */
    public int findByTelVagueCount(String telephone);

}