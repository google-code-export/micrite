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
import org.gaixie.micrite.security.SecurityException;
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
     * @param userRoleIds 用户所属角色id列表
     */
    public void add(User user,String[] userRoleIds) throws SecurityException;
    
    /**
     * 根据用户名判断用户在系统中是否存在。
     * 
     * @param username 用户名
     * @return true:存在；false：不存在
     */
    public boolean isExistedByUsername(String username);

    /**
     * 修改用户信息。
     * 密码不为空字符串时才修改密码；若修改的是当前登陆用户，则也修改内存中的该对象。
     * 
     * @see org.gaixie.micrite.beans.User
     * @param user <code>User</code>对象
     * @param currentUser 当前用户
     */
    public void updateInfo(User user) throws SecurityException;
    
    /**
     * 根据用户名查询用户集合（模糊查询）。
     * 
     * @see org.gaixie.micrite.beans.User
     * @param username 用户名
     * @return <code>User</code>对象列表
     */
    public List<User> findByUsernameVague(String username);
}
