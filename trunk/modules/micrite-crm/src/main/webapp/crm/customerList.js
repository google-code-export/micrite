<script type="text/javascript">
Ext.namespace('micrite.crm.customerList');

micrite.crm.customerList.SearchPanel = Ext.extend(micrite.ComplexEditorGrid, {
	
	colModelId:'ID',
	colModelName : 'Name',
	colModelMobile : 'Mobile',
	colModelSource : 'Source',
	colCreation_ts : 'Create Date',
	customerSourceChart:'Customer Source Chart',
	comboEmptyText:'ALL',
	
	initComponent:function() {
	//来源下拉框
	var RecordDef = 
	   Ext.data.Record.create([{name: 'id'},{name: 'name'}]);
	       
	var store = new Ext.data.Store({
		id: Ext.id(),    
		autoLoad:true,
		//设定读取的地址
		proxy : new Ext.data.HttpProxy({url: 'crm/getCustomerPartner.action'}),    
		//设定读取的格式    
		reader : new Ext.data.JsonReader({    
			id:"id"
		}, RecordDef),
		remoteSort: true   
	});
	var SourceTypestore = new Ext.data.Store({
		id: Ext.id(),    
		autoLoad:true,
		//设定读取的地址
		proxy : new Ext.data.HttpProxy({url: 'crm/getCustomerPartnerByAll.action'}),    
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
			advSearchField : [[
						          {name:this.colModelName,value:'name',xtype:'textfield'},
						          {name:this.colCreation_ts,value:'creation_ts',xtype:'uxspinnerdate'}
						      ]],	  
	        compSet: [
	             {url:0,reader:0,columns:0,bbarAction:0},
	             {url:1,reader:0,columns:0,bbarAction:0,tbarAction:-1}
	        ],
			searchMenu : [
				 this.searchCondition1,
				 this.searchCondition2
			],
			searchFields :[[
				 {advSearch:true},
	             this.searchCellphone,
	             {xtype:'textfield',
	              name:'telephone',
	              expression:'like',
	              width:120}
	            ],[
	             this.searchStartTime,
	             {xtype:'uxspinnerdate',
	              name:'startDate',
	              value:new Date(),
	              fieldPosition:'start',
	              strategy: new Ext.ux.form.Spinner.TimeStrategy({defaultValue:'00:00'})},
	             this.searchEndTime,
	             {xtype:'uxspinnerdate',
	              name:'endDate',
	              fieldPosition:'end',
	              value:new Date(),
	              strategy: new Ext.ux.form.Spinner.TimeStrategy({defaultValue:'23:59'})},
	              this.searchCustomerSourceType,
	              {xtype:'combo',
	               name:'customer.customerSource.id',
		           selectOnFocus:true,
		           valueField:'id',
		           hiddenName:'customer.customerSource.id',
		           displayField:'name',
		           fieldLabel: this.sourceText,
//		           emptyText:this.comboEmptyText,
		           editable:false,
		           allowBlank:false,
		           forceSelection:true,
		           triggerAction:'all',
		           store:SourceTypestore,
		           value:'0',
		           typeAhead: true}
	            ]],
	        urls: ['crm/findCustomer.action','crm/findCustomerByDateSpacing.action'],
	        readers : [[
			     {name: 'name'},
			     {name: 'telephone'},
			     {name: 'customerSource',mapping : 'customerSource.id'},
			     {name: 'creation_ts',type : 'date',dateFormat : 'time',mapping : 'creation_ts.time'}
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
		          {
		          	header: this.colCreation_ts,
		          	width: 100, sortable: true, dataIndex: 'creation_ts',
		          	renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
		          },
		          sm
			 ]],
	         tbarActions : [[{
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
	        	 text:'Line Chart',
	        	 iconCls :'line-chart-icon',
	        	 scope:this,
	        	 handler:this.getLineChart
	         },{
	        	 text:this.newCustomer,
	        	 iconCls :'add-icon',
	        	 scope:this,
	        	 handler:this.addCustomer
	         }]],
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
            url: 'crm/getCustomerSourceBarChart.action',
            params:this.getAllField()
        };
		var win = this.genChartWindow(c1,c2);
	}, //eof getBarChart
	
	getPieChart : function() {
		var c1 = {id:'customerList.piechar',
		          title:this.customerSourcePieChart};
        var c2 = {
            url: 'crm/getCustomerSourcePieChart.action',
            params:this.getAllField()
        };
        this.genChartWindow(c1,c2);
	}, //eof getPieChart
	
	getLineChart : function() {
		var c1 = {id:'customerList.linechar',
		          title:this.customerSourcePieChart};
        var c2 = {
            url: 'crm/getCustomerSourceLineChart.action',
            params:this.getAllField()
        };
        this.genChartWindow(c1,c2);
	}, //eof getPieChart
	
	addCustomer : function() {
			var win = micrite.util.genWindow({
	            id       : 'addCustomerWindow',
                title    : this.newCustomer,
                autoLoad : {url: 'crm/customerDetail.js',scripts:true},
                width    : 500,
                height   : 360
            });
	}, //eof addCustomer
	
	deleteCustomer : function() {
		var customerIds = this.selModel.selections.keys;
		var deleteRolesFun = function(buttonId, text, opt) {
			if (buttonId == 'yes') {
				micrite.util.ajaxRequest({
                    url: 'crm/deleteCustomer.action',
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
					url: 'crm/editCustomer.action',
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