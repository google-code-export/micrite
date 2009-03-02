/* ===========================================================
 * $Id$
 * This file is part of Micrite
 * ===========================================================
 *
 * (C) Copyright 2009, by Gaixie.org and Contributors.
 * 
 * Project Info:  http://www.gaixie.org/wiki/100:Mainpage
 *
 * Micrite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Micrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Micrite.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.gaixie.micrite.security.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.service.IUserService;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.ui.WebAuthenticationDetails;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
 
@ManagedResource(objectName="micrite:type=action,name=UserAction", description="Micrite UserAction Bean")
public class UserAction extends ActionSupport implements SessionAware{ 
	
	public UserAction(IUserService userService) {
		this.userService = userService;
	}
	public void setSession(Map session) {
		this.session = session;
	}

	private User user;
	private String role;
    private String remoteAddress;
    private String sessionId;
	private Map session;
	private IUserService userService;

	public String index() {
		SecurityContext securityContext = (SecurityContext) session.get("SPRING_SECURITY_CONTEXT");
		user = (User) securityContext.getAuthentication().getPrincipal();
		role = "";
		for (GrantedAuthority grantedAuthority : securityContext.getAuthentication().getAuthorities()) {
			role = (role.equals("")) ? grantedAuthority.getAuthority() : role
					+ "," + grantedAuthority.getAuthority();
		}
		WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) securityContext
				.getAuthentication().getDetails();
		remoteAddress = webAuthenticationDetails.getRemoteAddress();
		sessionId = webAuthenticationDetails.getSessionId();
		return SUCCESS;
    }    

	@ManagedAttribute(description="The role attribute")
	public String getRole() {
		return role;
	}

	@ManagedAttribute(description="The remoteAddress attribute")
	public String getRemoteAddress() {
		return remoteAddress;
	}

	@ManagedAttribute(description="The sessionId attribute")
	public String getSessionId() {
		return sessionId;
	}
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
