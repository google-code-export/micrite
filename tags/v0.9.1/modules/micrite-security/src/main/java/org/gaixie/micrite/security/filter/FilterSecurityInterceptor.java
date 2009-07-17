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

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.gaixie.micrite.beans.Authority;
import org.gaixie.micrite.security.service.ISecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.intercept.AbstractSecurityInterceptor;
import org.springframework.security.intercept.InterceptorStatusToken;
import org.springframework.security.intercept.ObjectDefinitionSource;
import org.springframework.security.intercept.web.DefaultFilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.RequestKey;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;


/**
 * 自定义过滤器，对URL资源进行安全控制.
 * <p>此类负责对URL请求进行安全验证，包含一个静态的权限资源<code>ObjectDefinitionSource</code>（{@link FilterInvocationDefinitionSource}类型）.</p>
 * <p>参照{@link AbstractSecurityInterceptor}了解详细的工作流程.</p>
 *
 */
public class FilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter, Ordered {
    //~ Static fields/initializers =====================================================================================

    private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";

    private static FilterInvocationDefinitionSource objectDefinitionSource;
    private static final String AUTHORITY_TYPE = "URL";
    //~ Instance fields ================================================================================================

    private boolean observeOncePerRequest = true;
    @Autowired
    private ISecurityInterceptor securityInterceptor;

    //~ Methods ========================================================================================================

    /**
     * Not used (we rely on IoC container lifecycle services instead)
     *
     * @param arg0 ignored
     *
     * @throws ServletException never thrown
     */
    public void init(FilterConfig arg0) throws ServletException {}

    /**
     * Not used (we rely on IoC container lifecycle services instead)
     */
    public void destroy() {}

    /**
     * Method that is actually called by the filter chain. Simply delegates to the {@link
     * #invoke(FilterInvocation)} method.
     *
     * @param request the servlet request
     * @param response the servlet response
     * @param chain the filter chain
     *
     * @throws IOException if the filter chain fails
     * @throws ServletException if the filter chain fails
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }

    public FilterInvocationDefinitionSource getObjectDefinitionSource() {
        return objectDefinitionSource;
    }

    @SuppressWarnings("unchecked")
    public Class getSecureObjectClass() {
        return FilterInvocation.class;
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        if ((fi.getRequest() != null) && (fi.getRequest().getAttribute(FILTER_APPLIED) != null)
            && observeOncePerRequest) {
            // filter already applied to this request and user wants us to observce
            // once-per-request handling, so don't re-do security checking
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } else {
            // first time this request being called, so perform security checking
            if (fi.getRequest() != null) {
                fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
            }

            InterceptorStatusToken token = super.beforeInvocation(fi);

            try {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            } finally {
                super.afterInvocation(token, null);
            }
        }
    }

    /**
     * Indicates whether once-per-request handling will be observed. By default this is <code>true</code>,
     * meaning the <code>FilterSecurityInterceptor</code> will only execute once-per-request. Sometimes users may wish
     * it to execute more than once per request, such as when JSP forwards are being used and filter security is
     * desired on each included fragment of the HTTP request.
     *
     * @return <code>true</code> (the default) if once-per-request is honoured, otherwise <code>false</code> if
     *         <code>FilterSecurityInterceptor</code> will enforce authorizations for each and every fragment of the
     *         HTTP request.
     */
    public boolean isObserveOncePerRequest() {
        return observeOncePerRequest;
    }

    /**
     * 初始化ObjectDefinitionSource对象，为安全验证提供权限资源。
     * 初始化时，获取所有类型为 <code>URL</code> 的权限标识符， 以 <code>LinkedHashMap</code>
     * 格式保存，<code>LinkedHashMap</code> 的key是 <code>Authority</code> 的value，
     * value是访问这些method所需要的权限 <code>ConfigAttributeDefinition</code>。
     */
    public ObjectDefinitionSource obtainObjectDefinitionSource() {
        if(objectDefinitionSource == null){
            UrlMatcher urlMatcher = new AntUrlPathMatcher(true);
            LinkedHashMap<RequestKey, ConfigAttributeDefinition> requestMap = 
                    new LinkedHashMap<RequestKey, ConfigAttributeDefinition>();
            
            List<Authority> authorities = securityInterceptor.loadAuthorities(AUTHORITY_TYPE);
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

            objectDefinitionSource = new DefaultFilterInvocationDefinitionSource(urlMatcher,requestMap);
        }
        return objectDefinitionSource;
    }
    /**
     * 刷新<code>objectDefinitionSource</code>对象
     */
    public static void refresh() {
        objectDefinitionSource = null;
    }

    public void setObjectDefinitionSource(FilterInvocationDefinitionSource newSource) {
        objectDefinitionSource = newSource;
    }

    public void setObserveOncePerRequest(boolean observeOncePerRequest) {
        this.observeOncePerRequest = observeOncePerRequest;
    }

    public int getOrder() {
        return FilterChainOrder.FILTER_SECURITY_INTERCEPTOR;
    }

}
