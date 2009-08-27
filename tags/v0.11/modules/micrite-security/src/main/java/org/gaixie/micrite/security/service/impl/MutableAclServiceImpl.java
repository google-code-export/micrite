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

import org.gaixie.micrite.beans.AbstractSecureObject;
import org.gaixie.micrite.beans.AclClass;
import org.gaixie.micrite.beans.AclEntry;
import org.gaixie.micrite.beans.AclObjectIdentity;
import org.gaixie.micrite.beans.AclSid;
import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.security.dao.IAclClassDAO;
import org.gaixie.micrite.security.dao.IAclEntryDAO;
import org.gaixie.micrite.security.dao.IAclObjectIdentityDAO;
import org.gaixie.micrite.security.dao.IAclSidDAO;
import org.gaixie.micrite.security.service.ISecurityAclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Authentication;
import org.springframework.security.acls.AccessControlEntry;
import org.springframework.security.acls.Acl;
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
import org.springframework.security.acls.sid.GrantedAuthoritySid;
import org.springframework.security.acls.sid.PrincipalSid;
import org.springframework.security.acls.sid.Sid;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

/**
 * 接口 <code>MutableAclService</code> 的实现类。
 * 
 */
public class MutableAclServiceImpl extends AclServiceImpl implements ISecurityAclService {

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
    public MutableAcl createAcl(ObjectIdentity objectIdentity)
            throws AlreadyExistsException {
       
        AclObjectIdentity aclObjectIdentity = getAclObjectIdentity(objectIdentity);
        // Check this object identity hasn't already been persisted
        if (aclObjectIdentity != null) {
            throw new AlreadyExistsException("Object identity '" + aclObjectIdentity + "' already exists");
        }
        
        AclClass aclClass = aclClassDAO.findByClass(objectIdentity.getJavaType().getName());
       
        // Need to retrieve the current principal, in order to know who "owns" this ACL (can be changed later on)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalSid sid = new PrincipalSid(auth);
        
        
        AclSid aclSid = getOrCreateAclSid(sid,true);
        
        aclObjectIdentity = new AclObjectIdentity();
        aclObjectIdentity.setAclClass(aclClass);
        aclObjectIdentity.setAclSid(aclSid);

        aclObjectIdentity.setObjectId(Long.parseLong((objectIdentity.getIdentifier().toString())));
        aclObjectIdentity.setInheriting(true);
        aclObjectIdentityDAO.save(aclObjectIdentity);
        
        // Retrieve the ACL via superclass (ensures cache registration, proper retrieval etc)
        Acl acl = readAclById(objectIdentity);
        return (MutableAcl) acl;
    }

   
    protected void createEntries(final MutableAcl acl) {
        int i =1;
        for (AccessControlEntry entry_:acl.getEntries()){
            AccessControlEntryImpl entry = (AccessControlEntryImpl) entry_;
            AclEntry aclEntry = new AclEntry();
            long oid = ((Long) acl.getId()).longValue();
            aclEntry.setAclObject(aclObjectIdentityDAO.get(oid));
            aclEntry.setAceOrder(i);
            AclSid aclSid = getOrCreateAclSid(entry.getSid(),true);
            aclEntry.setAclSid(aclSid);
            aclEntry.setAuditFailure(entry.isAuditFailure());
            aclEntry.setAuditSuccess(entry.isAuditSuccess());
            aclEntry.setGranting(entry.isGranting());
            aclEntry.setMask(entry.getPermission().getMask());
            aclEntryDAO.save(aclEntry);
            i++;
        }
    }
    
