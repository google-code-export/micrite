<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
Ext.namespace('micrite.security.authoritySelect');
micrite.security.authoritySelect.SearchPanel = Ext.extend(micrite.ComplexGrid, {
    onlyBinded:'Only Binded',
    addAuth:'Add Authority',
    name:'Name',
    type:'Type',
    value:'Value',

    initComponent:function() { 
        var sm = new Ext.grid.CheckboxSelectionModel();
        var config = {
			compSet:[{url:0,reader:0,columns:0,bbarAction:0}],
			searchFields :[[
	            this.name, {xtype:'textfield', name:'authority.name', width:120},
	            '',{xtype:'checkbox',boxLabel:this.onlyBinded, name:'binded'}
	        ]],
	        urls: ['findBindedAuths.action?roleId='+<%=request.getParameter("roleId")%>],
	        readers : [[
	            {name: 'name'},
	            {name: 'type'},        
	            {name: 'value'}
	        ]],
            columnsArray: [[
                {header: this.name, width: 100, sortable: true, dataIndex: 'name'},
                {header: this.type, width: 100, sortable: true, dataIndex: 'type'},        
                {header: this.value, width: 180, sortable: true, dataIndex: 'value'},       
	            sm
	        ]],	 
            tbarActions : [{
                text:this.addAuth,
                iconCls :'add-icon',
                scope:this,
                handler:this.addAuthFun
            }],
            bbarActions:[[{
                text:mbLocale.bindButton, 
                iconCls :'bind-icon',
                scope:this, 
                handler:this.bindAuthsFun
            },{
                text:mbLocale.unbindButton,
                iconCls :'unbind-icon',
                scope:this, 
                handler:this.unBindAuthsFun
            }]],
            sm:sm	                               
        }; // eo config object
        Ext.apply(this, Ext.apply(this.initialConfig, config)); 
        micrite.security.authoritySelect.SearchPanel.superclass.initComponent.apply(this, arguments);    
    },
    addAuthFun :function() {
        var win = micrite.util.genWindow({
            id: 'addAuthorityWindow',
            title    : this.addAuth,
            autoLoad : {url: 'security/authorityDetail.js',scripts:true},
            width    : 500,
            height   : 360
        });
    }, 
    bindAuthsFun:function() {
        //  选择的数据记录主键，形如“2, 4, 6, 10”
        var ids = this.selModel.selections.keys;
        if(ids.length<1){
            Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowSelectMsg);
            return;
        }        
        var bindAuths = function(buttonId, text, opt) {
            if (buttonId == 'yes') {
                micrite.util.ajaxRequest({
                    url:'bindAuths.action?roleId='+<%=request.getParameter("roleId")%>,
                    params:{'authIds':ids},                    
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
            fn: bindAuths,
            icon: Ext.MessageBox.QUESTION
        });    
    },    
    unBindAuthsFun:function() {
        //  选择的数据记录主键，形如“2, 4, 6, 10”
        var ids = this.selModel.selections.keys;
        if(ids.length<1){
            Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowSelectMsg);
            return;
        }       
        var unBindAuths = function(buttonId, text, opt) {
            if (buttonId == 'yes') {
                micrite.util.ajaxRequest({
                    url:'unBindAuths.action?roleId='+<%=request.getParameter("roleId")%>,
                    params:{'authIds':ids},                    
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
            fn: unBindAuths,
            icon: Ext.MessageBox.QUESTION
        });   
    }           
});

//  处理多语言
try {authoritySelectLocale();} catch (e) {}

Ext.onReady(function() {
    Ext.QuickTips.init();
//    var formPanel = new micrite.security.roleListSub.SearchPanel();
    Ext.WindowMgr.getActive().add(new micrite.security.authoritySelect.SearchPanel());
    Ext.WindowMgr.getActive().doLayout();    
});
</script>