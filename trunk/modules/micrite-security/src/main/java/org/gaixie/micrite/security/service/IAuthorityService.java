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

import java.util.LinkedHashMap;

import org.gaixie.micrite.beans.Authority;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.intercept.web.RequestKey;
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
    public boolean add(Authority authority, String roleIdBunch);
    
    public LinkedHashMap<RequestKey, ConfigAttributeDefinition> initRequestMap();  
    
    public LinkedHashMap<String, ConfigAttributeDefinition> initMethodMap();    

}
