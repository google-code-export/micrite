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

package org.gaixie.micrite.dao;

import java.util.List;
import java.io.Serializable;

/**
 * An interface shared by all business data access objects.
 * <p>
 * All CRUD (create, read, update, delete) basic data access operations are
 * isolated in this interface and shared accross all DAO implementations.
 *
 */
public interface IGenericDAO<T, ID extends Serializable> {

    /**
     * Tries to get an instance of the object 
     * @param id the id to search for
     * @return the requested instance, or <code>null</code> if not found
     */
    public T get(ID id);
    
    /**
     * Adds a new instance of the object
     * @param entity the instance to save
     */
    public void save(T entity);
    
    /**
     * Deletes the object
     * @param entity the object to delete
     */
    public void delete(T entity);
    
    /**
     * Updates the information of an existing object
     * @param entity the instance to update
     */
    public void update(T entity);

}