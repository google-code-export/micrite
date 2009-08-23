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

import org.gaixie.micrite.beans.Authority;
import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.dao.IAuthorityDAO;
import org.gaixie.micrite.security.dao.IRoleDAO;
import org.gaixie.micrite.security.dao.IUserDAO;
import org.gaixie.micrite.security.filter.FilterSecurityInterceptor;
import org.gaixie.micrite.security.filter.MethodSecurityInterceptor;
import org.gaixie.micrite.security.SecurityException;
import org.gaixie.micrite.security.service.IRoleService;
import org.gaixie.micrite.security.service.ISecurityAclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.MutableAclService;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.objectidentity.ObjectIdentity;
import org.springframework.security.acls.objectidentity.ObjectIdentityImpl;
import org.springframework.security.acls.sid.GrantedAuthoritySid;
import org.springframework.security.providers.dao.UserCache;

/**
 * 接口 <code>IRoleService</code> 的实现类。
 * 
 */
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleDAO roleDAO;
    @Autowired
    private IUserDAO userDAO;
    @Autowired
    private IAuthorityDAO authorityDAO;
    @Autowired
    private UserCache userCache;
    
    @Autowired
    private ISecurityAclService securityAclService;
    
    public List<Role> findByNameVaguePerPage(String name, int start, int limit) {
        return roleDAO.findByNameVaguePerPage(name, start, limit);
    }
    
    public int findByNameVagueCount(String name) {
        return roleDAO.findByNameVagueCount(name);
    }    
    
    public void deleteRoles(int[] roleIds) throws SecurityException {
        for (int i = 0; i < roleIds.length; i++) {
            int roleId = roleIds[i];
            if(userDAO.findByRoleIdCount(roleId)>0) {
                throw new SecurityException("error.role.delete.userNotEmptyInRole");
            }   
            if(authorityDAO.findByRoleIdCount(roleId)>0) {
                throw new SecurityException("error.role.delete.authNotEmptyInRole");
            }  
            Role role = roleDAO.get(roleId);
            delete(role);
        }
    }
    
    public void delete(Role role) {
        roleDAO.delete(role);
        // Delete the ACL information as well
        ObjectIdentity oid = new ObjectIdentityImpl(Role.class, (long)role.getId());
        securityAclService.deleteAcl(oid, false);
    }
    
    public void add(Role role) throws SecurityException {
        if(isExistedByRolename(role.getName())) {
            throw new SecurityException("error.role.add.roleNameInUse");
        }        
        roleDAO.save(role);
        // 默认和ROLE_ADMIN可以管理，
        // 同角色只读(需要在update或者delete的时候硬编码获取 ace.getMask()，弊大于利，不实现此功能)
//        securityAclService.addPermission(role, BasePermission.ADMINISTRATION, Role.class);
        securityAclService.addPermission(role, new GrantedAuthoritySid(role.getName()),BasePermission.READ, Role.class);        
        securityAclService.addPermission(role, new GrantedAuthoritySid("ROLE_ADMIN"),BasePermission.ADMINISTRATION, Role.class);
    }
    
    public boolean isExistedByRolename(String rolename) {
        Role role = roleDAO.findByRolename(rolename);
        if (role != null) {
            return true;
        }
        return false;
    }
    
    public void update(Role role) throws SecurityException {
        Role crole = roleDAO.get(role.getId());
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
        return roleDAO.findByUserIdPerPage(userId, start, limit);
    }
    
    public int findByUserIdCount(int userId) {
        return roleDAO.findByUserIdCount(userId);
    }    
    
    public void bindRolesToUser(int[] roleIds, int userId) {
        User user = userDAO.get(userId);
        for (int i = 0; i < roleIds.length; i++) {
            Role role = roleDAO.get(roleIds[i]);
            user.getRoles().add(role);
        }
        //  从cache中删除修改的对象
        if (userCache != null) {
            userCache.removeUserFromCache(user.getLoginname());
        }
    }    
    
    public void unBindRolesFromUser(int[] roleIds, int userId) {
        User user = userDAO.get(userId);
        for (int i = 0; i < roleIds.length; i++) {
            Role role = roleDAO.get(roleIds[i]);
            user.getRoles().remove(role);
        }
        //  从cache中删除修改的对象
        if (userCache != null) {
            userCache.removeUserFromCache(user.getLoginname());
        }
    }    
    
    public List<Role> findByAuthorityIdPerPage(int authorityId, int start, int limit) {
        return roleDAO.findByAuthorityIdPerPage(authorityId, start, limit);
    }
    
    public int findByAuthorityIdCount(int authorityId) {
        return roleDAO.findByAuthorityIdCount(authorityId);
    }    

    
    public void bindRolesToAuthority(int[] roleIds, int authorityId) {
        Authority authority = authorityDAO.get(authorityId);
        for (int i = 0; i < roleIds.length; i++) {
            Role role = roleDAO.get(roleIds[i]);
            authority.getRoles().add(role);
        }
        FilterSecurityInterceptor.refresh();
        MethodSecurityInterceptor.refresh();
        
    }    
    
    public void unBindRolesFromAuthority(int[] roleIds, int authorityId) {
        Authority authority = authorityDAO.get(authorityId);
        for (int i = 0; i < roleIds.length; i++) {
            Role role = roleDAO.get(roleIds[i]);
            authority.getRoles().remove(role);
        }
        FilterSecurityInterceptor.refresh();
        MethodSecurityInterceptor.refresh();
        
    }      
}
