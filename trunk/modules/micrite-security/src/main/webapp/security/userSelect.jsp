<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
Ext.namespace('micrite.security.userSelect');

micrite.security.userSelect.SearchPanel = function() {
    //  查询条件分组名称数组
    this.conNames = [this.byName];
    //  查询条件分组组件组数组
    this.conCmpGroups = [
        [this.username, {xtype:'textfield', name:'user.loginname', width:120},
         '',{xtype:'checkbox',boxLabel:'Only Matched', name:'matched'}
        ]
    ];
    //  超链菜单项数组
    this.actionButtonMenuItems =  [{
        text:this.addRole,
        handler:function() {
            mainPanel.loadModule('security/roleDetail.js', 'Role Detail');
        }
    }];    
    //  查询请求的url
    this.searchRequestURL = ['/' + document.location.href.split("/")[3] + '/security/findMatchedUsers.action?roleIds='+<%=request.getParameter("roleIds")%>];
    
    //  查询结果数据按此格式读取
    this.resultDataFields = [[
        {name: 'fullname'},
        {name: 'emailaddress'}
    ]];
    
    //  查询结果行选择模型
    this.resultRowSelectionModel = new Ext.grid.CheckboxSelectionModel();
    
    //  查询结果列
    this.resultColumns = [[
        {header: this.name, width: 100, sortable: true, dataIndex: 'fullname'},
        {header: this.description, width: 180, sortable: true, dataIndex: 'emailaddress'},
        this.resultRowSelectionModel
    ]];

    this.comboGrid = [{url:0,reader:0,column:0,button:0}];
    //  动作按钮数组
    this.resultProcessButtons = [[{
        text:mbLocale.submitButton, 
        scope:this, 
        handler:function() {
            //  选择的数据记录主键，形如“2, 4, 6, 10”
            var ids = this.resultGrid.selModel.selections.keys;
            console.log(<%=request.getParameter("roleIds")%>);
            if(ids.length<1){
                Ext.MessageBox.alert('提示','请选择一条记录');
                return;
            }
       
            var addUsersMatched = function(buttonId, text, opt) {
                if (buttonId == 'yes') {
                    Ext.Ajax.request({
                        url:'/' + document.location.href.split("/")[3] + '/addUsersMatched.action?roleIds='+<%=request.getParameter("roleIds")%>,
                        params:{'userIds':ids},
                        scope:this,
                        success:function(response, options) {
                            obj = Ext.util.JSON.decode(response.responseText);
                            showMsg('success', obj.message);
                        },
                        failure: function(response, options) {
                            obj = Ext.util.JSON.decode(response.responseText);
                            showMsg('failure', obj.message);
                        }
                    });
                }
            }
            Ext.Msg.show({
                title:'确认框',
                msg: '确定要删除吗？',
                buttons: Ext.Msg.YESNO,
                scope: this,
                fn: addUsersMatched,
                icon: Ext.MessageBox.QUESTION
            });
        }
    },{
        text:mbLocale.closeButton, 
        scope:this,
        handler:function() {
        //  选择的数据记录主键，形如“2, 4, 6, 10”
        var ids = this.resultGrid.selModel.selections.keys;
        console.log(<%=request.getParameter("roleIds")%>);
        if(ids.length<1){
            Ext.MessageBox.alert('提示','请选择一条记录');
            return;
        }
   
        var delUsersMatched = function(buttonId, text, opt) {
            if (buttonId == 'yes') {
                Ext.Ajax.request({
                    url:'/' + document.location.href.split("/")[3] + '/delUsersMatched.action?roleIds='+<%=request.getParameter("roleIds")%>,
                    params:{'userIds':ids},
                    scope:this,
                    success:function(response, options) {
                        obj = Ext.util.JSON.decode(response.responseText);
                        showMsg('success', obj.message);
                    },
                    failure: function(response, options) {
                        obj = Ext.util.JSON.decode(response.responseText);
                        showMsg('failure', obj.message);
                    }
                });
            }
        }
        Ext.Msg.show({
            title:'确认框',
            msg: '确定要删除吗？',
            buttons: Ext.Msg.YESNO,
            scope: this,
            fn: delUsersMatched,
            icon: Ext.MessageBox.QUESTION
        });
        }
    }]];
    
    micrite.security.userSelect.SearchPanel.superclass.constructor.call(this);
};

Ext.extend(micrite.security.userSelect.SearchPanel, micrite.panel.ComplexSearchPanel, {
    byName:'By Role Name',
    username:'User Name',
    addRole:'Add Role',
    name:'Name',
    description:'Description'
});

//  处理多语言
try {baseLocale();} catch (e) {}
try {roleListLocale();} catch (e) {}

Ext.onReady(function() {
    Ext.QuickTips.init();
//    var formPanel = new micrite.security.roleListSub.SearchPanel();
    
    Ext.getCmp('userSelectWindow').add(new micrite.security.userSelect.SearchPanel());
    Ext.getCmp('userSelectWindow').doLayout();
});
</script>