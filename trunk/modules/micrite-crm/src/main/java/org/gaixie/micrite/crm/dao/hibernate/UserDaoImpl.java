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
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Micrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Micrite.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.gaixie.micrite.crm.dao.hibernate;

import java.util.List;

import org.gaixie.micrite.crm.dao.IUserDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class UserDaoImpl extends HibernateDaoSupport implements IUserDao {
	public void save(Object entity){
		getHibernateTemplate().save(entity);
	}
	public void update(Object entity){
		getHibernateTemplate().update(entity);
	}
	public List findAll(Class entityClass){
		List list = getHibernateTemplate().find("from " + entityClass.getSimpleName() + " e");
		return list;
	}
	public List findSingleExact(Class entityClass, String column, String value) {
		List list = getHibernateTemplate().find(
				"from " + entityClass.getSimpleName() + " e where " + column + " = ?", value);
		return list;
	}
	public Object getEntity(Class entityClass, int id){
		Object o = getHibernateTemplate().get(entityClass.getClass(), id);
		return o;
	}
}
