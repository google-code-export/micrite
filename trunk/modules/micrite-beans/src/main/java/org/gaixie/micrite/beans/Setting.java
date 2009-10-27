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

package org.gaixie.micrite.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Micrite的一个配置。
 */
@Entity
@Table(name = "setting")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Setting implements Comparable {

	@Id
    @GeneratedValue
    private Integer id;
	private String name;
	private String value;
	private int sortindex;
	private boolean enabled;
    
    /**
     * No-arg constructor for JavaBean tools.
     */
    public Setting() {

    }
    
    /**
     * Full constructor
     */
    public Setting(String name,String value,int sortindex,boolean enabled) {
        this.name = name;
        this.value = value;     
        this.sortindex = sortindex;  
        this.enabled = enabled; 
    }  
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public int getSortindex() {
		return sortindex;
	}
	public void setSortindex(int sortindex) {
		this.sortindex = sortindex;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        final Setting setting = (Setting) obj;
        return getId() == setting.getId();
	}
    
    public String toString() {
        return  "Setting ('" + getId() + "'), " +
                "Name: '" + getName() + "'" +
                "Value: '" + getValue() + "'";
    }
    
    public int compareTo(Object o) {
        if (o instanceof Setting) {
            int i = this.getName().compareTo(((Setting)o).getName());
            System.out.println("this.getName()="+this.getName());
            System.out.println("((Setting)o).getName()="+((Setting)o).getName());
            System.out.println("i="+i);
            if (i==0){
                return Integer.valueOf(this.getSortindex()).compareTo(
                        Integer.valueOf( ((Setting)o).getSortindex())
                       );
            }else
                return i;
        }
        return 0;
    }    
}
