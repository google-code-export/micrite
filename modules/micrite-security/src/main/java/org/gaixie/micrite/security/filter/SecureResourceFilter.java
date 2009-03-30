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

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.RegexUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;

/**
 * 资源安全控制，负责校验用户访问的资源是否合法
 * @see org.springframework.security.intercept.web.FilterInvocationDefinitionSource
 */
public class SecureResourceFilter implements FilterInvocationDefinitionSource, InitializingBean {
    
    private UrlMatcher urlMatcher;

    private boolean useAntPath = true;
    
    private boolean lowercaseComparisons = true;
    
    /**
     * @param useAntPath the useAntPath to set
     */
    public void setUseAntPath(boolean useAntPath) {
        this.useAntPath = useAntPath;
    }
    
    /**
     * @param lowercaseComparisons
     */
    public void setLowercaseComparisons(boolean lowercaseComparisons) {
        this.lowercaseComparisons = lowercaseComparisons;
    }
    
    /* (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        
        // default url matcher will be RegexUrlPathMatcher
        this.urlMatcher = new RegexUrlPathMatcher();
        
        if (useAntPath) {  // change the implementation if required
            this.urlMatcher = new AntUrlPathMatcher();
        }
        
        // Only change from the defaults if the attribute has been set
        if ("true".equals(lowercaseComparisons)) {
            if (!this.useAntPath) {
                ((RegexUrlPathMatcher) this.urlMatcher).setRequiresLowerCaseUrl(true);
            }
        } else if ("false".equals(lowercaseComparisons)) {
            if (this.useAntPath) {
                ((AntUrlPathMatcher) this.urlMatcher).setRequiresLowerCaseUrl(false);
            }
        }
        
    }
    
    /* (non-Javadoc)
     * @see org.springframework.security.intercept.ObjectDefinitionSource#getAttributes(java.lang.Object)
     */
    public ConfigAttributeDefinition getAttributes(Object filter) throws IllegalArgumentException {
        
        FilterInvocation filterInvocation = (FilterInvocation) filter;
        String requestURI = filterInvocation.getRequestUrl();
        Map<String, String> urlAuthorities = this.getUrlAuthorities(filterInvocation);
        
        String grantedAuthorities = null;
        for(Iterator<Map.Entry<String, String>> iter = urlAuthorities.entrySet().iterator(); iter.hasNext();) {
            Map.Entry<String, String> entry = iter.next();
            String url = entry.getKey();
            
            if(urlMatcher.pathMatchesUrl(url, requestURI)) {
                grantedAuthorities = entry.getValue();
                break;
            }
            
        }
        
        if(grantedAuthorities != null) {
            ConfigAttributeEditor configAttrEditor = new ConfigAttributeEditor();
            configAttrEditor.setAsText(grantedAuthorities);
            return (ConfigAttributeDefinition) configAttrEditor.getValue();
        }
        
        return null;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.intercept.ObjectDefinitionSource#getConfigAttributeDefinitions()
     */
    @SuppressWarnings("unchecked")
	public Collection getConfigAttributeDefinitions() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.intercept.ObjectDefinitionSource#supports(java.lang.Class)
     */
    @SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
        return true;
    }
    
    /**
     * 
     * @param filterInvocation
     * @return
     */
    @SuppressWarnings("unchecked")
	private Map<String, String> getUrlAuthorities(FilterInvocation filterInvocation) {
        ServletContext servletContext = filterInvocation.getHttpRequest().getSession().getServletContext();
        return (Map<String, String>)servletContext.getAttribute("urlAuthorities");
    }

}