    /* (non-Javadoc)
     * @see org.springframework.security.acls.MutableAclService#deleteAcl(org.springframework.security.acls.objectidentity.ObjectIdentity, boolean)
     */
    public void deleteAcl(ObjectIdentity objectIdentity, boolean deleteChildren)
            throws ChildrenExistException {
        // 根据访问的Object取的相应的acl
        // 取得访问的Object的className和id
        String javaType = objectIdentity.getJavaType().getName();
        AclClass aclClass = aclClassDAO.findByClass(javaType);
        // No need to check for nulls, as guaranteed non-null by ObjectIdentity.getIdentifier() interface contract
        String identifier = objectIdentity.getIdentifier().toString();
        long id = (Long.valueOf(identifier)).longValue();
        AclObjectIdentity aclObjectIdentity =  aclObjectIdentityDAO.findByObjectId(aclClass.getId(), id);
        aclEntryDAO.deleteByIdentityId(aclObjectIdentity.getId());
        aclObjectIdentityDAO.delete(aclObjectIdentity);
        
    }

    /* (non-Javadoc)
     * @see org.springframework.security.acls.MutableAclService#updateAcl(org.springframework.security.acls.MutableAcl)
     */
    public MutableAcl updateAcl(MutableAcl acl) throws NotFoundException {
        // Delete this ACL's ACEs in the acl_entry table
//        long oid = ((Long) acl.getId()).longValue();
        AclObjectIdentity aclo = getAclObjectIdentity(acl.getObjectIdentity());
        aclEntryDAO.deleteByIdentityId(aclo.getId());

        // Create this ACL's ACEs in the acl_entry table
        createEntries(acl);

        // Retrieve the ACL via superclass (ensures cache registration, proper retrieval etc)
        return (MutableAcl) super.readAclById(acl.getObjectIdentity());
    }

    public AclObjectIdentity getAclObjectIdentity(ObjectIdentity objectIdentity){
        // 根据访问的Object取的相应的acl
        // 取得访问的Object的className和id
        String javaType = objectIdentity.getJavaType().getName();
        AclClass aclClass = aclClassDAO.findByClass(javaType);
        // if not found aclClass, create it!
        if (aclClass == null){
            aclClass = new AclClass();
            aclClass.setCls(javaType);
            aclClassDAO.save(aclClass);    
        }
        // No need to check for nulls, as guaranteed non-null by ObjectIdentity.getIdentifier() interface contract
        String identifier = objectIdentity.getIdentifier().toString();
        long id = (Long.valueOf(identifier)).longValue();
        AclObjectIdentity aclObjectIdentity =  aclObjectIdentityDAO.findByObjectId(aclClass.getId(), id);
        return aclObjectIdentity;
        
    }
    
    public AclSid getOrCreateAclSid(Sid sid, boolean allowCreate){
        String sidName = null;
        boolean principal = true;

        if (sid instanceof PrincipalSid) {
            sidName = ((PrincipalSid) sid).getPrincipal();
        } else if (sid instanceof GrantedAuthoritySid) {
            sidName = ((GrantedAuthoritySid) sid).getGrantedAuthority();
            principal = false;
        } else {
            throw new IllegalArgumentException("Unsupported implementation of Sid");
        }
        
        AclSid aclSid = aclSidDAO.findBySid(sidName, principal);
        if(aclSid==null&&allowCreate){
            aclSid = new AclSid();
            aclSid.setSid(sidName);
            aclSid.setPrincipal(principal);
            aclSidDAO.save(aclSid);
        }
        return aclSid;
        
    }

    public void addPermission(AbstractSecureObject secureObject, Permission permission, Class clazz) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Sid recipient;
        if (auth.getPrincipal() instanceof UserDetails) {
            recipient =  new PrincipalSid(((UserDetails) auth.getPrincipal()).getUsername());
        } else {
            recipient =  new PrincipalSid(auth.getPrincipal().toString());
        }

        addPermission(secureObject, recipient, permission, clazz);
    }

    public void addPermission(AbstractSecureObject securedObject, Sid recipient, Permission permission, Class clazz) {
        MutableAcl acl;

        ObjectIdentity oid = new ObjectIdentityImpl(clazz.getCanonicalName(), securedObject.getId());

        try {
            acl = (MutableAcl) readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = createAcl(oid);
        }        
                                                                  
        acl.insertAce(acl.getEntries().length, permission, recipient, true);
        updateAcl(acl);
    } 
}
