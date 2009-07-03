<script type="text/javascript">
Ext.namespace('micrite.security.authorityList');

micrite.security.authorityList.SearchPanel = function() {
    //  查询条件分组名称数组
    this.conNames = [''];
    //  查询条件分组组件组数组
    this.conCmpGroups = [
        [this.name, {xtype:'textfield', name:'authority.name', width:180}]
    ];
    //  超链菜单项数组
    this.actionButtonMenuItems =  [{
        text:this.addAuthority,
        iconCls :'add-icon',
        scope:this,
        handler:function() {
            var win;
            if(!(win = Ext.getCmp('addAuthorityWindow'))){
                win = new Ext.Window({
                    id: 'addAuthorityWindow',
                    title    : this.addAuthority,
                    closable : true,
                    autoLoad : {url: 'security/authorityDetail.js?'+(new Date).getTime(),scripts:true},
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
    this.searchRequestURL = ['/' + document.location.href.split("/")[3] + '/security/findAuthoritiesVague.action'];
    
    //  查询结果格式读取器
    this.resultDataFields = [[
        {name: 'id'},
	    {name: 'name'},
	    {name: 'value'},
	    {name: 'type'}
    ]];
    this.comboGrid = [{url:0,reader:0,column:0,button:0}];
    //  查询结果行选择模型
    this.resultRowSelectionModel = new Ext.grid.CheckboxSelectionModel();

    //  查询结果列
    this.resultColumns = [[
	                         {header: this.name, width: 120, sortable: true, dataIndex: 'name'},
	                         {header: this.value, width: 200, sortable: true, dataIndex: 'value',
	                        	 editor: new Ext.form.TextField({
	                        		 allowBlank: false
	                        	 })
	                         },
                             //{header: this.roles, width: 110, sortable: true, dataIndex: 'rolesString'},
                             {header: this.type, width: 50, sortable: true, dataIndex: 'type'},
                             this.resultRowSelectionModel
                         ]];
    this.edit = true;
    //  动作按钮数组
    this.resultProcessButtons = [[{
        text:this.bindRole, 
        iconCls :'bind-icon',
        scope:this,
        handler:function() {
            //  选择的数据记录主键，形如“2, 4, 6, 10”
            var authorityIds = this.resultGrid.selModel.selections.keys;
            if(authorityIds.length != 1){
                Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowSelectMsg);
                return;
            }
            var authority = this.resultGrid.selModel.getSelections();
            var win;
            if(!(win = Ext.getCmp('roleSelectWindow'))){
                win = new Ext.Window({
                    id: 'roleSelectWindow',
                    title    : this.bindRole  + ' -- ' +  authority[0].get('name') ,
                    closable : true,
                    autoLoad : {url: 'security/roleSelect.jsp?authorityId=' + authorityIds[0] + '&' + (new Date).getTime(), scripts:true},
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
            var authorityIds = this.resultGrid.selModel.selections.keys;
            if(authorityIds.length<1){
                Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridMultRowSelectMsg);
                return;
            }
            var deleteAuthorityFun = function(buttonId, text, opt) {
                if (buttonId == 'yes') {
                    Ext.Ajax.request({
                        url:'/' + document.location.href.split("/")[3] + '/deleteAuthority.action',
                        params:{'authIds':authorityIds},
                        scope:this,
                        callback:function(options,success,response) {
                            if (Ext.util.JSON.decode(response.responseText).success) {
                            	this.refresh();
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
                fn: deleteAuthorityFun,
                icon: Ext.MessageBox.QUESTION
            });        
        }
    },{
        text:mbLocale.submitButton, 
        iconCls :'save-icon',
        scope:this, 
        handler:function() {
            var store = this.resultGrid.getStore();
            var authority;
            if(store.getModifiedRecords().length!=1){
                Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowEditMsg);
                return;
            }
            authority = store.getModifiedRecords()[0];
            var updateAuthorityFun = function(buttonId, text, opt) {
                if (buttonId == 'yes') {
                    Ext.Ajax.request({
                        url:'/' + document.location.href.split("/")[3] + '/updateAuthority.action',
                        params:{'authority.id':authority.get('id'),
                                'authority.value':authority.get('value')},
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
                fn: updateAuthorityFun,
                icon: Ext.MessageBox.QUESTION
            });        
        }
    }]];
    
    micrite.security.authorityList.SearchPanel.superclass.constructor.call(this);
};

Ext.extend(micrite.security.authorityList.SearchPanel, micrite.panel.ComplexSearchPanel, {
    byName:'By Name',
    name:'Name',
    addAuthority:'Add Authority',
    value:'Value',
    type:'Type',
    bindRole:'Bind Role'
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
        new Ext.Viewport({
            layout:'fit',
            items:[formPanel]
        });
    }
});
</script>