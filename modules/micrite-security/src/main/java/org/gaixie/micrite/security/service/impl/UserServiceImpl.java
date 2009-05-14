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
    
    public boolean add(User user) {
        boolean result = false;
        try {
            userDao.save(user);
            result = true;
        } catch (Exception e) {
            logger.error("exception=" + e);
        }
        return result;
    }

    public boolean modifyInfo(User user) {
        boolean result = false;
        try {
            userDao.update(user);
            result = true;
        } catch (Exception e) {
            logger.error("exception=" + e);
        }
        return result;
    }
    
    public boolean setInvalid(User user) {
        boolean result = false;
        user.setIsenabled(false);
        try {
            userDao.update(user);
            result = true;
        } catch (Exception e) {
            logger.error("exception=" + e);
        }
        return result;
    }

    public boolean setValid(User user) {
        boolean result = false;
        user.setIsenabled(true);
        try {
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
