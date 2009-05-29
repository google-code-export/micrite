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
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.security.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;

import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.service.IUserService;
import org.gaixie.micrite.security.SecurityException; 
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
    private Map<String,String> returnMsg = new HashMap<String,String>();
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//
    /**
     * 新增用户。
     * 
     * @return "success"
     */
    public String add() {
        String[] userRoleIds = StringUtils.split(userRolesStr, ",");
        Set<Role> userRoles = userService.getRolesByIds(userRoleIds);
        user.setRoles(userRoles);
        try {
            userService.add(user);
            returnMsg.put("message", getText("save.success"));
            actionResult.put("success", true);
        } catch(SecurityException e) {
            returnMsg.put("message", getText(e.getMessage()));
            actionResult.put("success", false);
            logger.error(getText(e.getMessage()));
        }
        actionResult.put("result", returnMsg);
        return SUCCESS;
    }

    /**
     * 根据用户名判断用户是否已存在。
     * 
     * @return "success"
     */
    public String isExistedByUsername() {
        boolean isExisted = userService.isExistedByUsername(user.getUsername());
        actionResult.put("success", isExisted);
        return SUCCESS;
    }

    /**
     * 修改用户信息。
     * 
     * @return "success"
     */
    public String updateInfo() {
        userService.updateInfo(user.getId(), 
                               user.getFullname(), 
                               user.getEmailaddress(), 
                               user.getPlainpassword());
        returnMsg.put("message", getText("save.success"));
        actionResult.put("success", true);
        actionResult.put("result", returnMsg);
        return SUCCESS;
    }

    /**
     * 加载当前用户
     * 
     * @return "success"
     */
    public String loadCurrentUser() {
        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> userMap = new HashMap<String,Object>();
        userMap.put("id", currentUser.getId());
        userMap.put("fullname", currentUser.getFullname());
        userMap.put("emailaddress", currentUser.getEmailaddress());
        userMap.put("loginname", currentUser.getUsername());
        actionResult.put("data", userMap);
        actionResult.put("success", true);
        return SUCCESS;
    }

    /**
     * 根据用户名查询用户集合（模糊查询）。
     * 
     * @return "success"
     */
    public String findByUsernameVague() {
        users = userService.findByUsernameVague(user.getUsername());
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
