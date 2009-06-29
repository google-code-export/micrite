<script type="text/javascript">
Ext.namespace('micrite.security.roleList');

micrite.security.roleList.SearchPanel = function() {
    //  查询条件分组名称数组
    this.conNames = [''];
    //  查询条件分组组件组数组
    this.conCmpGroups = [
        [this.name, {xtype:'textfield', name:'role.name', width:120}]
    ];
    //  超链菜单项数组
    this.actionButtonMenuItems =  [{
        text:this.addRole,
        iconCls :'add-icon',
        handler:function() {
            mainPanel.loadModule('security/roleDetail.js', 'Role Detail');
        }
    }];    
    //  查询请求的url
    this.searchRequestURL = ['/' + document.location.href.split("/")[3] + '/security/findRolesVague.action'];
    
    //  查询结果数据按此格式读取
    this.resultDataFields = [[
        {name: 'name'},
        {name: 'description'}
    ]];
    this.comboGrid = [{url:0,reader:0,column:0,button:0}];
    //  查询结果行选择模型
    this.resultRowSelectionModel = new Ext.grid.CheckboxSelectionModel();
    
    //  查询结果列
    this.resultColumns = [[
        {header: this.name, width: 100, sortable: true, dataIndex: 'name'},
        {header: this.description, width: 180, sortable: true, dataIndex: 'description'},
        this.resultRowSelectionModel
    ]];

    //  动作按钮数组
    this.resultProcessButtons = [[{
        text:this.bindUser, 
        iconCls :'bind-icon',
        scope:this, 
        handler:function() {
            //  选择的数据记录主键，形如“2, 4, 6, 10”
            var roleIds = this.resultGrid.selModel.selections.keys;
            if(roleIds.length!=1){
                Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowSelectMsg);
                return;
            }
            var win;
            if(!(win = Ext.getCmp('userSelectWindow'))){
                win = new Ext.Window({
                    id: 'userSelectWindow',
                    title    : this.bindUser,
                    closable : true,
                    autoLoad : {url: 'security/userSelect.jsp?roleIds='+roleIds+'&'+(new Date).getTime(),scripts:true},
                    width    : 600,
                    height   : 420,
                    maximizable : true,
                    layout:'fit'
                });
            }
            win.show();
            win.center();            
        }
    },{
        text:this.bindAuthority,
        iconCls :'bind-icon',
        scope:this, 
        handler:function() {
            var roleIds = this.resultGrid.selModel.selections.keys;
            if(roleIds.length!=1){
                Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowSelectMsg);
                return;
            }
            var win;
            if(!(win = Ext.getCmp('authoritySelectWindow'))){
                win = new Ext.Window({
                    id: 'authoritySelectWindow',
                    title    : this.bindAuthority,
                    closable : true,
                    autoLoad : {url: 'security/authoritySelect.jsp?roleIds='+roleIds+'&'+(new Date).getTime(),scripts:true},
                    width    : 600,
                    height   : 420,
                    maximizable : true,
                    layout:'fit'
                });
            }
            win.show();
            win.center();            
        }
    },{
        text:mbLocale.deleteButton, 
        iconCls :'delete-icon',
        scope:this, 
        handler:function() {
            var roleIds = this.resultGrid.selModel.selections.keys;
            if(roleIds.length!=1){
                Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowSelectMsg);
                return;
            }
            var deleteRolesFun = function(buttonId, text, opt) {
                if (buttonId == 'yes') {
                    Ext.Ajax.request({
                        url:'/' + document.location.href.split("/")[3] + '/deleteRoles.action',
                        params:{'roleIds':roleIds},
                        scope:this,
                        callback:function(options,success,response) {
                            if (Ext.util.JSON.decode(response.responseText).success) {
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
                msg: mbLocale.delConfirmMsg,
                buttons: Ext.Msg.YESNO,
                scope: this,
                fn: deleteRolesFun,
                icon: Ext.MessageBox.QUESTION
            });        
        }
    }]];
    
    micrite.security.roleList.SearchPanel.superclass.constructor.call(this);
};

Ext.extend(micrite.security.roleList.SearchPanel, micrite.panel.ComplexSearchPanel, {
    bindUser:'Bind User',
    bindAuthority:'Bind Authority',
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