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

package org.gaixie.micrite.security.dao;

import java.util.List;

import org.gaixie.micrite.beans.User;

/**
 * 提供与<code>User</code>对象有关的DAO接口。
 * 
 */
public interface IUserDao {
    
    /**
     * 根据用户名查询用户。
     * 
     * @see org.gaixie.micrite.beans.User
     * @param username 用户名
     * @return <code>User</code>对象
     */
    public User findByUsername(String username);

    /**
     * 保存用户。
     * 
     * @see org.gaixie.micrite.beans.User
     * @param user <code>User</code>对象
     */
    public void save(User user);
    
    /**
     * 更新用户。
     * 
     * @see org.gaixie.micrite.beans.User
     * @param user <code>User</code>对象
     */
    public void update(User user);
    
    /**
     * 根据id得到用户。
     * 
     * @see org.gaixie.micrite.beans.User
     * @param id 用户id
     * @return <code>User</code>对象
     */
    public User getUser(Integer id);
    
    /**
     * 根据用户名查询用户集合（模糊查询）。
     * 
     * @see org.gaixie.micrite.beans.User
     * @param username 用户名
     * @return <code>User</code>对象列表
     */
    public List<User> findByUsernameVague(String username);
}
