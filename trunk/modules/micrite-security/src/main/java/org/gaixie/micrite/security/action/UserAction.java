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
import org.gaixie.micrite.beans.Setting;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.SecurityException;
import org.gaixie.micrite.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 用户管理，提供新增，修改，查询用户等功能。
 */
//@ManagedResource(objectName="micrite:type=action,name=UserAction", description="Micrite UserAction Bean")
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

    //  以下两个分页用
    //  起始索引
    private int start;
    //  限制数
    private int limit;
    //  记录总数（分页中改变页码时，会传递该参数过来）
    private int totalCount;

    //  action处理结果（map对象）
    private Map<String,Object> resultMap = new HashMap<String,Object>();
    
    // user setting
    private List<Setting> setting;
    
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
            resultMap.put("message", getText("save.success"));
            resultMap.put("success", true);
        } catch(SecurityException e) {
            resultMap.put("message", getText(e.getMessage()));
            resultMap.put("success", false);
            logger.error(getText(e.getMessage()));
        }
        return SUCCESS;
    }

    /**
     * 根据用户名判断用户是否已存在。
     * 
     * @return "success"
     */
    public String isExistedByUsername() {
        boolean isExisted = userService.isExistedByUsername(user.getUsername());
        resultMap.put("success", isExisted);
        return SUCCESS;
    }

    /**
     * 修改用户信息。
     * 
     * @return "success"
     */
    public String updateInfo() {
    	user.setSettings(setting);
        userService.updateInfo(user);
        resultMap.put("message", getText("save.success"));
        resultMap.put("success", true);
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
        userMap.put("user.id", currentUser.getId());
        userMap.put("user.fullname", currentUser.getFullname());
        userMap.put("user.emailaddress", currentUser.getEmailaddress());
        userMap.put("user.loginname", currentUser.getLoginname());
        //userMap.put( userService.findUserSettingById(currentUser.getId()).get(0).getId());
        resultMap.put("data", userMap);
        resultMap.put("settings", currentUser.getSettings());
        resultMap.put("success", true);
        return SUCCESS;
    }

    /**
     * 根据用户名查询用户集合（模糊查询）。
     * 
     * @return "success"
     */
    public String findByUsernameVague() {
        String username = user.getUsername();
        if (totalCount == 0) {
            //  初次查询时，要从数据库中读取总记录数
            Integer count = userService.findByUsernameVagueCount(username);
            setTotalCount(count);
        } 
        //  得到分页查询结果
        List<User> users = userService.findByUsernameVaguePerPage(username, start, limit);
        //  防止json死循环查找
//        for (User user : users) {
//            user.setRoles(null);
//        }
        resultMap.put("totalCount", totalCount);
        resultMap.put("success", true);
        resultMap.put("data", users);
        return SUCCESS;
    }
    
    public String findSettingByName(){
    	setSetting(userService.findSettingByName(setting.get(0).getName()));
    	return SUCCESS;
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setUserRolesStr(String userRolesStr) {
        this.userRolesStr = userRolesStr;
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

	public void setSetting(List<Setting> setting) {
		this.setting = setting;
	}

	public List<Setting> getSetting() {
		return setting;
	}

}
