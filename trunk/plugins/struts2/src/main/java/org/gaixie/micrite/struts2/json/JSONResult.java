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

package org.gaixie.micrite.struts2.json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 将Action中的对象转化为JSON格式
 * 
 */
public class JSONResult implements Result {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String target;
    private String excludes;
    private boolean prettyPrint;

    private static final Log log = LogFactory.getLog(JSONResult.class);
    private ResponseWrapper out = new ResponseWrapper();

    public void setPrettyPrint(boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getExcludes() {
        return excludes;
    }

    public void setExcludes(String excludes) {
        this.excludes = excludes;
    }
    
    public ResponseWrapper getOut() {
        return out;
    }

    public void setOut(ResponseWrapper out) {
        this.out = out;
    }

    public void execute(ActionInvocation invocation) throws Exception {

        ActionContext actionContext = invocation.getInvocationContext();
        HttpServletResponse response = (HttpServletResponse) actionContext
                .get(StrutsStatics.HTTP_RESPONSE);
        HttpServletRequest request = (HttpServletRequest) actionContext
                .get(StrutsStatics.HTTP_REQUEST);
        if (this.prettyPrint){
            
        }
        Object targetObject = extractTargetObject(invocation);
        if (targetObject != null){
           Class type = targetObject.getClass();
           JsonConfig jsonConfig = new JsonConfig();  
           jsonConfig.setExcludes( StringUtils.split(excludes, ","));  
           JsNumberJsonValueProcessor jjvp = new JsNumberJsonValueProcessor();
           jsonConfig.registerJsonValueProcessor(java.lang.Long.class, jjvp);
            if (type.isArray() || type.getName().indexOf("List")!=-1||type.getName().indexOf("Set")!=-1) {
                JSONArray json = JSONArray.fromObject(targetObject,jsonConfig);
                out.writeResult(request, response, json.toString());
            }else{
                JSONObject json = JSONObject.fromObject(targetObject,jsonConfig);
                out.writeResult(request, response, json.toString());
            }
        }
    }

    /**
     * find the target object according the target property(parameter) if not
     * set, the action will be used.
     * 
     * @param invocation
     * @return
     */
    private Object extractTargetObject(ActionInvocation invocation) {
        Object targetObject;
        if (this.target != null) {
            ValueStack stack = invocation.getStack();
            targetObject = stack.findValue(this.target);
            if (log.isTraceEnabled()) {
                log.trace(String.format("Evaluate serializer target %s to %s.",
                        target, targetObject));
            }
        } else {
            targetObject = invocation.getAction();
            if (log.isTraceEnabled()) {
                log.trace("Using action instance as serializer target.");
            }
        }
        return targetObject;
    }
}
