<script type="text/javascript">
Ext.namespace('micrite.crm.customerList');

micrite.crm.customerList.SearchPanel = function() {
    //  查询条件分组名称数组
    this.conNames = [this.searchCondition1, this.searchCondition2,this.searchCondition3];
    //  查询条件分组组件组数组
    this.conCmpGroups = [
   		[	this.searchCellphone, {xtype:'textfield', name:'telephone', width:120}],
   		[	this.searchStartTime, {xtype:'textfield', name:'time', width:120}],
        [	this.searchStartTime, {xtype:'datetimefield', name:'startTime', width:135},
        	this.searchEndTime,	{xtype:'datetimefield', name:'endTime', width:135},
        	'Gb',	{xtype:'checkbox', name:'gb', width:40,height:20},
        	'Gn',	{xtype:'checkbox', name:'gn', width:40,height:20},
        	'Gi',	{xtype:'checkbox', name:'gi', width:40,height:20},
        	'Gw',	{xtype:'checkbox', name:'gw', width:40,height:20}]
    ];
    //  超链菜单项数组
    this.actionButtonMenuItems =  [{
        text:this.customerSourceChart,
        iconCls :'add-icon',
        scope:this,
        handler:function() {
        	Ext.Ajax.request({
        		scope:this,
				url:'crm/getCustomerSourceBarChart.action?'+(new Date).getTime(),
				params:{telephone:this.curConFields[0].getRawValue()},
				method:'post',
				success:function(response){
					obj = Ext.util.JSON.decode(response.responseText);
					var filename = obj.filename;
					var win;
					if(!(win = Ext.getCmp('customerSourceChart'))){
				        win = new Ext.Window({
					            id    : 'customerSourceChart',
					            title	: this.customerSourceChart,
					            closable : true,
					            width    : 630,
					            height   : 520,
					            plain    : true,
					            maximizable: true,
					            renderTo : mainPanel.getActiveTab().items.itemAt(0).getId(),
								html:    '<img src = "'+'/' + document.location.href.split("/")[3]
				                                                                                  + '/DisplayChart?filename='+filename+'">',
					            layout   : 'fit'
				        	});
				        win.show();
				        win.center();
					}
				},
				failure:function(){
					Ext.Msg.alert('info','FALSE');
				}
    		});
        }
    },{
        text:this.newCustomer,
        iconCls :'add-icon',
        scope:this,
        handler:function() {
			var win;
	    	if(!(win = Ext.getCmp('addCusetomerWindow'))){
		        win = new Ext.Window({
		        	id: 'addCusetomerWindow',
		            title    : this.newCustomer,
		            closable : true,
		            autoLoad : {url: 'crm/customerDetail.js?'+(new Date).getTime(),scripts:true},
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
    this.searchRequestURL = ['/' + document.location.href.split("/")[3] + '/crm/findCustomer.action',null,null];
    
    //  查询结果数据按此格式读取
    this.resultDataFields = [[
		{name: 'id',type:'int'},
		{name: 'name'},
		{name: 'telephone'},
		{name: 'customer_source_id'}
    ]];
    this.comboGrid = [{url:0,reader:0,column:0,button:0},{url:0,reader:0,column:0,button:0},{url:0,reader:0,column:0,button:0}];
    //  查询结果行选择模型
    this.resultRowSelectionModel = new Ext.grid.CheckboxSelectionModel();
    
    //来源下拉框
    var RecordDef = Ext.data.Record.create([    
            {name: 'id'},{name: 'name'}                   
    ]); 
    var store = new Ext.data.Store({
    	id: Ext.id(),    
        autoLoad:true,
        //设定读取的地址
        proxy: new Ext.data.HttpProxy({url: '/' + document.location.href.split("/")[3] + '/crm/getCustomerPartner.action'}),    
        //设定读取的格式    
        reader: new Ext.data.JsonReader({    
            id:"id"
        }, RecordDef),
        remoteSort: true   
    });
	//下拉框renderer    
    comboBoxRenderer = function(combo) {
 		return function(value) {
			var idx = combo.store.find(combo.valueField, value);
		    var rec = combo.store.getAt(idx);
		    return (rec == null ? value : rec.get(combo.displayField) );
			
  		};
	}
    var combo = new Ext.form.ComboBox({
                valueField: "id",
  				displayField: "name",
  				store: store,
  				triggerAction :'all',
  				lazyRender:false 
    });
    
    //  查询结果列
    this.resultColumns = [[
         {header: this.colModelName, width: 100, sortable: true, dataIndex: 'name',editor:new Ext.form.TextField({
         	allowBlank: false
         })},
         {header: this.colModelMobile, width: 100, sortable: true, dataIndex: 'telephone',editor:new Ext.form.TextField({
         	allowBlank: false
         })},
         {header: this.colModelSource, width: 100, sortable: true, dataIndex: 'customer_source_id',editor:combo,renderer:comboBoxRenderer(combo)},
         this.resultRowSelectionModel
    ]];
	 this.edit = true;
    //  动作按钮数组
    this.resultProcessButtons = [[{
        text:mbLocale.deleteButton, 
        iconCls :'delete-icon',
        scope:this, 
        handler:function() {
            var customerIds = this.resultGrid.selModel.selections.keys;
            var deleteRolesFun = function(buttonId, text, opt) {
                if (buttonId == 'yes') {
                    Ext.Ajax.request({
                        url:'/' + document.location.href.split("/")[3] + '/crm/deleteCustomer.action',
                        params:{'customerIds':customerIds},
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
                fn: deleteRolesFun,
                icon: Ext.MessageBox.QUESTION
            });        
        }
    },{
        text:mbLocale.submitButton, 
        iconCls :'save-icon',
        scope:this, 
        handler:function() {
            var store = this.resultGrid.getStore();
            if(store.getModifiedRecords().length!=1){
                Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowEditMsg);
                return;
            }
            var customer = store.getModifiedRecords()[0];
            var updateRolesFun = function(buttonId, text, opt) {
                if (buttonId == 'yes') {
                    Ext.Ajax.request({
                        url:'/' + document.location.href.split("/")[3] + '/crm/editCustomer.action',
                        params:{'customerSourceId':customer.get('customer_source_id'),
                                'customer.name':customer.get('name'),
                                'customer.telephone':customer.get('telephone'),
                                'customer.id':customer.get('id')},
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
                fn: updateRolesFun,
                icon: Ext.MessageBox.QUESTION
            });        
        }
    }]];
    
    micrite.crm.customerList.SearchPanel.superclass.constructor.call(this);
};

Ext.extend(micrite.crm.customerList.SearchPanel, micrite.panel.ComplexSearchPanel, {
    colModelId : 'ID',
    colModelName : 'Name',
    colModelMobile : 'Mobile',
    colModelSource : 'Source',
    customerSourceChart:'Customer Source Chart'
});

//  处理多语言
try {customerListLocale();} catch (e) {}
try {baseLocale();} catch (e) {}
/**
 * 由于收到的数据是个数组，所以需要使用该函数提取值
 * 
 * @param val
 * @return
 */
function sourceType(val) {
    return val.name;
}

Ext.onReady(function() {
    Ext.QuickTips.init();
    var formPanel = new micrite.crm.customerList.SearchPanel();
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