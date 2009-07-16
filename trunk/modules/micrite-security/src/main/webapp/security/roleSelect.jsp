<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
Ext.namespace('micrite.security.roleSelect');
micrite.security.roleSelect.SearchPanel = Ext.extend(micrite.ComplexGrid, {
    addRole:'Add Role',
    onlyBinded:'Only Binded',
    roleName:'Role Name',
    roleDescription:'Role Description',

    initComponent:function() { 
        //  查询请求的url
        var searchURL = '';
        if (<%=(request.getParameter("userId") != null)%>) {
            searchURL = '/security/findRolesByUser.action?userId=' + <%=request.getParameter("userId")%>;
        } else {
            searchURL = '/security/findRolesByAuthority.action?authorityId=' + <%=request.getParameter("authorityId")%>;
        }
    
        var sm = new Ext.grid.CheckboxSelectionModel();
        var config = {
            compSet:[{url:0,reader:0,columns:0,bbarAction:0}],
            searchFields :[[
                this.roleName, {xtype:'textfield', name:'role.name', width:120},
                '',{xtype:'checkbox',boxLabel:this.onlyBinded, name:'binded'}
            ]],
            urls: [searchURL],
            readers : [[
                {name: 'name'},
                {name: 'description'}
            ]],
            columnsArray: [[
                {header: this.roleName, width: 100, sortable: true, dataIndex: 'name'},
                {header: this.roleDescription, width: 180, sortable: true, dataIndex: 'description'},
                sm
            ]],
            tbarActions : [{
                text:this.addRole,
                iconCls :'add-icon',
                scope:this,
                handler:this.addRoleFun
            }],
            bbarActions:[[{
                text:mbLocale.bindButton, 
                iconCls :'bind-icon',
                scope:this, 
                handler:this.bindRolesFun
            },{
                text:mbLocale.unbindButton,
                iconCls :'unbind-icon',
                scope:this, 
                handler:this.unBindRolesFun
            }]],
            sm:sm
        }; // eo config object
        Ext.apply(this, Ext.apply(this.initialConfig, config)); 
        micrite.security.roleSelect.SearchPanel.superclass.initComponent.apply(this, arguments);    
    },
    addRoleFun :function() {
        var win = micrite.util.genWindow({
            id: 'addRoleWindow',
            title    : this.addRole,
            autoLoad : {url: this.urlPrefix + '/security/roleDetail.js',scripts:true},
            width    : 500,
            height   : 360
        });
    }, 
    bindRolesFun:function() {
        //  选择的数据记录主键，形如“2, 4, 6, 10”
        var roleIds = this.selModel.selections.keys;
        if(roleIds.length < 1){
            Ext.MessageBox.alert(mbLocale.infoMsg, mbLocale.gridMultRowSelectMsg);
            return;
        }
        //  绑定请求的url
        var bindURL = '';
        if (<%=(request.getParameter("userId") != null)%>) {
            bindURL = '/bindRolesToUser.action?userId='+<%=request.getParameter("userId")%>;
        } else {
            bindURL = '/bindRolesToAuthority.action?authorityId='+<%=request.getParameter("authorityId")%>;
        }
   
        var submitFun = function(buttonId, text, opt) {
            if (buttonId == 'yes') {
                micrite.util.ajaxRequest({
                    url:this.urlPrefix + bindURL,
                    params:{'roleIds':roleIds},
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
            fn: submitFun,
            icon: Ext.MessageBox.QUESTION
        });
    },    
    unBindRolesFun:function() {
        //  选择的数据记录主键，形如“2, 4, 6, 10”
        var roleIds = this.selModel.selections.keys;
        if(roleIds.length < 1){
            Ext.MessageBox.alert(mbLocale.infoMsg, mbLocale.gridMultRowSelectMsg);
            return;
        }
        
        //  解除绑定请求的url
        var unBindURL = '';
        if (<%=(request.getParameter("userId") != null)%>) {
            unBindURL = '/unBindRolesFromUser.action?userId='+<%=request.getParameter("userId")%>;
        } else {
            unBindURL = '/unBindRolesFromAuthority.action?authorityId='+<%=request.getParameter("authorityId")%>;
        }
   
        var submitFun = function(buttonId, text, opt) {
            if (buttonId == 'yes') {
                micrite.util.ajaxRequest({
                    url:this.urlPrefix + unBindURL,
                    params:{'roleIds':roleIds},
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
            fn: submitFun,
            icon: Ext.MessageBox.QUESTION
        });
    }           
});
//  处理多语言
try {roleSelectLocale();} catch (e) {}

Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.WindowMgr.getActive().add(new micrite.security.roleSelect.SearchPanel());
    Ext.WindowMgr.getActive().doLayout();
});
</script>