<%--
/* ===========================================================
 * $Id$
 * This file is part of Micrite
 * ===========================================================
 *
 * (C) Copyright 2009, by Gaixie.org and Contributors.
 * 
 * Project Info:  http://www.gaixie.org/wiki/100:Mainpage
 *
 * Micrite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Micrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Micrite.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
--%>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head><title>Contacts Security Demo</title></head>
<body>
<h1>Contacts Security Demo</h1>
<p>To demonstrate a public method on <code>ContactManager</code>,
here's a random <code>Contact</code>:
<p>
	<s:property value="user"/>
</p>
<br/>
<p><a HREF="<s:url value="manage.action"/>">用户管理</a>
<a href="<s:url value="secure/debug.jsp"/>">角色设置</a></p>
<br/>
<p><a href="../j_spring_security_logout">Logout</a>(also clears any remember-me cookie)</p>
</body>
</html>
