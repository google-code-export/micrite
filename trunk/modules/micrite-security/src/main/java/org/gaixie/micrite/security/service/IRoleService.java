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

    public void delete(String[] roleIds) throws SecurityException;
    
    /**
     * 增加新角色。
     * 
     * @param role 用户
     * @throws SecurityException 角色名已存在时抛出
     */
    public void add(Role role) throws SecurityException;
    
}
