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

import java.util.HashSet;

import org.gaixie.micrite.beans.AclClass;
import org.gaixie.micrite.beans.AclEntry;
import org.gaixie.micrite.beans.AclObjectIdentity;
import org.gaixie.micrite.beans.AclSid;
import org.gaixie.micrite.beans.Authority;
import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.beans.Setting;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.dao.IAclClassDAO;
import org.gaixie.micrite.security.dao.IAclEntryDAO;
import org.gaixie.micrite.security.dao.IAclObjectIdentityDAO;
import org.gaixie.micrite.security.dao.IAclSidDAO;
import org.gaixie.micrite.security.dao.IAuthorityDAO;
import org.gaixie.micrite.security.dao.IRoleDAO;
import org.gaixie.micrite.security.dao.ISettingDAO;
import org.gaixie.micrite.security.dao.IUserDAO;
import org.gaixie.micrite.security.filter.FilterSecurityInterceptor;
import org.gaixie.micrite.security.filter.MethodSecurityInterceptor;
import org.gaixie.micrite.security.service.ISecDataPrepareService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Security数据初始化接口实现
 * @see org.gaixie.micrite.security.service.ISecDataPrepareService
 */
public class SecDataPrepareServiceImpl implements ISecDataPrepareService { 

    @Autowired
    private IUserDAO userDAO;
    @Autowired
    private IRoleDAO roleDAO;
    @Autowired
    private IAuthorityDAO authorityDAO;
    @Autowired
    private ISettingDAO settingDAO;
    @Autowired
    private IAclSidDAO aclSidDAO;
    @Autowired
    private IAclClassDAO aclClassDAO;
    @Autowired
    private IAclObjectIdentityDAO aclObjectIdentityDAO;
    @Autowired
    private IAclEntryDAO aclEntryDAO;
    
