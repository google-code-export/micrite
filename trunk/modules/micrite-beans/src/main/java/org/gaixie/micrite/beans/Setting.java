package org.gaixie.micrite.beans;
/**
 * 用户私有设置表对应
 */
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "setting")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Setting {
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	private String name;
	private String value;
	private int sortindex;
	private boolean enabled;
    
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
}
