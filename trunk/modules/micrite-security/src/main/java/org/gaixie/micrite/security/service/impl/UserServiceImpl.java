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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.beans.Setting;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.SecurityException;
import org.gaixie.micrite.security.dao.IRoleDao;
import org.gaixie.micrite.security.dao.ISettingDao;
import org.gaixie.micrite.security.dao.IUserDao;
import org.gaixie.micrite.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.dao.UserCache;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

/**
 * 用户业务实现
 * 
 * @see org.gaixie.micrite.security.service.IUserService
 */
public class UserServiceImpl implements IUserService, UserDetailsService {
    
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IRoleDao roleDao;
    @Autowired
    private ISettingDao settingDao;    

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
                role.getName();
        }
        
        return user;
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  IUserService Implement ~~~~~~~~~~~~~~~~~~~~~~~~~~//
    public void add(User user) throws SecurityException {
        if(isExistedByUsername(user.getUsername())) {
            throw new SecurityException("error.user.add.userNameInUse");
        }        

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
    
    public void updateInfo(User u) throws SecurityException {
        //  取出用户
        User user = userDao.getUser(u.getId());
        //  如果修改了用户名，则校验新用户名在系统是否存在
        if (!u.getUsername().equals(user.getUsername())) {
            if(isExistedByUsername(u.getUsername())) {
                throw new SecurityException("error.user.add.userNameInUse");
            } 
        }    

        // 修改用户
        user.setFullname(u.getFullname());
        user.setEmailaddress(u.getEmailaddress());
        //  密码为非空字符串，才修改密码
        if (!"".equals(user.getPlainpassword())&&user.getPlainpassword()!=null) {
            String cryptpassword = passwordEncoder.encodePassword(user.getPlainpassword(), null);
            user.setCryptpassword(cryptpassword);
        }
        
        if (u.getSettings() != null) {
            List<Setting> list = new ArrayList<Setting>();
    
            //判断是否需要更新setting,如果选项和缺省值一致,则不更新
            for (Setting s:u.getSettings()){
            	Setting setting = settingDao.getSetting(s.getId());
    //        	if (setting.getSortindex() != 0)
            		list.add(setting);
            }
            user.setSettings(list);
        }
        
        //  持久化修改
        userDao.update(user);
        
        String username = user.getLoginname();
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

    public Integer findByFullnameVagueCount(String fullname) {
        return userDao.findByFullnameVagueCount(fullname);
    }

    public List<User> findByFullnameVaguePerPage(String fullname, int start, int limit) {
        return userDao.findByFullnameVaguePerPage(fullname, start, limit);
    }
    
    public List<Setting> getSettings(int userId){
        User user = userDao.getUser(userId);
        List<Setting> settings = user.getSettings();
        if (settings.size()>0){   
            // org.hibernate.LazyInitializationException
            for (Setting setting:settings){
                setting.getName();
            }
            return settings;
        }
        return settingDao.findAllDefault();
    }

	public List<Setting> findSettingByName(String name) {
		return settingDao.findSettingByName(name);
	}	
    
    public void deleteUsers(int[] userIds) {
        for (int i = 0; i < userIds.length; i++) {
            userDao.delete(userIds[i]);
        }
    }
    
    public void enableUsers(int[] userIds) {
        for (int i = 0; i < userIds.length; i++) {
            User user = userDao.getUser(userIds[i]);
            user.setEnabled(!user.isEnabled());
            //  从cache中删除修改的对象
            if (userCache != null) {
                userCache.removeUserFromCache(user.getLoginname());
            }
        }
    }
    
    public List<User> findUsersByRoleId(int roleId, int start, int limit) {
        List<User> users = userDao.findByRoleId(roleId,start,limit);
        return users;
    }    

    public Integer findUsersByRoleIdCount(int roleId) {
        return userDao.findByRoleIdCount(roleId);
    }  
    
    public void bindUsers(int[] userIds, int roleId) {
        Role role = roleDao.getRole(roleId);
        for (int i = 0; i < userIds.length; i++) {
            User user = userDao.getUser(userIds[i]);
            Set<Role> roles =  user.getRoles();
            roles.add(role);
            user.setRoles(roles);
            //  从cache中删除修改的对象
            if (userCache != null) {
                userCache.removeUserFromCache(user.getLoginname());
            }
        }
    }    
    
    public void unBindUsers(int[] userIds, int roleId) {
        Role role = roleDao.getRole(roleId);
        for (int i = 0; i < userIds.length; i++) {
            User user = userDao.getUser(userIds[i]);
            Set<Role> roles =  user.getRoles();
            roles.remove(role);
            user.setRoles(roles);
            //  从cache中删除修改的对象
            if (userCache != null) {
                userCache.removeUserFromCache(user.getLoginname());
            }
        }
    }      
}
