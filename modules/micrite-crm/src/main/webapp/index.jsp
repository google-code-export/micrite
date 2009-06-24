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
--%>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Micrite CRM ModuleTest Page!</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="js-lib/ext-js/resources/css/ext-all.css">
<script type="text/javascript" src="js-lib/ext-js/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="js-lib/ext-js/ext-all-debug.js"></script>
<link rel="stylesheet" type="text/css" href="js-lib/ext-ux-js/resources/css/micrite-all.css">
<link rel="stylesheet" type="text/css" href="js-lib/ext-ux-js/resources/css/DateTime.css">
<script type="text/javascript" src="js-lib/ext-ux-js/DateTime.js"></script>
<script type="text/javascript" src="js-lib/ext-ux-js/BaseLocale.js"></script>
<script type="text/javascript" src="js-lib/ext-ux-js/locale/micrite-base-lang-<%=request.getParameter("request_locale")%>.js"></script>
<script type="text/javascript" src="crm/locale/micrite-crm-lang-<%=request.getParameter("request_locale")%>.js"></script>
   
<script>
	var mainPanel = false;
</script>
</head>
<body>
Customer List -- 
<a href="index.jsp?moduleName=customerList&request_locale=zh_CN" >中文</a> |
<a href="index.jsp?moduleName=customerList&request_locale=en" >English</a>
<br>

<%if(("customerList").equals(request.getParameter("moduleName"))){%>
<%@ include file="crm/customerList.js"%>
<%}%>
</body>
</html>