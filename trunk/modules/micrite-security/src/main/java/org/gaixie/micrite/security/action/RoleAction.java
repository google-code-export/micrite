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
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.security.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 处理用户对角色操作的事件。
 * 
 */
public class RoleAction extends ActionSupport{
	private static final long serialVersionUID = 3072284877032259302L;

	private static final Logger logger = Logger.getLogger(RoleAction.class);
	
	@Autowired
	private IRoleService roleService;

    //输出到页面的数据
    private List<Role> roles;

    //  用户
    private Role role;
    
    //  以下两个分页用
    //  起始索引
    private int start;
    //  限制数
    private int limit;
    //  action处理结果（map对象）
    private Map<String,Object> actionResult = new HashMap<String,Object>();
    private Map<String,String> returnMsg = new HashMap<String,String>();    

    // ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
   
    /**
     * 查找所有角色
     * @return "success"
     */
    public String findAll(){
    	roles = roleService.findAll();
    	//Hibernate的级联导致json出现异常：
    	//net.sf.json.JSONException: There is a cycle in the hierarchy!
    	//手动去点roles的级联对象
    	for(Role role : roles){
    		role.setAuthorities(null);
    	}
    	return SUCCESS;
    }

    /**
     * 根据角色名查询角色集合（模糊查询）。
     * 
     * @return "success"
     */
    public String findByNameVague() {
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        //  为了得到查询结果总数
        int count = roleService.findByNameVagueTotal(role.getName());
        //  得到分页查询结果
        List<Role> searchRoles = roleService.findByNameVaguePerPage(role.getName(), start, limit);
        for(Role role : searchRoles){
            role.setAuthorities(null);
        }
        actionResult.put("totalCounts", count);
        actionResult.put("success", true);
        actionResult.put("results", searchRoles);
        return SUCCESS;
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    

	/**
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }
    
    public void setStart(int start) {
        this.start = start;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
    
    public Map<String, Object> getActionResult() {
        return actionResult;
    }    
}
