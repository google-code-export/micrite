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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * ACL授予者权限。
 */
@Entity
@Table(name = "acl_entry")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AclEntry {

    @Id
    @GeneratedValue
    private long id;
    
    @ManyToOne(targetEntity = AclObjectIdentity.class)
    @JoinColumn(name = "acl_object_identity", nullable = false)
    private AclObjectIdentity aclObject;
    
    @Column(name = "ace_order", nullable = false)
    private Integer aceOrder;
    
    @ManyToOne(targetEntity = AclSid.class)
    @JoinColumn(name = "sid", nullable = false)
    private AclSid aclSid;
    
    @Column(name = "mask", nullable = false)
    private Integer mask;
    
    @Column(name = "granting", nullable = false)
    private boolean granting;

    @Column(name = "audit_success", nullable = false)
    private boolean auditSuccess;
    
    @Column(name = "audit_failure", nullable = false)
    private boolean auditFailure;    
    
    /**
     * No-arg constructor for JavaBean tools.
     */
    public AclEntry() {
        
    }

    /**
     * Full constructor
     */
    public AclEntry(AclObjectIdentity aclObject,Integer aceOrder,AclSid aclSid,
            Integer mask,boolean granting,boolean auditSuccess,boolean auditFailure) {
        this.aclObject = aclObject;
        this.aceOrder = aceOrder;
        this.aclSid = aclSid;
        this.mask = mask;
        this.granting = granting;    
        this.auditSuccess = auditSuccess;    
        this.auditFailure = auditFailure;            
    }  
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//     
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AclObjectIdentity getAclObject() {
        return aclObject;
    }

    public void setAclObject(AclObjectIdentity aclObject) {
        this.aclObject = aclObject;
    }      

    public Integer getAceOrder() {
        return aceOrder;
    }

    public void setAceOrder(Integer aceOrder) {
        this.aceOrder = aceOrder;
    }    
    
    public AclSid getAclSid() {
        return aclSid;
    }

    public void setAclSid(AclSid aclSid) {
        this.aclSid = aclSid;
    }  
    
    public Integer getMask() {
        return mask;
    }

    public void setMask(Integer mask) {
        this.mask = mask;
    }
    
    public boolean isGranting() {
        return granting;
    }
    
    public void setGranting(boolean granting) {
        this.granting = granting;
    }  
    
    public boolean isAuditSuccess() {
        return auditSuccess;
    }
    
    public void setAuditSuccess(boolean auditSuccess) {
        this.auditSuccess = auditSuccess;
    }   
    
    public boolean isAuditFailure() {
        return auditFailure;
    }
    
    public void setAuditFailure(boolean auditFailure) {
        this.auditFailure = auditFailure;
    }     
}
