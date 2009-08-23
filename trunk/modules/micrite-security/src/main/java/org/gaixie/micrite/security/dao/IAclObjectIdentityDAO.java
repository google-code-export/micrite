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

import java.util.Map;

import org.gaixie.micrite.beans.AclClass;
import org.gaixie.micrite.beans.AclObjectIdentity;
import org.gaixie.micrite.dao.IGenericDAO;
import org.springframework.security.acls.objectidentity.ObjectIdentity;
import org.springframework.security.acls.sid.Sid;

/**
 * 提供与 <code>AclObjectIdentity</code> 实体有关的DAO接口。
 * 
 */
public interface IAclObjectIdentityDAO extends IGenericDAO<AclObjectIdentity, Long>{

    /**
     * 根据受保护的对象名称id和对象实例Id获得AclObjectIdentity对象。
     * 
     * @see org.gaixie.micrite.beans.AclObjectIdentity
     * @param objectIdClass AclClass Id属性
     * @param objectIdIdentity 象实例Id，例如Role对象(USER_ADMIN)的Id为1
     * @return AclObjectIdentity对象
     */  
    AclObjectIdentity findByObjectId(long objectIdClass, long objectIdIdentity);
}
