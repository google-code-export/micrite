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

import org.gaixie.micrite.beans.Setting;
import org.gaixie.micrite.dao.IGenericDAO;
/**
 * 提供与<code>Setting</code>对象有关的DAO接口。
 * 
 */
public interface ISettingDAO extends IGenericDAO<Setting, Integer>{
    /**
     * 查询系统给用户的默认设置
     * @return <code>UserSetting</code>对象列表
     */
    public List<Setting> findAllDefault();
    
    /**
     * 根据配置项名称查询可用配置属性
     * @return <code>UserSetting</code>对象列表
     */
    public List<Setting> findSettingByName(String name);
    
}
