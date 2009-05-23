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
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.dao.IRoleDao;
import org.gaixie.micrite.security.dao.IUserDao;
import org.gaixie.micrite.security.service.IUserService;

/**
 * 用户业务实现
 * 
 * @see org.gaixie.micrite.security.service.IUserService
 */
public class UserServiceImpl implements IUserService {
    
    private final static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserDao userDao;
    @Autowired
    private IRoleDao roleDao;
    //  用于对明文密码加密
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public boolean add(User user, String[] userRoleIds) {
        boolean result = false;
        try {       
            //  明文密码
            String plainpassword = user.getPlainpassword();
            //  加密后的密码
            String cryptpassword = passwordEncoder.encodePassword(plainpassword, null);
            user.setCryptpassword(cryptpassword);
            //  设置用户的权限列表
            Set<Role> roles = new HashSet<Role>();
            for (String userRoleId : userRoleIds) {
                Role role = roleDao.getRole(Integer.parseInt(userRoleId));
                roles.add(role);
            }
            user.setRoles(roles);
            userDao.save(user);
            result = true;
        } catch (Exception e) {
            logger.error("exception=" + e);
        }
        return result;
    }

    public boolean isExistentByUsername(String username) {
        boolean result = false;
        try {
            User user = userDao.findByUsername(username);
            if (user != null) {
                result = true;
            }
        } catch (Exception e) {
            logger.error("exception=" + e);
        }
        return result;
    }
    
    public boolean updateInfo(User user, User currentUser) {
        boolean result = false;
        try {
            Integer userId = user.getId();
            String fullname = user.getFullname();
            String emailaddress = user.getEmailaddress();
            String loginname = user.getLoginname();
            String plainpassword = user.getPlainpassword();
            
            //  取出待修改用户
            User targetUser = userDao.getUser(userId);
            //  修改待修改用户
            targetUser.setFullname(fullname);
            targetUser.setEmailaddress(emailaddress);
            targetUser.setLoginname(loginname);
            //  密码为非空字符串，才修改密码
            if (!plainpassword.equals("")) {
                String cryptpassword = passwordEncoder.encodePassword(plainpassword, null);
                targetUser.setCryptpassword(cryptpassword);
            }
            //  持久化待修改用户
            userDao.update(targetUser);
            
            //  若修改的是当前登陆用户，则也修改内存中的该对象
            if (userId.equals(currentUser.getId())) {
                currentUser.setFullname(fullname);
                currentUser.setEmailaddress(emailaddress);
                currentUser.setLoginname(loginname);
                //  密码为非空字符串，才修改密码
                if (!plainpassword.equals("")) {
                    String cryptpassword = passwordEncoder.encodePassword(plainpassword, null);
                    currentUser.setCryptpassword(cryptpassword);
                }
            }
            result = true;
        } catch (Exception e) {
            logger.error("exception=" + e);
        }
        return result;
    }
    
    public List<User> findByUsernameVague(String username) {
        List<User> users = userDao.findByUsernameVague(username);
        return users;
    }
}
