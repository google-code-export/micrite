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

    public void save(Customer customer) {
        getHibernateTemplate().save(customer);

    }

    public void update(Customer customer) {
        getHibernateTemplate().update(customer);

    }

    public void delete(Customer customer) {
        getHibernateTemplate().delete(customer);

    }

    public Customer getCustomer(int id) {
        return (Customer) getHibernateTemplate().get(Customer.class, id);
    }

    public CustomerSource getCustomerSource(int id) {
        return (CustomerSource) getHibernateTemplate().get(CustomerSource.class, id);
    }
    
    @SuppressWarnings("unchecked")
	public List<CustomerSource> findAllCustomerSource() {
    	List<CustomerSource> cs = getHibernateTemplate().find("from CustomerSource e");
    	return cs;
    }

    public int getCount() {
        String sql = "select count(*) from Customer ";
        return ((Number) getHibernateTemplate().getSessionFactory()
        		.getCurrentSession().createQuery(sql).uniqueResult()).intValue();
    }
    
    @SuppressWarnings("unchecked")
	public List<Customer> findByTelExact(String telephone) {
    	List<Customer> list = getHibernateTemplate().find("from Customer e where telephone = ?", telephone);
    	return list;
    }

    @SuppressWarnings("unchecked")
	public List<Customer> findByTelVague(String telephone) {
    	List<Customer> list = getHibernateTemplate().find("from Customer e where telephone like ?", "%" + telephone + "%");
    	return list;
    }

}
