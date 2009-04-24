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

package org.gaixie.micrite.security.filter;

import java.util.LinkedHashMap;
import java.util.List;
import org.gaixie.micrite.beans.Authority;
import org.gaixie.micrite.security.dao.ISecurityDao;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.ConfigAttribute;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.SecurityConfig;
import org.springframework.security.intercept.web.RequestKey;
import org.springframework.security.ConfigAttributeEditor;

/**
 * 产生一个map，包含所有url:authority的映射
 * key是authoritie的value（ant表达式，表示一个url集合）
 * value是访问这些url所需要的权限（authority）
 * 用于设置urlDefinitionSource中的requestMap
 * 
 */
public class RequestMapFactoryBean implements FactoryBean {

    private static final String                                  AUTHORITY_TYPE = "URL";
	private ISecurityDao                                         securityDao;                            
    private LinkedHashMap<RequestKey, ConfigAttributeDefinition> requestMap;

    public void init() {
        requestMap = new LinkedHashMap<RequestKey, ConfigAttributeDefinition>();
        List<Authority> authorities = securityDao.find(AUTHORITY_TYPE);
        for (Authority authority : authorities) {
            RequestKey key = new RequestKey(authority.getValue());
            String grantedAuthorities = authority.getRoleAuthorities();
            if(grantedAuthorities != null) {
                ConfigAttributeEditor configAttrEditor = new ConfigAttributeEditor();
                configAttrEditor.setAsText(grantedAuthorities);
                ConfigAttributeDefinition definition = (ConfigAttributeDefinition) configAttrEditor.getValue();
                requestMap.put(key, definition);
            }
        }
    }

    public Object getObject() throws Exception {
        if (requestMap == null) {
            init();
        }
        return requestMap;
    }

    public Class getObjectType() {
        return LinkedHashMap.class;
    }

    public boolean isSingleton() {
        return true;
    }

	/**
	 * @return the securityDao
	 */
	public ISecurityDao getSecurityDao() {
		return securityDao;
	}

	/**
	 * @param securityDao  the securityDao to set
	 */
	public void setSecurityDao(ISecurityDao securityDao) {
		this.securityDao = securityDao;
	}
}
