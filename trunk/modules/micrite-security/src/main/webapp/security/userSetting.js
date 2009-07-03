<script type="text/javascript">

Ext.ns('micrite.security.userSetting');


micrite.security.userSetting.FormPanel =  Ext.extend(Ext.form.FormPanel, {
	id:'micrite.security.userSetting.FormPanel',
	fullName : 'Full Name',
    email : 'E-mail',
    userName: 'User Name',
    password: 'Password',
    passwordRepeat: 'Password Repeat',
	userInformation:'User Information',
	settings: 'Settings',
	skin : 'Skin',
	rowsPerPage : 'Rows Per Page',
	confirmPassword: 'Passwords do not match',
	initComponent:function() {
	    var config = {
	    	labelWidth: 150,
	    	frame : true,
	    	monitorValid:true,
	    	style:'padding:1px',
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
		                allowBlank:false
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
			        		 	'settings.name' :'RowsPerPage'
			            	 }
			            	 }),
			            displayField:'value',
			           	valueField:'id',
			           	id:'RowsPerPage',
			            hiddenId :'h_RowsPerPage',
			           	hiddenName:'settings.id',
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
			        		 	'settings.name' :'Skin'
			            	 }
			            	 }),
			            displayField:'value',
			           	valueField:'id',
			           	id:'Skin',
			           	hiddenId :'h_Skin',
			           	hiddenName:'settings.id',
			            triggerAction:'all',
			            fieldLabel:this.skin,
			            selectOnFocus:true
			        	})
			        ]
	    		},{
	    			buttonAlign:'center',
	    			buttons: [{
	    		        text: mbLocale.submitButton,
	    		        scope:this,
	    		        formBind:true,
	    		        handler:function() {
	    			    	this.getForm().submit({
	    			            url: '/' + document.location.href.split("/")[3] + '/updateUserInfo.action',
	    			            method: 'POST',
	    			            waitMsg: mbLocale.waitingMsg,
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
		    micrite.security.userSetting.FormPanel.superclass.initComponent.apply(this, arguments);
	
			//  “再次录入密码”校验器
			Ext.apply(Ext.form.VTypes, {
			    passwordAgain: function(val, field) {
			        var form = Ext.getCmp("micrite.security.userSetting.FormPanel").getForm();
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
			    passwordAgainText: this.confirmPassword
			});
		},// eo funtion initComponent
		onRender:function(){
	    micrite.security.userSetting.FormPanel.superclass.onRender.apply(this, arguments);
		this.form.load({
		        url: '/' + document.location.href.split("/")[3] + '/loadCurrentUser.action',
		        success:function(f,a){
		            Ext.each(a.result.settings,function(o,i){
		                Ext.getCmp(o.name).setRawValue(o.value);
		                Ext.fly('h_'+Ext.getCmp(o.name).getId()).dom.value=o.id;
		            });
		        }
		    });
		}

});

try{ userSettingLocale(); } catch(e){}
try {baseLocale();} catch (e) {}

Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    
    var formPanel = new micrite.security.userSetting.FormPanel();
    if (mainPanel) {
        mainPanel.getActiveTab().add(formPanel);
        mainPanel.getActiveTab().doLayout();
    } else {
        new Ext.Viewport({layout:'fit', items:[formPanel]});
    }
});
</script>