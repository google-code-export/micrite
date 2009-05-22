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

import org.gaixie.micrite.beans.Authority;
import org.gaixie.micrite.security.service.IAuthorityService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 用来响应用户对授权资源操作的事件。
 * <p>
 * 通过调用相关的Service类，完成对Authority基本信息的增加，删除，修改，查询。
 */
public class AuthorityAction extends ActionSupport{ 
	private static final long serialVersionUID = 1721180911011412346L;

	private IAuthorityService authorityService;

    //以Map格式存放操作的结果，然后由struts2-json插件转换为json对象
    private Map<String,Object> result = new HashMap<String,Object>();
    
    //获取的页面参数
    private Authority authority;
    private String roleIdBunch;

    /**
     * 带参数构造函数，实例化对象，并通过参数初始化<strong>authorityService</strong>
     * @param authorityService IAuthorityService接口，通过Ioc模式注入业务实例
     */
    public AuthorityAction(IAuthorityService authorityService) {
        this.authorityService = authorityService;
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    /**
     * 保存授权资源信息
     * @return "success"
     */
    public String save() {
        result.put("success", authorityService.add(authority, roleIdBunch));
        return SUCCESS;
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    

	/**
	 * @return the result
	 */
	public Map<String, Object> getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	/**
	 * @return the authority
	 */
	public Authority getAuthority() {
		return authority;
	}

	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	/**
	 * @return the roleIdBunch
	 */
	public String getRoleIdBunch() {
		return roleIdBunch;
	}

	/**
	 * @param roleIdBunch the roleIdBunch to set
	 */
	public void setRoleIdBunch(String roleIdBunch) {
		this.roleIdBunch = roleIdBunch;
	}
    

}
