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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
	<title>Spring Security</title>
</head>

<body>

      
    <form method="post" id="loginForm" action="${pageContext.request.contextPath}/j_spring_security_check">
        <p>User Name：</p>       
        <p><input type="text" name="j_username" id="j_username" /></p>
        <p>Password：</p>     
        <p><input type="password" name="j_password" id="j_password" /></p>
        <p><input type="submit" value="submit" /></p>
    </form> 
      
</body>

</html> 