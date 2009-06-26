<script type="text/javascript">
Ext.namespace('micrite.security.roleList');

micrite.security.roleList.SearchPanel = function() {
    //  查询条件分组名称数组
    this.conNames = [this.byName];
    //  查询条件分组组件组数组
    this.conCmpGroups = [
        [this.name, {xtype:'textfield', name:'role.name', width:120}]
    ];
    //  超链菜单项数组
    this.actionButtonMenuItems =  [{
        text:this.addRole,
        handler:function() {
            mainPanel.loadModule('security/roleDetail.js', 'Role Detail');
        }
    }];    
    //  查询请求的url
    this.searchRequestURL = '/' + document.location.href.split("/")[3] + '/security/findRolesVague.action';
    
    //  查询结果数据按此格式读取
    this.resultDataFields = [
        {name: 'name'},
        {name: 'description'}
    ];
    
    //  查询结果行选择模型
    this.resultRowSelectionModel = new Ext.grid.CheckboxSelectionModel();
    
    //  查询结果列
    this.resultColumns = [
        {header: this.name, width: 100, sortable: true, dataIndex: 'name'},
        {header: this.description, width: 180, sortable: true, dataIndex: 'description'},
        this.resultRowSelectionModel
    ];

    //  动作按钮数组
    this.resultProcessButtons = [{
        text:this.putUser, 
        scope:this, 
        handler:function() {
            //  选择的数据记录主键，形如“2, 4, 6, 10”
            var roleIds = this.resultGrid.selModel.selections.keys;
            console.log(roleIds);
            if(roleIds.length!=1){
                Ext.MessageBox.alert('提示','请选择一条记录');
                return;
            }
            var win;
            if(!(win = Ext.getCmp('userSelectWindow'))){
                win = new Ext.Window({
                    id: 'userSelectWindow',
                    title    : this.putUser,
                    closable : true,
                    autoLoad : {url: 'security/userSelect.jsp?roleIds='+roleIds+'&'+(new Date).getTime(),scripts:true},
                    width    : 500,
                    height   : 360,
                    maximizable : true,
                    layout:'fit'
                });
            }
            win.show();
            win.center();            
            console.log(roleIds.length);
        }
    },{
        text:mbLocale.closeButton, 
        handler:function() {}
    }];
    
    micrite.security.roleList.SearchPanel.superclass.constructor.call(this);
};

Ext.extend(micrite.security.roleList.SearchPanel, micrite.panel.SearchPanel, {
    putUser:'Set User Related',
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