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