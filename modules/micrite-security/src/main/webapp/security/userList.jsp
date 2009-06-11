<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
Ext.namespace('micrite.security.userList');

micrite.security.userList.SearchPanel = function() {
    //  查询条件分组名称数组
    this.conditionGroupNames = [this.byUsername];
    //  查询条件分组组件组数组
    this.conditionGroupComponentGroups = [
        [this.username, {xtype:'textfield', name:'user.loginname', width:120}]
    ];
    //  超链菜单项数组
    this.linkerMenuItems =  [{
        text:this.addUser,
        handler:function() {
            mainPanel.loadModule('security/userDetail.jsp', 'User Detail');
        }
    }];    
    //  查询请求的url
    this.requestURL = '/' + document.location.href.split("/")[3] + '/security/findUsersVague.action';
    //  查询结果格式读取器
    this.resultReader = new Ext.data.JsonReader(
	                                               {
	                                                totalProperty:'totalCounts',
	                                                root:'results'
	                                               }, 
	                                               [
	                                                {name: 'id', type: 'int'},
	                                                {name: 'fullname'},
	                                                {name: 'emailaddress'},
	                                                {name: 'loginname'},
	                                                {name: 'isenabled', type: 'boolean'}
	                                               ]
	);
    //  查询结果列
    this.resultColumns = [
	                         {dataIndex: 'id', hidden: true},
	                         {header: this.fullname, width: 100, sortable: true, dataIndex: 'fullname'},
	                         {header: this.emailaddress, width: 180, sortable: true, dataIndex: 'emailaddress'},
	                         {header: this.username, width: 90, sortable: true, dataIndex: 'loginname'},
	                         {header: this.isenabled, width: 70, sortable: true, dataIndex: 'isenabled'}
                         ];

    //  动作按钮数组
    this.actionButtons = [
	                         {text:mbLocale.submitButton, disabled:true}, 
	                         {text:mbLocale.closeButton, handler:function() {}}
                         ];
    
    micrite.security.userList.SearchPanel.superclass.constructor.call(this);
};

Ext.extend(micrite.security.userList.SearchPanel, micrite.panel.SearchPanel, {
    byUsername:'By User Name',
    username:'User Name',
    addUser:'Add User',
    fullname:'Full Name',
    emailaddress:'Email Address',
    isenabled:'Enabled'
});

//  处理多语言
try {baseLocale();} catch (e) {}
try {userListLocale();} catch (e) {}

Ext.onReady(function() {
    Ext.QuickTips.init();
    var formPanel = new micrite.security.userList.SearchPanel();
    
    if (mainPanel) {
        mainPanel.getActiveTab().add(formPanel);
        mainPanel.getActiveTab().doLayout();
    } else {
        new Ext.Viewport({
            layout:'fit',
            items:[formPanel]
        });
    }
});
</script>