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

package org.gaixie.micrite.security.service.impl;

import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.beans.Authority;
import org.gaixie.micrite.security.dao.IUserDao;
import org.gaixie.micrite.security.service.IUserService;

/**
 * 用户业务实现
 * @see org.gaixie.micrite.security.service.IUserService
 */
public class UserServiceImpl implements IUserService {

	private IUserDao userDao;
	
	public Set<Map<String,Object>> loadMenuByUser(User user,String node){
		Set<Map<String,Object>> menu = new HashSet<Map<String,Object>>();
		
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			Set<Authority> auths = role.getAuthorities();
			for (Authority auth : auths) {
				if("MENU".equals(auth.getType())){
					if(("allModulesRoot".equals(node))&&StringUtils.indexOf(auth.getValue(), "/")<0){
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("url", auth.getValue());  
						map.put("text", auth.getName()); 
						map.put("id", auth.getValue()); 
						map.put("leaf", false);
						menu.add(map); 						
					}
					
					if((!"allModulesRoot".equals(node))&&StringUtils.indexOf(auth.getValue(), node+"/")>=0){
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("url", auth.getValue());  
						map.put("text", auth.getName()); 
						map.put("id", auth.getValue()); 
						map.put("leaf", true);
						menu.add(map); 						
					}
				}

			}
		}
		return menu;
	}
    
	/**
	 * @return the userDao
	 */
	public IUserDao getUserDao() {
		return userDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

}
