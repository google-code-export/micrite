package org.gaixie.micirte.common.search;

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
    private Object value;
    /**
     * 条件关系，包含：=, <, >, <=, >=, like, in
     */
    private String realtion;
    /**
     * 字段类型，包含：numeric, string, date, boolean
     */
    private String type;

    
    /**
     * @param name
     * @param realtion
     * @param type
     * @param value
     */
    public SearchBean(String name, Object value, String realtion, String type) {
        this.name = name;
        this.realtion = realtion;
        this.type = type;
        this.value = value;
    }
    
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
        .append("name", name)
        .append("value", value)
        .append("realtion", realtion)
        .append("type", type).toString();        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRealtion() {
        return realtion;
    }

    public void setRealtion(String realtion) {
        this.realtion = realtion;
    }
}
