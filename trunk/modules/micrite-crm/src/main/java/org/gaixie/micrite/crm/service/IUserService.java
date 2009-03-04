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

package org.gaixie.micrite.crm.service;

import java.util.List;

import org.gaixie.micrite.beans.User;

/**
 * @author Administrator
 *
 */
public interface IUserService {
	
	/**
	 * 查找所有记录
	 * 
	 * @return
	 */
	public List<User> findAll();

	/**
	 * 修改用户状态
	 * 
	 * @param id
	 * @return
	 */
	public void disabled(int id);
	
	/**
	 * 得到用户
	 * 
	 * @return
	 */
	public User getUser(int id);

}
