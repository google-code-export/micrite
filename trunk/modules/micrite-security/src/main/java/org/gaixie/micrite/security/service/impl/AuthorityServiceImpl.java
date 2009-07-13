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

import org.gaixie.micrite.beans.Authority;
import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.security.SecurityException;
import org.gaixie.micrite.security.dao.IAuthorityDAO;
import org.gaixie.micrite.security.dao.IRoleDAO;
import org.gaixie.micrite.security.filter.FilterSecurityInterceptor;
import org.gaixie.micrite.security.filter.MethodSecurityInterceptor;
import org.gaixie.micrite.security.service.IAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 *
 */
public class AuthorityServiceImpl implements IAuthorityService {

    @Autowired
    private IAuthorityDAO authorityDAO;
    @Autowired
    private IRoleDAO roleDAO;

    public void add(Authority authority) {
        authorityDAO.save(authority);
    }
    
    public Integer findByNameVagueCount(String name) {
        return authorityDAO.findByNameVagueCount(name);
    }

    public List<Authority> findByNameVaguePerPage(String name, int start, int limit) {
        return authorityDAO.findByNameVaguePerPage(name, start, limit);
    }  
    
    public List<Authority> findAuthsByRoleIdPerPage(int roleId, int start, int limit) {
        List<Authority> auths = authorityDAO.findByRoleIdPerPage(roleId,start,limit);
        return auths;
    }    

    public Integer findAuthsByRoleIdCount(int roleId) {
        return authorityDAO.findByRoleIdCount(roleId);
    }  
    
    public void bindAuths(int[] authIds, int roleId) {
        Role role = roleDAO.get(roleId);
        for (int i = 0; i < authIds.length; i++) {
            Authority auth = authorityDAO.get(authIds[i]);
            Set<Role> roles =  auth.getRoles();
            roles.add(role);
            auth.setRoles(roles);
        }

        FilterSecurityInterceptor.refresh();
        MethodSecurityInterceptor.refresh();
    }    
    
    public void unBindAuths(int[] authIds, int roleId) {
        Role role = roleDAO.get(roleId);
        for (int i = 0; i < authIds.length; i++) {
            Authority auth = authorityDAO.get(authIds[i]);
            Set<Role> roles =  auth.getRoles();
            roles.remove(role);
            auth.setRoles(roles);
        }
        
        FilterSecurityInterceptor.refresh();
        MethodSecurityInterceptor.refresh();
    } 
    
    public void delete(int[] authIds) throws SecurityException {
        for (int i = 0; i < authIds.length; i++) {
            Authority authority = authorityDAO.get(authIds[i]);
            
            if(authority.getRoles() != null && authority.getRoles().size() > 0) {
                throw new SecurityException("error.authority.delete.roleNotEmptyInAuthority");
            }   
            authorityDAO.delete(authority);
        }
    }
    
    public void update(Authority authority){
    	Authority auth = authorityDAO.get(authority.getId());
    	auth.setValue(authority.getValue());
    }
    
}
