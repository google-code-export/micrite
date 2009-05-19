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

import org.gaixie.micrite.beans.Role;
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
     * @param userRoleIds 用户所属角色id列表
     * @return true:成功；false：失败
     */
    public boolean add(User user,String[] userRoleIds);
    
    /**
     * 根据用户名判断用户在系统中是否存在。
     * 
     * @param username 用户名
     * @return true:存在；false：不存在
     */
    public boolean isUserExistent(String username);

    /**
     * 修改用户名密码，只有用户名不为空字符串时才修改用户名，只有密码不为空字符串时才修改密码。
     * 
     * @param id 用户id
     * @param username 用户名
     * @param plainpassword 明文密码
     * @return true:成功；false：失败
     */
    public boolean modifyUsernamePassword(Integer id, String username, String plainpassword);
    
    /**
     * 根据用户名查询用户集合（模糊查询）。
     * 
     * @see org.gaixie.micrite.beans.User
     * @param username 用户名
     * @return <code>User</code>对象列表
     */
    public List<User> findUsersByUsername(String username);
    
    public List<Role> getAllRoles();
}
