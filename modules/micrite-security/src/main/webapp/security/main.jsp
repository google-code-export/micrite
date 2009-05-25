<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Welcome to Micrite!</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"
	href="../js-lib/ext-js/resources/css/ext-all.css" />
<script type="text/javascript" src="../js-lib/ext-js/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="../js-lib/ext-js/ext-all.js"></script>
<script type="text/javascript" src="main.js"></script>
<script type="text/javascript" src="../crm/customer.js"></script>
<style type="text/css">
#c3 {
	
}

#favo {
	margin-top: 10px;
}

#north {
	background-color: #356AA0;
}

#north p {
	line-height: 40px;
	text-indent: 15px;
	color: #CDEB8B;
}

#favo li {
	padding-left: 15px;
}

#favo li a {
	color: #666688;
	text-decoration: none;
	font-size: 10px;
}

.home {
	background-image: url(images/framework/home.gif) !important;
}

.menu {
	background-image: url(images/framework/menu.gif) !important;
}

.exit {
	background-image: url(images/framework/exit.gif) !important;
}

.email {
	background-image: url(images/framework/email.gif) !important;
}

.user {
	background-image: url(images/framework/user.gif) !important;
}
</style>
</head>
<body>
<div id="north">
<p></p>
</div>
<div id="center1">joker</div>
<div id="favo">
<ul>
	<li><a href="javascript:addTab()">CRM</a></li>
	<li><a href="javascript:addTab()">Security Manage</a></li>
</ul>
</div>
<div id="center2"></div>

<div id="c3"><input type="text" id="local-states" /></div>
<div id="c4" style="height:100%"></div>
</body>
</html>