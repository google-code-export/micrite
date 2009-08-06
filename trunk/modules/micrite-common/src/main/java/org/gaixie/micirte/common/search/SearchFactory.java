package org.gaixie.micirte.common.search;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

public class SearchFactory {
    
    /**
     * @param args
     * @throws ParseException 
     */
    public static void main(String[] args) throws ParseException {
        String str = "[name,yubo,like,string],[telephone,13810770810,=,string],[createDate,2009-08-02 00:00,>=,date],[interface_,1;2;3,in,numeric]";
        String[] detach = detach(str, '[', ']');
        for(String s: detach)
            System.out.println("detach =" + s);
        System.out.println("--------------------------");
        SearchBean[] search = getSearchTeam(str);
        for(SearchBean s: search)
            System.out.println("search =" + s.toString());
    }

    /**
     * @param scarchBunch 格式：
     * [name,value,relation,type],[name,value,relation,type],[name,value,relation,type]
     * name:    字段名称
     * value:   字段值
     * relation:条件关系，包含：=, <, >, <=, >=, like, in
     * type:    字段类型，包含：numeric, string, date, boolean
     * @return
     */
    public static SearchBean[] getSearchTeam(String scarchBunch) throws ParseException{
        String[] team = detach(scarchBunch, '[', ']');
        if(team== null)
            return null;
        SearchBean[] search = new SearchBean[team.length];
        for(int i = 0; i < team.length; i++){
            String[] element = StringUtils.split(team[i], ',');
            if(element == null || element.length != 4)
                throw new ParseException("Unable to parse the string: " + scarchBunch, -1);
            Object value;
            if(element[3].equals("date")){
                if(element[2].equals("<") || element[2].equals("<=")){
                    if(element[1].length() == 10)
                        element[1] += " 23:59:59";
                    else
                        element[1] += ":59";
                }
                else{
                    if(element[1].length() == 10)
                        element[1] += " 00:00:00";
                    else
                        element[1] += ":00";
                }
                value = DateUtils.parseDate(element[1], new String[] { "yy-MM-dd hh:mm:ss" });
            }
            else{
                if(element[2].equals("like")){
                    value = "%" + element[1] + "%";
                }
                else if(element[2].equals("in")){
                    value = StringUtils.split(element[1], ';');
                }
                else
                    value = element[1];
            }
            search[i] = new SearchBean(element[0], value, element[2], element[3]);
        }
        return search;
    }
    
    @SuppressWarnings("unchecked")
    public static DetachedCriteria generateCriteria(Class entity, SearchBean[] searchBean) throws ParseException{
        DetachedCriteria criteria = DetachedCriteria.forClass(entity);
        if(searchBean == null || searchBean.length == 0)
            return criteria;
        for(int i = 0; i < searchBean.length; i++){
            if(searchBean[i].equals("="))
                criteria.add(Expression.eq(searchBean[i].getName(), searchBean[i].getValue()));
            else if(searchBean[i].equals("<"))
                criteria.add(Expression.lt(searchBean[i].getName(), searchBean[i].getValue()));
            else if(searchBean[i].equals("<="))
                criteria.add(Expression.le(searchBean[i].getName(), searchBean[i].getValue()));
            else if(searchBean[i].equals(">"))
                criteria.add(Expression.gt(searchBean[i].getName(), searchBean[i].getValue()));
            else if(searchBean[i].equals("<="))
                criteria.add(Expression.ge(searchBean[i].getName(), searchBean[i].getValue()));
            else if(searchBean[i].equals("like"))
                criteria.add(Expression.like(searchBean[i].getName(), searchBean[i].getValue()));
            else if(searchBean[i].equals("in"))
                criteria.add(Expression.in(searchBean[i].getName(), (String[])searchBean[i].getValue()));
        }
        return criteria;
    }
    
    private static String[] detach(String str, char left, char right) throws ParseException{
        String string = str;
        if(string == null)
            return null;
        List<String> list = new ArrayList<String>();
        if(StringUtils.indexOf(string, left) == -1 || StringUtils.indexOf(string, right) == -1)
            throw new ParseException("Unable to parse the string: " + string, -1);
        while(StringUtils.indexOf(string, left) >= 0 && StringUtils.indexOf(string, right) >= 0){
            int il = StringUtils.indexOf(string, left);
            int ir = StringUtils.indexOf(string, right);
            if(il > ir){
                string = StringUtils.substring(string, right + 1);
                continue;
            }
            list.add(StringUtils.substring(string, il + 1, ir));
            string = StringUtils.substring(string, ir + 1);
        }
        return list.toArray(new String[list.size()]);
    }

}
