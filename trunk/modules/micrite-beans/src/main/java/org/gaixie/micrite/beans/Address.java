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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Micrite客户地址。
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Address {

    @Id
    @GeneratedValue
    private Integer id;
    
    @Column(name = "state", length = 100, nullable = false)        
    private String state;
    
    @Column(name = "city", length = 100, nullable = false)      
    private String city;

    @Column(name = "street", length = 255, nullable = false)      
    private String street;
 
    @Column(name = "postcode", length = 20, nullable = false)      
    private String postcode;
    
    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    /**
     * No-arg constructor for JavaBean tools.
     */
    public Address() {

    }
    
    /**
     * Full constructor
     */
    public Address(String state, String city, String street, String postcode, Customer customer) {
        this.state = state;
        this.city = city;     
        this.street = street;  
        this.postcode = postcode; 
        this.customer = customer;         
    }  
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        final Address address = (Address) o;

        if (!state.equals(address.state)) return false;
        if (!city.equals(address.city)) return false;
        if (!street.equals(address.street)) return false;
        if (!postcode.equals(address.postcode)) return false;
        if (!customer.equals(address.customer)) return false;
        
        return true;
    }
    
    public String toString() {
        return  "Address ('" + getId() + "'), " +
                "state: '" + getState() + "'" +
                "city: '" + getCity() + "'" +
                "street: '" + getStreet() + "'";
    }
}
