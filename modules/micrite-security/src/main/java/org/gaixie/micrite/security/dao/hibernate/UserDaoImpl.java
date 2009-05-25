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
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import org.gaixie.micrite.beans.Role;
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
        criteria.add(Expression.eq("isenabled", true));

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
    
    public User get(Integer id) {
        User user = (User)getHibernateTemplate().get(User.class, id);
        return user;
    }

    public List<User> findUsersByUsername(String username) {
        String hql = "from User u where u.loginname like ?";
        String[] paras = {"%" + username + "%"};
        List<User> users = getHibernateTemplate().find(hql,paras);
        return users;
    }
    
    public List<Role> getAllRoles() {
        String hql = "from Role r";
        List<Role> roles = getHibernateTemplate().find(hql);
        return roles;
    }
    
    public Role getRole(Integer id) {
        Role role = (Role)getHibernateTemplate().get(Role.class, id);
        return role;
    }
}
