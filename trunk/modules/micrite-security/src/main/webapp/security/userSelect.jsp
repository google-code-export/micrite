<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
Ext.namespace('micrite.security.userSelect');
micrite.security.userSelect.SearchPanel = Ext.extend(micrite.ComplexGrid, {
    onlyBinded:'Only Binded',
    addUser:'Add User',
    fullName:'Full Name',
    emailAddress:'Email Address',
    enabled:'Enabled',

    initComponent:function() { 
        var sm = new Ext.grid.CheckboxSelectionModel();
        var config = {
			compSet:[{url:0,reader:0,columns:0,bbarAction:0}],
			searchFields :[[
	            this.fullName, {xtype:'textfield', name:'user.fullname', width:120},
	            '',{xtype:'checkbox',boxLabel:this.onlyBinded, name:'binded'}
	        ]],
	        urls: ['/security/findBindedUsers.action?roleId='+<%=request.getParameter("roleId")%>],
	        readers : [[
	            {name: 'fullname'},
	            {name: 'emailaddress'},
	            {name: 'enabled'}
	        ]],
            columnsArray: [[
	            {header: this.fullName, width: 100, sortable: true, dataIndex: 'fullname'},
	            {header: this.emailAddress, width: 180, sortable: true, dataIndex: 'emailaddress'},
	            {header: this.enabled, width: 180, sortable: true, dataIndex: 'enabled'},        
	            sm
	        ]],	 
            tbarActions : [{
                text:this.addUser,
                iconCls :'add-icon',
                scope:this,
                handler:this.addUserFun
            }],
            bbarActions:[[{
                text:mbLocale.bindButton, 
                iconCls :'bind-icon',
                scope:this, 
                handler:this.bindUsersFun
            },{
                text:mbLocale.unbindButton,
                iconCls :'unbind-icon',
                scope:this, 
                handler:this.unBindUsersFun
            }]],
            sm:sm	                               
        }; // eo config object
        Ext.apply(this, Ext.apply(this.initialConfig, config)); 
        micrite.security.userSelect.SearchPanel.superclass.initComponent.apply(this, arguments);    
    },
    addUserFun :function() {
        var win = micrite.util.genWindow({
            id: 'addUserWindow',
            title    : this.addUser,
            autoLoad : {url: this.urlPrefix + '/security/userDetail.js',scripts:true},
            width    : 500,
            height   : 360
        });
    }, 
    bindUsersFun:function() {
        //  选择的数据记录主键，形如“2, 4, 6, 10”
        var ids = this.selModel.selections.keys;
        if(ids.length<1){
            Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowSelectMsg);
            return;
        }        
        var bindUsers = function(buttonId, text, opt) {
            if (buttonId == 'yes') {
                micrite.util.ajaxRequest({
                    url:this.urlPrefix + '/bindUsers.action?roleId='+<%=request.getParameter("roleId")%>,
                    params:{'userIds':ids},
                    scope:this,
                    success:Ext.emptyFn,
                    failure:Ext.emptyFn
                },this);
            }
        };
        Ext.Msg.show({
            title:mbLocale.infoMsg,
            msg: mbLocale.bindConfirmMsg,
            buttons: Ext.Msg.YESNO,
            scope: this,
            fn: bindUsers,
            icon: Ext.MessageBox.QUESTION
        });    
    },    
    unBindUsersFun:function() {
        //  选择的数据记录主键，形如“2, 4, 6, 10”
        var ids = this.selModel.selections.keys;
        if(ids.length<1){
            Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowSelectMsg);
            return;
        }       
        var unBindUsers = function(buttonId, text, opt) {
            if (buttonId == 'yes') {
                micrite.util.ajaxRequest({
                    url:this.urlPrefix + '/unBindUsers.action?roleId='+<%=request.getParameter("roleId")%>,
                    params:{'userIds':ids},
                    scope:this,
                    success:Ext.emptyFn,
                    failure:Ext.emptyFn
                },this);
            }
        };
        Ext.Msg.show({
            title:mbLocale.infoMsg,
            msg: mbLocale.unbindConfirmMsg,
            buttons: Ext.Msg.YESNO,
            scope: this,
            fn: unBindUsers,
            icon: Ext.MessageBox.QUESTION
        });   
    }           
});
//  处理多语言
try {userSelectLocale();} catch (e) {}

Ext.onReady(function() {
    Ext.QuickTips.init();
//    var formPanel = new micrite.security.roleListSub.SearchPanel();
    Ext.WindowMgr.getActive().add(new micrite.security.userSelect.SearchPanel());
    Ext.WindowMgr.getActive().doLayout();
});
</script>