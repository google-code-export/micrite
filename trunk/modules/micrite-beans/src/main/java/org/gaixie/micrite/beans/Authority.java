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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Micrite的一个授权资源，结合角色来实现系统安全策略。
 */
@Entity
@Table(name = "authorities")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Authority {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name", length = 255, nullable = false)  
    private String name;

    @Column(name = "type", length = 64, nullable = false)  
    private String type;
    
    @Column(name = "value", length = 255, nullable = false, unique = true) 
    private String value;

    @ManyToMany(targetEntity = Role.class)  
    @JoinTable(name = "role_authority_map", joinColumns = @JoinColumn(name = "authority_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Role> roles;

    /**
     * No-arg constructor for JavaBean tools.
     */
    public Authority() {

    }

    /**
     * Simple constructor
     */
    public Authority(String name,String type,String value) {
        this.name = name;
        this.type = type;     
        this.value = value;  
    }    
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Business Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//
    /**
     * 得到 <code>Authority</code> 被分配角色名称的字符串，并以 "," 分隔开。 
     * <p>
     * 如果访问一个 <code>URL</code> 需要拥有 <code>ROLE_ADMIN</code> 
     * 或者 <code>ROLE_USER</code> 角色，那么得到的字符串的值为：
     * </p>
     * <p>
     * <code>ROLE_ADMIN,ROLE_USER</code>
     * </p>
     */
    @Transient
    public String getRolesString() {
        List<String> rolesList = new ArrayList<String>();
        for (Role role : roles) {
            rolesList.add(role.getName());
        }
        return StringUtils.join(rolesList, ",");
    }    
}
