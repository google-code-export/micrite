/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gaixie.micrite.security.filter;

import java.util.LinkedHashMap;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.gaixie.micrite.beans.Authority;
import org.gaixie.micrite.security.service.ISecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.intercept.AbstractSecurityInterceptor;
import org.springframework.security.intercept.InterceptorStatusToken;
import org.springframework.security.intercept.ObjectDefinitionSource;
import org.springframework.security.intercept.method.MethodDefinitionSource;


/**
 * Provides security interception of AOP Alliance based method invocations.<p>The
 * <code>ObjectDefinitionSource</code> required by this security interceptor is of type {@link
 * MethodDefinitionSource}. This is shared with the AspectJ based security interceptor
 * (<code>AspectJSecurityInterceptor</code>), since both work with Java <code>Method</code>s.</p>
 *  <P>Refer to {@link AbstractSecurityInterceptor} for details on the workflow.</p>
 *
 * @author Ben Alex
 * @version $Id$
 */
public class MethodSecurityInterceptor extends AbstractSecurityInterceptor implements MethodInterceptor {
    //~ Instance fields ================================================================================================

    private static MethodDefinitionSource objectDefinitionSource;
    private static final String AUTHORITY_TYPE = "METHOD";
    @Autowired
    private ISecurityInterceptor securityInterceptor;

    //~ Methods ========================================================================================================

    public MethodDefinitionSource getObjectDefinitionSource() {
        return objectDefinitionSource;
    }

    @SuppressWarnings("unchecked")
    public Class getSecureObjectClass() {
        return MethodInvocation.class;
    }

    /**
     * This method should be used to enforce security on a <code>MethodInvocation</code>.
     *
     * @param mi The method being invoked which requires a security decision
     *
     * @return The returned value from the method invocation
     *
     * @throws Throwable if any error occurs
     */
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object result = null;
        InterceptorStatusToken token = super.beforeInvocation(mi);

        try {
            result = mi.proceed();
        } finally {
            result = super.afterInvocation(token, result);
        }

        return result;
    }

    /**
     * 初始化MethodDefinitionSource对象，为Method拦截器提供验证访问。
     * 
     */
    public ObjectDefinitionSource obtainObjectDefinitionSource() {
        if(objectDefinitionSource == null){
            AspectJMethodMatcher methodMatcher = new AspectJMethodMatcher();
            LinkedHashMap<String, ConfigAttributeDefinition> methodMap
                    = new LinkedHashMap<String, ConfigAttributeDefinition>();
            
            List<Authority> authorities = securityInterceptor.loadAuthorities(AUTHORITY_TYPE);
            methodMap = new LinkedHashMap<String, ConfigAttributeDefinition>();
            for (Authority authority : authorities) {
                String grantedAuthorities = authority.getRolesString();
                if (StringUtils.isNotEmpty(grantedAuthorities)) {
                    ConfigAttributeEditor configAttrEditor = new ConfigAttributeEditor();
                    configAttrEditor.setAsText(grantedAuthorities);
                    ConfigAttributeDefinition definition = (ConfigAttributeDefinition) configAttrEditor
                            .getValue();
                    methodMap.put(authority.getValue(), definition);
                }
            }
            objectDefinitionSource = new DefaultMethodDefinitionSource(methodMatcher, methodMap);
        }
        return objectDefinitionSource;
    }
    /**
     * 提供一个刷新MethodDefinitionSource的静态方法
     */
    public static void refresh() {
        objectDefinitionSource = null;
    }
    
    public void setObjectDefinitionSource(MethodDefinitionSource newSource) {
        objectDefinitionSource = newSource;
    }
}
