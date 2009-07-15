<script type="text/javascript">
Ext.namespace('micrite.security.authorityList');

micrite.security.authorityList.SearchPanel = Ext.extend(micrite.ComplexEditorGrid, {
    byName:'By Name',
    name:'Name',
    addAuthority:'Add Authority',
    value:'Value',
    type:'Type',
    bindRole:'Bind Role',

    initComponent:function() { 
    var sm = new Ext.grid.CheckboxSelectionModel();
    var config = {
            compSet:[{url:0,reader:0,columns:0,bbarAction:0}],
            searchFields :[[
                this.name, {xtype:'textfield', name:'authority.name', width:180}
            ]],
            urls: ['/security/findAuthoritiesVague.action'],
            readers : [[
                {name: 'id'},
                {name: 'name'},
                {name: 'value'},
                {name: 'type'}
            ]],
            columnsArray: [[
                {header: this.name, width: 120, sortable: true, dataIndex: 'name'},
                {header: this.value, width: 200, sortable: true, dataIndex: 'value',
                    		editor: new Ext.form.TextField({allowBlank: false})},
                {header: this.type, width: 50, sortable: true, dataIndex: 'type'},
                sm
             ]],
             tbarActions : [{
                 text:this.addAuthority,
                 iconCls :'add-icon',
                 scope:this,
                 handler:this.addAuthorityFun
             }],
             bbarActions:[[{
                 text:this.bindRole, 
                 iconCls :'bind-icon',
                 scope:this, 
                 handler:this.bindRoleFun
             },{
                 text:mbLocale.deleteButton, 
                 iconCls :'delete-icon',
                 scope:this, 
                 handler:this.deleteAuthorityFun
             },{
                 text:mbLocale.submitButton, 
                 iconCls :'save-icon',
                 scope:this, 
                 handler:this.saveAuthorityFun
             }]],
             sm:sm
        }; // eo config object
    Ext.apply(this, Ext.apply(this.initialConfig, config)); 
    micrite.security.authorityList.SearchPanel.superclass.initComponent.apply(this, arguments);    
    },

    addAuthorityFun :function() {
        var win = micrite.util.genWindow({
            id: 'addAuthorityWindow',
            title    : this.addAuthority,
            autoLoad : {url: this.urlPrefix + '/security/authorityDetail.js',scripts:true},
            width    : 500,
            height   : 360
        });
    },

    bindRoleFun:function() {
        //  选择的数据记录主键，形如“2, 4, 6, 10”
        var authorityIds = this.selModel.selections.keys;
        if(authorityIds.length!=1){
            Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowSelectMsg);
            return;
        }
        var authorities = this.selModel.getSelections();
        var win = micrite.util.genWindow({
            id: 'userSelectWindow',
            title    : this.bindUser+' -- '+authorities[0].get('name'),
            autoLoad : {url: this.urlPrefix + '/security/roleSelect.jsp?authorityId='+authorityIds[0],scripts:true},
            width    : 500,
            height   : 360
        });
    },

    deleteAuthorityFun:function() {
        var authorityIds = this.selModel.selections.keys;
        var deleteAuthorities = function(buttonId, text, opt) {
            if (buttonId == 'yes') {
                micrite.util.ajaxRequest({
                    url: this.urlPrefix + '/deleteAuthority.action',
                    params:{'authIds':authorityIds},
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
            fn: deleteAuthorities,
            icon: Ext.MessageBox.QUESTION
        });        
    },
    
    saveAuthorityFun:function() {
        var store = this.getStore();
        var authority;
        if(store.getModifiedRecords().length!=1){
            Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowEditMsg);
            return;
        }
        authority = store.getModifiedRecords()[0];
        var updateAuthorities = function(buttonId, text, opt) {
            if (buttonId == 'yes') {
                micrite.util.ajaxRequest({
                    url: this.urlPrefix + '/updateAuthority.action',
                    params:{'authority.id':authority.get('id'),
                        'authority.value':authority.get('value')},
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
            fn: updateAuthorities,
            icon: Ext.MessageBox.QUESTION
        });        
    }    
});

//  处理多语言
try {authorityListLocale();} catch (e) {}

Ext.onReady(function() {
    Ext.QuickTips.init();
    var formPanel = new micrite.security.authorityList.SearchPanel();
    
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