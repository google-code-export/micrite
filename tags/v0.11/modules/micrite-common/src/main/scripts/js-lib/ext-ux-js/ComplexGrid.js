Ext.ns('micrite');
micrite.ComplexGrid = {
    border : false,
    pageSize : parseInt(Ext.getDom('pageSize').value,10),
    dateErrorMsg : 'This is not a valid date - it must be in the format 2008-01-01',
    timeErrorMsg : 'This is not a valid time - it must be in the format 22:00',
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
	            xtype : 'button',
	            scope:this,
	            searchButton : true,
	            handler:this.startSearch
	        };
            //  向每个查询条件组中加入组件：“查询按钮”
            for (i = 0; i < this.searchFields.length; i++) {
                this.searchFields[i] = this.searchFields[i].concat(['-', searchButton]);
            }
        }
//        //创建actionButton
        if (this.tbarActions){
        	//  向每个查询条件组中加入组件：“动作按钮”
	        for (i = 0; i < this.searchFields.length; i++) {
	        	if (this.compSet[i].tbarAction > -1){
	        		var tbarAction = {
	                    text:mbLocale.actionMenu,
	                    menu:{xtype:'menu', items:this.tbarActions[this.compSet[i].tbarAction]}
                    };
                    this.searchFields[i] = this.searchFields[i].concat(['->', tbarAction]);
	        	}
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
        	this.queryData = undefined;
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
	 
	    var advSearch = items[0].advSearch;
	   
	    this.curFields = [];
	    //为了避免页面id冲突
	    var varName = {};
	    varName.startDate = Ext.id(); 
	    varName.endDate = Ext.id();
	    var radioPrefix = Ext.id();
	    /**
	     * 使用curFields的长度作为下标，是为了保证数组长度和提交字段数量一致
	     * 在每个判断下赋值是为了过滤一些非控件的item
	     */
        for (var j = 0; j < items.length; j++) {
	        item = items[j];
	        if (!item.realname){
	        	item.realname = item.name;
	        	item.name = radioPrefix + item.name;
	        }
	        if (item.xtype == 'textfield') {
	            item = new Ext.form.TextField(item);
	            this.curFields[this.curFields.length] = item;
	        } else if (item.xtype == 'checkbox') {
	        	if (item.forceSelection){
		        	Ext.apply(item,{
		        		listeners:{
		        			check:this.checkboxCheck
			        	}
		        	});
	        	}
	            item = new Ext.form.Checkbox(item);
	            this.curFields[this.curFields.length] = item;
	        } else if (item.xtype == 'datefield') {
	        	this.setDateConfig(item,varName);
	            item = new Ext.form.DateField(item);
	            this.curFields[this.curFields.length] = item;
	        } else if (item.xtype == 'radio') {
                item = new Ext.form.Radio(item);
                this.curFields[this.curFields.length] = item;
            } else if (item.xtype == 'uxspinner') {
                item = new Ext.ux.form.Spinner(item);
                this.curFields[this.curFields.length] = item;
            } else if (item.xtype == 'uxspinnerdate') {
            	var defalutTime;
            	if (!item.strategy){
            		if (item.fieldPosition == 'start'){
            			defalutTime = {defaultValue:'00:00'};
            		}else{
            			defalutTime = {defaultValue:'23:59'};
            		}
            	}else{
            		defalutTime = item.strategy;
            	}
            	var time = {
            		width : 60,
            		id : item.name + Ext.id(),
            		strategy : new Ext.ux.form.Spinner.TimeStrategy(defalutTime)
            	};
            	Ext.apply(item,{
            		timeId : time.id
            	});
            	this.setDateConfig(item,varName);
                item = new Ext.form.DateField(item);
                this.curFields[this.curFields.length] = item;
                toolbar.add(item);
                item = new Ext.ux.form.Spinner(time);
            } else if (item.xtype == 'button' || item.xtype == 'splitbutton') {
            	
            	if (item.searchButton && advSearch){
	                var advSearchField = this.advSearchField[this.compSet[i].advField];
	                this.queryData = {};//这个参数用于取回高级查询的结果
	                Ext.apply(item,{
	                	xtype : 'splitbutton',
	                	menu:{
		                	cls : 'x-date-menu',
		                	items:new Ext.ux.advanceSearch({
		                    	queryData:this.queryData,
		                    	advSearchField: advSearchField})
	                	}
	                });     
	                if (!Ext.isIE){
	                	Ext.apply(item.menu,{layout:'fit'});
	                }
            	}else{
            		delete item.menu;
            		Ext.apply(item,{xtype:'button'});
            	}
            } else if(item.xtype == 'combo'){
            	item = new Ext.form.ComboBox(item);
	            this.curFields[this.curFields.length] = item;
            }
            toolbar.add(item);
	    }
	    toolbar.doLayout();
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
                    	url: this.urls[this.compSet[i].url]
                    },micrite.util.proxyLoad())),
            reader : new Ext.data.JsonReader({
	                	totalProperty:'totalCount',
	            	    root:'data',id:'id'},
	            	    this.readers[this.compSet[i].reader])
        });
        return store;
     },
     startSearch : function() {
            this.store.baseParams = this.getAllField();
            this.store.rejectChanges(); 
            this.store.load({params:{start:0, limit:this.pageSize},callback:function(r,o,s){
                 // todo
            }});
     },
     advanceSearch : function(b,e){
    	 //todo
     },
    getAllField : function (){
		var rt  = ''; // 高级查询返回该字符串
		var checkboxResult = {}; //复选框值串
		var result = {}; //普通查询返回该字符串
		var value = null,name = null,temp = null,cbValues = [];
		for (var i = 0; i < this.curFields.length; i++) {
            name = this.curFields[i].realname;
            if (this.curFields[i].xtype == 'checkbox') {
                if (temp != name){
             		temp = name;
             		cbValues = [];
             	}
             	if (this.curFields[i].checked){
             		value = this.curFields[i].getRawValue();
             		//如果没有指定Value，value值为true
             		if (this.curFields[i].inputValue === undefined){
             			value = true;
             		}
             		cbValues[cbValues.length] = value;
             		value = cbValues;
             		checkboxResult[name] = value;
             	}
             	result[name] = value;
             } else if (this.curFields[i].xtype == 'radio') {
              	if (this.curFields[i].checked){
             		value = this.curFields[i].getRawValue();
             		rt = rt + '['+ name + ',' + value + ',=],';
             		
             	}
             	result[name] = value;
         	 } else if (this.curFields[i].xtype == 'combo') {
         	 	value = this.curFields[i].getValue();
              	if (value == 0 || value){
             		rt = rt + '['+ name + ',' + value + ',=],';
             	}
             	result[name] = value;
         	 } else if (this.curFields[i].xtype == 'uxspinnerdate') {
         		if (!this.curFields[i].isValid()){
         			showMsg('failure',this.dateErrorMsg);
         			return;
         		}
             	value = this.curFields[i].getRawValue();
             	var time = Ext.getCmp(this.curFields[i].timeId);
             	value = value + ' ' + time.getRawValue();
             	if (!time.isValid()){
             		showMsg('failure',this.timeErrorMsg);
             		return;
             	}
             	//判断时间框为空
//        		if(time.getRawValue().length<1){
//        			time.markInvalid(Ext.form.TextField.prototype.blankText);
//    	            return;
//    	        }
             	
             	if (!temp&&this.curFields[i].expression=='between'){
             		temp = value;
             	}else if (temp){
             		temp = temp + ';' + value;
             		rt = rt + '['+ name + ',' + temp + ','+ this.curFields[i].expression +'],';
             		temp = null;
             	}
             	result[name] = value;
             } else {
             	if (!this.curFields[i].isValid()){
             		showMsg('failure',this.dateErrorMsg);
             		return;
             	}
             	value = this.curFields[i].getRawValue().trim();
             	if (value){
	             	rt = rt + '['+ name + ',' + value + ','+ this.curFields[i].expression + '],';
             	}
             	result[name] = value;
             }
         }
       
         if (this.queryData){
	         Ext.iterate(this.queryData,function(item,index,allItems){
	        	 rt = rt + '['+ this.queryData[item] +'],';
	         },this);
         }
  		if (checkboxResult){
	         Ext.iterate(checkboxResult,function(item,index,allItems){
	            var checkbox = '';
	            for (var i=0;i<checkboxResult[item].length;i++){
	           	 	checkbox = checkbox + checkboxResult[item][i]+';';
	            }
	            rt = rt + '['+ item + ',' + checkbox + ',in],';
	         },this);

 		}
  		//this.searchBean 是否使用searchbean
  		if (this.searchBean || this.queryData){
  			return {queryString:rt}
  		}
         return result;
     },
     initCompSet : function(item,index,allItem) {
        	var defaultCompSet = {url:0,reader:0,columns:0,bbarAction:-1,tbarAction:0,advField:0};
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
     	if (this.compSet[i].url != this.previousCompSet.url ||
     	    this.compSet[i].columns  != this.previousCompSet.columns){
     		 var store = this.getStoreById(i);
             this.reconfigure(store,this.getColumnById(i));
             this.getBottomToolbar().bind(store);
     	}
     },
     genChartWindow : function(c1,c2) {
     	var win = micrite.util.genWindow(Ext.apply(c1,{border:true}));
     	if (!win){
     		return false;
     	}
        Ext.apply(c2,{
        	success:function(r,o){
            var obj = Ext.decode(r.responseText);
                  win.getLayoutTarget().update(obj.map+'<img src = "DisplayChart?filename='+obj.filename+'" usemap="#'+obj.mapName+'">');
            }
        });
        micrite.util.ajaxRequest(c2,this);
        return win;
     },
     setDateConfig : function(item,varName){
     	Ext.apply(item,{
    		format: 'Y-m-d',
    		width:100
    	});
     	//加入了一个fieldPosition参数，用于控制日期的范围
    	if (item.fieldPosition == 'start'){
    		varName.startDateValue =  item.value ? item.value : item.defaultValue;
        	Ext.apply(item,{
        		id : varName.startDate,
        		defaultValue : varName.startDateValue ,
        		endDateField : varName.endDate,
        		vtype : 'daterange'
        	});
        	delete item.value;
        }else if (item.fieldPosition == 'end'){
        	varName.endDateValue = item.value ? item.value : item.defaultValue;
        	Ext.apply(item,{
        		id : varName.endDate,
        		startDateField : varName.startDate,
        		defaultValue : varName.endDateValue ,
        		vtype : 'daterange',
        		listeners:{
        		    scope : this,
        			render : function(){
         	    		Ext.getCmp(varName.startDate).setValue(varName.startDateValue);
         	    		Ext.getCmp(varName.endDate).setValue(varName.endDateValue);
        			}
        		}
        	});
        	delete item.value;
        }
    	return item;
     },
     checkboxCheck:function(cb,checked){
 		if (!checked){
    		Ext.each(Ext.select('[name='+cb.name+']').elements,function(item,index){
    			if (item.checked){
    				checked = true;
    			}
    		});
    		if (!checked){
    			cb.el.dom.checked = true;
    		}
		}
	},
     listeners:{
        render:function() {
    	   this.genTopField(0);
    	   this.genButtomField(0);
    	   this.previousCompSet = this.compSet[0];
        }
     }
}; //eo extend

