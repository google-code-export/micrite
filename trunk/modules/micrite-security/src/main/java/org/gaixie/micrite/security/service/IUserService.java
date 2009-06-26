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
import org.gaixie.micrite.beans.Setting;
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
     * @param user对象,可更新属性包括, 用户id,newFullname 新名称,
     * newEmailaddress 新email地址,newPlainpassword 新密码（明文）
     * user对象包括一个setting对象
     */
    public void updateInfo(User user);

    /**
     * 根据用户名查询用户的总数（模糊查询）。
     * 
     * @param username 用户名
     */
    public Integer findByUsernameVagueCount(String username);

    /**
     * 根据用户名查询用户集合（模糊查询，分页）。
     * 
     * @param username 用户名
     * @param start 起始索引
     * @param limit 限制数
     */
    public List<User> findByUsernameVaguePerPage(String username, int start, int limit);
    
    
    /**
     * 根据配置项名称查询可用配置
     * @param name
     * @return
     */
    public List<Setting> findSettingByName(String name);

    /**
     * 根据用户查询所拥有的个性化配置
     * @param userId
     * @return
     */    
    public List<Setting> getSettings(int userId);
    
    /**
     * 根据用户id查询用户角色列表
     * 
     * @param userId 用户id
     */
    public Set<Role> findUserRoles(Integer userId);
    
    /**
     * 删除若干用户。
     * 
     * @param userIds 用户id数组
     */
    public void deleteUsers(String[] userIds);
    
    /**
     * 设置用户状态可用/不可用：将原可用的设置为不可用，原不可用的设置为可用。
     * 
     * @param userIds 用户id数组
     */
    public void enableOrDisableUsers(String[] userIds);
    
    public List<User> findUsersByRoleId(int roleId, int start, int limit);
    
    public Integer findUsersByRoleIdCount(int roleId);
    
    public void addUsersMatched(String[] userIds, int roleId);
    
    public void delUsersMatched(String[] userIds, int roleId);
    
}
