/**
 * 
 */
package org.gaixie.micrite.crm.dao.hibernate;

import java.util.List;

import org.gaixie.micrite.crm.dao.IAbstractDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 使用hibernate实现持久化数据操作
 * @see org.gaixie.micrite.crm.dao.IAbstractDao
 * @see org.springframework.orm.hibernate3.support.HibernateDaoSupport
 */
public class AbstractDaoImpl extends HibernateDaoSupport implements
		IAbstractDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gaixie.micrite.dao.IGenericDao#save(java.lang.Object)
	 */
	public void save(Object entity) {
		getHibernateTemplate().save(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gaixie.micrite.dao.IGenericDao#update(java.lang.Object)
	 */
	public void update(Object entity) {
		getHibernateTemplate().update(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gaixie.micrite.dao.IGenericDao#delete(java.lang.Object)
	 */
	public void delete(Object entity) {
		getHibernateTemplate().delete(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gaixie.micrite.dao.IGenericDao#getEntity(java.lang.Class, int)
	 */
	public Object getEntity(Class entityClass, int id) {
		Object o = getHibernateTemplate().get(entityClass, id);
		return o;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gaixie.micrite.dao.IGenericDao#findAll(java.lang.Class)
	 */
	public List findAll(Class entityClass) {
		List list = getHibernateTemplate().find(
				"from " + entityClass.getSimpleName() + " e");
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gaixie.micrite.dao.IGenericDao#findExact(java.lang.Class,
	 * java.lang.String, java.lang.String)
	 */
	public List findExact(Class entityClass, String column, Object value) {
		List list = getHibernateTemplate().find(
				"from " + entityClass.getSimpleName() + " e where " + column
						+ " = ?", value);
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gaixie.micrite.dao.IGenericDao#findVague(java.lang.Class,
	 * java.lang.String, java.lang.String)
	 */
	public List findVague(Class entityClass, String column, String value) {
		List list = getHibernateTemplate().find(
				"from " + entityClass.getSimpleName() + " e where " + column
						+ " like ?", "%"+value+"%");
		return list;
	}

}
