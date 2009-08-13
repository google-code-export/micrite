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
import java.util.List;

import org.gaixie.micirte.common.search.SearchBean;
import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.CustomerSource;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

/**
 * 客户管理服务接口，封装客户服务业务模型
 */
public interface ICustomerService {
    /**
     * 新增客户
     * @param customer 客户实体
     */
    public void add(Customer customer);

    /**
     * 修改客户信息
     * @param customer 客户实体
     */
    public void update(Customer customer);

    /**
     * 获取客户来源
     * @return 客户来源集合
     */
    public List<CustomerSource> findALLCustomerSource();
    
   /**
    * 高级查询获取用户来源的饼图对象
    * @param telephone
    * @return PieDataset
    */
    public PieDataset getCustomerSourcePieDataset(SearchBean[] queryBean);
    
    /**
     * 高级查询获取用户来源的2D柱图对象
     * @return CategoryDataset
     */
    public CategoryDataset getCustomerSourceBarDataset(SearchBean[] queryBean);
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
     * 删除客户
     * @param customerIds 客户的id数组
     */
    public void delete(int[] customerIds);
    /**
     * 日期间隔查及customerSourceType普通询客户 
     * @param createDate
     * @param start
     * @param limit
     * @return
     */
    public List<Customer> findByCreateDateSpacingPerPage(Date startDate,Date endDate, int start, int limit,int customerSourceType);
    /**
     * 日期间隔及customerSourceType普通查询客户总记录数
     * @param createDate
     * @return
     */
    public int findByCreateDateSpacingCount(Date startDate,Date endDate,int customerSourceType);

}
