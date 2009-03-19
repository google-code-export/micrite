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

import org.gaixie.micrite.beans.Resource;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.dao.ISecurityDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 安全管理持久化实现，基于hibernate
 * @author Maven Yu
 *
 */
public class SecurityDaoImpl extends HibernateDaoSupport implements ISecurityDao {

	/* (non-Javadoc)
	 * @see org.gaixie.micrite.security.dao.ISecurityDao#loadUserByUsername(java.lang.String)
	 */
	public List<User> loadUserByUsername(String username) {
		List<User> users = getHibernateTemplate().find(
				"FROM User user WHERE user.loginname = ? AND user.isenabled = true", username);
		return users;
	}
	
	/* (non-Javadoc)
	 * @see org.gaixie.micrite.security.dao.ISecurityDao#loadUrlAuthorities()
	 */
	public List<Resource> loadUrlAuthorities(){
        List<Resource> urlResources = getHibernateTemplate().find("FROM Resource resource WHERE resource.type = ?", "URL");
        return urlResources;
	}
	
}
