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

import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.dao.IGenericDAO;

/**
 * 提供与 <code>Role</code> 实体有关的DAO接口。
 * 
 */
public interface IRoleDAO extends IGenericDAO<Role, Integer>{
    
    /**
     * 根据角色名查询角色集合（模糊查询，分页）。
     * 
     * @see org.gaixie.micrite.beans.Role
     * @param name 角色名
     * @param start 起始索引
     * @param limit 限制数
     * @return <code>Role</code>对象列表
     */
    public List<Role> findByNameVaguePerPage(String name, int start, int limit);
    
    /**
     * 根据角色名查询角色集合记录总数。
     * 
     * @see org.gaixie.micrite.beans.Role
     * @param name 角色名
     */
    public Integer findByNameVagueCount(String name);

    /**
     * 根据角色名查询角色。
     * 
     * @see org.gaixie.micrite.beans.Role
     * @param rolename 角色名
     * @return <code>Role</code>对象
     */
    public Role findByRolename(String rolename);
    
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
    public Integer findByUserIdCount(int userId);
    
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
    public Integer findByAuthorityIdCount(int authorityId);

}
