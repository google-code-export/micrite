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

import org.apache.log4j.Logger;
import org.gaixie.micrite.beans.Authority;
import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.action.LoginAction;
import org.gaixie.micrite.security.dao.IAuthorityDao;
import org.gaixie.micrite.security.dao.IRoleDao;
import org.gaixie.micrite.security.dao.IUserDao;
import org.gaixie.micrite.security.filter.FilterSecurityInterceptor;
import org.gaixie.micrite.security.filter.MethodSecurityInterceptor;
import org.gaixie.micrite.security.SecurityException;
import org.gaixie.micrite.security.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.providers.dao.UserCache;

/**
 * 
 *
 */
public class RoleServiceImpl implements IRoleService {

    private static final Logger logger = Logger.getLogger(LoginAction.class); 
    @Autowired
    private IRoleDao roleDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IAuthorityDao authorityDao;
    @Autowired
    private UserCache userCache;
    
    public List<Role> findAll() {
        List<Role> roles = roleDao.findAll();
        return roles;
    }
    
    public List<Role> findByNameVaguePerPage(String name, int start, int limit) {
        return roleDao.findByNameVaguePerPage(name, start, limit);
    }
    
    public int findByNameVagueTotal(String name) {
        return roleDao.findByNameVagueTotal(name);
    }    
    
    public void delete(String[] roleIds) throws SecurityException {
        for (int i = 0; i < roleIds.length; i++) {
            int roleId = Integer.parseInt(roleIds[i]);
            if(userDao.findByRoleIdCount(roleId)>0) {
                throw new SecurityException("error.role.delete.userNotEmptyInRole");
            }   
            if(authorityDao.findByRoleIdCount(roleId)>0) {
                throw new SecurityException("error.role.delete.authNotEmptyInRole");
            }  
            roleDao.delete(roleId);
        }
    }
    
    public void add(Role role) throws SecurityException {
        if(isExistedByRolename(role.getName())) {
            throw new SecurityException("error.role.add.roleNameInUse");
        }        
        roleDao.save(role);
    }
    
    public boolean isExistedByRolename(String rolename) {
        Role role = roleDao.findByRolename(rolename);
        if (role != null) {
            return true;
        }
        return false;
    }
    
    public void update(Role role) throws SecurityException {
        Role crole = roleDao.getRole(role.getId());
        /*
         * 不要修改role.name，否则需要把role下面的所有user的cache都要清空
         * 如果要修改role.name ，可以通过独立的方法修改，或者直接更新数据库，然后 restart web server
         */
//        if(!(crole.getName()).equals(role.getName())
//                &&isExistedByRolename(role.getName())) {
//            throw new SecurityException("error.role.add.roleNameInUse");
//        }        
//        crole.setName(role.getName());
        crole.setDescription(role.getDescription());
    }    
    
    
    public List<Role> findByUserIdPerPage(int userId, int start, int limit) {
        return roleDao.findByUserIdPerPage(userId, start, limit);
    }
    
    public int findByUserIdCount(int userId) {
        return roleDao.findByUserIdCount(userId);
    }    
    
    public void bindRolesToUser(String[] roleIds, int userId) {
        User user = userDao.getUser(userId);
        for (int i = 0; i < roleIds.length; i++) {
            Role role = roleDao.getRole(Integer.parseInt(roleIds[i]));
            user.getRoles().add(role);
        }
        //  从cache中删除修改的对象
        if (userCache != null) {
            userCache.removeUserFromCache(user.getLoginname());
        }
    }    
    
    public void unBindRolesFromUser(String[] roleIds, int userId) {
        User user = userDao.getUser(userId);
        for (int i = 0; i < roleIds.length; i++) {
            Role role = roleDao.getRole(Integer.parseInt(roleIds[i]));
            user.getRoles().remove(role);
        }
        //  从cache中删除修改的对象
        if (userCache != null) {
            userCache.removeUserFromCache(user.getLoginname());
        }
    }    
    
    public List<Role> findByAuthorityIdPerPage(int authorityId, int start, int limit) {
        return roleDao.findByAuthorityIdPerPage(authorityId, start, limit);
    }
    
    public int findByAuthorityIdCount(int authorityId) {
        return roleDao.findByAuthorityIdCount(authorityId);
    }    

    
    public void bindRolesToAuthority(String[] roleIds, int authorityId) {
        Authority authority = authorityDao.getAuthority(authorityId);
        for (int i = 0; i < roleIds.length; i++) {
            Role role = roleDao.getRole(Integer.parseInt(roleIds[i]));
            authority.getRoles().add(role);
        }
        FilterSecurityInterceptor.refresh();
        MethodSecurityInterceptor.refresh();
        
    }    
    
    public void unBindRolesFromAuthority(String[] roleIds, int authorityId) {
        Authority authority = authorityDao.getAuthority(authorityId);
        for (int i = 0; i < roleIds.length; i++) {
            Role role = roleDao.getRole(Integer.parseInt(roleIds[i]));
            authority.getRoles().remove(role);
        }
        FilterSecurityInterceptor.refresh();
        MethodSecurityInterceptor.refresh();
        
    }      
}
