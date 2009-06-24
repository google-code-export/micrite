<script type="text/javascript">
Ext.namespace('micrite.security.userList');

micrite.security.userList.SearchPanel = function() {
    //  查询条件名称数组
    this.conNames = [this.byUsername, this.byUsername + 'Other'];
    //  查询条件组件组数组
    this.conCmpGroups = [
        [this.username, {xtype:'textfield', name:'user.loginname', width:120}],
        [this.username + 'Other', {xtype:'textfield', name:'user.loginname', width:120}]
    ];
    //  动作按钮上的菜单项
    this.actionButtonMenuItems =  [{
        text:this.addUser,
        handler:function() {
            mainPanel.loadModule('security/userDetail.js', 'User Detail');
        }
    }];    
    //  查询请求的url
    this.searchRequestURL = ['/' + document.location.href.split("/")[3] + '/security/findUsersVague.action'];
    //  查询结果数据按此格式读取
    this.resultDataFields = [[
                                {name: 'fullname'},
                                {name: 'emailaddress'},
                                {name: 'loginname'},
                                {name: 'enabled', type: 'boolean'}
                            ]];
    //  查询结果行选择模型
    this.resultRowSelectionModel = new Ext.grid.CheckboxSelectionModel();
    //  查询结果列数组
    this.resultColumns = [
                          [
                             {header: this.fullname, width: 100, sortable: true, dataIndex: 'fullname'},
                             {header: this.emailaddress, width: 180, sortable: true, dataIndex: 'emailaddress'},
                             {header: this.username, width: 90, sortable: true, dataIndex: 'loginname'},
                             {header: this.enabled, width: 70, sortable: true, dataIndex: 'enabled'},
                             this.resultRowSelectionModel
                         ],[
                         
                          {header: 'kkakak', width: 100, sortable: true, dataIndex: 'fullname'},
                          {header: 'fffd', width: 180, sortable: true, dataIndex: 'emailaddress'},
                          {header: this.username, width: 90, sortable: true, dataIndex: 'loginname'},
                          {header: this.enabled, width: 70, sortable: true, dataIndex: 'enabled'},
                          this.resultRowSelectionModel
                          
                         ]];
    this.comboGrid = [
                      {url:0,reader:0,column:0},
                      {url:0,reader:0,column:1}
                     ];
    //  查询结果处理按钮数组
    this.resultProcessButtons = [
        {
            text:'修改角色', 
            scope:this, 
            handler:function() {
                //  选择的数据记录主键，形如“2, 4, 6, 10”
                var recordIdsSelected = '' + this.resultGrid.selModel.selections.keys;
                //  去掉recordIdsSelected中的空格
                recordIdsSelected = recordIdsSelected.replace(/ /g,'');
                if (recordIdsSelected == '') {
                    Ext.MessageBox.alert('提示','请选择数据记录');
                    return;
                }
                //  若选择多条记录
                if (recordIdsSelected.indexOf(',') > 0) {
                    Ext.MessageBox.alert('提示','只能选择一条数据记录');
                    return;
                }
                var userId = recordIdsSelected;
                //  加载所有角色
                var allRoleListStore = new Ext.data.Store({
                    autoLoad:true,
                    proxy:new Ext.data.HttpProxy({url: '/' + document.location.href.split("/")[3] + '/findRolesAll.action'}),    
                    reader:new Ext.data.JsonReader({id: 'id'}, [{name: 'name'}]),
                    listeners:{
                        load:function() {
                            //  一旦加载完所有角色，取用户角色，将用户角色项设置为已选择
                            var userRoleListStore = new Ext.data.Store({
                                proxy: new Ext.data.HttpProxy({url: '/' + document.location.href.split("/")[3] + '/findUserRoles.action'}),    
                                reader: new Ext.data.JsonReader({id: 'id'}, [{name: 'name'}]),
                                listeners:{
                                    load:function(st, records, options) {
                                        var userRoleListGrid = Ext.getCmp('userRoleListGrid');
                                        var userRoleRecords = [records.length];
                                        for (var i = 0; i < records.length; i++) {
                                            var userRoleRecord = userRoleListGrid.store.getById(records[i].id);
                                            userRoleRecords[i] = userRoleRecord;
                                        }
                                        userRoleListGrid.selModel.selectRecords(userRoleRecords);
                                    }
                                }
                            });
                            userRoleListStore.load({params:{'user.id':userId}});
                        }
                    }
                });
                var roleListGridSelModel = new Ext.grid.CheckboxSelectionModel();
                //  查询结果列
                var roleListGridColumns = [
                    roleListGridSelModel,
                    {header: 'Role Name', sortable: true, dataIndex: 'name'}
                ];
                var win = new Ext.Window({
                            title:"标题",
                            width:600 ,
                            height:400,
                            items:[{
                                id:'userRoleListGrid',
                                xtype:'grid',
                                autoHeight:true,
                                border:false,
                                loadMask:{
                                    msg:mbLocale.loadingMsg
                                },
                                stripeRows:true,
                                selModel:roleListGridSelModel,
                                viewConfig:{
                                    forceFit:true,
                                    enableRowBody:true
                                },
                                store:allRoleListStore,
                                colModel:new Ext.grid.ColumnModel(roleListGridColumns)
                            }],
                            buttons:[{text:'保存', handler:function() {}},
                                     {text:'关闭', handler:function() {win.close()}}]
                });
                win.show();
            }
        },                                                          
        {
            text:'删除', 
            scope:this, 
            handler:function() {
                //  选择的数据记录主键，形如“2, 4, 6, 10”
                var recordIdsSelected = '' + this.resultGrid.selModel.selections.keys;
                //  去掉recordIdsSelected中的空格
                recordIdsSelected = recordIdsSelected.replace(/ /g,'');
                if (recordIdsSelected == '') {
                    Ext.MessageBox.alert('提示','请选择数据记录');
                    return;
                }
                var deleteUsersFun = function(buttonId, text, opt) {
                    if (buttonId == 'yes') {
                        Ext.Ajax.request({
                            url:'/' + document.location.href.split("/")[3] + '/deleteUsers.action',
                            params:{'userIdsStr':recordIdsSelected},
                            scope:this,
                            success:function(response, options) {
                                obj = Ext.util.JSON.decode(response.responseText);
                                showMsg('success', obj.message);
                                this.searchFun();                                               
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
                    fn: deleteUsersFun,
                    icon: Ext.MessageBox.QUESTION
                });
            }
        },
        {
            text:'置可用/不可用', 
            scope:this, 
            handler:function() {
                //  选择的数据记录主键，形如“2, 4, 6, 10”
                var recordIdsSelected = '' + this.resultGrid.selModel.selections.keys;
                //  去掉recordIdsSelected中的空格
                recordIdsSelected = recordIdsSelected.replace(/ /g,'');
                if (recordIdsSelected == '') {
                    Ext.MessageBox.alert('提示','请选择数据记录');
                    return;
                }
                var recordsSelected = this.resultGrid.selModel.getSelections();
                for (var i = 0; i < recordsSelected.length; i++) {
                    //  判断所选择的用户可用状态是否一致（通过下一个条数据和上一条数据的可用状态比较来判断）
                    if (i > 0 && (recordsSelected[i].get('enabled') != recordsSelected[i - 1].get('enabled'))) {
                        Ext.MessageBox.alert('提示','请确保选择的用户可用状态一致');
                        return;
                    }
                }
                var enableOrDisableUsersFun = function(buttonId, text, opt) {
                    if (buttonId == 'yes') {
                        Ext.Ajax.request({
                            url:'/' + document.location.href.split("/")[3] + '/enableOrDisableUsers.action',
                            params:{'userIdsStr':recordIdsSelected},
                            scope:this,
                            success:function(response, options) {
                                obj = Ext.util.JSON.decode(response.responseText);
                                showMsg('success', obj.message);
                                this.searchFun();                                               
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
                    msg: '确定修改用户可用状态吗？',
                    buttons: Ext.Msg.YESNO,
                    scope: this,
                    fn: enableOrDisableUsersFun,
                    icon: Ext.MessageBox.QUESTION
                });
            }
        }
    ];
    
    micrite.security.userList.SearchPanel.superclass.constructor.call(this);
};

Ext.extend(micrite.security.userList.SearchPanel, micrite.panel.SearchPanel, {
    byUsername:'By User Name',
    username:'User Name',
    addUser:'Add User',
    fullname:'Full Name',
    emailaddress:'Email Address',
    enabled:'Enabled'
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