package org.gaixie.micrite.dao;

import java.util.List;
import java.io.Serializable;

/**
 * An interface shared by all business data access objects.
 * <p>
 * All CRUD (create, read, update, delete) basic data access operations are
 * isolated in this interface and shared accross all DAO implementations.
 * The current design is for a state-management oriented persistence layer
 * (for example, there is no UDPATE statement function) that provides
 * automatic transactional dirty checking of business objects in persistent
 * state.
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
     * @param instance the instance to save
     */
    public void save(T entity);
    
    /**
     * Deletes the object
     * @param instance the object to delete
     */
    public void delete(T entity);
    
    /**
     * Updates the information of an existing object
     * @param instance the instance to update
     */
    public void update(T entity);

}