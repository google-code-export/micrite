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

package org.gaixie.micrite.crm.dao.hibernate;

import java.util.List;
import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.CustomerSource;
import org.gaixie.micrite.crm.dao.ICustomerDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 客户管理持久化实现，基于hibernate
 * @see org.gaixie.micrite.crm.dao.ICustomerDao
 */
public class CustomerDaoImpl extends HibernateDaoSupport implements ICustomerDao {

    /*
     * (non-Javadoc)
     * 
     * @see org.gaixie.micrite.dao.ICustomerDao#save(org.gaixie.micrite.beans.Customer)
     */
    public void saveCustomer(Customer customer) {
        getHibernateTemplate().save(customer);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.gaixie.micrite.dao.ICustomerDao#update(org.gaixie.micrite.beans.Customer)
     */
    public void updateCustomer(Customer customer) {
        getHibernateTemplate().update(customer);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.gaixie.micrite.dao.ICustomerDao#delete(org.gaixie.micrite.beans.Customer)
     */
    public void deleteCustomer(Customer customer) {
        getHibernateTemplate().delete(customer);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.gaixie.micrite.dao.ICustomerDao#getCustomer(int)
     */
    public Customer getCustomer(int id) {
        return (Customer) getHibernateTemplate().get(Customer.class, id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.gaixie.micrite.dao.ICustomerDao#getCustomerSource(int)
     */
    public CustomerSource getCustomerSource(int id) {
        return (CustomerSource) getHibernateTemplate().get(CustomerSource.class, id);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.gaixie.micrite.dao.ICustomerDao#findAllCustomerSource()
     */
    public List findAllCustomerSource() {
        return getHibernateTemplate().find(
                "from " + CustomerSource.class.getSimpleName() + " e");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.gaixie.micrite.dao.ICustomerDao#getCustomerCount()
     */
    public int getCustomerCount() {
        String sql = "select count(*) from " + Customer.class.getSimpleName();
        return ((Number) getHibernateTemplate()
                .getSessionFactory().getCurrentSession().createQuery(sql).uniqueResult()).intValue();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.gaixie.micrite.dao.ICustomerDao#findCustomerExact(java.lang.String, java.lang.Object)
     */
    public List findCustomerExact(String column, Object value) {
        return getHibernateTemplate().find(
                "from " + Customer.class.getSimpleName() + " e where " + column
                        + " = ?", value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.gaixie.micrite.dao.ICustomerDao#findCustomerVague(java.lang.String, java.lang.String)
     */
    public List findCustomerVague(String column, String value) {
        return getHibernateTemplate().find(
                "from " + Customer.class.getSimpleName() + " e where " + column
                        + " like ?", "%"+value+"%");
    }
}
