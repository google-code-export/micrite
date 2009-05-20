<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
Ext.ns('micrite.security.userDetailModify');
FormPanel = function() {
    Ext.form.Field.prototype.msgTarget = 'side';

    FormPanel.superclass.constructor.call(this, {
        id: 'userDetailModifyForm',
        frame: false,
        labelAlign: 'left',
        header: false,
        border: false,
        bodyBorder: false,
        autoHeight:true,
        style: {
            "margin-top": "10px" 
        },    
        items: [{
            border:false
        },{
            xtype: 'fieldset',
            labelWidth: 90,
            title:this.userDetailModifyText,
            layout:'form',
            width: 300,
            defaults: {width: 150},    
            defaultType: 'textfield',
            autoHeight: true,
            style: {
                "margin-left": "10px" 
            },
            items: [{
                xtype:'hidden',
                id: 'modify.id',
                name: 'modify.id'
            },{
                id: 'modify.fullname',
                name: 'modify.fullname',
                fieldLabel: this.fullnameText
            },{
                id: 'modify.emailaddress',
                name: 'modify.emailaddress',
                fieldLabel: this.emailaddressText
            },{
                id: 'modify.loginname',
                name: 'modify.loginname',
                fieldLabel: this.loginnameText
            },{
                id: 'modify.plainpassword',
                name: 'modify.plainpassword',
                inputType: 'password',
                fieldLabel: this.passwordText
            },{
                id: 'modify.plainpasswordAgain',
                name: 'modify.plainpasswordAgain',
                inputType: 'password',
                fieldLabel: this.passwordAgainText
            }]
        }],
        reader: new Ext.data.JsonReader({
                    successProperty: 'success', 
                    root: 'data'                 
                }, [
		           {name: 'modify.id', mapping:'id'},
	               {name: 'modify.fullname', mapping:'fullname'},
	               {name: 'modify.emailaddress', mapping:'emailaddress'},
	               {name: 'modify.loginname', mapping:'loginname'}
	            ]),
        buttons: [{
            text: this.submitText,
            handler: function(){
            Ext.getCmp("userDetailModifyForm").getForm().submit({
                url: '/' + document.location.href.split("/")[3] + '/userModifyInfo.action',
                method: 'POST',
                disabled:true,
                waitMsg: this.waitingMsg,
                params:{
                    'user.id': Ext.getCmp('modify.id').getValue(),
                    'user.fullname': Ext.getCmp('modify.fullname').getValue(),
                    'user.emailaddress': Ext.getCmp('modify.emailaddress').getValue(),
                    'user.loginname': Ext.getCmp('modify.loginname').getValue(),
                    'user.plainpassword': Ext.getCmp('modify.plainpassword').getValue()
                },
                success: function(form, action){
                    Ext.MessageBox.alert('Message', 'Modify successed.');
                },
                failure: function(form, action){
                    Ext.MessageBox.alert('Message', 'Modify failed.');
                }
            });}                    
        },{
            text: this.cancelText
        }],
        buttonAlign:'left'
    });
}

micrite.security.userDetailModify.FormPanel=Ext.extend(FormPanel, Ext.FormPanel, {
    userDetailModifyText: 'User Detail Modify',
    fullnameText: 'Full Name',
    emailaddressText: 'Email Address',
    loginnameText: 'User Name',
    passwordText: 'Password',
    passwordAgainText: 'Password Again',
    submitText: 'Modify',
    cancelText: 'Cancel',
    waitingMsg: 'Saving Data...'
});

Ext.onReady(function() {
    Ext.QuickTips.init();
    var formPanel = new micrite.security.userDetailModify.FormPanel();
    formPanel.form.load({
        url: '/' + document.location.href.split("/")[3] + '/userGetCurrentUser.action',
        method:'GET'
    });
    if (mainPanel){
        mainPanel.getActiveTab().add(formPanel);
        mainPanel.getActiveTab().doLayout();
    }else{
        new Ext.Viewport({
            layout:'fit',
            items:[
                formPanel
            ]
        });
    }
});
</script>