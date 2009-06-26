<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
Ext.namespace('micrite.security.authoritySelect');

micrite.security.authoritySelect.SearchPanel = function() {
    //  查询条件分组名称数组
    this.conNames = [this.byName];
    //  查询条件分组组件组数组
    this.conCmpGroups = [
        [this.name, {xtype:'textfield', name:'authority.name', width:120},
         '',{xtype:'checkbox',boxLabel:'Only Binded', name:'binded'}
        ]
    ];
    //  超链菜单项数组
    this.actionButtonMenuItems =  [{
        text:this.addAuth,
        handler:function() {
            mainPanel.loadModule('security/authorityDetail.js', 'Authority Detail');
        }
    }];    
    //  查询请求的url
    this.searchRequestURL = ['/' + document.location.href.split("/")[3] + '/security/findBindedAuths.action?roleIds='+<%=request.getParameter("roleIds")%>];
    
    //  查询结果数据按此格式读取
    this.resultDataFields = [[
        {name: 'name'},
        {name: 'type'},        
        {name: 'value'}
    ]];
    
    //  查询结果行选择模型
    this.resultRowSelectionModel = new Ext.grid.CheckboxSelectionModel();
    
    //  查询结果列
    this.resultColumns = [[
        {header: this.name, width: 100, sortable: true, dataIndex: 'name'},
        {header: this.type, width: 100, sortable: true, dataIndex: 'type'},        
        {header: this.value, width: 180, sortable: true, dataIndex: 'value'},
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
       
            var bindAuths = function(buttonId, text, opt) {
                if (buttonId == 'yes') {
                    Ext.Ajax.request({
                        url:'/' + document.location.href.split("/")[3] + '/bindAuths.action?roleIds='+<%=request.getParameter("roleIds")%>,
                        params:{'authIds':ids},
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
                fn: bindAuths,
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
   
        var unBindAuths = function(buttonId, text, opt) {
            if (buttonId == 'yes') {
                Ext.Ajax.request({
                    url:'/' + document.location.href.split("/")[3] + '/unBindAuths.action?roleIds='+<%=request.getParameter("roleIds")%>,
                    params:{'authIds':ids},
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
            fn: unBindAuths,
            icon: Ext.MessageBox.QUESTION
        });
        }
    }]];
    
    micrite.security.authoritySelect.SearchPanel.superclass.constructor.call(this);
};

Ext.extend(micrite.security.authoritySelect.SearchPanel, micrite.panel.ComplexSearchPanel, {
    byName:'By Authority Name',
    addAuth:'Add Authority',
    name:'Name',
    type:'Type',
    value:'Value'
});

//  处理多语言
try {baseLocale();} catch (e) {}
try {roleListLocale();} catch (e) {}

Ext.onReady(function() {
    Ext.QuickTips.init();
//    var formPanel = new micrite.security.roleListSub.SearchPanel();
    
    Ext.getCmp('authoritySelectWindow').add(new micrite.security.authoritySelect.SearchPanel());
    Ext.getCmp('authoritySelectWindow').doLayout();
});
</script>