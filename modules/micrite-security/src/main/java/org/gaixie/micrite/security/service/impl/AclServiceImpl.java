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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gaixie.micrite.beans.AclClass;
import org.gaixie.micrite.beans.AclEntry;
import org.gaixie.micrite.beans.AclObjectIdentity;
import org.gaixie.micrite.beans.AclSid;
import org.gaixie.micrite.security.dao.IAclClassDAO;
import org.gaixie.micrite.security.dao.IAclEntryDAO;
import org.gaixie.micrite.security.dao.IAclObjectIdentityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.Acl;
import org.springframework.security.acls.AclService;
import org.springframework.security.acls.NotFoundException;
import org.springframework.security.acls.Permission;
import org.springframework.security.acls.domain.AccessControlEntryImpl;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclImpl;
import org.springframework.security.acls.domain.AuditLogger;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.objectidentity.ObjectIdentity;
import org.springframework.security.acls.sid.GrantedAuthoritySid;
import org.springframework.security.acls.sid.PrincipalSid;
import org.springframework.security.acls.sid.Sid;
import org.springframework.security.util.FieldUtils;

/**
 * 接口 <code>AclService</code> 的实现类。
 * 
 */
public class AclServiceImpl implements AclService {

    @Autowired
    private IAclObjectIdentityDAO aclObjectIdentityDAO;
    
    @Autowired
    private AclAuthorizationStrategy aclAuthorizationStrategy;
    
    @Autowired
    private AuditLogger auditLogger;
    
    @Autowired
    private IAclEntryDAO aclEntryDAO;
    
    @Autowired
    private IAclClassDAO aclClassDAO; 
    
    /* (non-Javadoc)
     * @see org.springframework.security.acls.AclService#findChildren(org.springframework.security.acls.objectidentity.ObjectIdentity)
     */
    public ObjectIdentity[] findChildren(ObjectIdentity parentIdentity) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.acls.AclService#readAclById(org.springframework.security.acls.objectidentity.ObjectIdentity)
     */
    public Acl readAclById(ObjectIdentity object) throws NotFoundException {
        return readAclById(object, null);
    }

    /* (non-Javadoc)
     * @see org.springframework.security.acls.AclService#readAclById(org.springframework.security.acls.objectidentity.ObjectIdentity, org.springframework.security.acls.sid.Sid[])
     */
    @SuppressWarnings("unchecked")
    public Acl readAclById(ObjectIdentity object, Sid[] sids)
            throws NotFoundException {
        Map map = readAclsById(new ObjectIdentity[] {object}, sids);
        return (Acl) map.get(object);
    }

    /* (non-Javadoc)
     * @see org.springframework.security.acls.AclService#readAclsById(org.springframework.security.acls.objectidentity.ObjectIdentity[])
     */
    @SuppressWarnings("unchecked")
    public Map readAclsById(ObjectIdentity[] objects) throws NotFoundException {
        return readAclsById(objects, null);
    }

    /* (non-Javadoc)
     * @see org.springframework.security.acls.AclService#readAclsById(org.springframework.security.acls.objectidentity.ObjectIdentity[], org.springframework.security.acls.sid.Sid[])
     */
    @SuppressWarnings("unchecked")
    public Map readAclsById(ObjectIdentity[] objects, Sid[] sids)
            throws NotFoundException {

        final Map acls = new HashMap(); 
        for (ObjectIdentity object :objects){
            // 根据访问的Object取的相应的acl
            // 取得访问的Object的className和id
            String javaType = object.getJavaType().getName();
            AclClass aclClass = aclClassDAO.findByClass(javaType);
            // No need to check for nulls, as guaranteed non-null by ObjectIdentity.getIdentifier() interface contract
            String identifier = object.getIdentifier().toString();
            long id = (Long.valueOf(identifier)).longValue();
            AclObjectIdentity aclObjectIdentity =  aclObjectIdentityDAO.findByObjectId(aclClass.getId(), id);
            // 如果访问的对象没有初始化acl，需要创建一个零时的acl，但aces为空，
            // spring security似乎没有对acl为空做判断
            if(aclObjectIdentity==null){
                AclImpl acl = new AclImpl(object, 0, 
                        aclAuthorizationStrategy, auditLogger, 
                        null, null, false, new PrincipalSid("admin"));
                acls.put(object, acl); 
                continue;
            }
            AclSid aclOwnerSid = aclObjectIdentity.getAclSid();
            Sid owner;

            if (aclOwnerSid.isPrincipal()) {
                owner = new PrincipalSid(aclOwnerSid.getSid());
            } else {
                owner = new GrantedAuthoritySid(aclOwnerSid.getSid());
            }
            AclImpl acl = new AclImpl(object, aclObjectIdentity.getId(), 
                                        aclAuthorizationStrategy, auditLogger, 
                                        null, null, false, owner);
            acls.put(object, acl);  
            
            Field acesField = FieldUtils.getField(AclImpl.class, "aces");
            List aces;

            try {
                acesField.setAccessible(true);
                aces = (List) acesField.get(acl);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("Could not obtain AclImpl.ace field: cause[" + ex.getMessage() + "]");
            }
            
            List<AclEntry> aclEntrys = aclEntryDAO.findByIdentityId(aclObjectIdentity.getId());
            
            for(AclEntry aclEntry:aclEntrys){
                AclSid aclSid = aclEntry.getAclSid();
                Sid recipient;
                if (aclSid.isPrincipal()) {
                    recipient = new PrincipalSid(aclSid.getSid());
                } else {
                    recipient = new GrantedAuthoritySid(aclSid.getSid());
                }  
                
                int mask = aclEntry.getMask();
                Permission permission = convertMaskIntoPermission(mask);
                boolean granting = aclEntry.isGranting();
                boolean auditSuccess = aclEntry.isAuditSuccess();
                boolean auditFailure = aclEntry.isAuditFailure();       
                
                AccessControlEntryImpl ace = new AccessControlEntryImpl(aclEntry.getId(), acl, recipient, permission, granting,
                        auditSuccess, auditFailure);       
                
                // Add the ACE if it doesn't already exist in the ACL.aces field
                 if (!aces.contains(ace)) {
                     aces.add(ace);
                 }                   
            }
       
        }
        return acls;
    }
    
    protected Permission convertMaskIntoPermission(int mask) {
        return BasePermission.buildFromMask(mask);
    }
   
}