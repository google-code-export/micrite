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

import org.gaixie.micrite.beans.Authority;
import org.gaixie.micrite.security.SecurityException;

/**
 * 授权资源服务接口，为系统授权及资源管理提供业务模型
 * 
 */
public interface IAuthorityService {

    /**
     * 新增一个授权资源
     * @param customer 客户实体
     * @param customerSourceId 客户来源id
     * @return 成功：true；失败：false
     */
    public void add(Authority authority);
    
    /**
     * 根据名称查找授权数量
     * @param name 授权名称
     * @return 授权数量
     */
    public Integer findByNameVagueCount(String name);
    
    /**
     * 根据名称查找授权集合
     * @param name 授权名称
     * @return 授权集合
     */
    public List<Authority> findByNameVaguePerPage(String name, int start, int limit);
    
    /**
     * 查找指定角色的授权集合
     * @param roleId 角色ID
     * @param start 起始索引
     * @param limit 限制数
     * @return 授权集合
     */
    public List<Authority> findAuthsByRoleId(int roleId, int start, int limit);
    
    /**
     * 查找指定角色的授权数量
     * @param roleId 角色ID
     * @return 授权数量
     */
    public Integer findAuthsByRoleIdCount(int roleId);
    
    /**
     * 将授权绑定角色
     * @param authIds 授权ID数组
     * @param roleId 角色ID
     */
    public void bindAuths(int[] authIds, int roleId);
    
    /**
     * 将角色与授权解除绑定
     * @param authIds 授权ID数组
     * @param roleId 角色ID
     */
    public void unBindAuths(int[] authIds, int roleId);
    
    /**删除授权
     * @param authIds 授权ID
     * @throws SecurityException 此授权已关联角色
     */
    public void delete(int[] authIds) throws SecurityException ;
    
    /**
     * 更新授权值
     * @param authority 授权对象
     */
    public void update(Authority authority);
}
