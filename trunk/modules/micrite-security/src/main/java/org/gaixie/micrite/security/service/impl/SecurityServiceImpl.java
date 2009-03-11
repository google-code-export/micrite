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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gaixie.micrite.beans.Resource;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.dao.ISecurityDao;
import org.gaixie.micrite.security.service.ISecurityService;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

public class SecurityServiceImpl implements UserDetailsService, ISecurityService   {

	private ISecurityDao securityDao;
	
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		List<User> users = securityDao.loadUserByUsername(username);
		if (users.isEmpty()) {
			throw new UsernameNotFoundException("User " + username + " has no GrantedAuthority");
		}
		return users.get(0);
	}

    public Map<String, String> loadUrlAuthorities() {
        Map<String, String> urlAuthorities = new HashMap<String, String>();  
        List<Resource> urlResources = securityDao.loadUrlAuthorities();  
        for(Resource resource : urlResources) {  
            urlAuthorities.put(resource.getValue(), resource.getRoleAuthorities());  
        }  
        return urlAuthorities;  
    }

	public ISecurityDao getSecurityDao() {
		return securityDao;
	}

	public void setSecurityDao(ISecurityDao securityDao) {
		this.securityDao = securityDao;
	}
    
}
