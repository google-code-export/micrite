<script type="text/javascript">
Ext.namespace('micrite.security.roleList');

micrite.security.roleList.SearchPanel = Ext.extend(micrite.ComplexEditorGrid, {
    bindUser:'Bind User',
    bindAuthority:'Bind Authority',
    addRole:'Add Role',
    name:'Name',
    description:'Description',
        
    initComponent:function() { 
    var sm = new Ext.grid.CheckboxSelectionModel();
    var config = {
            compSet:[{url:0,reader:0,columns:0,bbarAction:0}],
            searchFields :[[
                this.name, {xtype:'textfield', name:'role.name', width:120}
            ]],
            urls: ['findRolesVague.action'],
            readers : [[
                {name: 'name'},
                {name: 'description'}
            ]],
            columnsArray: [[
                {header: this.name, width: 100, sortable: true, dataIndex: 'name',editor: new Ext.form.TextField({
                    allowBlank: false, disabled:true
                })},
                {header: this.description, width: 180, sortable: true, dataIndex: 'description',editor: new Ext.form.TextField({
                    allowBlank: false
                })},
                sm
             ]],
             tbarActions : [{
                 text:this.addRole,
                 iconCls :'add-icon',
                 scope:this,
                 handler:this.addRoleFun
             }],
             bbarActions:[[{
                 text:this.bindUser, 
                 iconCls :'bind-icon',
                 scope:this, 
                 handler:this.bindUserFun
             },{
                 text:this.bindAuthority,
                 iconCls :'bind-icon',
                 scope:this, 
                 handler:this.bindAuthorityFun
             },{
                 text:mbLocale.deleteButton, 
                 iconCls :'delete-icon',
                 scope:this, 
                 handler:this.deleteRoleFun
             },{
                 text:mbLocale.submitButton, 
                 iconCls :'save-icon',
                 scope:this, 
                 handler:this.saveRoleFun                 
             }]],
             sm:sm
        }; // eo config object
    Ext.apply(this, Ext.apply(this.initialConfig, config)); 
    micrite.security.roleList.SearchPanel.superclass.initComponent.apply(this, arguments);    
    },
    addRoleFun :function() {
        var win = micrite.util.genWindow({
            id: 'addRoleWindow',
            title    : this.addRole,
            autoLoad : {url: 'security/roleDetail.js',scripts:true},
            width    : 500,
            height   : 360
        });
    },
    bindUserFun:function() {
        //  选择的数据记录主键，形如“2, 4, 6, 10”
        var roleIds = this.selModel.selections.keys;
        if(roleIds.length!=1){
            Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowSelectMsg);
            return;
        }
        var roles = this.selModel.getSelections();
        var win = micrite.util.genWindow({
            id: 'userSelectWindow',
            title    : this.bindUser+' -- '+roles[0].get('name'),
            autoLoad : {url: 'security/userSelect.jsp?roleId='+roleIds[0],scripts:true},
            width    : 600,
            height   : 400,
            border   : true
        });
    },
    bindAuthorityFun:function() {
        var roleIds = this.selModel.selections.keys;
        if(roleIds.length!=1){
            Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowSelectMsg);
            return;
        }
        var roles = this.selModel.getSelections();
        var win = micrite.util.genWindow({
            id: 'authoritySelectWindow',
            title    : this.bindAuthority+' -- '+roles[0].get('name'),
            autoLoad : {url: 'security/authoritySelect.jsp?roleId='+roleIds[0],scripts:true},
            width    : 600,
            height   : 400,
            border   : true            
        });        
    },
    deleteRoleFun:function() {
        var roleIds = this.selModel.selections.keys;
        if (roleIds.length <= 0) {
            Ext.MessageBox.alert(mbLocale.infoMsg, mbLocale.gridMultRowSelectMsg);
            return;
        }        
        var deleteRoles = function(buttonId, text, opt) {
            if (buttonId == 'yes') {
                micrite.util.ajaxRequest({
                    url: 'deleteRoles.action',
                    params:{'roleIds':roleIds},
                    success:function(r,o){
                        var res = Ext.decode(r.responseText);
                        if (res&&res.success){
                            this.store.reload();
                        }
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
            fn: deleteRoles,
            icon: Ext.MessageBox.QUESTION
        });        
    },
    saveRoleFun:function() {
        var store = this.getStore();
        var role;
        if(store.getModifiedRecords().length!=1){
            Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowEditMsg);
            return;
        }
        role = store.getModifiedRecords()[0];
        var updateRoles = function(buttonId, text, opt) {
            if (buttonId == 'yes') {
                micrite.util.ajaxRequest({
                    url: 'updateRole.action',
                    params:{'role.id':role.id,
                            'role.name':role.get('name'),
                            'role.description':role.get('description')},
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
            fn: updateRoles,
            icon: Ext.MessageBox.QUESTION
        });        
    }    
});

//  处理多语言
try {roleListLocale();} catch (e) {}

Ext.onReady(function() {
    Ext.QuickTips.init();
    var formPanel = new micrite.security.roleList.SearchPanel();
    
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