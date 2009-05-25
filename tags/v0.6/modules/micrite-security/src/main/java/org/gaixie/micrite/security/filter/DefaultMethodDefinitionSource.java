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

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.intercept.method.AbstractMethodDefinitionSource;

/**
 * 从 <code>MethodMapFactoryBean</code> 中获取被调用的方法所需要的权限标识符。
 * 
 * @see org.gaixie.micrite.security.filter.MethodMapFactoryBean
 */
public class DefaultMethodDefinitionSource extends
        AbstractMethodDefinitionSource {

    private AspectJMethodMatcher methodMatcher;
    private LinkedHashMap<String, ConfigAttributeDefinition> methodMap = new LinkedHashMap<String, ConfigAttributeDefinition>();

    public DefaultMethodDefinitionSource(AspectJMethodMatcher methodMatcher,
            LinkedHashMap<String, ConfigAttributeDefinition> methodMap) {
        this.methodMatcher = methodMatcher;
        this.methodMap = methodMap;
    }

    @SuppressWarnings("unchecked")
    public Collection getConfigAttributeDefinitions() {
        return null;
    }

    @Override
    protected ConfigAttributeDefinition lookupAttributes(Method method) {
        Set<Entry<String, ConfigAttributeDefinition>> set = getMethodMap()
                .entrySet();
        for (Entry<String, ConfigAttributeDefinition> entry : set) {
            String pattern = entry.getKey();
            boolean matched = getMethodMatcher().match(pattern, method);
            if (matched) {
                return entry.getValue();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public ConfigAttributeDefinition getAttributes(Method method,
            Class targetClass) {
        return null;
    }

    public Map<String, ConfigAttributeDefinition> getMethodMap() {
        return methodMap;
    }

    public AspectJMethodMatcher getMethodMatcher() {
        return methodMatcher;
    }
}
