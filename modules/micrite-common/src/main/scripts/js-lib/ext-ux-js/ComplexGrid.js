Ext.ns('micrite','micrite.util');

micrite.util = {
	 	genWindow : function(c){
	 		 if (c.id && Ext.getCmp(c.id)) {
                Ext.getCmp(c.id).center();
                return;
            }
	 		var pbid = Ext.id();
	        var win = new Ext.Window(Ext.apply({
		            closable : true,
		            width    : 640,
		            height   : 520,
		            plain    : true,
		            maximizable: true,
		            html:    '<div id="'+ pbid +'" style="height:100%;top:40%;left:30%;position:absolute;"></div>',
		            layout   : 'fit',
	            },c));
	        win.show();
	        win.center();
            var pbar3 = new Ext.ProgressBar({
                            width:Math.round(win.width*0.4,0),
                            renderTo:Ext.get(pbid)
                        });
            pbar3.wait({
                interval:100,
                text:'Loading...',
                increment:10
            });
	        return win;
	    },
	    ajaxRequest : function (c,scope){
	    	var success = function(r,o){
	    		var res = Ext.decode(r.responseText);
	    		if (res.message)
	    		   showMsg('success',res.message);
	    	};
	    	var failure = function(r,o){
	    		var res = Ext.decode(r.responseText);
	    		if (res.expired){
	    			window.location='j_spring_security_logout';
	    		}else if (res.message){
	    			showMsg('failure', res.message);
	    			Ext.Msg.alert('info','FALSE');
	    		}
            };
	    	c.success = success.createSequence(c.success,scope);
	    	c.failure = failure.createSequence(c.failure,scope);
	        Ext.Ajax.request(Ext.apply({
	            scope:scope,
	            method:'post',
	            requestexception:function(conn,response,options){
	            	Ext.Message.alert('Serivce Down');
	            }
	        },c));
	    }
 	}

micrite.ComplexGrid = {
    border : false,
    pageSize : parseInt(Ext.getDom('pageSize').value,10),
    urlPrefix : '/' + document.location.href.split("/")[3],
    initComponent:function() {
        var config = {
            viewConfig:{forceFit:true}
        }; // eo config object
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
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
        //创建分组查询组件
        if (this.searchFields){
	        //  查询按钮
	        var searchButton = {
	            text:'Search',
	            cls:'x-btn-text-icon details',
	            scope:this,
	            handler:this.startSearch
	        };
            //  向每个查询条件组中加入组件：“查询按钮”
            for (i = 0; i < this.searchFields.length; i++) {
                this.searchFields[i] = this.searchFields[i].concat(['-', searchButton]);
            }
        }
        //创建actionButton
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
        this.selModel = new Ext.grid.RowSelectionModel();
        //创建数据源
        this.store = this.getStoreById(0);
        //创建分页工具栏
        this.bbar = new Ext.PagingToolbar({
            pageSize : this.pageSize,
            store : this.store,
            displayInfo : true,
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
	    for (var j = 0; j < items.length; j++) {
	        item = items[j];
	        if (item.xtype == 'textfield') {
	            item = new Ext.form.TextField(item);
	            this.curFields[this.curFields.length] = item;
	        } else if (item.xtype == 'checkbox') {
	            item = new Ext.form.Checkbox(item);
	            this.curFields[this.curFields.length] = item;
	        } else if (item.xtype == 'datefield') {
	            item = new Ext.form.DateField(item);
	            this.curFields[this.curFields.length] = item;
	        } else if (item.xtype == 'spinnerfield') {
                item = new Ext.ux.form.SpinnerField(item);
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
        var rn = new Ext.grid.RowNumberer({
            scope : this,
            renderer:function(value, metadata, record, rowIndex) {
                var start = this.store.lastOptions.params.start;
                return  start + 1 + rowIndex;
            }
        });
        return new Ext.grid.ColumnModel([rn].concat(this.columnsArray[this.compSet[i].columns]));
    },
    getStoreById : function(i){
        var store =  new Ext.data.Store({
            proxy : new Ext.data.HttpProxy({
                    	url: this.urlPrefix + this.urls[this.compSet[i].url],
                        listeners:{
                            loadexception:function(proxy, options, resp, error) {
                                obj = Ext.util.JSON.decode(resp.responseText);
                                showMsg('failure', obj);
                            }
                        }
                    }),
            reader : new Ext.data.JsonReader({
	                	totalProperty:'totalCount',
	            	    root:'data',id:'id'},
	            	    this.readers[this.compSet[i].reader])
        });
        return store;
     },
     startSearch : function() {
            this.store.baseParams = {};
            var value = null,name = null;
            for (var i = 0; i < this.curFields.length; i++) {
                if (this.curFields[i].xtype == 'checkbox') {
                    value = this.curFields[i].checked;   
                } else {
                    value = this.curFields[i].getRawValue();
                }
                name = this.curFields[i].getName();
                this.store.baseParams[name] = value;
            }
            this.store.rejectChanges(); 
            this.store.load({params:{start:0, limit:this.pageSize},callback:function(r,o,s){
                //To Do
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
            toolbar.displayItem.setText('');
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
     	var win = micrite.util.genWindow(c1);
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
     listeners:{
        render:function() {
    	   this.genTopField(0);
    	   this.genButtomField(0);
    	   this.previousCompSet = this.compSet[0];
    	   delete this.tbar;
        }
     }
};

micrite.ComplexEditorGrid = Ext.extend(Ext.grid.EditorGridPanel,micrite.ComplexGrid);
micrite.ComplexGrid = Ext.extend(Ext.grid.GridPanel,micrite.ComplexGrid);
//eof
Ext.reg('complexgrid', micrite.ComplexGrid);


