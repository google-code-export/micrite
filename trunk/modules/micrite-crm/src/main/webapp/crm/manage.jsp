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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head><title>Micrite CRM</title></head>
<body>
<h1>Micrite CRM</h1>
<table cellpadding=3 border=0>
	<tr>
		<td><b>id</b></td>
		<td><b>Name</b></td>
		<td><b>Email</b></td>
		<td><b>Disabled</b></td>
	</tr>
<s:if test="users.size > 0">
	<s:iterator value="users">
		<tr id="row_<s:property value="id"/>">
			<td><s:property value="id" /></td>
			<td><s:property value="name" /></td>
			<td><s:property value="email" /></td>
			<s:url id="disabledUrl" action="disabled">
				<s:param name="id" value="id" />
			</s:url>
			<td><a href="%{disabledUrl}"><s:property value="disabled" /></a></td>
		</tr>
	</s:iterator>
</s:if>
</table>
<p><a href="<s:url value="add.htm"/>">Add</a></p>
</body>
</html>
