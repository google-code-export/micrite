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
import org.gaixie.micrite.security.dao.ISecurityDao;
import org.gaixie.micrite.beans.Authority;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.ConfigAttribute;
import org.springframework.security.SecurityConfig;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;

/**
 * 从数据库中取得所有Method相关的权限标识符
 * 产生一个map，包含所有method:authority的映射
 * key是authoritie的value（表示一个method集合）
 * value是访问这些method所需要的权限（authority）
 */
public class MethodMapFactoryBean implements FactoryBean {

    private static final String                              AUTHORITY_TYPE = "METHOD";
    private ISecurityDao                                     securityDao;
    private LinkedHashMap<String, ConfigAttributeDefinition> methodMap;

    public void init() {
        List<Authority> authorities = securityDao.find(AUTHORITY_TYPE);
        methodMap = new LinkedHashMap<String, ConfigAttributeDefinition>();
        for (Authority authority : authorities) {
            String grantedAuthorities = authority.getRoleAuthorities();
            if(grantedAuthorities != null) {
                ConfigAttributeEditor configAttrEditor = new ConfigAttributeEditor();
                configAttrEditor.setAsText(grantedAuthorities);
                ConfigAttributeDefinition definition = (ConfigAttributeDefinition) configAttrEditor.getValue();
                methodMap.put(authority.getValue(), definition);
            }        	
        }
    }

    public Object getObject() throws Exception {
        if (methodMap == null) {
            init();
        }
        return methodMap;
    }

    public Class getObjectType() {
        return LinkedHashMap.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public ISecurityDao getSecurityDao() {
        return securityDao;
    }

    public void setSecurityDao(ISecurityDao securityDao) {
        this.securityDao = securityDao;
    }
}
