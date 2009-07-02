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
    
    public Integer findByNameVagueCount(String name);
    
    public List<Authority> findByNameVaguePerPage(String name, int start, int limit);
    
    public List<Authority> findAuthsByRoleId(int roleId, int start, int limit);
    
    public Integer findAuthsByRoleIdCount(int roleId);
    
    public void bindAuths(int[] authIds, int roleId);
    
    public void unBindAuths(int[] authIds, int roleId);
    
    public void delete(int[] authIds) throws SecurityException ;
    public void update(Authority authority) throws SecurityException ;
}
