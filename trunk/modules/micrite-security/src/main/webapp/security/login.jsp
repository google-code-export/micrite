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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<head>
	<title>登录</title>
	<link rel="stylesheet" type="text/css" href="../js-lib/ext-js/resources/css/ext-all.css">	
	<script type="text/javascript" src="../js-lib/ext-js/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="../js-lib/ext-js/ext-all.js"></script>
	<script>
	Ext.onReady(function(){
	    Ext.QuickTips.init();
	 
		// Create a variable to hold our EXT Form Panel. 
		// Assign various config options as seen.	 
	    var login = new Ext.FormPanel({ 
	        labelWidth:80,
	        url:'${pageContext.request.contextPath}/j_spring_security_check', 
	        frame:true, 
	        title:'Please Login', 
	        defaultType:'textfield',
		monitorValid:true,
		// Specific attributes for the text fields for username / password. 
		// The "name" attribute defines the name of variables sent to the server.
	        items:[{ 
	                fieldLabel:'Username', 
	                name:'j_username', 
	                allowBlank:false 
	            },{ 
	                fieldLabel:'Password', 
	                name:'j_password', 
	                inputType:'password', 
	                allowBlank:false 
	            }],
	 
		// All the magic happens after the user clicks the button     
	        buttons:[{ 
	                text:'登录',
	                formBind: true,	 
	                // Function that fires when user clicks the button 
	                handler:function(){ 
	                    login.getForm().submit({ 
	                        method:'POST', 
	                        waitTitle:'Connecting', 
	                        waitMsg:'Sending data...',
	 
	                        success:function(){ 
	                        	Ext.Msg.alert('Status', 'Login Successful!', function(btn, text){
					   if (btn == 'ok'){
			                       // var redirect = 'hello.jsp'; 
			                        //window.location = redirect;
			                        document.getElementById('loginForm').submit();
	                                   }
				        });
	                        },
	 
	 
	                        failure:function(form, action){ 
	                            if(action.failureType == 'server'){ 
	                                obj = Ext.util.JSON.decode(action.response.responseText); 
	                                Ext.Msg.alert('Login Failed!', obj.errors.reason); 
	                            }else{ 
	                                Ext.Msg.alert('Warning!', 'Authentication server is unreachable : ' + action.response.responseText); 
	                            } 
	                            login.getForm().reset(); 
	                        } 
	                    }); 
	                } 
	            }] 
	    });
	 
	 
		// This just creates a window to wrap the login form. 
		// The login object is passed to the items collection.       
	    var win = new Ext.Window({
	        layout:'fit',
	        width:300,
	        height:150,
	        closable: false,
	        resizable: false,
	        plain: true,
	        border: false,
	        items: [login]
		});
		win.show();
	});
	</script>
</head>

<body>
    <form method="post" id="loginForm" action="${pageContext.request.contextPath}">
 
    </form> 
</body>

</html> 