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

package org.gaixie.micrite.crm.service.impl;

import java.util.List;

import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.crm.service.IUserService;

public class UserServiceImplMock implements IUserService {
   
	private List<User> users;
	
	public List<User> findAll(){
		return users;
	}

	public void disabled(int id){
		User wantedUser = new User();
		for (User user:users)
		{
			if (user.getId().equals(id))
			{
				wantedUser = user;
				break;
			}
		}
		wantedUser.setDisabled(true);
	}

	public User getUser(int id){
		return new User();
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
