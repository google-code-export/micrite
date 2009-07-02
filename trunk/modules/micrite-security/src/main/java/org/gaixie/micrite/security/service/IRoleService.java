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
import org.gaixie.micrite.security.SecurityException;

/**
 * 授权资源服务接口，为系统授权及资源管理提供业务模型
 * 
 */
public interface IRoleService {

    /**
     * 查找并返回所有角色对象
     * @return 角色对象集合
     */
    public List<Role> findAll();
    
    /**
     * 根据角色名查询用户集合（模糊查询，分页）。
     * 
     * @param name 角色名
     * @param start 起始索引
     * @param limit 限制数
     */
    public List<Role> findByNameVaguePerPage(String name, int start, int limit);
    
    public int findByNameVagueTotal(String name);

    public void delete(int[] roleIds) throws SecurityException;
    
    /**
     * 增加新角色。
     * 
     * @param role 角色对象
     * @throws SecurityException 角色名已存在时抛出
     */
    public void add(Role role) throws SecurityException;
    
    /**
     * 修改角色。
     * 
     * @param role 角色对象
     * @throws SecurityException 角色名已存在时抛出
     */
    public void update(Role role) throws SecurityException;    
    
    /**
     * 根据用户id查询角色列表（分页）。
     * 
     * @param userId 用户id
     * @param start 起始索引
     * @param limit 限制数
     * @return 角色列表
     */
    public List<Role> findByUserIdPerPage(int userId, int start, int limit);
    
    /**
     * 根据用户id查询角色的总数。
     * 
     * @param userId 用户id
     * @return 角色的总数
     */
    public int findByUserIdCount(int userId);
    
    /**
     * 将角色绑定到用户。
     * 
     * @param roleIds 角色id数组
     * @param userId 用户id
     */
    public void bindRolesToUser(int[] roleIds, int userId);    
    
    /**
     * 将角色从用户上解除绑定。
     * 
     * @param roleIds 角色id数组
     * @param userId 用户id
     */
    public void unBindRolesFromUser(int[] roleIds, int userId);

    /**
     * 根据资源id查询角色列表（分页）。
     * 
     * @param authorityId 资源id
     * @param start 起始索引
     * @param limit 限制数
     * @return 角色列表
     */
    public List<Role> findByAuthorityIdPerPage(int authorityId, int start, int limit);
    
    /**
     * 根据资源id查询角色的总数。
     * 
     * @param authorityId 资源id
     * @return 角色的总数
     */
    public int findByAuthorityIdCount(int authorityId);

    /**
     * 将角色绑定到资源。
     * 
     * @param roleIds 角色id数组
     * @param authorityId 资源id
     */
    public void bindRolesToAuthority(int[] roleIds, int authorityId);
    
    /**
     * 将角色从资源上解除绑定。
     * 
     * @param roleIds 角色id数组
     * @param authorityId 资源id
     */
    public void unBindRolesFromAuthority(int[] roleIds, int authorityId);

}
