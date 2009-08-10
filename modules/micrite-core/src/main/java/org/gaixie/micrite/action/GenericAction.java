package org.gaixie.micrite.action;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gaixie.micirte.common.search.SearchBean;
import org.gaixie.micirte.common.search.SearchFactory;

import com.opensymphony.xwork2.ActionSupport;

public class GenericAction extends ActionSupport {
    private static final long serialVersionUID = -6554298643237377735L;

    //以Map格式存放操作的结果，然后由struts2-json插件转换为json对象
    private Map<String,Object> resultMap = new HashMap<String,Object>();

    //  起始索引
    private int start;
    //  限制数
    private int limit;
    //  记录总数（分页中改变页码时，会传递该参数过来）
    private int totalCount;

    private SearchBean[] queryBean;
    
    public boolean isFirstSearch(){
        return totalCount == 0;
    }
    
    @SuppressWarnings("unchecked")
    public void putResultList(List data){
        resultMap.put("totalCount", totalCount);
        resultMap.put("success", true);
        resultMap.put("data", data);
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public SearchBean[] getQueryBean() {
        return queryBean;
    }

    public void setQueryString(String queryString) throws ParseException {
        this.queryBean = SearchFactory.getSearchTeam(queryString);
    }

}
