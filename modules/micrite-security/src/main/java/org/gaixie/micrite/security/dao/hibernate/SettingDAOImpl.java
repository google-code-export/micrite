package org.gaixie.micrite.security.dao.hibernate;

import java.util.List;

import org.gaixie.micrite.beans.Setting;
import org.gaixie.micrite.dao.hibernate.GenericDAOImpl;
import org.gaixie.micrite.security.dao.ISettingDAO;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

@SuppressWarnings("unchecked")
public class SettingDAOImpl extends GenericDAOImpl<Setting, Integer> implements ISettingDAO {


	public List<Setting> findSettingByName(String name) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Setting.class);
        criteria.add(Expression.eq("name", name ));
        //sortindex
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public List<Setting> findAllDefault() {
	    DetachedCriteria criteria = DetachedCriteria.forClass(Setting.class);
	    criteria.add(Expression.eq("sortindex", 0 ));
	    return getHibernateTemplate().findByCriteria(criteria);
	}

}
