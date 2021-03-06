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

import org.gaixie.micrite.beans.AclSid;
import org.gaixie.micrite.dao.hibernate.GenericDAOImpl;
import org.gaixie.micrite.security.dao.IAclSidDAO;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;

/**
 * 接口<code>IAclSid</code> 的Hibernate实现。
 *
 */
public class AclSidDAOImpl extends GenericDAOImpl<AclSid, Long>  implements IAclSidDAO {

    /* (non-Javadoc)
     * @see org.gaixie.micrite.security.dao.IAclSidDAO#findBySid(java.lang.String, boolean)
     */
    @SuppressWarnings("unchecked")
    public AclSid findBySid(String sid, boolean principal) {
        DetachedCriteria criteria = DetachedCriteria.forClass(AclSid.class);
        criteria.add(Expression.eq("sid", sid))
                .add(Expression.eq("principal", principal));

        List<AclSid> list = getHibernateTemplate().findByCriteria(criteria);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }    
}
