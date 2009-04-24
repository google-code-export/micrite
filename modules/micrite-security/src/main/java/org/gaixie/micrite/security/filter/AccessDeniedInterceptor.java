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

package org.gaixie.micrite.security.filter;

import java.lang.reflect.Method;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.security.AccessDeniedException;

/**
 * 控制权限的AOP拦截器抛出AccessDeniedException后，进行相应后续处理
 * 
 *
 */
public class AccessDeniedInterceptor implements ThrowsAdvice {

    public void afterThrowing(Method method, Object[] args, Object target,
            AccessDeniedException exception) {
        System.out.println("access denied.....");
        //TODO 通过DatabaseMethodDefinitionSource拒绝访问后的处理
    }
}
