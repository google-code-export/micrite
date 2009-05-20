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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.security.context.SecurityContext;

import com.opensymphony.xwork2.ActionSupport;

import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.service.IUserService;
 
/**
 * 用户管理，提供新增，修改，查询用户等功能
 * @see com.opensymphony.xwork2.ActionSupport
 */
@ManagedResource(objectName="micrite:type=action,name=UserAction", description="Micrite UserAction Bean")
public class UserAction extends ActionSupport implements SessionAware {

    private static final Logger logger = Logger.getLogger(UserAction.class);

    //  提供 用户管理服务，本类要调用它来完成功能
    @Autowired
    private IUserService userService;
    
    //  用户
    private User user;
    //  全部角色列表，为前端页面多选框提供数据
    private List<Role> allRoles;
    //  用户角色拼串，型如“2,4,6”
    private String userRolesStr;
    //  查询结果
    private List<User> users;
    //  老用户名
    private String usernameOld;

    //  action处理结果（map对象），以供客户端读取action处理结果信息
    private Map<String,Object> actionResult = new HashMap<String,Object>();
    
    //  为了访问servlet中session对象
    private Map session;
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    /**
     * 新增用户。
     * 
     * @return 永远返回"success"
     */
    public String add() {
        boolean result = false;
        String[] userRoleIds = StringUtils.split(userRolesStr, ",");
        result = userService.addUser(user,userRoleIds);
        if (result) {
            actionResult.put("success", true);
        } else {
            actionResult.put("success", false);
        }
        logger.debug("actionResult=" + actionResult);
        return SUCCESS;
    }

    /**
     * 根据用户名判断用户在系统中是否存在。
     * 
     * @return 永远返回"success"
     */
    public String isExistentByUsername() {
        boolean result = false;
        String usename = user.getLoginname();
        result = userService.isExistentByUsername(usename);
        if (result) {
            actionResult.put("success", true);
        } else {
            actionResult.put("success", false);
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
        logger.debug("user=" + user);
        SecurityContext securityContext = (SecurityContext) session.get("SPRING_SECURITY_CONTEXT");
        User currentUser = (User) securityContext.getAuthentication().getPrincipal();
        result = userService.modifyUserInfo(user,currentUser);
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
        String username = user.getLoginname();
        users = userService.findUsersByUsername(username);
        return SUCCESS;
    }
    
    /**
     * 得到所有的角色列表
     * 
     * @return 永远返回"success"
     */
    public String getAllRoleList() {
        allRoles = userService.getAllRoles();
        //  防止json对象查找死循环
        for (Role role:allRoles) {
            role.setAuthorities(null);
        }
        return SUCCESS;
    }
    
    /**
     * 得到当前用户
     * 
     * @return 永远返回"success"
     */
    public String getCurrentUser() {
        SecurityContext securityContext = (SecurityContext) session.get("SPRING_SECURITY_CONTEXT");
        User currentUser = (User) securityContext.getAuthentication().getPrincipal();
        Map<String,Object> userMap = new HashMap<String,Object>();
        userMap.put("id", currentUser.getId());
        userMap.put("fullname", currentUser.getFullname());
        userMap.put("emailaddress", currentUser.getEmailaddress());
        userMap.put("loginname", currentUser.getLoginname());
        List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
        dataList.add(userMap);
        actionResult.put("data", dataList);
        actionResult.put("success", true);
        return SUCCESS;
    }
    
    /* 
     * @see org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
     */
    public void setSession(Map session) {
        this.session = session;
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

    public String getUsernameOld() {
        return usernameOld;
    }

    public void setUsernameOld(String usernameOld) {
        this.usernameOld = usernameOld;
    }

    public void setActionResult(Map<String, Object> actionResult) {
        this.actionResult = actionResult;
    }

    public void setAllRoles(List<Role> allRoles) {
        this.allRoles = allRoles;
    }

    public List<Role> getAllRoles() {
        return allRoles;
    }

    public String getUserRolesStr() {
        return userRolesStr;
    }

    public void setUserRolesStr(String userRolesStr) {
        this.userRolesStr = userRolesStr;
    }

}
