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

/**
 * 提供与 <code>Authority</code> 实体有关的DAO接口。
 * 
 */
public interface IAuthorityDao {
    /**
     * 根据权限类型获得权限对象集合。
     * 
     * @param type 权限类型
     * @return <code>Authority</code> 对象集合
     */
    public List<Authority> findByType(String type);
    
    /**
     * 保存Authority
     * @param  authority authority对象
     */
    public void save(Authority authority);

    /**
     * 更新Authority
     * @param  authority authority对象
     */
    public void update(Authority authority);
    
    /**
     * 删除Authority
     * @param  authority authority对象
     */
    public void delete(Authority authority);

    public Authority getAuthority(Integer id);
    
    public Integer findByNameVagueCount(String name);
    
    public List<Authority> findByNameVaguePerPage(String name, int start, int limit);
    
    public List<Authority> findByRoleId(int roleId, int start, int limit);   
    
    public Integer findByRoleIdCount(int roleId);

}