    @SuppressWarnings("serial")
    public void initDataForRun() {
        if(settingDAO.findSettingByName("RowsPerPage")!=null) return;
        
        //insert  into setting(name,value,sortindex) values ('RowsPerPage','20',0);
        //insert  into setting(name,value,sortindex) values ('RowsPerPage','100',1);
        //insert  into setting(name,value,sortindex) values ('Skin','Blue',0);
        //insert  into setting(name,value,sortindex) values ('Skin','Gray',1);
        Setting setting = new Setting("RowsPerPage","20",0,true);
        settingDAO.save(setting);
        setting = new Setting("RowsPerPage","100",1,true);
        settingDAO.save(setting);
        setting = new Setting("Skin","Blue",0,true);
        settingDAO.save(setting);        
        setting = new Setting("Skin","Gray",1,true);
        settingDAO.save(setting);
        
        //着三个角色为系统默认角色，不要修改此脚本，甚至是名称
        //insert  into roles(name,description) values ('ROLE_ADMIN','系统管理员，拥有全部模块权限');
        //insert  into roles(name,description) values ('ROLE_USER','基本用户，无管理权限');
        //insert  into roles(name,description) values ('AFTER_ACL_COLLECTION_READ','如果此角色有绑定的方法，会对方法调用后返回的结果集进行ACL过滤');
        final Role roleAdmin = new Role("ROLE_ADMIN","系统管理员，拥有全部模块权限");
        roleDAO.save(roleAdmin);
        
        final Role roleUser = new Role("ROLE_USER","基本用户，无管理权限");
        roleDAO.save(roleUser);
        
        final Role roleAcl = new Role("AFTER_ACL_COLLECTION_READ","如果此角色有绑定的方法，会对方法调用后返回的结果集进行ACL过滤");
        roleDAO.save(roleAcl);

        //insert  into authorities(name,type,value) values ('All URL','URL','/**');
        //insert  into authorities(name,type,value) values ('/Security Modules/User List','URL','/security/userList.js*');
        //insert  into authorities(name,type,value) values ('/Security Modules/Authority List','URL','/security/authorityList.js*');
        //insert  into authorities(name,type,value) values ('/Security Modules/Role List','URL','/security/roleList.js*');
        //insert  into authorities(name,type,value) values ('/CRM Modules/Customer List','URL','/crm/customerList.js*');
        Authority authority = new Authority("All URL","URL","/**");
        authority.setRoles(new HashSet<Role>(){{add(roleAdmin);add(roleUser);}});
        authorityDAO.save(authority);
        
        authority = new Authority("/Security Modules/User List","URL","/security/userList.js*");
        authority.setRoles(new HashSet<Role>(){{add(roleAdmin);add(roleUser);}});
        authorityDAO.save(authority);

        authority = new Authority("/Security Modules/Authority List","URL","/security/authorityList.js*");
        authority.setRoles(new HashSet<Role>(){{add(roleAdmin);add(roleUser);}});
        authorityDAO.save(authority);
        
        authority = new Authority("/Security Modules/Role List","URL","/security/roleList.js*");
        authority.setRoles(new HashSet<Role>(){{add(roleAdmin);add(roleUser);}});
        authorityDAO.save(authority);
        
        authority = new Authority("/CRM Modules/Customer List","URL","/crm/customerList.js*");
        authority.setRoles(new HashSet<Role>(){{add(roleAdmin);add(roleUser);}});
        authorityDAO.save(authority);
        
        //insert  into authorities(name,type,value) values ('Role List Search Method protect','METHOD','* org.gaixie.micrite.security.service.IRoleService.*PerPage(..)');
        //insert  into authorities(name,type,value) values ('Role Bind Method protect','METHOD','* org.gaixie.micrite.security.service.I*Service.*bind*(..)');
        //insert  into authorities(name,type,value) values ('Role unBind Method protect','METHOD','* org.gaixie.micrite.security.service.I*Service.*unBind*(..)');

        //insert  into authorities(name,type,value) values ('add action of security module protect','METHOD','* org.gaixie.micrite.security.service.I*Service.add*(..)');
        //insert  into authorities(name,type,value) values ('update action of security module protect','METHOD','* org.gaixie.micrite.security.service.I*Service.update*(..)');
        //insert  into authorities(name,type,value) values ('delete action of security module protect','METHOD','* org.gaixie.micrite.security.service.I*Service.delete*(..)');
        //insert  into authorities(name,type,value) values ('update me method protect','METHOD','* org.gaixie.micrite.security.service.IUserService.updateMe(..)');
        authority = new Authority("Role List Search Method protect","METHOD","* org.gaixie.micrite.security.service.IRoleService.*PerPage(..)");
        authority.setRoles(new HashSet<Role>(){{add(roleAdmin);add(roleUser);add(roleAcl);}});
        authorityDAO.save(authority);        
        
        authority = new Authority("Role Bind Method protect","METHOD","* org.gaixie.micrite.security.service.I*Service.*bind*(..)");
        authority.setRoles(new HashSet<Role>(){{add(roleAdmin);}});
        authorityDAO.save(authority);
        
        authority = new Authority("Role unBind Method protect","METHOD","* org.gaixie.micrite.security.service.I*Service.*unBind*(..)");
        authority.setRoles(new HashSet<Role>(){{add(roleAdmin);}});
        authorityDAO.save(authority);
        
        authority = new Authority("add action of security module protect","METHOD","* org.gaixie.micrite.security.service.I*Service.add*(..)");
        authority.setRoles(new HashSet<Role>(){{add(roleAdmin);}});
        authorityDAO.save(authority);
        
        authority = new Authority("update action of security module protect","METHOD","* org.gaixie.micrite.security.service.I*Service.update*(..)");
        authority.setRoles(new HashSet<Role>(){{add(roleAdmin);}});
        authorityDAO.save(authority);

        authority = new Authority("delete action of security module protect","METHOD","* org.gaixie.micrite.security.service.I*Service.delete*(..)");
        authority.setRoles(new HashSet<Role>(){{add(roleAdmin);}});
        authorityDAO.save(authority);

        authority = new Authority("update me method protect","METHOD","* org.gaixie.micrite.security.service.IUserService.updateMe(..)");
        authority.setRoles(new HashSet<Role>(){{add(roleAdmin);add(roleUser);}});
        authorityDAO.save(authority);
        
        //insert  into userbase(fullname,loginname,cryptpassword,emailaddress,enabled) values ('Administrator','admin','e10adc3949ba59abbe56e057f20f883e','administrator@micrite.org',1);
        //insert  into userbase(fullname,loginname,cryptpassword,emailaddress,enabled) values ('Tommy Wang','user','e10adc3949ba59abbe56e057f20f883e','tommywang@micrite.org',1);
        User user = new User("Administrator","admin","e10adc3949ba59abbe56e057f20f883e","test1.micrite@gmail.com",true);
        user.setRoles(new HashSet<Role>(){{add(roleAdmin);}});
        userDAO.save(user);
        
        user = new User("Tommy Wang","user","e10adc3949ba59abbe56e057f20f883e","micritetest2@163.com",true);
        user.setRoles(new HashSet<Role>(){{add(roleUser);}});
        userDAO.save(user);
        
        //insert into acl_sid(principal,sid) values (0,'ROLE_ADMIN');
        //insert into acl_sid(principal,sid) values (0,'ROLE_USER');
        
        AclSid aclSidAdmin = new AclSid(false,"ROLE_ADMIN");
        aclSidDAO.save(aclSidAdmin); 
        
        AclSid aclSidUser = new AclSid(false,"ROLE_USER");
        aclSidDAO.save(aclSidUser);        
        
        //insert into acl_class(class) values ('org.gaixie.micrite.beans.Role');
        AclClass aclClass = new AclClass("org.gaixie.micrite.beans.Role");
        aclClassDAO.save(aclClass); 
        
        //insert into acl_object_identity(object_id_class,object_id_identity,owner_sid,entries_inheriting) 
        //values (1,1,1,1);
        //insert into acl_object_identity(object_id_class,object_id_identity,owner_sid,entries_inheriting) 
        //values (1,2,1,1);
        //insert into acl_object_identity(object_id_class,object_id_identity,owner_sid,entries_inheriting) 
        //values (1,3,1,1);
        AclObjectIdentity ao1 = new AclObjectIdentity(aclClass,roleAdmin.getId(),aclSidAdmin,true);
        aclObjectIdentityDAO.save(ao1);   
        AclObjectIdentity ao2 = new AclObjectIdentity(aclClass,roleUser.getId(),aclSidAdmin,true);
        aclObjectIdentityDAO.save(ao2);  
        AclObjectIdentity ao3 = new AclObjectIdentity(aclClass,roleAcl.getId(),aclSidAdmin,true);
        aclObjectIdentityDAO.save(ao3);  
        
        //insert into acl_entry(acl_object_identity,ace_order,sid,mask,granting,audit_success,audit_failure) 
        //values (1,1,1,16,1,0,0);
        //insert into acl_entry(acl_object_identity,ace_order,sid,mask,granting,audit_success,audit_failure) 
        //values (2,1,1,16,1,0,0);  
        //insert into acl_entry(acl_object_identity,ace_order,sid,mask,granting,audit_success,audit_failure) 
        //values (2,2,2,16,1,0,0); 
        //insert into acl_entry(acl_object_identity,ace_order,sid,mask,granting,audit_success,audit_failure) 
        //values (3,1,1,16,1,0,0); 
        
        AclEntry aclEntry = new AclEntry(ao1,1,aclSidAdmin,16,true,false,false);
        aclEntryDAO.save(aclEntry);   
        aclEntry = new AclEntry(ao2,1,aclSidAdmin,16,true,false,false);
        aclEntryDAO.save(aclEntry);   
        aclEntry = new AclEntry(ao2,2,aclSidUser,16,true,false,false);
        aclEntryDAO.save(aclEntry);   
        aclEntry = new AclEntry(ao3,1,aclSidAdmin,16,true,false,false);
        aclEntryDAO.save(aclEntry);   
        
        FilterSecurityInterceptor.refresh();
        MethodSecurityInterceptor.refresh();
    }
}
