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

package org.gaixie.micrite.security.dao.hibernate;

import java.util.List;

import org.gaixie.micrite.beans.AclEntry;
import org.gaixie.micrite.dao.hibernate.GenericDAOImpl;
import org.gaixie.micrite.security.dao.IAclEntryDAO;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * 接口<code>IAclEntry</code> 的Hibernate实现。
 *
 */
public class AclEntryDAOImpl extends GenericDAOImpl<AclEntry, Long>  implements IAclEntryDAO {

    /* (non-Javadoc)
     * @see org.gaixie.micrite.security.dao.IAclEntryDAO#findByIdentityId(long)
     */
    @SuppressWarnings("unchecked")
    public List<AclEntry> findByIdentityId(long aclObjectIdentity) {
        DetachedCriteria criteria = DetachedCriteria.forClass(AclEntry.class);
        criteria.createCriteria("aclObject")
                .add(Expression.eq("id", aclObjectIdentity));

        return getHibernateTemplate().findByCriteria(criteria);
    }

}
