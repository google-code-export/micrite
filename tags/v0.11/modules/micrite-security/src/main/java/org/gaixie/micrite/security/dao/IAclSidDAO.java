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

import org.gaixie.micrite.beans.AclSid;
import org.gaixie.micrite.dao.IGenericDAO;

/**
 * 提供与 <code>AclSid</code> 实体有关的DAO接口。
 * 
 */
public interface IAclSidDAO extends IGenericDAO<AclSid, Long>{

    /**
     * 根据sid和principal获得AclSid对象。
     * 
     * @see org.gaixie.micrite.beans.AclSid
     * @param sid 可能是用户名，也可能是角色名
     * @param principal 如果为true:sid为用户名，false:sid为角色名
     * @return AclSid对象
     */  
    public AclSid findBySid(String sid, boolean principal);

}
