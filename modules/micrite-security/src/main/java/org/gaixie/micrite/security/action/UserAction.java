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
import org.springframework.jmx.export.annotation.ManagedResource;
import com.opensymphony.xwork2.ActionSupport;

import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.service.IUserService;
 
/**
 * 用户管理，提供新增，修改，查询用户等功能
 * @see com.opensymphony.xwork2.ActionSupport
 */
@ManagedResource(objectName="micrite:type=action,name=UserAction", description="Micrite UserAction Bean")
public class UserAction extends ActionSupport {

    private static final Logger logger = Logger.getLogger(UserAction.class);

    //  提供 用户管理服务，本类要调用它来完成功能
    private IUserService userService;
    
    //  用户
    private User user;
    //  查询条件，用户名
    private String username;
    //  查询结果
    private List<User> users;
    //  角色列表，为前端页面多选框提供数据
    private List<Role> roles;
    //  action处理结果（map对象），以供客户端读取action处理结果信息
    private Map<String,Object> actionResult = new HashMap<String,Object>();
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    /**
     * 新增用户。
     * 
     * @return 永远返回"success"
     */
    public String save() {
        boolean result = false;
        result = userService.add(user);
        if (result) {
            actionResult.put("success", true);
        } else {
            actionResult.put("failure", true);
        }
        logger.debug("actionResult=" + actionResult);
        return SUCCESS;
    }

    /**
     * 修改用户信息。
     * 
     * @return 永远返回"success"
     */
    public String modifyInfo() {
        boolean result = false;
        result = userService.modifyInfo(user);
        if (result) {
            actionResult.put("success", true);
        } else {
            actionResult.put("failure", true);
        }
        logger.debug("actionResult=" + actionResult);
        return SUCCESS;
    }

    /**
     * 根据用户名查询用户集合（模糊查询）。
     * 
     * @return 永远返回"success"
     */
    public String findUsersByUsername() {
        users = userService.findUsersByUsername(username);
        return SUCCESS;
    }
    
    /**
     * 得到所有的角色列表
     * 
     * @return 永远返回"success"
     */
    public String getAllRoles() {
        roles = null;
        return SUCCESS;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~  Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Map<String, Object> getActionResult() {
        return actionResult;
    }

    public void setActionResult(Map<String, Object> actionResult) {
        this.actionResult = actionResult;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
