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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.providers.dao.UserCache;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import org.gaixie.micrite.beans.Authority;
import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.dao.IRoleDao;
import org.gaixie.micrite.security.dao.IUserDao;
import org.gaixie.micrite.security.service.IUserService;
import org.gaixie.micrite.security.SecurityException;

/**
 * 用户业务实现
 * 
 * @see org.gaixie.micrite.security.service.IUserService
 */
public class UserServiceImpl implements IUserService, UserDetailsService {
    
    private final static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserDao userDao;
    @Autowired
    private IRoleDao roleDao;

    //  用于对明文密码加密
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserCache userCache;
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  UserDetailsService Implement ~~~~~~~~~~~~~~~~~~~~~~~~~~//
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User " + username
            + " has no GrantedAuthority");
        }
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            Set<Authority> auths = role.getAuthorities();
            for (Authority auth : auths) {
                auth.getName();
            }
        }
        return user;
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  IUserService Implement ~~~~~~~~~~~~~~~~~~~~~~~~~~//
    public void add(User user, String[] roleIds) throws SecurityException {
        if(isExistedByUsername(user.getUsername())) {
            throw new SecurityException("error.user.add.userNameInUse");
        }        

        //  设置用户所属角色
        Set<Role> roles = new HashSet<Role>();
        for (String roleId : roleIds) {
            Role role = roleDao.getRole(Integer.parseInt(roleId));
            roles.add(role);
        }
        user.setRoles(roles);
        
        //  明文密码
        String plainpassword = user.getPlainpassword();
        //  加密后的密码
        String cryptpassword = passwordEncoder.encodePassword(plainpassword, null);
        user.setCryptpassword(cryptpassword);
        user.setEnabled(true);
        userDao.save(user);
    }

    public boolean isExistedByUsername(String username) {
        User user = userDao.findByUsername(username);
        if (user != null) {
            return true;
        }
        return false;
    }
    
    public void updateInfo(Integer id,
                           String newFullname,
                           String newEmailaddress,
                           String newPlainpassword) {
        //  取出用户
        User user = userDao.getUser(id);
        //  修改用户
        user.setFullname(newFullname);
        user.setEmailaddress(newEmailaddress);
        //  密码为非空字符串，才修改密码
        if (!newPlainpassword.equals("")) {
            String cryptpassword = passwordEncoder.encodePassword(newPlainpassword, null);
            user.setCryptpassword(cryptpassword);
        }
        //  持久化修改
        userDao.update(user);
        
        String username = user.getUsername();
        
        //  从cache中删除修改的对象
        if (userCache != null) {
            userCache.removeUserFromCache(username);
        }
        
        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        //  如果修改的是当前登陆用户，则要重新设置SecurityContext中认证用户
        if (username.equals(currentAuthentication.getName())) {
            SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(currentAuthentication));
        }
    }
    
    private Authentication createNewAuthentication(Authentication currentAuth) {
        UserDetails user = loadUserByUsername(currentAuth.getName());

        UsernamePasswordAuthenticationToken newAuthentication =
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        newAuthentication.setDetails(currentAuth.getDetails());

        return newAuthentication;
    }

    public Integer findByUsernameVagueCount(String username) {
        return userDao.findByNameVagueCount(username);
    }

    public List<User> findByUsernameVaguePerPage(String username, int start, int limit) {
        return userDao.findByUsernameVaguePerPage(username, start, limit);
    }

}
