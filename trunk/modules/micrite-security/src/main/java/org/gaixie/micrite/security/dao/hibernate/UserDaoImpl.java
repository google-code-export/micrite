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

package org.gaixie.micrite.security.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.dao.IUserDao;

/**
 * 接口<code>IUserDao</code> 的Hibernate实现。
 * 
 */
@ManagedResource(objectName = "micrite:type=dao,name=UserDaoImpl", description = "Micrite UserDaoImpl Bean")
public class UserDaoImpl extends HibernateDaoSupport implements IUserDao {
    
    @SuppressWarnings("unchecked")
    public User findByUsername(String username) {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Expression.eq("loginname", username));

        List<User> list = getHibernateTemplate().findByCriteria(criteria);
        if (!list.isEmpty()) {
            return list.get(0);
        }

        return null;
    }

    public void save(User user) {
        getHibernateTemplate().save(user);
    }
    
    public void update(User user) {
        getHibernateTemplate().update(user);
    }
    
    public User getUser(Integer id) {
        User user = (User)getHibernateTemplate().get(User.class, id);
        return user;
    }

    public Integer findByFullnameVagueCount(String fullname) {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Expression.like("fullname", "%" + fullname + "%"));
        criteria.setProjection(Projections.rowCount());
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }

    @SuppressWarnings("unchecked")
    public List<User> findByFullnameVaguePerPage(String fullname, int start, int limit) {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Expression.like("fullname", "%" + fullname + "%"));
        return getHibernateTemplate().findByCriteria(criteria, start, limit);
    }    
    
    public void delete(User user) {
        getHibernateTemplate().delete(user);
    }

    /*
     * 控制返回的结果集下面的几种方法：
     * criteria.setProjection(Projections.projectionList()
     *        .add(Projections.property("id"))
     *        .add(Projections.property("fullname"))
     *        .add(Projections.property("loginname"))                
     *        .add(Projections.property("emailaddress"))
     *        .add(Projections.property("enabled"))                
     * );
     * 
     * 或者
     * getHibernateTemplate().find("select u from User u join u.roles as r where r.id=?",roleId );
     * 
     */    
    @SuppressWarnings("unchecked")
    public List<User> findByRoleIdPerPage(int roleId, int start, int limit) {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class)
                                        .createCriteria("roles","r")
                                        .add(Expression.eq("r.id",roleId));
        return getHibernateTemplate().findByCriteria(criteria,start,limit);
    }
    
    @SuppressWarnings("unchecked")
    public Integer findByRoleIdCount(int roleId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.setProjection(Projections.rowCount());
        DetachedCriteria subCriteria = criteria.createCriteria("roles");
        subCriteria.add(Expression.eq("id",roleId));
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
//        List<User> list = getHibernateTemplate().find("select u from User u join u.roles as r where r.id=?",roleId );
//        return list;
    }
    
}
