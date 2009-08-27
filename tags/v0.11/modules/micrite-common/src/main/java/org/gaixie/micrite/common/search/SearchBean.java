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

package org.gaixie.micrite.common.search;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 用来保存自定义查询的相关数据。
 * <p>
 *
 */
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
