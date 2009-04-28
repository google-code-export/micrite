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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.struts2.interceptor.SessionAware;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.beans.Authority;
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

	private IUserService userService;
	
	private User user;
    private String node;
	private Map session;
	private Set<Map<String,Object>> menu;
	private Map loginResult = new HashMap<String,Object>();
	private Map<String,String> errorMsg = new HashMap<String,String>();
	
	/**
	 * 带参数构造函数，实例化对象，并通过参数初始化<strong>userService</strong>
	 * @param userService IUserService接口，通过Ioc模式注入业务实例
	 * @see org.gaixie.micrite.security.service.IUserService
	 */
	public LoginAction(IUserService userService) {
		this.userService = userService;
	}

	public Set<Map<String,Object>> getMenu() {
		return menu;
	}

	public void setMenu(Set<Map<String,Object>> menu) {
		this.menu = menu;
	}
	
	public Map<String, Object> getLoginResult() {
		return loginResult;
	}

	public void setLoginResult(Map<String, Object> loginResult) {
		this.loginResult = loginResult;
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
		loginResult.put("success", true);
		return SUCCESS;
    }    

	public String loginFaile(){
		errorMsg.put("reason", "login faile");
		loginResult.put( "success", false );  
		loginResult.put( "errorMsg", errorMsg ); 
		return SUCCESS;
	}

	public String loadMenu(){
		menu = userService.loadMenuByUser(user,node);
		return SUCCESS;
	}
	

	/**
	 * @return node
	 */
	public String getNode() {
		return node;
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

	/**
	 * @param node 菜单上选择的节点id
	 */
	public void setNode(String node) {
		this.node = node;
	}
	
}
