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

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Micrite应用的一个客户。
 */
@Entity
@Table(name = "customers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer extends AbstractSecureObject implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    
    @Column(name = "name", length = 255, nullable = false)        
    private String name;
    
    @Column(name = "salutation", length = 10)
    private String salutation;
 
    @Column(name = "title", length = 64)
    private String title;
 
    @Column(name = "company", length = 128)
    private String company;

    @Column(name = "phone_mobile", length = 20)
    private String phoneMobile;
   
    @Column(name = "phone_other", length = 20)
    private String phoneOther;

    @Column(name = "dont_call", nullable = false)
    private boolean dontCall=false;

    @Temporal(TemporalType.TIMESTAMP)    
    @Column(name = "birthday")
    private Date birthday;
 
    @Temporal(TemporalType.TIMESTAMP)    
    @Column(name = "delta_ts", nullable = false)
    private Date deltaTime;
    
    @Column(name = "notes" , length = 4000)
    private String notes;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "mapped_user_id")
    private User mappedUser;
    
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;    
    
    @Temporal(TemporalType.TIMESTAMP)    
    @Column(name = "creation_ts", nullable = false)    
    private Date creationTime;
    
    @ManyToOne(targetEntity = CustomerSource.class)
    @JoinColumn(name = "customer_source_id")
    private CustomerSource customerSource;

    /**
     * No-arg constructor for JavaBean tools
     */
    public Customer() {}
    
    /**
     * Full constructor
     */
    public Customer(String name,String phoneMobile,Date creationTime,CustomerSource customerSource) {
        this.name = name;
        this.phoneMobile = phoneMobile;
        this.creationTime = creationTime;
        this.deltaTime = creationTime;
        this.birthday = creationTime;
        this.customerSource = customerSource;        
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

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
  
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    
    public boolean isDontCall() {
        return dontCall;
    }
    public void setDontCall(boolean dontCall) {
        this.dontCall = dontCall;
    }
    
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public String getPhoneMobile() {
        return phoneMobile;
    }

    public void setPhoneMobile(String phoneMobile) {
        this.phoneMobile = phoneMobile;
    }

    public String getPhoneOther() {
        return phoneOther;
    }

    public void setPhoneOther(String phoneOther) {
        this.phoneOther = phoneOther;
    }
    
    public CustomerSource getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(CustomerSource customerSource) {
        this.customerSource = customerSource;
    }
    public User getMappedUser() {
        return mappedUser;
    }

    public void setMappedUser(User mappedUser) {
        this.mappedUser = mappedUser;
    }
    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }    
    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
    public Date getDeltaTime() {
        return deltaTime;
    }

    public void setDeltaTime(Date deltaTime) {
        this.deltaTime = deltaTime;
    }
}
