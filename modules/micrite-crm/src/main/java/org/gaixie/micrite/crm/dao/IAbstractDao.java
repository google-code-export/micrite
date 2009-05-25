/**
 * 
 */
package org.gaixie.micrite.crm.dao;

import java.util.List;

/**
 * 持久化数据操作接口
 */
public interface IAbstractDao {
	/**
	 * 保存实体
	 * @param  entity 实体对象
	 */
	public void save(Object entity);

	/**
	 * 更新实体
	 * @param  entity 实体对象
	 */
	public void update(Object entity);

	/**
	 * 删除实体
	 * @param  entity 实体对象
	 */
	public void delete(Object entity);

	/**
	 * 根据ID获得实体对象
	 * @param  entityClass 实体类名（不含包名）
	 * @param  id 实体对象id
	 * @return 实体对象
	 */
	public Object getEntity(Class entityClass, int id);

	/**
	 * 获取实体所有对象集合
	 * @param  entityClass 实体类名（不含包名）
	 * @return 实体对象集合
	 */
	public List findAll(Class entityClass);
	
	/**
	 * 根据字段值精确查找
	 * @param  entityClass 实体类名（不含包名）
	 * @param  column 字段名
	 * @param  value 字段值
	 * @return 实体对象集合
	 */
	public List findExact(Class entityClass, String column, Object value);

	/**
	 * 根据字段值模糊查找
	 * @param  entityClass 实体类名（不含包名）
	 * @param  column 字段名
	 * @param  value 字段值
	 * @return 实体对象集合
	 */
	public List findVague(Class entityClass, String column, String value);


}
