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

import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.dao.hibernate.GenericDAOImpl;
import org.gaixie.micrite.security.dao.IRoleDAO;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;

/**
 * 接口<code>IRoleDao</code> 的Hibernate实现。
 *
 */
public class RoleDAOImpl extends GenericDAOImpl<Role, Integer>  implements IRoleDAO {

    @SuppressWarnings("unchecked")
    public List<Role> findByNameVaguePerPage(String name, int start, int limit) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Role.class);
        criteria.add(Expression.like("name", "%"+name+"%"));
        return getHibernateTemplate().findByCriteria(criteria,start,limit);            
    }	
    
    public Integer findByNameVagueCount(String name) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Role.class);
        criteria.add(Expression.like("name", "%"+name+"%"));
        criteria.setProjection(Projections.rowCount());
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }   
    
    @SuppressWarnings("unchecked")
    public Role findByRolename(String rolename) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Role.class);
        criteria.add(Expression.eq("name", rolename));

        List<Role> list = getHibernateTemplate().findByCriteria(criteria);
        if (!list.isEmpty()) {
            return list.get(0);
        }

        return null;
    }
    
    @SuppressWarnings("unchecked")
    public List<Role> findByUserIdPerPage(int userId, int start, int limit) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Role.class);
        criteria.createCriteria("users")
                .add(Expression.eq("id", userId));
        return getHibernateTemplate().findByCriteria(criteria, start, limit);
    }
    
    public Integer findByUserIdCount(int userId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Role.class);
        criteria.setProjection(Projections.rowCount())
                .createCriteria("users")
                .add(Expression.eq("id", userId));
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }

    @SuppressWarnings("unchecked")
    public List<Role> findByAuthorityIdPerPage(int authorityId, int start, int limit) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Role.class);
        criteria.createCriteria("authorities")
                .add(Expression.eq("id", authorityId));
        return getHibernateTemplate().findByCriteria(criteria, start, limit);
    }
    
    public Integer findByAuthorityIdCount(int authorityId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Role.class);
        criteria.setProjection(Projections.rowCount())
                .createCriteria("authorities")
                .add(Expression.eq("id", authorityId));
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }

}
