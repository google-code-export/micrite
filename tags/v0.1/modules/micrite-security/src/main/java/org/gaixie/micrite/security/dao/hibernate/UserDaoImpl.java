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

import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.dao.IUserDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import org.springframework.jmx.export.annotation.ManagedResource;
 
/**
 * 用户管理持久化实现，基于hibernate
 * @author Maven Yu
 *
 */
@ManagedResource(objectName="micrite:type=dao,name=UserDaoImpl", description="Micrite UserDaoImpl Bean")
public class UserDaoImpl extends HibernateDaoSupport implements IUserDao {

	/* (non-Javadoc)
	 * @see org.gaixie.micrite.security.dao.IUserDao#findAll()
	 */
	public List<User> findAll(){
		String hql = "from User e";
		List<User> list = getHibernateTemplate().find(hql);
		return list;
	}
}
