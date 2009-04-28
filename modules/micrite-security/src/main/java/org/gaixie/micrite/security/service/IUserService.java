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

package org.gaixie.micrite.security.service;

import java.util.Set;
import java.util.Map;
import org.gaixie.micrite.beans.User;

/**
 * 用户业务模型
 *
 */
public interface IUserService {

	/**
	 * 根据User获得对应的入口 URL，用于显式在菜单上
	 * @see org.gaixie.micrite.beans.User
	 * @param user User实体
	 * @param node 菜单上当前节点id
	 * @return Menu Item List
	 */
	public Set<Map<String,Object>> loadMenuByUser(User user,String node);
	
}
