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
import org.springframework.security.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;

import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.common.action.MicriteAction;
import org.gaixie.micrite.security.service.IUserService;
import org.gaixie.micrite.security.SecurityException; 
/**
 * 用户管理，提供新增，修改，查询用户等功能。
 */
//@ManagedResource(objectName="micrite:type=action,name=UserAction", description="Micrite UserAction Bean")
public class UserAction extends MicriteAction {

    private static final long serialVersionUID = 5843976450199930680L;

    private static final Logger logger = Logger.getLogger(UserAction.class);

    //  用户管理服务，本类要调用它来完成功能
    @Autowired
    private IUserService userService;
    
    //  用户
    private User user;
    //  用户角色拼串，型如“2,4,6”
    private String userRolesStr;
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//
    /**
     * 新增用户。
     * 
     * @return "success"
     */
    public String add() {
        String[] userRoleIds = StringUtils.split(userRolesStr, ",");
        try {
            userService.add(user, userRoleIds);
            setMsg(true, getText("save.success"));
        } catch(SecurityException e) {
            setMsg(false, getText(e.getMessage()));
            logger.error(getText(e.getMessage()));
        }
        genResultForUpdate();
        return SUCCESS;
    }

    /**
     * 根据用户名判断用户是否已存在。
     * 
     * @return "success"
     */
    public String isExistedByUsername() {
        boolean isExisted = userService.isExistedByUsername(user.getUsername());
        setMsg(isExisted, null);
        genResultForUpdate();
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
        setMsg(true, getText("save.success"));
        genResultForUpdate();
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
        setMsg(true, null);
        genResultForLoad(userMap);
        return SUCCESS;
    }

    /**
     * 根据用户名查询用户集合（模糊查询）。
     * 
     * @return "success"
     */
    public String findByUsernameVague() {
        String username = user.getUsername();
        if (getTotalCount() == 0) {
            logger.debug("---->"+getTotalCount());
            //  初次查询时，要从数据库中读取总记录数
            int totalCount = userService.findByUsernameVagueCount(username);
            setTotalCount(totalCount);
        } 
        //  得到分页查询结果
        List<User> users = userService.findByUsernameVaguePerPage(username, getStart(), getLimit());
        //  防止json死循环查找
        for (User user : users) {
            user.setRoles(null);
        }
        setMsg(true, null);
        genResultForFind(users);
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
}
