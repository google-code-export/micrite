<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>   
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="forgotPassword.step1.title"/></title>
</head>
<body>

<s:if test="username == null&&key == null">
	<b><s:text name="forgotPassword.step1.title"/></b>
	<br/>
	<br/>
    <s:property value="%{resultMap.message}" />
	<s:form action="forgotPasswordStepOne" namespace="/" >
	   	<s:textfield label="%{getText('forgotPassword.username')}" name="username"/>
	   	<s:submit value="%{getText('submit')}" />
	</s:form>
</s:if>
<s:elseif test="username != null&&key == null">
	<b><s:text name="forgotPassword.step1.title"/></b>
	<br/>
	<br/>
    <s:property value="%{resultMap.message}" />
</s:elseif>

<s:if test="password == null&&key !=null">
	<b><s:text name="forgotPassword.step2.title"/></b>
	<br/>
	<br/>
    <s:property value="%{resultMap.message}" />
    <s:if test="resultMap.success">	
		<s:form action="forgotPasswordStepTwo" namespace="/" >
		    <s:hidden name="key" value="%{key}" />
		   	<s:password label="%{getText('forgotPassword.password')}" name="password"/>
		   	<s:password label="%{getText('forgotPassword.repassword')}" name="repassword"/>
		   	<s:submit value="%{getText('submit')}" />
		</s:form>
	</s:if>
</s:if>
<s:elseif test="password != null&&key != null">
	<b><s:text name="forgotPassword.step2.title"/></b>
	<br/>
	<br/>
    <s:property value="%{resultMap.message}" />
</s:elseif>
</body>
</html>