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

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.gaixie.micrite.beans.Authority;
import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.security.SecurityException;
import org.gaixie.micrite.security.dao.IAuthorityDao;
import org.gaixie.micrite.security.dao.IRoleDao;
import org.gaixie.micrite.security.filter.FilterSecurityInterceptor;
import org.gaixie.micrite.security.filter.MethodSecurityInterceptor;
import org.gaixie.micrite.security.service.IAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 *
 */
public class AuthorityServiceImpl implements IAuthorityService {

    private static final Logger logger = Logger.getLogger(AuthorityServiceImpl.class);
    @Autowired
    private IAuthorityDao authorityDao;
    @Autowired
    private IRoleDao roleDao;

    public void add(Authority authority) {
        authorityDao.save(authority);
    }
    
    public Integer findByNameVagueCount(String name) {
        return authorityDao.findByNameVagueCount(name);
    }

    public List<Authority> findByNameVaguePerPage(String name, int start, int limit) {
        return authorityDao.findByNameVaguePerPage(name, start, limit);
    }  
    
    public List<Authority> findAuthsByRoleId(int roleId, int start, int limit) {
        List<Authority> auths = authorityDao.findByRoleId(roleId,start,limit);
        return auths;
    }    

    public Integer findAuthsByRoleIdCount(int roleId) {
        return authorityDao.findByRoleIdCount(roleId);
    }  
    
    public void bindAuths(int[] authIds, int roleId) {
        Role role = roleDao.getRole(roleId);
        for (int i = 0; i < authIds.length; i++) {
            Authority auth = authorityDao.getAuthority(authIds[i]);
            Set<Role> roles =  auth.getRoles();
            roles.add(role);
            auth.setRoles(roles);
        }

        FilterSecurityInterceptor.refresh();
        MethodSecurityInterceptor.refresh();
    }    
    
    public void unBindAuths(int[] authIds, int roleId) {
        Role role = roleDao.getRole(roleId);
        for (int i = 0; i < authIds.length; i++) {
            Authority auth = authorityDao.getAuthority(authIds[i]);
            Set<Role> roles =  auth.getRoles();
            roles.remove(role);
            auth.setRoles(roles);
        }
        
        FilterSecurityInterceptor.refresh();
        MethodSecurityInterceptor.refresh();
    } 
    
    public void delete(int[] authIds) throws SecurityException {
        for (int i = 0; i < authIds.length; i++) {
            Authority authority = authorityDao.getAuthority(authIds[i]);
            
            if(authority.getRoles() != null && authority.getRoles().size() > 0) {
                throw new SecurityException("error.authority.delete.roleNotEmptyInAuthority");
            }   
            authorityDao.delete(authority);
        }
    }
    
    public void update(Authority authority){
    	Authority auth = authorityDao.getAuthority(authority.getId());
    	auth.setValue(authority.getValue());
    }
    
}
