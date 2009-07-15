<script type="text/javascript">
Ext.namespace('micrite.security.userList');

micrite.security.userList.SearchPanel = Ext.extend(micrite.ComplexEditorGrid, {
    userName:'User Name',
    fullName:'Full Name',
    email:'Email',
    enabled:'Enabled',
    bindRoles:'Bind Roles',
    addUser:'Add User',
    enableUsersButton:'Enable/Disable',
    statusAccordConfMsg:'Please make sure users selected are all enabled or disabled!',
    enableUsersConfMsg:'Are you sure want to enable the users?',
    disableUsersConfMsg:'Are you sure want to disable the users?',
        
    initComponent:function() { 
    var sm = new Ext.grid.CheckboxSelectionModel();
    var config = {
            compSet:[{url:0,reader:0,columns:0,bbarAction:0}],
            searchFields :[[
                this.userName, {xtype:'textfield', name:'user.fullname', width:120}
            ]],
            urls: ['/security/findUsersVague.action'],
            readers : [[
                {name: 'fullname'},
                {name: 'emailaddress'},
                {name: 'loginname'},
                {name: 'enabled', type: 'boolean'}
            ]],
            columnsArray: [[
                {header: this.fullName, width: 100, sortable: true, dataIndex: 'fullname',
                 editor: new Ext.form.TextField({allowBlank: false})},
                {header: this.email, width: 180, sortable: true, dataIndex: 'emailaddress',
                 editor: new Ext.form.TextField({})},
                {header: this.userName, width: 90, sortable: true, dataIndex: 'loginname',
                 editor: new Ext.form.TextField({allowBlank: false})},
                {header: this.enabled, width: 70, sortable: true, dataIndex: 'enabled'},
                sm
            ]],
            tbarActions : [{
                text:this.addUser,
                iconCls :'add-icon',
                scope:this,
                handler:this.addUserFun
            }],
            bbarActions:[[{
                text:this.bindRoles, 
                iconCls :'bind-icon',
                scope:this, 
                handler:this.bindRolesFun
            },{
                text:mbLocale.deleteButton, 
                iconCls :'delete-icon',
                scope:this, 
                handler:this.deleteUsersFun
            },{
                text:this.enableUsersButton, 
                iconCls :'other-icon',
                scope:this, 
                handler:this.enableUsersFun
            },{
                text:mbLocale.submitButton, 
                iconCls :'save-icon',
                scope:this, 
                handler:this.saveUserFun                 
            }]],
            sm:sm
        }; // eo config object
    Ext.apply(this, Ext.apply(this.initialConfig, config));
    micrite.security.userList.SearchPanel.superclass.initComponent.apply(this, arguments);    
    },
    addUserFun :function() {
        var win = micrite.util.genWindow({
            id: 'addUserWindow',
            title    : this.addUser,
            autoLoad : {url: this.urlPrefix + '/security/userDetail.js',scripts:true},
            width    : 500,
            height   : 360
        });
    },
    bindRolesFun :function() {
        //  选择的数据记录主键，形如“2, 4, 6, 10”
        var userIds = this.selModel.selections.keys;
        if(userIds.length != 1){
            Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowSelectMsg);
            return;
        }
        var users = this.selModel.getSelections();
        var win = micrite.util.genWindow({
            id: 'roleSelectWindow',
            title    : this.bindRoles + ' -- ' +  users[0].get('fullname'),
            autoLoad : {url: 'security/roleSelect.jsp?userId=' + userIds[0], scripts:true},
            width    : 500,
            height   : 360
        });
    },
    deleteUsersFun :function() {
        var userIds = this.selModel.selections.keys;
        if (userIds.length <= 0) {
            Ext.MessageBox.alert(mbLocale.infoMsg, mbLocale.gridMultRowSelectMsg);
            return;
        }
        var submitFun = function(buttonId, text, opt) {
            if (buttonId == 'yes') {
                micrite.util.ajaxRequest({
                    url: this.urlPrefix + '/deleteUsers.action',
                    params:{'userIds':userIds},
                    success:function(r,o){
                        this.store.reload();
                    },
                    failure:Ext.emptyFn
                },this);
            }
        };
        Ext.Msg.show({
            title:mbLocale.infoMsg,
            msg: mbLocale.delConfirmMsg,
            buttons: Ext.Msg.YESNO,
            scope: this,
            fn: submitFun,
            icon: Ext.MessageBox.QUESTION
        });
    },
    enableUsersFun :function() {
        var userIds = this.selModel.selections.keys;
        if (userIds.length <= 0) {
            Ext.MessageBox.alert(mbLocale.infoMsg, mbLocale.gridMultRowSelectMsg);
            return;
        }
        var users = this.selModel.getSelections();
        for (var i = 0; i < users.length; i++) {
            //  判断所选择的用户可用状态是否一致（通过下一个条数据和上一条数据的可用状态比较来判断）
            if (i > 0 && (users[i].get('enabled') != users[i - 1].get('enabled'))) {
                Ext.MessageBox.alert(mbLocale.infoMsg, this.statusAccordConfMsg);
                return;
            }
        }
        var submitFun = function(buttonId, text, opt) {
            if (buttonId == 'yes') {
                micrite.util.ajaxRequest({
                    url: this.urlPrefix + '/enableUsers.action',
                    params:{'userIds':userIds},
                    success:function(r,o){
                        this.store.reload();
                    },
                    failure:Ext.emptyFn
                },this);
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
            fn: submitFun,
            icon: Ext.MessageBox.QUESTION
        });
    },
    saveUserFun :function() {
        var store = this.getStore();
        if(store.getModifiedRecords().length != 1){
            Ext.MessageBox.alert(mbLocale.infoMsg, mbLocale.gridRowEditMsg);
            return;
        }
        var user = store.getModifiedRecords()[0];
        var submitFun = function(buttonId, text, opt) {
            if (buttonId == 'yes') {
                micrite.util.ajaxRequest({
                    url: this.urlPrefix + '/updateUserInfo.action',
                    params:{'user.id':user.id,
                            'user.fullname':user.get('fullname'),
                            'user.emailaddress':user.get('emailaddress'),
                            'user.loginname':user.get('loginname')},
                    success:function(r,o){
                        this.store.commitChanges();
                    },
                    failure:Ext.emptyFn
                },this);
            }
        };
        Ext.Msg.show({
            title:mbLocale.infoMsg,
            msg: mbLocale.updateConfirmMsg,
            buttons: Ext.Msg.YESNO,
            scope: this,
            fn: submitFun,
            icon: Ext.MessageBox.QUESTION
        });        
    }
});

//  处理多语言
try {userListLocale();} catch (e) {}

Ext.onReady(function() {
    Ext.QuickTips.init();
    var formPanel = new micrite.security.userList.SearchPanel();
    
    if (mainPanel) {
        mainPanel.getActiveTab().add(formPanel);
        mainPanel.getActiveTab().doLayout();
    } else {
        var vp = new Ext.Viewport({
            layout:'fit',
            items:[formPanel]
        });
    }
});
</script>