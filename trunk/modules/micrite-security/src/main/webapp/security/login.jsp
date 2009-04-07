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
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<title>Micrite</title>
<link rel="stylesheet" type="text/css"
	href="../js-lib/ext-js/resources/css/ext-all.css" />
<script type="text/javascript" src="../js-lib/ext-js/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="../js-lib/ext-js/ext-all.js"></script>
<style>
.micrite {
	padding-top: 4px;
	padding-right: 550px;
}
</style>
<script type="text/javascript">
Ext.SSL_SECURE_URL = "resources/images/default/s.gif";
Ext.BLANK_IMAGE_URL = "resources/images/default/s.gif";

LoginWindow = function(config) {
	this.width = 400;
	this.height = 260;
	this.closable = false;
	this.title = this.titleText;
	this.bbar = [
			'->', {
				text :'<a href="http://micrite.gaixie.org" target="_blank">micrite.gaixie.org</a>',
				xtype :'tbtext'
			}];
	Ext.apply(this, config);
	LoginWindow.superclass.constructor.call(this);
}

Ext.namespace('micrite.security.framework');

micrite.security.framework.LoginWindow = Ext.extend(LoginWindow, Ext.Window, {
	// 类成员变量
    titleText:'Micrite Login',
    submitText:'Submit',
    cancelText:'Cancel',    
    usernameText:'Username:',
    passwordText:'Password:',
    
    // 类成员函数	
	onActionComplete : function(f, a) {
		window.location = 'main.jsp';
	},
	
	onActionFailed : function(f, a) {
		obj = Ext.util.JSON.decode(a.response.responseText);
		Ext.Msg.alert('登录失败!', obj.errorMsg.reason);
	},
	
	initComponent : function() {
		this.submitUrl = "../j_spring_security_check";
		this.loginPanel = new Ext.form.FormPanel( {
			frame :true,
			region :'center',
			id :'loginpanel',
			bodyStyle :'padding:10px',
			buttons : [ {
				text :this.submitText,
				handler :onSubmit
			}, {
				text :this.cancelText,
				handler :Ext.emptyFn
			} ],
			items : {
				layout :'form',
				labelWidth :100,
				layoutConfig : {
					labelSeparator :''
				},
				items : [ {
					fieldLabel :this.usernameText,
					labelStyle :'text-align:right',
					name :'j_username',
					xtype :'textfield',
					anchor :'80%'
				}, {
					fieldLabel :this.passwordText,
					labelStyle :'text-align:right',
					xtype :'textfield',
					inputType :'password',
					name :'j_password',
					anchor :'80%'
				} ]
			},
			listeners : {
				'actioncomplete' : {
					fn :this.onActionComplete,
					scope :this
				},
				'actionfailed' : {
					fn :this.onActionFailed,
					scope :this
				}
			},
			url :this.submitUrl
		});
		var form = this.loginPanel.getForm();
		function onSubmit() {
			form.submit( {
				reset :true
			});
		};

		this.layout = "border";
		this.border = false;
		this.items = [ {
			xtype :'panel',
			contentEl :'logo',
			region :'north',
			height :100
		}, this.loginPanel ];
		
		micrite.security.framework.LoginWindow.superclass.initComponent.call(this);
	}
});

Ext.onReady( function() {
	var loginWindow = new micrite.security.framework.LoginWindow();
	loginWindow.show();
});
</script>
<%if(session.getAttribute("WW_TRANS_I18N_LOCALE")!=null){%>
<script type="text/javascript" src="../security/locale/micrite-security-lang-<%=session.getAttribute("WW_TRANS_I18N_LOCALE")%>.js"></script>
<%}%>
</head>
<body>
<%
//out.println("Locale is :"+session.getAttribute("WW_TRANS_I18N_LOCALE"));
//out.println("Locale is :"+request.getParameter("request_locale"));
%>
<div id="logo"><img src="images/framework/login.jpg" /></div>
</body>
</html>