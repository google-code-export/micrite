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

package org.gaixie.micrite.security.dao;

import java.util.List;

import org.gaixie.micrite.beans.Resource;
import org.gaixie.micrite.beans.User;

/**
 * 安全管理持久化管理
 *
 */
public interface ISecurityDao {
	/**
	 * 根据用户名加载用户	
	 * @param username 用户名
	 * @return 用户实体资源
	 */
	public List<User> loadUserByUsername(String username);
	
	/**
	 * 加载所有资源
	 * @return 资源资源
	 */
	public List<Resource> loadUrlAuthorities();
}
