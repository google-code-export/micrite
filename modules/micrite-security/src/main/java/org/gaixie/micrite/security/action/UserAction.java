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
import org.apache.struts2.ServletActionContext;
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
    //  用户角色id
    private int roleId;

    //  用户id数组
    private int[] userIds;
    
    private boolean binded;
    
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
    private List<Setting> settings;
    
    private String username;
    private String key;  
    private String password;
    private String repassword; 
    // ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//
    
    /**
     * 忘记密码第一步
     * @return "success"
     */
    public String forgotPasswordStepOne() {
        // 初次进入忘记密码页面，给出相关提示
        if(username == null){
            resultMap.put("message", getText("forgotPassword.step1.description"));
            resultMap.put("showForm", true);
        }else{
            try {
                String baseUrl = StringUtils.substringBefore(
                        ServletActionContext.getRequest().getRequestURL().toString(),
                        "forgotPasswordStepOne.action");
                userService.forgotPasswordStepOne(username,baseUrl,getLocale().toString());
                resultMap.put("message", getText("forgotPassword.step1.successful"));
                resultMap.put("showForm", false);
            } catch (SecurityException e) {
                resultMap.put("message", getText(e.getMessage()));
                resultMap.put("showForm", true);
                logger.error(getText(e.getMessage()));
            }
        }
        return SUCCESS;
    }   
    
    /**
     * 忘记密码第二步
     * @return "success"
     */
    public String forgotPasswordStepTwo() {
        // 初次进入忘记密码页面，给出相关提示key
        if(password == null){
            try {
                User cUser = userService.findByTokenKey(key);
                resultMap.put("message", getText("forgotPassword.step2.description",new String[]{cUser.getFullname()}));
                resultMap.put("showForm", true);
            } catch (SecurityException e) {
                resultMap.put("message", getText(e.getMessage()));
                resultMap.put("showForm", false);
                logger.error(getText(e.getMessage()));
            }
        }else{
            try {
                if(password.trim().length()<1){  
                    resultMap.put("message", getText("forgotPassword.step2.passwordIsNull"));
                    resultMap.put("showForm", true);
                }else if(!password.equals(repassword)){  
                    resultMap.put("message", getText("forgotPassword.step2.passwordNotMatch"));
                    resultMap.put("showForm", true);
                }else{  
                    userService.forgotPasswordStepTwo(key,password);
                    resultMap.put("message", getText("forgotPassword.step2.successful"));
                    resultMap.put("showForm", false);
                }
            } catch (SecurityException e) {
                resultMap.put("message", getText(e.getMessage()));
                resultMap.put("showForm", true);
                logger.error(getText(e.getMessage()));
            }
        }
        return SUCCESS;
    }    
    /**
     * 新增用户。
     * 
     * @return "success"
     */
    public String add() {
        try {
            userService.add(user);
            resultMap.put("message", getText("save.success"));
            resultMap.put("success", true);
        } catch (SecurityException e) {
            resultMap.put("message", getText(e.getMessage()));
            resultMap.put("success", false);
            logger.error(getText(e.getMessage()));
        }
        return SUCCESS;
    }

    /**
     * 修改用户信息。
     * 
     * @return "success"
     */
    public String updateMe() {
    	user.setSettings(settings);
        try {
            userService.updateMe(user);
            resultMap.put("message", getText("save.success"));
            resultMap.put("success", true);
        } catch (SecurityException e) {
            resultMap.put("message", getText(e.getMessage()));
            resultMap.put("success", false);
            logger.error(getText(e.getMessage()));
        }
        return SUCCESS;
    }

    /**
     * 通过用户列表修改用户信息。
     * 
     * @return "success"
     */
    public String update() {
        try {
            userService.update(user);
            resultMap.put("message", getText("save.success"));
            resultMap.put("success", true);
        } catch (SecurityException e) {
            resultMap.put("message", getText(e.getMessage()));
            resultMap.put("success", false);
            logger.error(getText(e.getMessage()));
        }
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
        resultMap.put("data", userMap);
        resultMap.put("settings", userService.getSettings(currentUser.getId()));
        resultMap.put("success", true);
        return SUCCESS;
    }

    /**
     * 根据用户名查询用户集合（模糊查询）。
     * 
     * @return "success"
     */
    public String findByFullnameVague() {
        String fullname = user.getFullname();
        if (totalCount == 0) {
            //  初次查询时，要从数据库中读取总记录数
            Integer count = userService.findByFullnameVagueCount(fullname);
            setTotalCount(count);
        } 
        //  得到分页查询结果
        List<User> users = userService.findByFullnameVaguePerPage(fullname, start, limit);
        resultMap.put("totalCount", totalCount);
        resultMap.put("success", true);
        resultMap.put("data", users);
        return SUCCESS;
    }

    /**
     * 根据名称查找可选择配置项。
     * 
     * @return "success"
     */    
    public String findSettingByName(){
    	setSettings(userService.findSettingByName(settings.get(0).getName()));
    	return SUCCESS;
    }
    
    /**
     * 删除若干用户。
     * 
     * @return "success"
     */
    public String delete() {
        userService.delete(userIds);
        resultMap.put("message", getText("save.success"));
        resultMap.put("success", true);
        return SUCCESS;
    }

    /**
     * 设置用户状态可用/不可用。
     * 
     * @return "success"
     */
    public String updateStatus() {
        userService.updateStatus(userIds);
        resultMap.put("message", getText("save.success"));
        resultMap.put("success", true);
        return SUCCESS;
    }

    /**
     * 根据角色查询用户。
     * 
     * @return "success"
     */    
    public String findBindedUsers() {
        
        if(!binded) return findByFullnameVague();

        if (totalCount == 0) {
            //  初次查询时，要从数据库中读取总记录数
            Integer count = userService.findUsersByRoleIdCount(roleId);
            setTotalCount(count);
        } 
        
        List<User> users = userService.findUsersByRoleIdPerPage(roleId, start, limit);
        resultMap.put("totalCount", totalCount);    
        resultMap.put("success", true);
        resultMap.put("data", users);

        return SUCCESS;
    }

    /**
     * 将用户绑定到角色。
     * 
     * @return "success"
     */    
    public String bindUsers() {
        userService.bindUsers(userIds,roleId);
        resultMap.put("message", getText("save.success"));
        resultMap.put("success", true);
        return SUCCESS;
    }

    /**
     * 将用户从角色上解除绑定。
     * 
     * @return "success"
     */    
    public String unBindUsers() {
        userService.unBindUsers(userIds,roleId);
        resultMap.put("message", getText("save.success"));
        resultMap.put("success", true);
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

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setUserIds(int[] userIds) {
        this.userIds = userIds;
    }
    
    public Map<String, Object> getResultMap() {
        return resultMap;
    }

	public void setSettings(List<Setting> settings) {
		this.settings = settings;
	}

	public List<Setting> getSettings() {
		return settings;
	}
	
    public void setBinded(boolean binded) {
        this.binded = binded;
    }
    
    public boolean isBinded() {
        return binded;
    }    

    public void setUsername(String username) {
        this.username = username;
    }  
    
    public String getUsername() {
        return username;
    }  
    
    public void setKey(String key) {
        this.key = key;
    }  
    
    public String getKey() {
        return key;
    } 
    
    public void setPassword(String password) {
        this.password = password;
    }  
    
    public String getPassword() {
        return password;
    }    
    
    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }  
    
    public String getRepassword() {
        return repassword;
    }      
}
