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
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

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
    
    public boolean add(User user) {
        boolean result = false;
        try {       
            //  明文密码
            String plainpassword = user.getPlainpassword();
            //  加密后的密码
            String cryptpassword = passwordEncoder.encodePassword(plainpassword, null);
            user.setCryptpassword(cryptpassword);
            userDao.save(user);
            result = true;
        } catch (Exception e) {
            logger.error("exception=" + e);
        }
        return result;
    }

    public boolean isUserExistent(String username) {
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
    
    public boolean modifyUsernamePassword(Integer id, String username, String plainpassword) {
        boolean result = false;
        try {
            User user = userDao.get(id);
            //  用户名为非空字符串，才修改
            if (!username.equals("")) {
                user.setLoginname(username);
            }
            //  密码为非空字符串，才修改
            if (!plainpassword.equals("")) {
                String cryptpassword = passwordEncoder.encodePassword(plainpassword, null);
                user.setCryptpassword(cryptpassword);
            }
            userDao.update(user);
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
}