micrite.ComplexEditorGrid = Ext.extend(Ext.grid.EditorGridPanel,micrite.ComplexGrid);
micrite.ComplexGrid = Ext.extend(Ext.grid.GridPanel,micrite.ComplexGrid);
Ext.reg('complexgrid', micrite.ComplexGrid);
//eof



Ext.apply(Ext.form.VTypes, {
    daterange : function(val, field) {
        var date = field.parseDate(val);
        if(!date){
            return;
        }
        if (field.startDateField) {
            var start = Ext.getCmp(field.startDateField);
            start.setMaxValue(date);
           // start.validate();
            this.dateRangeMax = date;
        } else if (field.endDateField) {
            var end = Ext.getCmp(field.endDateField);
            end.setMinValue(date);
           // end.validate();
            this.dateRangeMin = date;
        }
        return true;
    }
});


Ext.ns('Ext.ux');


Ext.ux.advanceSearch = Ext.extend(Ext.grid.GridPanel, {
	constructor: function (config) {
		Ext.apply(this, config,{
			border:false,
			height:300,
			width:450,
			enableHdMenu : false
		});
		// 
		var expCombo = new Ext.form.ComboBox({
			triggerAction: 'all',
			lazyRender:true,
			width:60,
			editable :false,
			allowBlank : false,
			mode: 'local',
			getListParent: function() {
				return this.el.up('.x-menu');
			},
			store: new Ext.data.ArrayStore({
				fields: [
				         'expId',
				         'expName'
				         ],
				         data: []
			}),
			valueField: 'expId',
			displayField: 'expName'
		});  		
		var cols = this.advSearchField;
		var colData = [];
		for (var ci=0;ci<cols.length;ci++){
			colData[ci] = [cols[ci].value,cols[ci].name];
		}
	
		this.recOrder=[];//记录条件插入顺序
		var nameCombo = new Ext.form.ComboBox({
			triggerAction: 'all',
			lazyRender:true,
			width:120,
			editable :false,
			mode: 'local',
			allowBlank : false,
			getListParent: function() {
			return this.el.up('.x-menu');
		},
		store: new Ext.data.ArrayStore({
			fields: [
			         'colId',
			         'coldName'
			         ],
			         data: colData
		}),
		valueField: 'colId',
		displayField: 'coldName',
		listeners:{
			scope: this,
			'select':this.onChangeCol.createDelegate(this,[expCombo],true)
		}
		});
	    var tbspacer = {xtype: 'tbspacer', width: 3};
		var tbar = new Ext.Toolbar({
			items: [
			    nameCombo,tbspacer,expCombo,tbspacer,tbspacer,
		        {
		        	text: 'Add',
		        	scope : this,
		        	handler : this.onAdd.createDelegate(this,[nameCombo,expCombo])
		        },
		        '->',
		        {
		        	text: 'Delete',
		        	scope : this,
		        	handler : this.onDelete
		        }	    	        
			]   	    
		});
		var reader = new Ext.data.JsonReader({
			totalProperty: 'total',
			successProperty: 'success',
			root: 'data'
		}, [
		    {name: 'name'},
		    {name: 'expression'},
		    {name: 'value'}
		    ]);
		var store = new Ext.data.Store({
			reader: reader,
			autoSave: true
		});
		Ext.apply(this, {
			viewConfig: {
			forceFit:true
		},
		tbar : tbar,
		columns: [new Ext.grid.RowNumberer(),
		          {header: Ext.grid.PropertyColumnModel.prototype.nameText,dataIndex: 'name'},
		          {header: "Expression",dataIndex: 'expression'},
		          {header:Ext.grid.PropertyColumnModel.prototype.valueText,dataIndex: 'value'}],
		          store:store
		});
		Ext.ux.advanceSearch.superclass.constructor.call(this);
	},
	onAdd : function(c,e){
		var items = this.getTopToolbar().items;
		if (!c.isValid() || !e.isValid()){
			return;
		}
		if (!items.itemAt(4).isValid()){
			return;
		}
		var value = items.itemAt(4).getRawValue();
		if (items.length > 9){
			if(items.itemAt(6).getRawValue().length<1){
				items.itemAt(6).markInvalid(Ext.form.TextField.prototype.blankText);
				return;
			}
			if (!items.itemAt(6).isValid()){
				return;
			}
			value = value + ' ' + items.itemAt(6).getRawValue();
		}
	
		var u = new this.store.recordType({
			id:c.value,
			name : c.getRawValue(),
			expression : e.getRawValue(),
			value: value
		});
		if (this.queryData[c.value]){
			for (var i=0;i<this.recOrder.length;i++){
				if (this.recOrder[i].data.name==c.getRawValue()){
					this.recOrder[i] = u;
				}
			}
		}else{
			this.recOrder[this.recOrder.length] = u;
		}
		// [name,yubo,like]
		this.queryData[c.value]= [c.value,value,e.getRawValue()];
		this.store.removeAll();
		var tmp = this.recOrder.slice();//拷贝数组
		this.store.add(tmp.reverse());
	},
	onDelete : function(o, t) {
		var index = this.getSelectionModel().getSelected();
		if (!index) {
			return false;
		}
		this.recOrder.remove(index);
		delete this.queryData[index.data.id];
		this.store.removeAll();
		var tmp = this.recOrder.slice();
		this.store.add(tmp.reverse());
	},
	onChangeCol : function(c,r,num,expCombo){
		expCombo.clearValue();
		expCombo.getStore().removeAll();
		var toolbar = this.getTopToolbar();
		while (toolbar.items.length > 8) {
			var item = toolbar.items.itemAt(toolbar.items.length-5);
			toolbar.items.remove(item);
			item.destroy();
		}
		item = this.advSearchField[num];
		var items,expSet;
		if (item.xtype == 'textfield'){
			items = {
					xtype : 'textfield',
					width : 100
			};
			expSet = [[0,'='],[1,'like']];
		} else if (item.xtype == 'datefield'){
			items = {
					xtype : 'datefield',
					width : 100,
					format: 'Y-m-d',
					menu : new Ext.menu.DateMenu({
						hideOnClick: false,
						allowOtherMenus :true
					})
			};
			expSet = [[0,'='],[1,'>='],[3,'<=']];
		} else if (item.xtype == 'combo'){
			items = {
					xtype : 'combo',
					width : 100,
					allowBlank : false
			};
			expSet = [[0,'in']];
		} else if (item.xtype == 'numberfield'){
			items = {
					xtype : 'numberfield',
					width : 100,
					allowBlank : false
			};
			expSet = [[0,'='],[1,'>'],[2,'>='],[3,'<'],[4,'<=']];
		} else if (item.xtype == 'uxspinnerdate'){
			var time = new Ext.ux.form.Spinner({
				width : 80,
				strategy : new Ext.ux.form.Spinner.TimeStrategy()
			});
			items = [{
				xtype : 'datefield',
				width : 100,
				allowBlank : false,
				value:new Date(),
				format: 'Y-m-d',
				menu : new Ext.menu.DateMenu({
					hideOnClick: false,
					allowOtherMenus :true
				})
			},{xtype: 'tbspacer', width: 3},time];
			expSet = [[0,'='],[1,'>='],[3,'<=']];
		}
		if (item.xtype != 'uxspinnerdate'){
			if (item.config){
				Ext.apply(items,item.config);
			}
		}
		expCombo.getStore().loadData(expSet);
		toolbar.insertButton(4,items);
		toolbar.doLayout();
	}
});

