/**
 * 
 */
package org.gaixie.micrite.crm.dao;

import java.util.List;

/**
 * @author Maven.yu
 *
 */
public interface IAbstractDao {
	/**
	 * 新增
	 * @param  entity
	 * @return
	 */
	public void save(Object entity);

	/**
	 * 更新
	 * @param  entity
	 * @return
	 */
	public void update(Object entity);

	/**
	 * 删除
	 * @param  entity
	 * @return
	 */
	public void delete(Object entity);

	/**
	 * 根据ID获得实体
	 * @param  entityClass
	 * @param  id
	 * @return
	 */
	public Object getEntity(Class entityClass, int id);

	/**
	 * 查找单表所有记录
	 * @param  entityClass
	 * @return
	 */
	public List findAll(Class entityClass);
	
	/**
	 * 精确查找
	 * @param  entityClass
	 * @param  column
	 * @param  value
	 * @return
	 */
	public List findExact(Class entityClass, String column, Object value);

	/**
	 * 模糊查找
	 * @param  entityClass
	 * @param  column
	 * @param  value
	 * @return
	 */
	public List findVague(Class entityClass, String column, String value);


}
