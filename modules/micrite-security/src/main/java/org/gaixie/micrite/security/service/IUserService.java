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
import java.util.Set;

import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.SecurityException;

/**
 * 提供与用户管理有关的服务。
 * 
 */
public interface IUserService {
    
    /**
     * 根据roleId数组返回role列表。
     * 
     * @param roleIds roleId数组
     */
    public Set<Role> getRolesByIds(String[] roleIds);

    /**
     * 增加新用户。
     * 
     * @param user 用户
     * @throws SecurityException 用户名已存在时抛出
     */
    public void add(User user) throws SecurityException;
    
    /**
     * 根据用户名判断用户是否已存在。
     * 
     * @param username 用户名
     * @return true:存在；false：不存在
     */
    public boolean isExistedByUsername(String username);

    /**
     * 修改用户信息。
     * 密码不为空字串时才修改密码。
     * 
     * @param id 用户id
     * @param newFullname 新名称
     * @param newEmailaddress 新email地址
     * @param newPlainpassword 新密码（明文）
     */
    public void updateInfo(Integer id,
                           String newFullname,
                           String newEmailaddress,
                           String newPlainpassword);

    /**
     * 根据用户名查询用户集合（模糊查询）。
     * 
     * @param username 用户名
     */
    public List<User> findByUsernameVague(String username);
}
