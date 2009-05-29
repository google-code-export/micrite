<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
/**
 * micrite.security.userDetailModify 修改用户信息
 * 
 * @author xingzhaoyong
 */
Ext.ns('micrite.security.userDetailModify');

//  FormPanel构造函数
micrite.security.userDetailModify.FormPanel = function() {
    //  “再次录入密码”校验器
    Ext.apply(Ext.form.VTypes, {
        passwordAgain: function(val, field) {
            var form = Ext.getCmp("userDetailModifyForm").getForm();
            var plainpassword = form.findField('plainpassword').getValue();
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

    //  这里定义Panel的外观，内部控件等
    micrite.security.userDetailModify.FormPanel.superclass.constructor.call(this, {
        id: 'userDetailModifyForm',
        bodyBorder: false,
        autoHeight:true,
        style: {"margin-top": "10px" },    
        items: [{
            xtype: 'fieldset',
            labelWidth: 120,
            title:this.userDetailModifyText,
            layout:'form',
            width: 350,
            defaults: {width: 170},    
            defaultType: 'textfield',
            autoHeight: true,
            style: {"margin-left": "10px"},
            items: [{
                name: 'id',
                xtype:'hidden'
            },{
                fieldLabel: this.fullnameText,
                name: 'fullname',
                allowBlank:false
            },{
                fieldLabel: this.emailaddressText,
                name: 'emailaddress',
                vtype: 'email'
            },{
                fieldLabel: this.loginnameText,
                name: 'loginname',
                disabled:true,
                allowBlank:false
            },{
                fieldLabel: this.passwordText,
                name: 'plainpassword',
                inputType: 'password'
            },{
                fieldLabel: this.passwordAgainText,
                name: 'plainpasswordAgain',
                inputType: 'password',
                vtype: 'passwordAgain'
            }]
        }],
        buttons: [{
            text: this.submitText,
            handler: function() {
	            var form = Ext.getCmp("userDetailModifyForm").getForm();
                //  构建form的提交参数
	            var params = {
	                    'user.id': form.findField('id').getValue(),
	                    'user.fullname': form.findField('fullname').getValue(),
	                    'user.emailaddress': form.findField('emailaddress').getValue(),
	                    'user.loginname': form.findField('loginname').getValue(),
	                    'user.plainpassword': form.findField('plainpassword').getValue()
	            };
                //  form提交
	            form.submit({
	                url: '/' + document.location.href.split("/")[3] + '/updateUserInfo.action',
	                method: 'POST',
	                disabled:true,
	                waitMsg: this.waitingMsg,
	                params: params,
	                success: function(form, action) {
	                    obj = Ext.util.JSON.decode(action.response.responseText);
	                    Ext.MessageBox.alert('Message', obj.result.message);	                
	                },
	                failure: function(form, action) {
	                    obj = Ext.util.JSON.decode(action.response.responseText);
	                    Ext.MessageBox.alert('Message', obj.result.message);
	                }
	            });
	        }
        },{
            text: this.cancelText
        }],
        buttonAlign:'left'
    });
};// FormPanel构造函数结束

//  页面上的字符串在这里定义
micrite.security.userDetailModify.FormPanel = Ext.extend(micrite.security.userDetailModify.FormPanel, 
                                                         Ext.FormPanel, 
                                                         {
														    userDetailModifyText: 'User Detail Modify',
														    fullnameText: 'Full Name',
														    emailaddressText: 'Email Address',
														    loginnameText: 'User Name',
														    passwordText: 'Password',
														    passwordAgainText: 'Password Again',
														    submitText: 'Save',
														    cancelText: 'Cancel',
														    waitingMsg: 'Saving Data...'
														});

Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    
    var formPanel = new micrite.security.userDetailModify.FormPanel();

    //  首先将表单上元素的默认值加载进来
    formPanel.form.load({url: '/' + document.location.href.split("/")[3] + '/loadCurrentUser.action'});
    
    if (mainPanel) {
        mainPanel.getActiveTab().add(formPanel);
        mainPanel.getActiveTab().doLayout();
    } else {
        new Ext.Viewport({layout:'fit', items:[formPanel]});
    }
});
</script>