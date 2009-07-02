<script type="text/javascript">
Ext.namespace('micrite.security.userList');

micrite.security.userList.SearchPanel = function() {
    //  查询条件名称数组
    this.conNames = [''];
    //  查询条件组件组数组
    this.conCmpGroups = [
        [this.userName, {xtype:'textfield', name:'user.fullname', width:120}]
    ];
    //  动作按钮上的菜单项
    this.actionButtonMenuItems = [{
        text:this.addUserButton,
        iconCls :'add-icon',
        scope:this,
        handler:function() {
	    	var win;
	    	if(!(win = Ext.getCmp('addUserWindow'))){
		        win = new Ext.Window({
		        	id: 'addUserWindow',
		            title    : this.addUserButton,
		            closable : true,
		            autoLoad : {url: 'security/userDetail.js?'+(new Date).getTime(),scripts:true},
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
    this.searchRequestURL = ['/' + document.location.href.split("/")[3] + '/security/findUsersVague.action'];
    //  查询结果数据按此格式读取
    this.resultDataFields = [
                                [
                                    {name: 'fullname'},
                                    {name: 'emailaddress'},
                                    {name: 'loginname'},
                                    {name: 'enabled', type: 'boolean'}
                                ]
                            ];
    //  查询结果行选择模型
    this.resultRowSelectionModel = new Ext.grid.CheckboxSelectionModel();
    //  查询结果列数组
    this.resultColumns = [
                             [
                                 {
                                     header: this.fullName, 
                                     width: 100, 
                                     sortable: true, 
                                     dataIndex: 'fullname',
                                     editor: new Ext.form.TextField({allowBlank: false})
                                 },
                                 {
                                     header: this.email, 
                                     width: 180, 
                                     sortable: true, 
                                     dataIndex: 'emailaddress',
                                     editor: new Ext.form.TextField({})
                                 },
                                 {   
                                     header: this.userName, 
                                     width: 90, 
                                     sortable: true, 
                                     dataIndex: 'loginname',
                                     editor: new Ext.form.TextField({allowBlank: false})
                                 },
                                 {header: this.enabled, width: 70, sortable: true, dataIndex: 'enabled'},
                                 this.resultRowSelectionModel
                             ]
                         ];
    this.edit = true;
    //  查询结果处理按钮数组
    this.resultProcessButtons = [
    [
        {
            text:this.bindRoles, 
            iconCls :'bind-icon',
            scope:this,
            handler:function() {
                //  选择的数据记录主键，形如“2, 4, 6, 10”
                var userIds = this.resultGrid.selModel.selections.keys;
                if(userIds.length != 1){
                    Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowSelectMsg);
                    return;
                }
                var users = this.resultGrid.selModel.getSelections();
                var win;
                if(!(win = Ext.getCmp('roleSelectWindow'))){
                    win = new Ext.Window({
                        id: 'roleSelectWindow',
                        title    : users[0].get('fullname') + ' ' + this.bindRoles ,
                        closable : true,
                        autoLoad : {url: 'security/roleSelect.jsp?userId=' + userIds[0] + '&' + (new Date).getTime(), scripts:true},
                        width    : 600,
                        height   : 420,
                        maximizable : true,
                        layout:'fit'
                    });
                }
                win.show();
                win.center();
            }
        },                                                          
        {
            text:mbLocale.deleteButton, 
            iconCls :'delete-icon',
            scope:this, 
            handler:function() {
                var userIds = this.resultGrid.selModel.selections.keys;
                if (userIds.length == 0) {
                    Ext.MessageBox.alert(mbLocale.infoMsg, mbLocale.gridMultRowSelectMsg);
                    return;
                }
                var deleteUsersFun = function(buttonId, text, opt) {
                    if (buttonId == 'yes') {
                        Ext.Ajax.request({
                            url:'/' + document.location.href.split("/")[3] + '/deleteUsers.action',
                            params:{'userIds':userIds},
                            scope:this,
                            callback:function(options, success, response) {
                                if (Ext.util.JSON.decode(response.responseText).success) {
                                    this.refresh();
                                    obj = Ext.util.JSON.decode(response.responseText);
                                    showMsg('success', obj.message);
                                } else {
                                    obj = Ext.util.JSON.decode(response.responseText);
                                    showMsg('failure', obj.message);
                                }
                            }
                        });
                    }
                };
                Ext.Msg.show({
                    title:mbLocale.infoMsg,
                    msg: mbLocale.delConfirmMsg,
                    buttons: Ext.Msg.YESNO,
                    scope: this,
                    fn: deleteUsersFun,
                    icon: Ext.MessageBox.QUESTION
                });
            }
        },
        {
            text:this.enableUsersButton, 
            iconCls :'other-icon',
            scope:this, 
            handler:function() {
                var userIds = this.resultGrid.selModel.selections.keys;
                if (userIds.length == 0) {
                    Ext.MessageBox.alert(mbLocale.infoMsg, mbLocale.gridMultRowSelectMsg);
                    return;
                }
                var users = this.resultGrid.selModel.getSelections();
                for (var i = 0; i < users.length; i++) {
                    //  判断所选择的用户可用状态是否一致（通过下一个条数据和上一条数据的可用状态比较来判断）
                    if (i > 0 && (users[i].get('enabled') != users[i - 1].get('enabled'))) {
                        Ext.MessageBox.alert(mbLocale.infoMsg, this.statusAccordConfMsg);
                        return;
                    }
                }
                var enableUsersFun = function(buttonId, text, opt) {
                    if (buttonId == 'yes') {
                        Ext.Ajax.request({
                            url:'/' + document.location.href.split("/")[3] + '/enableUsers.action',
                            params:{'userIds':userIds},
                            scope:this,
                            callback:function(options, success, response) {
                                if (Ext.util.JSON.decode(response.responseText).success) {
                                    this.refresh();
                                    obj = Ext.util.JSON.decode(response.responseText);
                                    showMsg('success', obj.message);
                                } else {
                                    obj = Ext.util.JSON.decode(response.responseText);
                                    showMsg('failure', obj.message);
                                }
                            }
                        });
                    }
                };
                
                //  确认框提示信息
                var enableOrDisableUsersConfMsg = '';
                if (users[0].get('enabled')) {
                    enableOrDisableUsersConfMsg = this.disableUsersConfMsg;
                } else {
                    enableOrDisableUsersConfMsg = this.enableUsersConfMsg;
                }
                
                Ext.Msg.show({
                    title:mbLocale.infoMsg,
                    msg: enableOrDisableUsersConfMsg,
                    buttons: Ext.Msg.YESNO,
                    scope: this,
                    fn: enableUsersFun,
                    icon: Ext.MessageBox.QUESTION
                });
            }
        },{
            text:mbLocale.submitButton, 
            iconCls :'save-icon',
            scope:this, 
            handler:function() {
                var store = this.resultGrid.getStore();
                if(store.getModifiedRecords().length != 1){
                    Ext.MessageBox.alert(mbLocale.infoMsg, mbLocale.gridRowEditMsg);
                    return;
                }
                var user = store.getModifiedRecords()[0];
                var updateUserFun = function(buttonId, text, opt) {
                    if (buttonId == 'yes') {
                        Ext.Ajax.request({
                            url:'/' + document.location.href.split("/")[3] + '/updateUserInfo.action',
                            params:{'user.id':user.id,
                                    'user.fullname':user.get('fullname'),
                                    'user.emailaddress':user.get('emailaddress'),
                                    'user.loginname':user.get('loginname')},
                            scope:this,
                            callback:function(options,success,response) {
                                if (Ext.util.JSON.decode(response.responseText).success) {
                                    store.commitChanges();
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
                    msg: mbLocale.updateConfirmMsg,
                    buttons: Ext.Msg.YESNO,
                    scope: this,
                    fn: updateUserFun,
                    icon: Ext.MessageBox.QUESTION
                });        
            }
        }
    ]
    ];
    this.comboGrid = [{url:0,reader:0,column:0,button:0}];
    
    micrite.security.userList.SearchPanel.superclass.constructor.call(this);
};

Ext.extend(micrite.security.userList.SearchPanel, micrite.panel.ComplexSearchPanel, {
    userName:'User Name',
    fullName:'Full Name',
    email:'Email',
    enabled:'Enabled',
    bindRoles:'Bind Roles',
    addUserButton:'Add User',
    enableUsersButton:'Enable/Disable',
    statusAccordConfMsg:'Please make sure users selected are all enabled or disabled!',
    enableUsersConfMsg:'Are you sure want to enable the users?',
    disableUsersConfMsg:'Are you sure want to disable the users?'
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