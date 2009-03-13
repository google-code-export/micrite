<%--
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
<div style="width: 300px;border-style: solid">
	<p><b>查询用户</b></p>
	<s:form action="find" namespace="/crm" validate="false">
		<s:textfield id="telephone" label="手机号" name="telephone"/><s:submit value="查询" />
	</s:form>
	<s:if test="customers.size > 0">
		<table cellpadding=3 border=0>
			<tr>
				<td><b>id</b></td>
				<td><b>姓名</b></td>
				<td><b>电话</b></td>
				<td><b>客户</b></td>
				<td></td>
			</tr>
		<s:iterator value="customers">
			<tr id="row_<s:property value="id"/>">
				<td><s:property value="id" /></td>
				<td><s:property value="name" /></td>
				<td><s:property value="telephone" /></td>
				<td><s:property value="customerSource.name" /></td>
				<s:url id="editUrl" action="edit" namespace="/crm">
					<s:param name="customerId" value="id" />
				</s:url>
				<td><s:a href="%{editUrl}">修改</s:a></td>
			</tr>
		</s:iterator>
	</table>
	</s:if>
</div>

<br/>
<div style="width: 300px;border-style: solid">
	<p><b>增加/修改用户</b></p>
	<s:form action="save" namespace="/crm" validate="true">
		<p>已增加会员数：<font color="red"><b><s:property value="customerNum" /></b></font> 人</p>
		<s:textfield id="customer.id" name="customer.id" required="true" cssStyle="display:none"/>
		<s:textfield id="customer.name" label="姓名" required="true" name="customer.name"/>
		<s:textfield id="customer.telephone" label="手机号" required="true" name="customer.telephone"/>
		<s:select label="客户"
			list="customerSource" 
			name="customerSourceId"
	        listKey="id"
	        listValue="name"
	        required="true"
			/>
		<s:submit value="增加/修改" />
	</s:form>
</div>
</body>
</html>
