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
    //  全部角色列表，为前端页面多选框提供数据
    private List<Role> allRoles;
    //  查询结果
    private List<User> users;
    //  老用户名
    private String usernameOld;
    
    //  action处理结果（map对象），以供客户端读取action处理结果信息
    private Map<String,Object> actionResult = new HashMap<String,Object>();
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    /**
     * 新增用户。
     * 
     * @return 永远返回"success"
     */
    public String add() {
        boolean result = false;
        result = userService.add(user);
        if (result) {
            actionResult.put("success", true);
        } else {
            actionResult.put("success", false);
        }
        logger.debug("actionResult=" + actionResult);
        return SUCCESS;
    }

    /**
     * 判断用户在系统中是否存在。
     * 
     * @return 永远返回"success"
     */
    public String isUserExistent() {
        boolean result = false;
        String usename = user.getUsername();
        result = userService.isUserExistent(usename);
        if (result) {
            actionResult.put("success", true);
        } else {
            actionResult.put("success", false);
        }
        logger.debug("actionResult=" + actionResult);
        return SUCCESS;
    }

    /**
     * 修改用户名密码。
     * 
     * @return 永远返回"success"
     */
    public String modifyUsernamePassword() {
        boolean result = false;
        Integer id = user.getId();
        String username = user.getUsername();
        String plainpassword = user.getPlainpassword();
        result = userService.modifyUsernamePassword(id, username, plainpassword);
        if (result) {
            actionResult.put("success", true);
        } else {
            actionResult.put("success", false);
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
        users = userService.findUsersByUsername(user.getUsername());
        return SUCCESS;
    }
    
    /**
     * 得到所有的角色列表
     * 
     * @return 永远返回"success"
     */
    public String getAllRoleList() {
        allRoles = null;
        return SUCCESS;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~  Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void setAllRoles(List<Role> allRoles) {
        this.allRoles = allRoles;
    }

    public String getUsernameOld() {
        return usernameOld;
    }

    public void setUsernameOld(String usernameOld) {
        this.usernameOld = usernameOld;
    }

    public List<Role> getAllRoles() {
        return allRoles;
    }
}
