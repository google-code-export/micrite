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

import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.dao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

/**
 * 提供基于Spring Security的用户身份验证。
 * <p>
 * Spring Security在用户登录时、进行URL和Method安全拦截时， 都会调用 <code>UserDetailsService</code>
 * 接口进行身份验证。
 * 
 * @see org.springframework.security.userdetails.UserDetailsService
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserDao userDao;

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User " + username
                    + " has no GrantedAuthority");
        }
        return user;
    }
}
