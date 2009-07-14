<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 
    这里的Access denied与403.jsp不同，这里是表示因为你没有登录，所以access denied
    403.jsp表示如果你登录了，但是URL拦截认证失败
--%>
{'message':'Access denied or session expired, please <a id="session-expired" href="j_spring_security_logout">login</a>','success':false}