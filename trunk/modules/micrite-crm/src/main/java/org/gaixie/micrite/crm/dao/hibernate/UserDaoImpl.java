/* ===========================================================
 * $Id$
 * This file is part of Micrite
 * ===========================================================
 *
 * (C) Copyright 2009, by Gaixie.org and Contributors.
 * 
 * Project Info:  http://www.gaixie.org/wiki/100:Mainpage
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

import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.crm.dao.IUserDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class UserDaoImpl extends HibernateDaoSupport implements IUserDao {
	public List<User> findAll(){
		List<User> users = getHibernateTemplate().find("from User e");
		return users;
	}

	public User getUser(int id){
		User user = (User)getHibernateTemplate().get(User.class,id);
		return user;
	}

	public boolean disabled(int id){
		User user = (User)getHibernateTemplate().get(User.class,id);
		user.setDisabled(true);
		return true;
	}
}
