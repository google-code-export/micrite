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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;
 
/**
 * 登录用户管理
 * @see com.opensymphony.xwork2.ActionSupport
 * @see org.apache.struts2.interceptor.SessionAware
 */
@ManagedResource(objectName="micrite:type=action,name=LoginAction", description="Micrite LoginAction Bean")
public class LoginAction extends ActionSupport implements SessionAware{ 
	private static final long serialVersionUID = -5277215719944190914L;

	private static final Logger logger = Logger.getLogger(LoginAction.class); 
	
	@Autowired
	private ILoginService loginService;
	
	private User user;
    private String node;
	private Map<String,Object> session;
	private Set<Map<String,Object>> menu;
	private Map<String,Object> loginResult = new HashMap<String,Object>();
	private Map<String,String> errorMsg = new HashMap<String,String>();
	
    // ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
	/**
	 * 默认起始事件，获得登录用户信息
	 * @return "success"
	 */
	public String loginSuccess() {
		loginResult.put("success", true);
		return SUCCESS;
    }    

	public String loginFailed(){
		errorMsg.put("reason", getText("error.login.authenticationFailed"));
		loginResult.put( "success", false );  
		loginResult.put( "errorMsg", errorMsg ); 
		return SUCCESS;
	}

	public String loadMenu(){
        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		menu = loginService.loadChildNodes(currentUser,node);
		return SUCCESS;
	}
	
    // ~~~~~~~~~~~~~~~~~~~~~~~  Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
	/**
	 * @return node
	 */
	public String getNode() {
		return node;
	}

    /**
     * @param node 菜单上选择的节点id
     */
    public void setNode(String node) {
        this.node = node;
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
	public void setSession(Map<String,Object> session) {
		this.session = session;
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
}
