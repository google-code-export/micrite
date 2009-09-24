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

import org.gaixie.micrite.beans.Token;
import org.gaixie.micrite.dao.IGenericDAO;

/**
 * 提供与<code>Token</code>对象有关的DAO接口。
 * 
 */
public interface ITokenDAO extends IGenericDAO<Token, Integer>{
    /**
     * 根据密钥获得用户令牌
     * @return <code>Token</code>对象
     */
    public Token findByKey(String key);
    
}
