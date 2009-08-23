package org.gaixie.micrite.common.search;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SearchBean {

    /**
     * 字段名称
     */
    private String name;
    /**
     * 字段值
     */
    private String value;
    /**
     * 条件关系，包含：=, <, >, <=, >=, like, in
     */
    private String relation;

    
    /**
     * @param name
     * @param realtion
     * @param type
     * @param value
     */
    public SearchBean(String name, String value, String relation) {
        this.name = name;
        this.relation = relation;
        this.value = value;
    }
    
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
        .append("name", name)
        .append("value", value)
        .append("realtion", relation).toString();        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

}
