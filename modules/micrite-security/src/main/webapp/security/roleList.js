<script type="text/javascript">
Ext.namespace('micrite.security.roleList');

micrite.security.roleList.SearchPanel = function() {
    //  查询条件分组名称数组
    this.conditionGroupNames = [this.byName];
    //  查询条件分组组件组数组
    this.conditionGroupComponentGroups = [
        [this.name, {xtype:'textfield', name:'role.name', width:120}]
    ];
    //  超链菜单项数组
    this.linkerMenuItems =  [{
        text:this.addRole,
        handler:function() {
            mainPanel.loadModule('security/roleDetail.js', 'Role Detail');
        }
    }];    
    //  查询请求的url
    this.requestURL = '/' + document.location.href.split("/")[3] + '/security/findRolesVague.action';
    //  查询结果格式读取器
    this.resultReader = new Ext.data.JsonReader(
	                                               {
	                                                totalProperty:'totalCounts',
	                                                root:'results'
	                                               }, 
	                                               [
	                                                {name: 'id', type: 'int'},
	                                                {name: 'name'},
	                                                {name: 'description'}
	                                               ]
	);
    //  查询结果列
    this.resultColumns = [
	                         {dataIndex: 'id', hidden: true},
	                         {header: this.name, width: 100, sortable: true, dataIndex: 'name'},
	                         {header: this.description, width: 180, sortable: true, dataIndex: 'description'}
                         ];

    //  动作按钮数组
    this.actionButtons = [
	                         {text:mbLocale.submitButton, disabled:true}, 
	                         {text:mbLocale.closeButton, handler:function() {}}
                         ];
    
    micrite.security.roleList.SearchPanel.superclass.constructor.call(this);
};

Ext.extend(micrite.security.roleList.SearchPanel, micrite.panel.SearchPanel, {
    byName:'By Role Name',
    name:'Role Name',
    addRole:'Add Role',
    name:'Name',
    description:'Description'
});

//  处理多语言
try {baseLocale();} catch (e) {}
try {roleListLocale();} catch (e) {}

Ext.onReady(function() {
    Ext.QuickTips.init();
    var formPanel = new micrite.security.roleList.SearchPanel();
    
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