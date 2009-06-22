package org.gaixie.micrite.security.dao.hibernate;

import java.util.List;

import org.gaixie.micrite.beans.Setting;
import org.gaixie.micrite.security.dao.ISettingDao;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@SuppressWarnings("unchecked")
public class SettingDaoImpl extends HibernateDaoSupport implements ISettingDao {


	public List<Setting> findSettingByName(String name) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Setting.class);
        criteria.add(Expression.eq("name", name ));
        //sortindex
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public Setting getSetting(int id) {
		return (Setting)getHibernateTemplate().get(Setting.class, id);
	}
	
	public List<Setting> findAllDefault() {
	    DetachedCriteria criteria = DetachedCriteria.forClass(Setting.class);
	    criteria.add(Expression.eq("sortindex", 0 ));
	    return getHibernateTemplate().findByCriteria(criteria);
	}

}
