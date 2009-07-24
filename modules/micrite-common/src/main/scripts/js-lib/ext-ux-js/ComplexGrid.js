Ext.ns('micrite');
/**
yourClassName = Ext.extend(micrite.ComplexEditorGrid, {
	//class本身多语言字符，声明在这里
	anyName :'ID',

	initComponent:function() { 
	配置项说明
	var config = {
	        基本配置项，必须声明
			可以是一个空对象，如果有bbarAction必须配置
			配置项的值，0，1，2...等等代表对应数组的下标
	        compSet: [
	             {url:0,reader:0,columns:0,bbarAction:0}
	        ],
	        组合查询条件切换菜单，选项
	           根据需要配置，如果没有多个组合条件，则不需要声明
			searchMenu : [
				 this.s1,s2
			],
			查询条件，必须声明
			searchFields :[[
	             this.searchCellphone,
	             {xtype:'textfield',
	              name:'customer.telephone',
	              width:120}
	            ],[
	             this.searchStartTime,
	             {xtype:'datefield', name:'startTime', width:135},
	             this.searchEndTime,
	             {xtype:'datefield', name:'endTime', width:135}
	        ]],
	        查询请求地址，必须声明
	        urls: ['/crm/findCustomer.action','/crm/findCustomerNew.action'],
	        readers : [[
			     {name: 'id',type:'int'},
			     {name: 'name'},
			     {name: 'telephone'},
			     {name: 'customer_source_id'}
	        ]],
	        表格字段，必须声明
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
		          new Ext.grid.CheckboxSelectionModel()
	         ]],
	         表格下方工具栏按钮，选项
	         tbarActions : [{
	        	 text:this.customerSourceBarChart,
	        	 iconCls :'bar-chart-icon',
	        	 scope:this,
	        	 handler:this.yourFun
	         }],
	         表格上方工具栏最右端菜单，选项
	         bbarActions:[[{
	        	 text:mbLocale.deleteButton, 
	        	 iconCls :'delete-icon',
	        	 scope:this, 
	        	 handler:this.yourFun
	         }]]
	    }; // eo config object
		// apply config
		//以下两行代码必须有
		Ext.apply(this, Ext.apply(this.initialConfig, config)); 
		yourClassName.superclass.initComponent.apply(this, arguments);
	}，
	//你的handler调用的函数声明在下面
	yourFun : function() {
        });
	} //eof addCustomer
}); //eo extend	
**/
micrite.ComplexGrid = {
    border : false,
    spinnerDateSuffix : '_time',
    pageSize : parseInt(Ext.getDom('pageSize').value,10),
    varName : {}, //用于存贮date和time用的临时变量
    dateErrorMsg : 'This is not a valid date - it must be in the format 2008-01-01',
    timeErrorMsg : 'This is not a valid time - it must be in the format 22:00',
    urlPrefix : '/' + document.location.href.split("/")[3],
    initComponent:function() {
 
	    var config = {
			layout : 'fit',
			loadMask : true,
	        viewConfig:{forceFit:true}
	    }; // eo config object
        // apply config
        Ext.apply(this, Ext.applyIf(this.initialConfig, config));
        if (this.compSet){
        	Ext.each(this.compSet,this.initCompSet,this);
        	this.previousCompSet = {}; //用于判断bbar上的button是否重新生成
        }
        //  创建tbar
        this.tbar = new Ext.Toolbar();

        //创建分组查询条件菜单
        if (this.searchMenu){
	         //  创建查询条件按钮上的菜单
	        var menu = new Ext.menu.Menu();
	        for (var i = 0; i < this.searchMenu.length; i++) {
	            menu.add({
	                text:this.searchMenu[i],
	                value:i,
	                scope:this,
	                handler: this.switchGroup
	            });
	        }
	        //  创建查询条件按钮
	        var searchMenu = new Ext.Button({
	            text:this.searchMenu[0],
	            value:0,
	            tooltip:'Click for more search options',
	            menu:menu
	        });
	        this.tbar.add([searchMenu,'-']);            
        }
//        //创建分组查询组件
        if (this.searchFields){
	        //  查询按钮
	        var searchButton = {
	            text:mbLocale.searchButton,
	            cls:'x-btn-text-icon details',
	            scope:this,
	            handler:this.startSearch
	        };
            //  向每个查询条件组中加入组件：“查询按钮”
            for (i = 0; i < this.searchFields.length; i++) {
                this.searchFields[i] = this.searchFields[i].concat(['-', searchButton]);
            }
        }
//        //创建actionButton
        if (this.tbarActions){
        	var a = {
                text:mbLocale.actionMenu,
                menu:{xtype:'menu', items:this.tbarActions}
        	};
        	//  向每个查询条件组中加入组件：“动作按钮”
	        for (i = 0; i < this.searchFields.length; i++) {
	            this.searchFields[i] = this.searchFields[i].concat(['->', a]);
	        }
        }
        this.colModel = this.getColumnById(0);
        
        //创建数据源
        this.store = this.getStoreById(0);
        //创建分页工具栏
        this.bbar = new Ext.PagingToolbar({
            pageSize : this.pageSize,
            store : this.store,
            displayInfo : true,
            plugins: new Ext.ux.ProgressBarPager(),
            listeners : {
            	scope : this,
                render:function(c){
                }
             }
        });
        micrite.ComplexGrid.superclass.initComponent.apply(this, arguments);
    }, /** eo function initComponent **/
    switchGroup : function(o,t){
        var menuBtn = this.getTopToolbar().items.first();
        if (menuBtn.value != o.value){
            menuBtn.setText(o.text);
            menuBtn.value = o.value;
            this.genTopField(o.value);
            this.genButtomField(o.value);
            this.resetGrid();
            this.reconfigureGrid(o.value);
            this.previousCompSet = this.compSet[menuBtn.value];
            
        }
    },
    genTopField : function(i){
    	
    	var toolbar = this.getTopToolbar();
    	while (toolbar.items.length > 2) {
            var item = toolbar.items.last();
            toolbar.items.remove(item);
            item.destroy();
        }
	    var items = this.searchFields[i];
	    this.curFields = [];
	    /**
	     * 使用curFields的长度作为下标，是为了保证数组长度和提交字段数量一致
	     * 在每个判断下赋值是为了过滤一些非控件的item
	     */
	    var dateIndex = true;
	  
	    this.varName['startDate'] = Ext.id(); 
	    this.varName['endDate'] = Ext.id();
	    
	    for (var j = 0; j < items.length; j++) {
	        item = items[j];
	        if (item.xtype == 'textfield') {
	            item = new Ext.form.TextField(item);
	            this.curFields[this.curFields.length] = item;
	        } else if (item.xtype == 'checkbox') {
	        	Ext.apply(item,{listeners:{check:function(cb,checked){
	        		if (!checked){
		        		Ext.each(Ext.select('[name='+cb.name+']').elements,function(item,index){
		        			if (item.checked) checked = true;
		        		});
		        		if (!checked) cb.el.dom.checked = true;
	        		}
	        	}}});
	            item = new Ext.form.Checkbox(item);
	            this.curFields[this.curFields.length] = item;
	        } else if (item.xtype == 'datefield') {
	        	this.setDateConfig(item);
	            item = new Ext.form.DateField(item);
	            this.curFields[this.curFields.length] = item;
	        } else if (item.xtype == 'radio') {
                item = new Ext.form.Radio(item);
                this.curFields[this.curFields.length] = item;
            } else if (item.xtype == 'uxspinner') {
                item = new Ext.ux.form.Spinner(item);
                this.curFields[this.curFields.length] = item;
            } else if (item.xtype == 'uxspinnerdate') {
            	var time = {
            		width : 80,
            		id : item.name + this.spinnerDateSuffix,
            		strategy : item.strategy
            	}
            	this.setDateConfig(item);
                item = new Ext.form.DateField(item);
                this.curFields[this.curFields.length] = item;
                toolbar.add(item);
                item = new Ext.ux.form.Spinner(time);
            }
            toolbar.add(item);
	    }
	    toolbar.doLayout();
	    this.varName = {};
    },
    genButtomField : function(i){
    	if (this.previousCompSet.bbarAction == this.compSet[i].bbarAction){
    		return;
    	}
    	var toolbar = this.getBottomToolbar();
	   	while (toolbar.items.length > 13) {
	   		var item = toolbar.items.itemAt(toolbar.items.length-3);
	   		toolbar.items.remove(item);
	   		item.destroy();
	   	}

	   	if (this.bbarActions.length && this.compSet[i].bbarAction > -1) {
	   		toolbar.insertButton(11,'-');
	   		toolbar.insertButton(12,this.bbarActions[this.compSet[i].bbarAction]);
	   	}
	    toolbar.doLayout();
    },
    getColumnById : function(i){
        var rn = new Ext.grid.RowNumberer(
            {
                scope : this,
                renderer:function(value, metadata, record, rowIndex) {
                    //var start = this.store.lastOptions.params.start;
                    return  1 + rowIndex;
                }
            }
        );
        return new Ext.grid.ColumnModel([rn].concat(this.columnsArray[this.compSet[i].columns]));
    },
    getStoreById : function(i){
        var store =  new Ext.data.Store({
            proxy : new Ext.data.HttpProxy(Ext.apply({
                    	url: this.urlPrefix + this.urls[this.compSet[i].url]
                    },micrite.util.proxyLoad())),
            reader : new Ext.data.JsonReader({
	                	totalProperty:'totalCount',
	            	    root:'data',id:'id'},
	            	    this.readers[this.compSet[i].reader])
        });
        return store;
     },
     startSearch : function() {
            this.store.baseParams = {};
            var value = null,name = null,temp = null,idx = [];
            for (var i = 0; i < this.curFields.length; i++) {
            	name = this.curFields[i].getName();
                if (this.curFields[i].xtype == 'checkbox') {
                	if (temp != name){
                		temp = name;
                		idx = [];
                	}
                	if (this.curFields[i].checked){
                		value = this.curFields[i].getRawValue();
                		//如果没有指定Value，value值为true
                		if (this.curFields[i].inputValue == undefined){
                			value = true;
                		}
                		idx[idx.length] = value;
                		value = idx;
                		this.store.baseParams[name] = value;
                	}
                } else if (this.curFields[i].xtype == 'radio') {
                 	if (this.curFields[i].checked){
                		value = this.curFields[i].getRawValue();
                		this.store.baseParams[name] = value;
                	}
            	 } else if (this.curFields[i].xtype == 'uxspinnerdate') {
            		if (!this.curFields[i].isValid()){
            			showMsg('failure',this.dateErrorMsg);
            			return;
            		}
                	value = this.curFields[i].getRawValue();
                	var time = Ext.getCmp(this.curFields[i].name + this.spinnerDateSuffix);
                	value = value + ' ' + time.getRawValue();
                	if (!time.isValid()){
                		showMsg('failure',this.timeErrorMsg);
                		return;
                	}
                    this.store.baseParams[name] = value;
                } else {
                	 console.log(this.curFields[i].isValid());
                	value = this.curFields[i].getRawValue();
                    this.store.baseParams[name] = value;
                }
            }
            this.store.rejectChanges(); 
            this.store.load({params:{start:0, limit:this.pageSize},callback:function(r,o,s){
                 // console.log(33);
            }});
     },
     initCompSet : function(item,index,allItem) {
        	var defaultCompSet = {url:0,reader:0,columns:0,bbarAction:-1};
        	this.compSet[index] = Ext.apply(defaultCompSet,item);
     },
     resetGrid : function() {
        	this.getStore().removeAll();
        	var toolbar = this.getBottomToolbar();
            toolbar.afterTextItem.setText(String.format(toolbar.afterPageText, 1));
            toolbar.inputItem.setValue('');
            toolbar.first.setDisabled(true);
            toolbar.prev.setDisabled(true);
            toolbar.next.setDisabled(true);
            toolbar.last.setDisabled(true);
            toolbar.updateInfo();
            toolbar.displayItem.reset(false);
        },
     reconfigureGrid : function(i){
     	if (this.compSet[i].url != this.previousCompSet.url 
     	  || this.compSet[i].columns  != this.previousCompSet.columns){
     		 var store = this.getStoreById(i);
             this.reconfigure(store,this.getColumnById(i));
             this.getBottomToolbar().bind(store);
     	}
     },
     genChartWindow : function(c1,c2) {
     	var win = micrite.util.genWindow(Ext.apply(c1,{border:true}));
     	if (!win) return;
        Ext.apply(c2,{
        	success:function(r,o){
            var obj = Ext.decode(r.responseText);
                  win.getLayoutTarget().update('<img src = "'+ this.urlPrefix +
                        '/DisplayChart?filename='+obj.filename+'">');
            }
        });
        micrite.util.ajaxRequest(c2,this);
        return win;
     },
     setDateConfig : function(item){
     	Ext.apply(item,{
    		format: 'Y-m-d'
    	});
     	//加入了一个fieldPosition参数，用于控制日期的范围
    	if (item.fieldPosition == 'start'){
        	Ext.apply(item,{
        		id : this.varName['startDate'],
        		endDateField : this.varName['endDate'],
        		vtype : 'daterange'
        	});
        }else if (item.fieldPosition == 'end'){
        	Ext.apply(item,{
        		id : this.varName['endDate'],
        		startDateField : this.varName['startDate'],
        		vtype : 'daterange'
        	});
        }
    	return item;
     },
     listeners:{
        render:function() {
    	   this.genTopField(0);
    	   this.genButtomField(0);
    	   this.previousCompSet = this.compSet[0];
        }
     }
};

micrite.ComplexEditorGrid = Ext.extend(Ext.grid.EditorGridPanel,micrite.ComplexGrid);
micrite.ComplexGrid = Ext.extend(Ext.grid.GridPanel,micrite.ComplexGrid);
//eof
Ext.reg('complexgrid', micrite.ComplexGrid);


Ext.apply(Ext.form.VTypes, {
    daterange : function(val, field) {
        var date = field.parseDate(val);

        if(!date){
            return;
        }
        if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
            var start = Ext.getCmp(field.startDateField);
            start.setMaxValue(date);
            start.validate();
            this.dateRangeMax = date;
        } 
        else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
            var end = Ext.getCmp(field.endDateField);
            end.setMinValue(date);
            end.validate();
            this.dateRangeMin = date;
        }
        return true;
    }
});

