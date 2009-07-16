<script type="text/javascript">
Ext.namespace('micrite.crm.customerList');

micrite.crm.customerList.SearchPanel = Ext.extend(micrite.ComplexEditorGrid, {
	
	colModelId:'ID',
	colModelName : 'Name',
	colModelMobile : 'Mobile',
	colModelSource : 'Source',
	customerSourceChart:'Customer Source Chart',
	
	initComponent:function() {
	//来源下拉框
	var RecordDef = 
	   Ext.data.Record.create([{name: 'id'},{name: 'name'}]);
	       
	var store = new Ext.data.Store({
		id: Ext.id(),    
		autoLoad:true,
		//设定读取的地址
		proxy : new Ext.data.HttpProxy({url: this.urlPrefix + '/crm/getCustomerPartner.action'}),    
		//设定读取的格式    
		reader : new Ext.data.JsonReader({    
			id:"id"
		}, RecordDef),
		remoteSort: true   
	});
	// 下拉框renderer
	var comboBoxRenderer = function(combo) {
		return function(value) {
			var idx = combo.store.find(combo.valueField, value);
			var rec = combo.store.getAt(idx);
			return (rec === null ? value : rec.get(combo.displayField) );
	
		};
	};
	var combo = new Ext.form.ComboBox({
		valueField: "id",
		displayField: "name",
		store: store,
		triggerAction :'all',
		lazyRender:false 
	});
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	var config = {
	        compSet: [
	             {url:0,reader:0,columns:0,bbarAction:0},
	             {url:0,reader:0,columns:0,bbarAction:0},
	             {url:0,reader:0,columns:1}
	        ],
			searchMenu : [
				 this.searchCondition1,
				 this.searchCondition2,
				 this.searchCondition3
			],
			searchFields :[[
	             this.searchCellphone,
	             {xtype:'textfield',
	              name:'customer.telephone',
	              width:120}
	            ],[
	             this.searchStartTime,
	             {xtype:'datefield',
	              name:'mydate',
	              width:135},
	             {xtype:'uxspinner',
	              name:'mydate',
	              fieldLabel: 'Time',
	              strategy: new Ext.ux.form.Spinner.TimeStrategy(),
	              width:135}
	            ],[
	             this.searchStartTime,
	             {xtype:'datefield', name:'startTime', width:135},
	             this.searchEndTime,
	             {xtype:'datefield', name:'endTime', width:135},
	             'Gb',
	             {xtype:'checkbox', name:'gb', width:40,height:20},
	             'Gn',
	             {xtype:'checkbox', name:'gn', width:40,height:20},
	             'Gi',
	             {xtype:'checkbox', name:'gi', width:40,eight:20},
	             'Gw',
	             {xtype:'checkbox', name:'gw', width:40,height:20}
	        ]],
	        urls: ['/crm/findCustomer.action','/crm/findCustomerNew.action'],
	        readers : [[
			     {name: 'name'},
			     {name: 'telephone'},
			     {name: 'customerSource',mapping : 'customerSource.id'}
	        ]],
			columnsArray: [[
		          {
		          	header:this.colModelName,
		          	width:100, sortable: true,dataIndex: 'name',
		          	editor:new Ext.form.TextField({allowBlank: false})
		          },
		          {
		          	header: this.colModelMobile,
		          	width: 100, sortable: true, dataIndex: 'telephone',
		          	editor:new Ext.form.TextField({allowBlank: false})
		          },
		          {
		          	header: this.colModelSource,
		          	width: 100, sortable: true, dataIndex: 'customerSource',
		          	editor:combo,renderer:comboBoxRenderer(combo)
		          },
		          sm
			 ],[
                  {
                    header:'col1',
                    width:100, sortable: true,dataIndex: 'name',
                    editor:new Ext.form.TextField({allowBlank: false})
                  },
                  {
                    header: 'col2',
                    width: 100, sortable: true, dataIndex: 'telephone',
                    editor:new Ext.form.TextField({allowBlank: false})
                  },
                  {
                    header: 'col3',
                    width: 100, sortable: true, dataIndex: 'customer_source_id',
                    editor:combo,renderer:comboBoxRenderer(combo)
                  },
                  sm
             ]],
	         tbarActions : [{
	        	 text:this.customerSourceBarChart,
	        	 iconCls :'bar-chart-icon',
	        	 scope:this,
	        	 handler:this.getBarChart
	         },{
	        	 text:this.customerSourcePieChart,
	        	 iconCls :'pie-chart-icon',
	        	 scope:this,
	        	 handler:this.getPieChart
	         },{
	        	 text:this.newCustomer,
	        	 iconCls :'add-icon',
	        	 scope:this,
	        	 handler:this.addCustomer
	         }],
	         bbarActions:[[{
	        	 text:mbLocale.deleteButton, 
	        	 iconCls :'delete-icon',
	        	 scope:this, 
	        	 handler:this.deleteCustomer
	         },{
	        	 text:mbLocale.submitButton, 
	        	 iconCls :'save-icon',
	        	 scope:this, 
	        	 handler:this.saveCustomer
	         }]],
	         sm:sm
	    }; // eo config object

		// apply config
		Ext.apply(this, Ext.apply(this.initialConfig, config)); 
		micrite.crm.customerList.SearchPanel.superclass.initComponent.apply(this, arguments);
    }, // eo function initComponent
    
	getBarChart : function(o,t,id) {
		var c1 = {id:'customerList.barchar',
		          title:this.customerSourceBarChart};
		var c2 = {
            url: this.urlPrefix + '/crm/getCustomerSourceBarChart.action',
            params:{'customer.telephone':this.curFields[0].getRawValue()}
        };
		var win = this.genChartWindow(c1,c2);
	}, //eof getBarChart
	
	getPieChart : function() {
		var c1 = {id:'customerList.piechar',
		          title:this.customerSourcePieChart};
        var c2 = {
            url: this.urlPrefix + '/crm/getCustomerSourcePieChart.action',
            params:{'customer.telephone':this.curFields[0].getRawValue()}
        };
        this.genChartWindow(c1,c2);
	}, //eof getPieChart
	
	addCustomer : function() {
			var win = micrite.util.genWindow({
	            id       : 'addCustomerWindow',
                title    : this.newCustomer,
                autoLoad : {url: this.urlPrefix + '/crm/customerDetail.js',scripts:true},
                width    : 500,
                height   : 360
            });
	}, //eof addCustomer
	
	deleteCustomer : function() {
		var customerIds = this.selModel.selections.keys;
		var deleteRolesFun = function(buttonId, text, opt) {
			if (buttonId == 'yes') {
				micrite.util.ajaxRequest({
                    url: this.urlPrefix + '/crm/deleteCustomer.action',
                    params:{'customerIds':customerIds},
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
			fn: deleteRolesFun,
			icon: Ext.MessageBox.QUESTION
		});        
	}, //eof deleteCustomer
	
	saveCustomer : function() {
		var store = this.getStore();
		if(store.getModifiedRecords().length!=1){
			Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowEditMsg);
			return;
		}
		var customer = store.getModifiedRecords()[0];
		var updateRolesFun = function(buttonId, text, opt) {
			if (buttonId == 'yes') {
				micrite.util.ajaxRequest({
					url: this.urlPrefix + '/crm/editCustomer.action',
					params:{'customer.customerSource.id':customer.get('customerSource'),
					'customer.name':customer.get('name'),
					'customer.telephone':customer.get('telephone'),
					'customer.id':customer.id},
					scope:this,
					success:function(r,o){
                        this.getStore().commitChanges();
                    },
                    failure:Ext.emptyFn
				});
			}
		};
		Ext.Msg.show({
			title:mbLocale.infoMsg,
			msg: mbLocale.updateConfirmMsg,
			buttons: Ext.Msg.YESNO,
			scope: this,
			fn: updateRolesFun,
			icon: Ext.MessageBox.QUESTION
		});        
	} // eof saveCustomer
}); //eo extend

// 处理多语言
try {customerListLocale();} catch (e) {}

Ext.onReady(function() {
	Ext.QuickTips.init();
	var formPanel = new micrite.crm.customerList.SearchPanel();
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