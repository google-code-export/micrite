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
import org.gaixie.micrite.beans.Authority;
import org.gaixie.micrite.security.SecurityException;
import org.gaixie.micrite.security.service.IAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 用来响应用户对授权资源操作的事件。
 * <p>
 * 通过调用相关的Service类，完成对Authority基本信息的增加，删除，修改，查询。
 */
public class AuthorityAction extends ActionSupport{ 
	private static final long serialVersionUID = 1721180911011412346L;

	private static final Logger logger = Logger.getLogger(AuthorityAction.class);
	
	@Autowired
	private IAuthorityService authorityService;

    //以Map格式存放操作的结果，然后由struts2-json插件转换为json对象
    private Map<String,Object> resultMap = new HashMap<String,Object>();
    
    //获取的页面参数
    private Authority authority;

    //  以下两个分页用
    //  起始索引
    private int start;
    //  限制数
    private int limit;
    //  记录总数（分页中改变页码时，会传递该参数过来）
    private int totalCount;
    
    private String roleIds;
    private String[] authIds;
    private boolean binded;
    
    private String strAuthIds;

    // ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    /**
     * 保存授权资源信息
     * @return "success"
     */
    public String save() {
    	authorityService.add(authority);
    	resultMap.put("message", getText("save.success"));
        resultMap.put("success", true);
        return SUCCESS;
    }
    
    public String delete() {
    	logger.debug("strAuthIds =" + strAuthIds);
        String[] ids = StringUtils.split(strAuthIds, ",");
        try {
        	authorityService.delete(ids);
            resultMap.put("message", getText("delete.success"));
            resultMap.put("success", true);
        } catch(SecurityException e) {
            resultMap.put("message", getText(e.getMessage()));
            resultMap.put("success", false);
            logger.warn(getText(e.getMessage()));            
        }
        return SUCCESS;
    }
    
    /**
     * 修改角色。
     * 
     * @return "success"
     */
    public String update() {
        try {
        	authorityService.update(authority);
            resultMap.put("message", getText("save.success"));
            resultMap.put("success", true);
        } catch(SecurityException e) {
            resultMap.put("message", getText(e.getMessage()));
            resultMap.put("success", false);
            logger.error(getText(e.getMessage()));
        }
        return SUCCESS;
    }    

    public String findByNameVague() {
        String name = authority.getName();
        if (totalCount == 0) {
            //  初次查询时，要从数据库中读取总记录数
            Integer count = authorityService.findByNameVagueCount(name);
            setTotalCount(count);
        } 
        //  得到分页查询结果
        logger.debug("name="+name);
        logger.debug("totalCount="+totalCount);
        List<Authority> auths = authorityService.findByNameVaguePerPage(name, start, limit);
        logger.debug("auths="+auths.size());
        resultMap.put("totalCount", totalCount);
        resultMap.put("success", true);
        resultMap.put("data", auths);
        return SUCCESS;
    }
    
    public String findBindedAuths() {
        
        String[] rIds = StringUtils.split(roleIds, ",");
        if(!binded) return findByNameVague();

        if (totalCount == 0) {
            //  初次查询时，要从数据库中读取总记录数
            Integer count = authorityService.findAuthsByRoleIdCount(Integer.parseInt(rIds[0]));
            setTotalCount(count);
        } 
        
        List<Authority> auths = authorityService.findAuthsByRoleId(Integer.parseInt(rIds[0]), start, limit);
        resultMap.put("totalCount", totalCount);    
        resultMap.put("success", true);
        resultMap.put("data", auths);

        return SUCCESS;
    }

    public String bindAuths() {
        String[] rIds = StringUtils.split(roleIds, ",");
        authorityService.bindAuths(authIds,Integer.parseInt(rIds[0]));
        resultMap.put("message", getText("save.success"));
        resultMap.put("success", true);
        return SUCCESS;
    }
    
    public String unBindAuths() {
        String[] rIds = StringUtils.split(roleIds, ",");
        authorityService.unBindAuths(authIds,Integer.parseInt(rIds[0]));
        resultMap.put("message", getText("save.success"));
        resultMap.put("success", true);
        return SUCCESS;
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    

	/**
	 * @return the result
	 */
	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	/**
	 * @param result the result to set
	 */
	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
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

    public void setStart(int start) {
        this.start = start;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    
    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
    
    public void setAuthIds(String[] authIds) {
        this.authIds = authIds;
    }
    
    public void setBinded(boolean binded) {
        this.binded = binded;
    }
    
    public boolean isBinded() {
        return binded;
    }

	/**
	 * @return the strAuthIds
	 */
	public String getStrAuthIds() {
		return strAuthIds;
	}

	/**
	 * @param strAuthIds the strAuthIds to set
	 */
	public void setStrAuthIds(String strAuthIds) {
		this.strAuthIds = strAuthIds;
	}  
}
