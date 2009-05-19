<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
Ext.ns('micrite.security.userDetail');
FormPanel = function() {
    Ext.form.Field.prototype.msgTarget = 'side';

    var recordDef = Ext.data.Record.create([
        {name: 'id'},{name: 'name'}                   
    ]); 
    var dataStore = new Ext.data.Store({    
        autoLoad:true,
        //设定读取的地址
        proxy: new Ext.data.HttpProxy({url: '/' + document.location.href.split("/")[3] + '/userGetAllRoleList.action'}),    
        //设定读取的格式    
        reader: new Ext.data.JsonReader({    
            id:"id"
        }, recordDef),
        remoteSort: true
    });
    
    FormPanel.superclass.constructor.call(this, {
        id: 'userDetailForm',
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
            title:this.userDetailText,
            layout:'form',
            width: 300,
            defaults: {width: 150},    
            defaultType: 'textfield',
            autoHeight: true,
            style: {
                "margin-left": "10px" 
            },
            items: [{
                id: 'fullname',
                fieldLabel: this.fullnameText
            },{
                id: 'emailaddress',
                fieldLabel: this.emailaddressText
            },{
                id: 'loginname',
                fieldLabel: this.loginnameText
            },{
                id: 'plainpassword',
                fieldLabel: this.passwordText
            },{
                xtype:'multiselect',
                id:"userRoles",
                fieldLabel:this.rolesText,
                dataFields:["id", "name"], 
                store: dataStore,
                valueField:"id",
                displayField:"name",
                width:150,
                height:120,
                allowBlank:true,
                legend:"Multiselect"
           }]
       }],
        buttons: [{
            text: this.submitText,
            handler: function(){
            Ext.getCmp("userDetailForm").getForm().submit({
                url: '/' + document.location.href.split("/")[3] + '/userAdd.action',
                method: 'POST',
                disabled:true,
                waitMsg: this.waitingMsg,
                params:{
                    'user.fullname': Ext.getCmp('fullname').getValue(),
                    'user.emailaddress': Ext.getCmp('emailaddress').getValue(),
                    'user.loginname': Ext.getCmp('loginname').getValue(),
                    'user.plainpassword': Ext.getCmp('plainpassword').getValue(),
                    'userRolesStr': Ext.getCmp('userRoles').getValue()
                },
                success: function(form, action){
                    Ext.MessageBox.alert('Message', 'Plan saved.');
                },
                failure: function(form, action){
                    Ext.MessageBox.alert('Message', 'Save failed.');
                }
            });}                    
        },{
            text: this.cancelText
        }],
        buttonAlign:'left'
    });
    
}

micrite.security.userDetail.FormPanel=Ext.extend(FormPanel, Ext.FormPanel, {
    userDetailText: 'User Detail',
    fullnameText: 'Full Name',
    emailaddressText: 'Email Address',
    loginnameText: 'User Name',
    passwordText: 'Password',
    rolesText: 'Roles',
    submitText: 'Save',
    cancelText: 'Cancel',
    waitingMsg: 'Saving Data...'
});

Ext.onReady(function() {
    Ext.QuickTips.init();
    var formPanel = new micrite.security.userDetail.FormPanel();
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