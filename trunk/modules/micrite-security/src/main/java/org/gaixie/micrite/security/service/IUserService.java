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

package org.gaixie.micrite.security.service;

import java.util.List;

import org.gaixie.micrite.beans.User;

/**
 * 提供与用户管理有关的服务。
 * 
 */
public interface IUserService {
    
    /**
     * 增加新用户。
     * 
     * @see org.gaixie.micrite.beans.User
     * @param user <code>User</code>对象
     * @return true:成功；false：失败
     */
    public boolean add(User user);
    
    /**
     * 修改用户信息。
     * 
     * @see org.gaixie.micrite.beans.User
     * @param user <code>User</code>对象
     * @return true:成功；false：失败
     */
    public boolean modifyInfo(User user);
    
    /**
     * 用户置无效。
     * 
     * @see org.gaixie.micrite.beans.User
     * @param user <code>User</code>对象
     * @return true:成功；false：失败
     */
    public boolean setInvalid(User user);

    /**
     * 用户置有效。
     * 
     * @see org.gaixie.micrite.beans.User
     * @param user <code>User</code>对象
     * @return true:成功；false：失败
     */
    public boolean setValid(User user);

    /**
     * 根据用户名查询用户集合（模糊查询）。
     * 
     * @see org.gaixie.micrite.beans.User
     * @param username 用户名
     * @return <code>User</code>对象列表
     */
    public List<User> findUsersByUsername(String username);
}
