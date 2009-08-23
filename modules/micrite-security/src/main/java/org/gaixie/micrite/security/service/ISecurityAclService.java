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
package org.gaixie.micrite.security.service;

import org.gaixie.micrite.beans.AbstractSecureObject;
import org.springframework.security.acls.MutableAclService;
import org.springframework.security.acls.Permission;
import org.springframework.security.acls.sid.Sid;

/**
 * 提供SecurityAcl权限操作的接口
 *
 */
public interface ISecurityAclService extends MutableAclService{

    /**
     * 将指定对象实例授权给当前用户。
     * 
     * @param securedObject 受保护的对象实例
     * @param permission 分配的权限
     * @param clazz 类名 如 Role.class
     */  
    @SuppressWarnings("unchecked")
    public void addPermission(AbstractSecureObject securedObject, Permission permission, Class clazz);
    
    /**
     * 将指定对象实例授权给指定用户或角色。
     * 
     * @param securedObject 受保护的对象实例
     * @param recipient 接受权限的sid
     * @param permission 分配的权限
     * @param clazz 类名 如 Role.class
     */      
    @SuppressWarnings("unchecked")
    public void addPermission(AbstractSecureObject securedObject, Sid recipient, Permission permission, Class clazz);

}
