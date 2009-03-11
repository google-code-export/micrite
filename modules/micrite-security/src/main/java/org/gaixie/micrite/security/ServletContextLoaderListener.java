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

package org.gaixie.micrite.security;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.gaixie.micrite.security.service.ISecurityService;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author Downpour
 */
public class ServletContextLoaderListener implements ServletContextListener{

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        ISecurityService securityManager = this.getSecurityManager(servletContext);
        
        Map<String, String> urlAuthorities = securityManager.loadUrlAuthorities();
        servletContext.setAttribute("urlAuthorities", urlAuthorities);
    }

    
    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().removeAttribute("urlAuthorities");
    }

    /**
     * Get SecurityManager from ApplicationContext
     * 
     * @param servletContext
     * @return
     */
    protected ISecurityService getSecurityManager(ServletContext servletContext) {
       return (ISecurityService) WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean("securityManager"); 
    }

}
