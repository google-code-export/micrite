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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * ACL领域对象实例相关信息。
 */
@Entity
@Table(name = "acl_object_identity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AclObjectIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne(targetEntity = AclClass.class)
    @JoinColumn(name = "object_id_class", nullable = false)
    private AclClass aclClass;

    @Column(name = "object_id_identity", nullable = false)
    private long objectId;

    @ManyToOne(targetEntity = AclObjectIdentity.class)
    @JoinColumn(name = "parent_object")
    private AclObjectIdentity parentAclObject;
    
    @ManyToOne(targetEntity = AclSid.class)
    @JoinColumn(name = "owner_sid")
    private AclSid aclSid;
    
    @Column(name = "entries_inheriting", nullable = false)
    private boolean inheriting;
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//     
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AclClass getAclClass() {
        return aclClass;
    }

    public void setAclClass(AclClass aclClass) {
        this.aclClass = aclClass;
    }
    
    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }  
    
    public AclObjectIdentity getParentAclObject() {
        return parentAclObject;
    }

    public void setParentAclObject(AclObjectIdentity parentAclObject) {
        this.parentAclObject = parentAclObject;
    }      
    
    public AclSid getAclSid() {
        return aclSid;
    }

    public void setAclSid(AclSid aclSid) {
        this.aclSid = aclSid;
    }    
    
    public boolean isInheriting() {
        return inheriting;
    }
    
    public void setInheriting(boolean inheriting) {
        this.inheriting = inheriting;
    }    
}
