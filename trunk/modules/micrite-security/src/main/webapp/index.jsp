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
<meta content="text/html; charset=utf-8" http-equiv="Content-Type"/>
<title>Welcome to Micrite</title>
<style type="text/css">
#ct-wrap { text-align:center; }
#ct { text-align:left; margin:auto; width:400px; }
#center { float:left; width: 200px;}
#east { float:right; clear:right; width: 200px;}
body { font-size:13px; vertical-align:middle; margin-top: 20%; padding:0;}
.font{ text-decoration:none; font-size: 42px;}
.red{ color:red; }
.blue{ color:blue; }
#center a:hover{ color:white; background-color:red;}
#east a:hover{ color:white; background-color: blue;}
</style>
</head>

<body>
	<div id="ct-wrap">
		<div id="ct">
			<div id="center"><a href="login.action?request_locale=zh_CN" class="font red">中文简体</a></div>
			<div id="east"><a href="login.action?request_locale=en" class="font blue">English</a></div>
		</div>
	</div>
</body>
</html>