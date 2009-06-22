<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
/**
 * micrite.security.userDetailModify 修改用户信息
 * 
 * @author xingzhaoyong
 */
Ext.ns('micrite.security');


micrite.security.userSetting =  Ext.extend(Ext.form.FormPanel, {
	id:'micrite.security.userform',
	fullName : 'Full Name',
    email : 'E-mail',
    userName: 'User Name',
    password: 'Password',
    passwordRepeat: 'Password Repeat',
	userInformation:'User Information',
	settings: 'settings',
	theme : 'Theme',
	rowsPerPage : 'Rows Per Page',
	initComponent:function() {
	    var config = {
	    	labelWidth: 150,
	    	frame : true,
	    	monitorValid:true,
	    	width: 350,
	    	items: [{
	        	xtype:'fieldset',
	        	title: this.userInformation,
	        	collapsible: true,
		        autoHeight:true,
		        defaults: {width: 210},
		        defaultType: 'textfield',
		        items :[{
		                name: 'user.id',
		                xtype:'hidden'
	            	},{
		                fieldLabel: this.fullName,
		                name: 'user.fullname',
		                allowBlank:false
		            },{
		                fieldLabel: this.email,
		                name: 'user.emailaddress',
		                vtype:'email'
		            },{
		                fieldLabel: this.userName,
		                name: 'user.loginname',
		                disabled : true
		            }, {
		                fieldLabel: this.password,
		                name: 'user.plainpassword',
		                inputType: 'password'
		            }, {
		                fieldLabel: this.passwordRepeat,
		                id:'rePassword',
		                name: 'plainpasswordAgain',
		                inputType: 'password',
		                vtype: 'passwordAgain'
		            }
		        ]
		    },{
		        xtype:'fieldset',
		        title: this.settings,
		        collapsible: true,
		        autoHeight:true,
		        defaults: {width: 210},
		        defaultType: 'textfield',
		        items :[
		        	new Ext.form.ComboBox({
			        	 store:new Ext.data.JsonStore({
			            	 fields:[
			    	        	  {name:'id', type:'int'},
			    	        	  {name:'value', type:'int'}
			            	 ],
			            	 url:'/' + document.location.href.split("/")[3] + '/loadSetting.action',
			            	 baseParams:{
			        		 	'setting.name' :'RowsPerPage'
			            	 }
			            	 }),
			            displayField:'value',
			           	valueField:'id',
			           	id:'RowsPerPage',
			            hiddenId :'h_RowsPerPage',
			           	hiddenName:'setting.id',
			            triggerAction:'all',
			            fieldLabel: this.rowsPerPage,
			            selectOnFocus:true
			        	}),
		        	new Ext.form.ComboBox({
			        	 store:new Ext.data.JsonStore({
			            	 fields:[
			    	        	  {name:'id', type:'int'},
			    	        	  {name:'value', type:'string'}
			            	 ],
			            	 url:'/' + document.location.href.split("/")[3] + '/loadSetting.action',
			            	 baseParams:{
			        		 	'setting.name' :'Theme'
			            	 }
			            	 }),
			            displayField:'value',
			           	valueField:'id',
			           	id:'Theme',
			           	hiddenId :'h_Theme',
			           	hiddenName:'setting.id',
			            triggerAction:'all',
			            fieldLabel:this.theme,
			            selectOnFocus:true
			        	})
			        ]
	    		},{
	    			buttonAlign:'center',
	    			buttons: [{
	    		        text: 'Save',
	    		        scope:this,
	    		        formBind:true,
	    		        style: 'text-align:center',
	    		        handler:function() {
	    			    	this.getForm().submit({
	    			            url: '/' + document.location.href.split("/")[3] + '/updateUserInfo.action',
	    			            method: 'POST',
	    			            waitMsg: 'wating...',
	    			            success: function(form, action) {
	    			                obj = Ext.util.JSON.decode(action.response.responseText);
	    			                showMsg('success', obj.message);	 
	    			            },
	    			            failure: function(form, action) {
	    			                obj = Ext.util.JSON.decode(action.response.responseText);
	    			                showMsg('failure', obj.message);
	    			            }
	    			    	})
	    				} 
	    		    }]
		    	}]
	    	}; // eof config object
		    // apply config
		    Ext.apply(this, Ext.apply(this.initialConfig, config));
		    // call parent
		    micrite.security.userSetting.superclass.initComponent.apply(this, arguments);
	
			//  “再次录入密码”校验器
			Ext.apply(Ext.form.VTypes, {
			    passwordAgain: function(val, field) {
			        var form = Ext.getCmp("micrite.security.userform").getForm();
			        var plainpassword = form.findField('user.plainpassword').getValue();
			        if (val != plainpassword) {
			            return false;
			        } else {
			            return true;
			        }
			    },
			});
			//  处理“再次录入密码”校验未通过时显示的提示信息
			Ext.apply(Ext.form.VTypes, {
			    passwordAgainText: '请确保两次输入的密码相同'
			});
		}// eo funtion initComponent
});


Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    
    var formPanel = new micrite.security.userSetting();

    //  首先将表单上元素的默认值加载进来
    formPanel.form.load({
        	url: '/' + document.location.href.split("/")[3] + '/loadCurrentUser.action',
        	success:function(f,a){
				Ext.each(a.result.settings,function(o,i){
					Ext.getCmp(o.name).setRawValue(o.value);
					Ext.fly('h_'+Ext.getCmp(o.name).getId()).dom.value=o.id;
				});
			}
        });
    
    if (mainPanel) {
        mainPanel.getActiveTab().add(formPanel);
        mainPanel.getActiveTab().doLayout();
    } else {
        new Ext.Viewport({layout:'fit', items:[formPanel]});
    }
});
</script>