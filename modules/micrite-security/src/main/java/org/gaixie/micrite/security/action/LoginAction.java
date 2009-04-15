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

package org.gaixie.micrite.security.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.struts2.interceptor.SessionAware;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.service.IUserService;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.ui.WebAuthenticationDetails;

import com.opensymphony.xwork2.ActionSupport;
 
/**
 * 登录用户管理
 * @see com.opensymphony.xwork2.ActionSupport
 * @see org.apache.struts2.interceptor.SessionAware
 */
@ManagedResource(objectName="micrite:type=action,name=LoginAction", description="Micrite LoginAction Bean")
public class LoginAction extends ActionSupport implements SessionAware{ 
	
	private User user;
	private String role;
    private String remoteAddress;
    private String sessionId;
	private Map session;
	private boolean success;
	private Map<String,String> errorMsg = new HashMap<String,String>();
	private InputStream inputStream;
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Map<String, String> getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(Map<String, String> errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * 默认起始事件，获得登录用户信息
	 * @return "success"
	 */
	public String loginSuccess() {
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
		

		Map map = new HashMap();  
		map.put( "success", true );  

		JSONObject jsonObject = JSONObject.fromObject(map); 
		this.inputStream = new StringBufferInputStream(jsonObject.toString());
		return SUCCESS;
    }    

	public String loginFaile(){
		success = false;
		errorMsg.put("reason", "login faile");
		Map map = new HashMap();  
		map.put( "success", false );  
		map.put( "errorMsg", errorMsg ); 
		JSONObject jsonObject = JSONObject.fromObject(map); 
		this.inputStream = new StringBufferInputStream(jsonObject.toString());
		return SUCCESS;
	}
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @return the role
	 */
	@ManagedAttribute(description="The role attribute")
	public String getRole() {
		return role;
	}

	/**
	 * @return the remoteAddress
	 */
	@ManagedAttribute(description="The remoteAddress attribute")
	public String getRemoteAddress() {
		return remoteAddress;
	}

	/**
	 * @return sessionId
	 */
	@ManagedAttribute(description="The sessionId attribute")
	public String getSessionId() {
		return sessionId;
	}
	
    /**
     * @return the user
     */
    public User getUser() {
		return user;
	}

	/**
	 * @param user 用户实体
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
	 */
	public void setSession(Map session) {
		this.session = session;
	}

	
}
