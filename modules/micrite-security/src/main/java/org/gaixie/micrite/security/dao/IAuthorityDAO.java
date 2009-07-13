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

import org.gaixie.micrite.beans.Authority;
import org.gaixie.micrite.dao.IGenericDAO;

/**
 * 提供与 <code>Authority</code> 实体有关的DAO接口。
 * 
 */
public interface IAuthorityDAO extends IGenericDAO<Authority, Integer>{
    /**
     * 根据权限类型获得权限对象集合。
     * 
     * @param type 权限类型
     * @return <code>Authority</code> 对象集合
     */
    public List<Authority> findByType(String type);
    
    /**
     * 按名称模糊查询，获得Authority集合数量
     * @param name 授权数量
     * @return 授权集合数量
     */
    public Integer findByNameVagueCount(String name);
    
    /**
     * 按名称模糊查询Authority集合(分页)
     * @param name 授权名称
     * @param start 起始索引
     * @param limit 限制数
     * @return Authority集合
     */
    public List<Authority> findByNameVaguePerPage(String name, int start, int limit);
    
    /**
     * 查找指定角色的Authority集合(分页)
     * @param roleId 角色ID
     * @param start 起始索引
     * @param limit 限制数
     * @return Authority集合
     */
    public List<Authority> findByRoleIdPerPage(int roleId, int start, int limit);   

    /**
     * 查找指定角色的Authority集合
     * @param roleId 角色ID
     * @return Authority集合
     */
    public List<Authority> findByRoleId(int roleId);   
    
    /**
     * 查找指定角色的Authority数量
     * @param roleId
     * @return Authority数量
     */
    public Integer findByRoleIdCount(int roleId);

}
