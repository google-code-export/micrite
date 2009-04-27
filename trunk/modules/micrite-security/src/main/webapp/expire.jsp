<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Welcome to Micrite!</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="js-lib/ext-js/resources/css/ext-all.css">
    <script type="text/javascript" src="js-lib/ext-js/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="js-lib/ext-js/ext-all-debug.js"></script>
<script>
Ext.onReady(function(){
	var a = Ext.get('expired');
	a.on('click',function(){
		window.location='j_spring_security_logout';
		})
})

</script>
</head>
<body>
<%-- 
    这里的Access denied与403.jsp不同，这里是表示因为你没有登录，所以access denied
    403.jsp表示如果你登录了，但是URL拦截认证失败
--%>
Access denied or session expired, please <a id="expired" href="#">login</a>.
</body>
</html>