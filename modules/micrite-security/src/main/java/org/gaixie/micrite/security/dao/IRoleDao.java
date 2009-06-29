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

/**
 * 提供与 <code>Role</code> 实体有关的DAO接口。
 * 
 */
public interface IRoleDao {
    /**
     * 根据所有角色对象集合.
     * 
     * @return <code>Role</code> 对象集合
     */
    public List<Role> findAll();
    
    /**
     * 根据角色Id获得角色对象.
     * @param id 角色ID
     * @return <code>Role</code> 对象
     */
    public Role getRole(int id);
    
    /**
     * 保存Role
     * @param  role role对象
     */
    public void save(Role role);

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
    

    public Integer findByNameVagueTotal(String name);

    public void delete(Integer id);    
}
