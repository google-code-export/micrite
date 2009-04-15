/**
 * 
 */
package org.gaixie.micrite.crm.dao;

import java.util.List;
import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.CustomerSource;

/**
 * 客户管理持久化接口
 */
public interface ICustomerDao {
	/**
	 * 保存Customer
	 * @param  customer customer对象
	 */
	public void saveCustomer(Customer customer);

	/**
	 * 更新Customer
	 * @param  customer customer对象
	 */
	public void updateCustomer(Customer customer);

	/**
	 * 删除Customer
	 * @param  customer customer对象
	 */
	public void deleteCustomer(Customer customer);

	/**
	 * 根据ID获得Customer对象
	 * @param  id Customer对象id
	 * @return Customer对象
	 */
	public Customer getCustomer(int id);

	/**
	 * 根据ID获得CustomerSource对象
	 * @param  id CustomerSource对象id
	 * @return CustomerSource对象
	 */
	public CustomerSource getCustomerSource(int id);
	

	/**
	 * 获得所有CustomerSource
	 * @return CustomerSource对象集合
	 */
	public List findAllCustomerSource();

	/**
	 * 获得Customer记录数
	 * @return Customer对象数目
	 */
	public int getCustomerCount();
	
	/**
	 * 根据字段值精确查找Customer
	 * @param  column 字段名
	 * @param  value 字段值
	 * @return Customer对象集合
	 */
	public List findCustomerExact(String column, Object value);

	/**
	 * 根据字段值模糊查找Customer
	 * @param  column 字段名
	 * @param  value 字段值
	 * @return Customer对象集合
	 */
	public List findCustomerVague(String column, String value);

}
