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
import org.gaixie.micrite.security.dao.IAuthorityDao;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.intercept.web.RequestKey;

/**
 * 获取所有类型为 <code>URL</code> 的权限标识符。
 * <p>
 * 在Bean初始化时，获取所有类型为 <code>URL</code> 的权限标识符， 以 <code>LinkedHashMap</code>
 * 格式保存，供Spring Security在进行URL拦截验证时访问。
 * </p>
 * <p>
 * <code>LinkedHashMap</code> 的key是 <code>Authority</code> 的value，
 * value是访问这些method所需要的权限 <code>ConfigAttributeDefinition</code>。
 */
public class RequestMapFactoryBean implements FactoryBean {

    private static final String AUTHORITY_TYPE = "URL";
    @Autowired
    private IAuthorityDao authorityDao;
    private LinkedHashMap<RequestKey, ConfigAttributeDefinition> requestMap;

    public void init() {
        requestMap = new LinkedHashMap<RequestKey, ConfigAttributeDefinition>();
        List<Authority> authorities = authorityDao.findByType(AUTHORITY_TYPE);
        for (Authority authority : authorities) {
            RequestKey key = new RequestKey(authority.getValue());
            String grantedAuthorities = authority.getRolesString();
            if (grantedAuthorities != null) {
                ConfigAttributeEditor configAttrEditor = new ConfigAttributeEditor();
                configAttrEditor.setAsText(grantedAuthorities);
                ConfigAttributeDefinition definition = (ConfigAttributeDefinition) configAttrEditor
                        .getValue();
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

    @SuppressWarnings("unchecked")
    public Class getObjectType() {
        return LinkedHashMap.class;
    }

    public boolean isSingleton() {
        return true;
    }

}
