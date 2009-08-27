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

import org.gaixie.micrite.beans.AclEntry;
import org.gaixie.micrite.dao.IGenericDAO;

/**
 * 提供与 <code>AclEntry</code> 实体有关的DAO接口。
 * 
 */
public interface IAclEntryDAO extends IGenericDAO<AclEntry, Long>{

    /**
     * 根据aclObjectId查询ACE集合。
     * 
     * @see org.gaixie.micrite.beans.AclEntry
     * @param aclObjectIdentity acl Id
     * @return <code>AclEntry</code>对象列表
     */
    public List<AclEntry> findByIdentityId(long aclObjectIdentity);

    /**
     * 根据aclObjectId批量删除ACE。
     * 
     * @see org.gaixie.micrite.beans.AclEntry
     * @param aclObjectIdentity acl Id
     * @return 删除行数 
     */    
    public int deleteByIdentityId(long aclObjectId);

}
