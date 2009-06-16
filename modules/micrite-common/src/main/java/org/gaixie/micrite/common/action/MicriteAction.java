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

package org.gaixie.micrite.common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Micrite的公用Action类。
 */
//@ManagedResource(objectName="micrite:type=action,name=UserAction", description="Micrite UserAction Bean")
public class MicriteAction extends ActionSupport {

    private static final long serialVersionUID = 5843976660199930680L;

    private static final Logger logger = Logger.getLogger(MicriteAction.class);

    //  以下两个分页用
    //  起始索引
    private int start;
    //  限制数
    private int limit;
    //  记录总数（分页中改变页码时，会传递该参数过来）
    private int totalCount;
    
    private String msg;
    
    private boolean successed = false;
    
    //  action处理结果（map对象）
    private Map<String,Object> resultMap = new HashMap<String,Object>();
    
    public void genResultForUpdate() {
        resultMap.put("success", successed);
        resultMap.put("message", msg);
    }

    public void genResultForLoad(Object data) {
        if(!successed) resultMap.put("message", msg);
        resultMap.put("data", data);
        resultMap.put("success", successed);
    }
    
    public void genResultForFind(Object data) {
        if(!successed) resultMap.put("message", msg);
        resultMap.put("data", data);
        resultMap.put("totalCount", totalCount);
        resultMap.put("success", successed);
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    

    public void setStart(int start) {
        this.start = start;
    }

    public int getStart() {
        return start;
    }
    
    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        logger.debug("limit111=" + limit);
        return limit;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setMsg(boolean successed, String msg) {
        this.successed = successed;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
    
    public boolean getSuccessed() {
        return successed;
    }
    
    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }
    
    public Map<String, Object> getResultMap() {
        return resultMap;
    }
}
