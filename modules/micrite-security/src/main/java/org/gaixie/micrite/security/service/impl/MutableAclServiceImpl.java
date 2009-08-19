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

package org.gaixie.micrite.security.service.impl;

import org.gaixie.micrite.beans.AclClass;
import org.gaixie.micrite.beans.AclEntry;
import org.gaixie.micrite.beans.AclObjectIdentity;
import org.gaixie.micrite.beans.AclSid;
import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.security.dao.IAclClassDAO;
import org.gaixie.micrite.security.dao.IAclEntryDAO;
import org.gaixie.micrite.security.dao.IAclObjectIdentityDAO;
import org.gaixie.micrite.security.dao.IAclSidDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Authentication;
import org.springframework.security.acls.AccessControlEntry;
import org.springframework.security.acls.AlreadyExistsException;
import org.springframework.security.acls.ChildrenExistException;
import org.springframework.security.acls.MutableAcl;
import org.springframework.security.acls.MutableAclService;
import org.springframework.security.acls.NotFoundException;
import org.springframework.security.acls.Permission;
import org.springframework.security.acls.domain.AccessControlEntryImpl;
import org.springframework.security.acls.domain.AclImpl;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.objectidentity.ObjectIdentity;
import org.springframework.security.acls.objectidentity.ObjectIdentityImpl;
import org.springframework.security.acls.sid.PrincipalSid;
import org.springframework.security.context.SecurityContextHolder;

/**
 * 接口 <code>MutableAclService</code> 的实现类。
 * 
 */
public class MutableAclServiceImpl extends AclServiceImpl implements MutableAclService {

    @Autowired
    private IAclObjectIdentityDAO aclObjectIdentityDAO;
    
    @Autowired
    private IAclSidDAO aclSidDAO;    

    @Autowired
    private IAclClassDAO aclClassDAO;  

    @Autowired
    private IAclEntryDAO aclEntryDAO; 
    
    /* (non-Javadoc)
     * @see org.springframework.security.acls.MutableAclService#createAcl(org.springframework.security.acls.objectidentity.ObjectIdentity)
     */
    public MutableAcl createAcl(ObjectIdentity object)
            throws AlreadyExistsException {
       
        AclClass aclClass = new AclClass();
        aclClass.setCls(object.getJavaType().getName());
        aclClassDAO.save(aclClass);        
       
        // Need to retrieve the current principal, in order to know who "owns" this ACL (can be changed later on)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalSid sid = new PrincipalSid(auth);
        AclSid aclSid = new AclSid();
        aclSid.setSid(sid.getPrincipal());
        aclSid.setPrincipal(true);
        aclSidDAO.save(aclSid);
        System.out.println("aclSid:"+aclSid.getId());
        
        AclObjectIdentity aclObjectIdentity = new AclObjectIdentity();
        aclObjectIdentity.setAclClass(aclClass);
        aclObjectIdentity.setAclSid(aclSid);
        System.out.println(object.getIdentifier().toString());
        aclObjectIdentity.setObjectId(Integer.parseInt(object.getIdentifier().toString()));
        aclObjectIdentity.setInheriting(true);
        aclObjectIdentityDAO.save(aclObjectIdentity);
        System.out.println(sid.getPrincipal());
        System.out.println("aclObjectIdentity:"+aclObjectIdentity.getId());
        grantPermissions(1, "user", BasePermission.ADMINISTRATION);
        return null;
    }

    private void grantPermissions(int num, String recipientUsername, Permission permission) {
        AclImpl acl = (AclImpl) this.readAclById(new ObjectIdentityImpl(Role.class,new Long(num)));
        System.out.println(acl.getEntries().length);
        System.out.println(acl.getOwner());
        acl.insertAce(acl.getEntries().length, permission, new PrincipalSid(recipientUsername), true);
        createEntries(acl);
    }
    
    protected void createEntries(final MutableAcl acl) {

        for (AccessControlEntry entry_:acl.getEntries()){
            AccessControlEntryImpl entry = (AccessControlEntryImpl) entry_;
            AclEntry aclEntry = new AclEntry();
            aclEntry.setAclObject(aclObjectIdentityDAO.get(6l));
            aclEntry.setAceOrder(1);
            aclEntry.setAclSid(aclSidDAO.get(7l));
            aclEntry.setAuditFailure(entry.isAuditFailure());
            aclEntry.setAuditSuccess(entry.isAuditSuccess());
            aclEntry.setGranting(entry.isGranting());
            aclEntry.setMask(entry.getPermission().getMask());
            aclEntryDAO.save(aclEntry);
            System.out.println(entry.getPermission().getMask());
            System.out.println(entry.isGranting());
            System.out.println(entry.isAuditSuccess());   
            System.out.println(entry.isAuditFailure());              
        }
    }
    
    /* (non-Javadoc)
     * @see org.springframework.security.acls.MutableAclService#deleteAcl(org.springframework.security.acls.objectidentity.ObjectIdentity, boolean)
     */
    public void deleteAcl(ObjectIdentity objectIdentity, boolean deleteChildren)
            throws ChildrenExistException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.springframework.security.acls.MutableAclService#updateAcl(org.springframework.security.acls.MutableAcl)
     */
    public MutableAcl updateAcl(MutableAcl acl) throws NotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

}
