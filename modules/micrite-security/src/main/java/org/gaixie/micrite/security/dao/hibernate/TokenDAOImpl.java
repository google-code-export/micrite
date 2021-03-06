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

import org.gaixie.micrite.beans.Token;
import org.gaixie.micrite.dao.hibernate.GenericDAOImpl;
import org.gaixie.micrite.security.dao.ITokenDAO;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * 接口<code>ITokenDAO</code> 的Hibernate实现。
 * 
 */
@SuppressWarnings("unchecked")
public class TokenDAOImpl extends GenericDAOImpl<Token, Integer> implements ITokenDAO {


    public Token findByKey(String key) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Token.class);
        criteria.add(Expression.eq("key", key ));

        List<Token> list = getHibernateTemplate().findByCriteria(criteria);
        if (!list.isEmpty()) {
            return list.get(0);
        }

        return null;
    }

}
