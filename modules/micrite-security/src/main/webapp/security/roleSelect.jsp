<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
Ext.namespace('micrite.security.roleSelect');

micrite.security.roleSelect.SearchPanel = function() {
    //  查询条件分组名称数组
    this.conNames = [''];
    //  查询条件分组组件组数组
    this.conCmpGroups = [
        [this.roleName, {xtype:'textfield', name:'role.name', width:120},
         '',{xtype:'checkbox',boxLabel:this.onlyBinded, name:'binded'}
        ]
    ];
    //  超链菜单项数组
    this.actionButtonMenuItems = [{
        text:this.addRole,
        iconCls :'add-icon',
        scope:this,
        handler:function() {
            var win;
            if(!(win = Ext.getCmp('addRoleWindow'))){
                win = new Ext.Window({
                    id: 'addRoleWindow',
                    title    : this.addRole,
                    closable : true,
                    autoLoad : {url: 'security/roleDetail.js?'+(new Date).getTime(),scripts:true},
                    width    : 500,
                    height   : 360,
                    maximizable : true,
                    layout:'fit'
                });
            }
            win.show();
            win.center();
        }
    }];    
    //  查询请求的url
    var searchURL = '';
    //  绑定请求的url
    var bindURL = '';
    //  解除绑定请求的url
    var unBindURL = '';
    if (<%=(request.getParameter("userId") != null)%>) {
        searchURL = '/security/findRolesByUser.action?userId=' + <%=request.getParameter("userId")%>;
        bindURL = '/bindRolesToUser.action?userId='+<%=request.getParameter("userId")%>;
        unBindURL = '/unBindRolesFromUser.action?userId='+<%=request.getParameter("userId")%>;
    } else {
        searchURL = '/security/findRolesByAuthority.action?authorityId=' + <%=request.getParameter("authorityId")%>;
        bindURL = '/bindRolesToAuthority.action?authorityId='+<%=request.getParameter("authorityId")%>;
        unBindURL = '/unBindRolesFromAuthority.action?authorityId='+<%=request.getParameter("authorityId")%>;
    }
    //  查询请求的url
    this.searchRequestURL = ['/' + document.location.href.split("/")[3] + searchURL];
    //  查询结果数据按此格式读取
    this.resultDataFields = [[
        {name: 'name'},
        {name: 'description'}
    ]];
    
    //  查询结果行选择模型
    this.resultRowSelectionModel = new Ext.grid.CheckboxSelectionModel();
    
    //  查询结果列
    this.resultColumns = [[
        {header: this.roleName, width: 100, sortable: true, dataIndex: 'name'},
        {header: this.roleDescription, width: 180, sortable: true, dataIndex: 'description'},
        this.resultRowSelectionModel
    ]];

    this.comboGrid = [{url:0,reader:0,column:0,button:0}];
    //  动作按钮数组
    this.resultProcessButtons = [[{
        text:mbLocale.bindButton, 
        iconCls :'bind-icon',
        scope:this, 
        handler:function() {
            //  选择的数据记录主键，形如“2, 4, 6, 10”
            var roleIds = this.resultGrid.selModel.selections.keys;
            if(roleIds.length < 1){
                Ext.MessageBox.alert(mbLocale.infoMsg, mbLocale.gridMultRowSelectMsg);
                return;
            }
       
            var bindRoles = function(buttonId, text, opt) {
                if (buttonId == 'yes') {
                    Ext.Ajax.request({
                        url:'/' + document.location.href.split("/")[3] + bindURL,
                        params:{'roleIds':roleIds},
                        scope:this,
                        callback:function(options, success, response) {
                            if (Ext.util.JSON.decode(response.responseText).success) {
                                this.refresh();
                                obj = Ext.util.JSON.decode(response.responseText);
                                showMsg('success', obj.message);
                            }else{
                                obj = Ext.util.JSON.decode(response.responseText);
                                showMsg('failure', obj.message);                                
                            }
                        }
                    });
                }
            }
            Ext.Msg.show({
                title:mbLocale.infoMsg,
                msg: mbLocale.bindConfirmMsg,
                buttons: Ext.Msg.YESNO,
                scope: this,
                fn: bindRoles,
                icon: Ext.MessageBox.QUESTION
            });
        }
    },{
        text:mbLocale.unbindButton, 
        iconCls :'unbind-icon',
        scope:this,
        handler:function() {
        //  选择的数据记录主键，形如“2, 4, 6, 10”
        var roleIds = this.resultGrid.selModel.selections.keys;
        if(roleIds.length < 1){
            Ext.MessageBox.alert(mbLocale.infoMsg, mbLocale.gridMultRowSelectMsg);
            return;
        }
   
        var unBindRoles = function(buttonId, text, opt) {
            if (buttonId == 'yes') {
                Ext.Ajax.request({
                    url:'/' + document.location.href.split("/")[3] + unBindURL,
                    params:{'roleIds':roleIds},
                    scope:this,
                    callback:function(options, success, response) {
                        if (Ext.util.JSON.decode(response.responseText).success) {
                            this.refresh();
                            obj = Ext.util.JSON.decode(response.responseText);
                            showMsg('success', obj.message);
                        }else{
                            obj = Ext.util.JSON.decode(response.responseText);
                            showMsg('failure', obj.message);                                
                        }
                    }
                });
            }
        }
        Ext.Msg.show({
            title:mbLocale.infoMsg,
            msg: mbLocale.unbindConfirmMsg,
            buttons: Ext.Msg.YESNO,
            scope: this,
            fn: unBindRoles,
            icon: Ext.MessageBox.QUESTION
        });
        }
    }]];
    
    micrite.security.roleSelect.SearchPanel.superclass.constructor.call(this);
};

Ext.extend(micrite.security.roleSelect.SearchPanel, micrite.panel.ComplexSearchPanel, {
    addRole:'Add Role',
    onlyBinded:'Only Binded',
    roleName:'Role Name',
    roleDescription:'Role Description'
});

//  处理多语言
try {roleSelectLocale();} catch (e) {}

Ext.onReady(function() {
    Ext.QuickTips.init();
    
    Ext.getCmp('roleSelectWindow').add(new micrite.security.roleSelect.SearchPanel());
    Ext.getCmp('roleSelectWindow').doLayout();
});
</script>