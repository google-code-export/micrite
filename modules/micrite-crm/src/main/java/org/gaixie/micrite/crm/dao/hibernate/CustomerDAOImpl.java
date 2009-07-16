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
import org.gaixie.micrite.crm.dao.ICustomerDAO;
import org.gaixie.micrite.dao.hibernate.GenericDAOImpl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 客户管理持久化实现，基于hibernate
 * @see org.gaixie.micrite.crm.dao.ICustomerDAO
 */
public class CustomerDAOImpl extends GenericDAOImpl<Customer, Integer> implements ICustomerDAO {

    public CustomerSource getCustomerSource(int id) {
        return (CustomerSource) getHibernateTemplate().get(CustomerSource.class, id);
    }
    
    @SuppressWarnings("unchecked")
	public List<CustomerSource> findAllCustomerSource() {
    	List<CustomerSource> cs = getHibernateTemplate().find("from CustomerSource e");
    	return cs;
    }

    @SuppressWarnings("unchecked")
    public List findCSGroupByTelVague(String tel) {
        String sql =
            "select count(cs.name),cs.name from Customer  c join c.customerSource cs where telephone like ? group by cs.name";
        return  getHibernateTemplate().find(sql, "%" + tel + "%");
    }
    @SuppressWarnings("unchecked")
    public List<Customer> findByTelVaguePerPage(String telephone, int start,int limit) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);
        criteria.add(Expression.like("telephone", "%"+telephone+"%"));
        return getHibernateTemplate().findByCriteria(criteria,start,limit); 
    }
    public int findByTelVagueCount(String telephone) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);
        criteria.add(Expression.like("telephone", "%"+telephone+"%"));
        criteria.setProjection(Projections.rowCount());
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }

}
