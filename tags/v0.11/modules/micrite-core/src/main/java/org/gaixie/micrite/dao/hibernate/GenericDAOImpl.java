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

package org.gaixie.micrite.dao.hibernate;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.io.Serializable;

import org.gaixie.micrite.dao.IGenericDAO;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implements the generic CRUD data access operations using Hibernate.
 * <p>
 * To write a DAO, subclass and parameterize this class with your entity.
 * Of course, assuming that you have a traditional 1:1 appraoch for
 * Entity:DAO design. 
 *
 */
public abstract class GenericDAOImpl<T,ID extends Serializable> 
        extends HibernateDaoSupport implements IGenericDAO<T, ID> {
    private Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public GenericDAOImpl() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }
    
    public void save(T entity) {
        getHibernateTemplate().save(entity);

    }

    public void update(T entity) {
        getHibernateTemplate().update(entity);

    }

    public void delete(T entity) {
        getHibernateTemplate().delete(entity);
    }

    @SuppressWarnings("unchecked")    
    public T get(ID id) {
        return (T) getHibernateTemplate().get(getPersistentClass(), id);
    }
}
