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
    private PasswordEncoder passwordEncoder;
    
    public boolean addUser(User user, String[] userRoleIds) {
        boolean result = false;
        try {       
            //  明文密码
            String plainpassword = user.getPlainpassword();
            logger.debug("plainpassword=" + plainpassword);
            //  加密后的密码
            String cryptpassword = passwordEncoder.encodePassword(plainpassword, null);
            logger.debug("cryptpassword=" + cryptpassword);
            user.setCryptpassword(cryptpassword);
            //  设置用户的权限列表
            Set<Role> roles = new HashSet<Role>();
            for (String userRoleId : userRoleIds) {
                Role role = userDao.getRole(Integer.parseInt(userRoleId));
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
            List<User> users = userDao.findUsersByUsername(username);
            if (users.size() > 0) {
                result = true;
            }
        } catch (Exception e) {
            logger.error("exception=" + e);
        }
        return result;
    }
    
    public boolean modifyUserInfo(User user, User currentUser) {
        boolean result = false;
        try {
            //  待修改的用户
            User targetUser = null;
            Integer userId = user.getId();
            Integer currentUserId = currentUser.getId();
            //  如果待修改的用户是当前用户
            if (userId.equals(currentUserId)) {
                targetUser = currentUser;
            } else {
                targetUser = userDao.get(userId);
            }
            String fullname = user.getFullname();
            String emailaddress = user.getEmailaddress();
            String username = user.getLoginname();
            String plainpassword = user.getPlainpassword();
            targetUser.setFullname(fullname);
            targetUser.setEmailaddress(emailaddress);
            targetUser.setLoginname(username);
            //  密码为非空字符串，才修改密码
            if (!plainpassword.equals("")) {
                String cryptpassword = passwordEncoder.encodePassword(plainpassword, null);
                targetUser.setCryptpassword(cryptpassword);
            }
            userDao.update(targetUser);
            result = true;
        } catch (Exception e) {
            logger.error("exception=" + e);
        }
        return result;
    }
    
    public List<User> findUsersByUsername(String username) {
        List<User> users = userDao.findUsersByUsername(username);
        return users;
    }
    
    public List<Role> getAllRoles() {
        List<Role> roles = userDao.getAllRoles();
        return roles;
    }
}
