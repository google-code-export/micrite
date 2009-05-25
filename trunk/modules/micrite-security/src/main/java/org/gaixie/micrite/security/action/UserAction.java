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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.security.context.SecurityContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.service.IUserService;
 
/**
 * 用户管理，提供新增，修改，查询用户等功能。
 */
@ManagedResource(objectName="micrite:type=action,name=UserAction", description="Micrite UserAction Bean")
public class UserAction extends ActionSupport {

    private static final long serialVersionUID = 5843976450199930680L;

    private static final Logger logger = Logger.getLogger(UserAction.class);

    //  用户管理服务，本类要调用它来完成功能
    @Autowired
    private IUserService userService;
    
    //  用户
    private User user;
    //  用户角色拼串，型如“2,4,6”
    private String userRolesStr;

    //  查询用户的查询结果
    private List<User> users;
    //  action处理结果（map对象）
    private Map<String,Object> actionResult = new HashMap<String,Object>();
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//
    /* 得到当前用户 */
    private User getCurrentUser() {
        ActionContext ctx = ActionContext.getContext();
        SecurityContext securityContext = (SecurityContext)ctx.getSession().get("SPRING_SECURITY_CONTEXT");
        return (User)securityContext.getAuthentication().getPrincipal();
    }
    
    /**
     * 新增用户。
     * 
     * @return "success"
     */
    public String add() {
        boolean result = false;
        String[] userRoleIds = StringUtils.split(userRolesStr, ",");
        result = userService.add(user,userRoleIds);
        actionResult.put("success", result);
        logger.debug("actionResult=" + actionResult);
        return SUCCESS;
    }

    /**
     * 根据用户名判断用户在系统中是否存在。
     * 
     * @return "success"
     */
    public String isExistedByUsername() {
        boolean result = false;
        String usename = user.getLoginname();
        result = userService.isExistedByUsername(usename);
        actionResult.put("success", result);
        return SUCCESS;
    }

    /**
     * 修改用户信息。
     * 
     * @return "success"
     */
    public String updateInfo() {
        boolean result = false;
        User currentUser = this.getCurrentUser();
        result = userService.updateInfo(user, currentUser);
        actionResult.put("success", result);
        return SUCCESS;
    }

    /**
     * 根据用户名查询用户集合（模糊查询）。
     * 
     * @return "success"
     */
    public String findByUsernameVague() {
        String username = user.getLoginname();
        users = userService.findByUsernameVague(username);
        return SUCCESS;
    }
    
    /**
     * 加载当前用户
     * 
     * @return "success"
     */
    public String loadCurrentUser() {
        User currentUser = this.getCurrentUser();
        Map<String,Object> userMap = new HashMap<String,Object>();
        userMap.put("id", currentUser.getId());
        userMap.put("fullname", currentUser.getFullname());
        userMap.put("emailaddress", currentUser.getEmailaddress());
        userMap.put("loginname", currentUser.getLoginname());
        actionResult.put("data", userMap);
        actionResult.put("success", true);
        return SUCCESS;
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUserRolesStr(String userRolesStr) {
        this.userRolesStr = userRolesStr;
    }

    public List<User> getUsers() {
        return users;
    }

    public Map<String, Object> getActionResult() {
        return actionResult;
    }
}
