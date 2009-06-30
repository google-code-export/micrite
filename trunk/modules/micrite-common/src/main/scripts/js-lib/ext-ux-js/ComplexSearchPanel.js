Ext.namespace('micrite.panel');

/** 
 * @class micrite.panel.ComplexSearchPanel 
 * micrite的查询面板
 */ 
micrite.panel.ComplexSearchPanel = function(config) {
    Ext.apply(this, config);
    micrite.panel.ComplexSearchPanel.superclass.constructor.call(this);
};

Ext.extend(micrite.panel.ComplexSearchPanel, Ext.Panel, {

    // 配置项开始
    /**
     * @cfg {Array} conNames 查询条件名称数组，形式如下：
     * ['条件1','条件2',...]
     */
    conNames:[],

    /**
     * @cfg {Array} conCmpGroups 查询条件组件组数组，形式如下：
     * [
     *     [
     *         '用户名',
     *         {xtype:'textfield',name:'username'},
     *         {xtype:'tbspacer'},
     *         {xtype:'tbspacer'},
     *         {xtype:'checkbox', boxLabel:'有效', name:'isValid'},
     *         {xtype:'checkbox', boxLabel:'无效', name:'isUnvalid'}
     *     ],
     *     [
     *         '手机',
     *         {xtype:'textfield',name:'mobiletel'},
     *         {xtype:'tbspacer'},
     *         '注册时间',
     *         {xtype:'datetimefield',name:'registerTime'}
     *     ],
     *     ...
     * ]
     */
    conCmpGroups:[],

    /**
     * @cfg {Array} actionButtonMenuItems 动作按钮上的菜单项，形式如下：
     * [
     *     {
     *         text:'增加新用户',
     *         handler:function() {}
     *     },
     *     ...
     * ]
     */
    actionButtonMenuItems:[],
    
    /**
     * @cfg {String} searchRequestURL 查询请求的URL
     */
    searchRequestURL:'',
    
    /**
     * @cfg {Array} resultDataFields 查询结果数据按此格式读取
     */
    resultDataFields:[],

    /**
     * @cfg {Ext.grid.RowSelectionModel} resultRowSelectionModel 查询结果行选择模型
     */
    resultRowSelectionModel:new Ext.grid.RowSelectionModel(),

    /**
     * @cfg {Array} resultColumns 查询结果列数组
     */
    resultColumns:[],

    /**
     * @cfg {Array} resultProcessButtons 查询结果处理按钮数组
     */
    resultProcessButtons:[],
    
    /**
    * @cfg {Array} comboGrid 切换查询条件时，grid重构条件,每个配置想默认为0，
    * 根据需要指定，可以不配置，如果配置，必须跟查询菜单的长度一直,整数值代表各配置项数组的索引
    * [
    * 	[{ur:0,reader:0,column:0,button:0}],
    * 	[{ur:0,reader:0,column:0,button:0}]
    * ]
    */
    comboGrid:[],
    // 配置项结束
    

 
    //  查询结果grid
    resultGrid:null,
    //  查询函数
    searchFun:null,
    ptbar:Ext.id(),
    layout:'border',
    border:false,
    prevMenu:{url:0,reader:0,column:0,button:0},
    initComponent:function() {
    	   //  自定义变量
        //  当前查询条件Field数组
        this.curConFields = [];
        //  分页显示行号用
        var recordStart = 0;
        
        //  查询函数（点击查询按钮执行）
        this.searchFun = function() {
            recordStart = 0;
            this.resultGrid.store.baseParams = {};
            var n = 0;
            for (var i = 0; i < this.curConFields.length; i++) {
                var v = null;
                var name = null;   
                if (this.curConFields[i].xtype == 'checkbox') {
                    v = this.curConFields[i].checked;   
                } else {
                    v = this.curConFields[i].getRawValue();
                }
                name = this.curConFields[i].getName();
                this.resultGrid.store.baseParams[name] = v;
                n++;
            }
            if (n) {
            	var ptbar = this.ptbar;
            	this.resultGrid.store.removeAll();
                this.resultGrid.store.load({params:{start:0, limit:Ext.get('pageSize').dom.value},callback:function(r,o,s){
//                	if (s&&r.length>0)
//                		Ext.getCmp(ptbar).enable();
//                	else
//                		Ext.getCmp(ptbar).disable();
                }});
            }
        };         
        //  查询按钮
        var searchButton = {
            text:mbLocale.searchButton,
            cls:'x-btn-text-icon details',
            scope:this,
            handler:this.searchFun
        };
        
        //  动作按钮（位于工具栏右面）
        var actionButton = {
            text:'Action',
            menu:{xtype:'menu', items:this.actionButtonMenuItems}
        };
        
        //  向每个查询条件组件组中加入两个共享组件：“查询按钮”和“动作按钮”
        for (var i = 0; i < this.conCmpGroups.length; i++) {
            var temp = ['-'].concat(this.conCmpGroups[i]);
            this.conCmpGroups[i] = temp.concat(['-', searchButton, '->', actionButton]);
        }

        //  修改查询条件按钮的属性
        var modifyConButtonFun = function(panel, menuItemSelectedValue) {
            var conButton = panel.getTopToolbar().items.first();
            conButton.value = menuItemSelectedValue;
            conButton.setText(panel.conNames[menuItemSelectedValue]); 
        };
        //  重建ToolbarItems
        var reCreateToolbarItemsFun = function(panel, menuItemSelectedValue) {
            var items = panel.conCmpGroups[menuItemSelectedValue];
            for (var i = 0; i < items.length; i++) {
                var item = items[i];
                if (item.xtype == 'textfield') {
                    item = new Ext.form.TextField(item);
                    panel.curConFields[panel.curConFields.length] = item;
                } else if (item.xtype == 'checkbox') {
                    item = new Ext.form.Checkbox(item);
                    panel.curConFields[panel.curConFields.length] = item;
                } else if (item.xtype == 'datetimefield') {
                    item = new Ext.boco.DateTimeField(item);
                    panel.curConFields[panel.curConFields.length] = item;
                }
                panel.getTopToolbar().add(item);
            }
        };   
        
        //  重建Grid
        var reCreateGrid = function(panel, menuItemSelectedValue){
//        	panel.resultGrid.getBottomToolbar().removeAll();
//        	panel.resultGrid.getBottomToolbar().updateInfo(); 
	        Ext.getCmp(panel.ptbar).field.dom.value = 1;
	        Ext.getCmp(panel.ptbar).first.setDisabled(true);
	        Ext.getCmp(panel.ptbar).prev.setDisabled(true);
	        Ext.getCmp(panel.ptbar).next.setDisabled(true);
	        Ext.getCmp(panel.ptbar).last.setDisabled(true);
	        Ext.getCmp(panel.ptbar).afterTextEl.el.innerHTML = 'of 1';
	        Ext.getCmp(panel.ptbar).displayEl.update('');
        	//Ext.getCmp(panel.ptbar).disable();
            var bstore = store(panel,menuItemSelectedValue);
        	panel.resultGrid.reconfigure(bstore
        			,columnModel(panel,menuItemSelectedValue));
        	Ext.getCmp(panel.ptbar).bind(bstore);
        	
         
        	
        	var toolbarItems = Ext.getCmp(panel.ptbar).items;
        	 while (toolbarItems.length > 12) {
        		var item = toolbarItems.last();
        		toolbarItems.remove(item);
        		 item.destroy();
        	};
                if (panel.resultProcessButtons.length > 0) {
                	var i = panel.comboGrid[menuItemSelectedValue].button;
                
                	if (i>-1)
                       Ext.getCmp(panel.ptbar).add(panel.resultProcessButtons[i]);
                    else{
                        panel.prevMenu.button = -1;
                    }
                }
      
             Ext.apply(panel.prevMenu,panel.comboGrid[menuItemSelectedValue]);
        }
        //  删除ToolbarItems
        var removeToolbarItemsFun = function(panel) {
            var toolbarItems = panel.getTopToolbar().items;
            while (toolbarItems.length > 1) {
                var item = toolbarItems.last();
                toolbarItems.remove(item);
                item.destroy();
            }
        };
        //  切换查询条件
        var switchConFun = function(menuItem) {
            var menuItemSelectedValue = menuItem.value;
            var conButton = this.getTopToolbar().items.first();
            //  若改变了菜单项
            if (menuItemSelectedValue != conButton.value) {
                //  修改查询条件按钮的属性
                modifyConButtonFun(this, menuItemSelectedValue);
                //  删除ToolbarItems
                removeToolbarItemsFun(this);
                //  重建ToolbarItems
                reCreateToolbarItemsFun(this, menuItemSelectedValue);
                //  重建Grid
                Ext.applyIf(this.comboGrid[menuItemSelectedValue],{url:0,reader:0,column:0,button:-1});
                if (this.prevMenu.url != this.comboGrid[menuItemSelectedValue].url
                || this.prevMenu.button != this.comboGrid[menuItemSelectedValue].button
                || this.prevMenu.column != this.comboGrid[menuItemSelectedValue].column
                || this.prevMenu.reader != this.comboGrid[menuItemSelectedValue].reader)
                    reCreateGrid(this, menuItemSelectedValue);
            }
        };
        //  创建查询条件按钮上的菜单
        var conButtonMenu = new Ext.menu.Menu();
        for (var i = 0; i < this.conNames.length; i++) {
            conButtonMenu.add({
                text:this.conNames[i],
                value:i,
                scope:this,
                handler:switchConFun
            });
        }
        
        //  创建查询条件按钮
        var conButton = new Ext.Button({
            text:this.conNames[0],
            value:0,
            tooltip:'Click for more search options',
            menu:conButtonMenu
        });            

        //  创建tbar
        this.tbar = new Ext.Toolbar({
            ctCls:'search-all-tbar',
            items:[conButton]
        });
        

        var reader = function (ithis,imenu){
          
        	if (arguments.length < 2 
        			|| imenu > ithis.comboGrid.length
        			|| !ithis.comboGrid[imenu].reader)
        		imenu = 0;
        	else
        		imenu = ithis.comboGrid[imenu].reader;
        	return new Ext.data.JsonReader({totalProperty:'totalCount', root:'data', id:'id'},
                                             ithis.resultDataFields[imenu]);
        };
        //  创建store
        var store = function (ithis,imenu){
        	if (arguments.length < 2 
        			|| imenu > ithis.comboGrid.length
        			|| !ithis.comboGrid[imenu].url)
        		imenu = 0;
        	else
        		imenu = ithis.comboGrid[imenu].url;
        	return new Ext.data.Store({
            proxy:new Ext.data.HttpProxy({url:ithis.searchRequestURL[imenu],
                                          listeners:{
                                              loadexception:function(proxy, options, response, error) {
                                                  obj = Ext.util.JSON.decode(response.responseText);
                                                  showMsg('failure', obj);
            									}
                                              }
                                          }),
            reader:reader(ithis,imenu)
        });
        };
        //  创建分页工具栏
        var bbtn = this.resultProcessButtons;
        var cbtn = this.comboGrid[0].button;
        var pagingToolbar = new Ext.PagingToolbar({
        	id:this.ptbar,
            pageSize:parseInt(Ext.get('pageSize').dom.value),
            store:store(this),
            displayInfo:true,
            doLoad : function(start) {
                recordStart = start;
                var o = {totalCount:this.store.getTotalCount()};
                pn = this.paramNames;
                o[pn.start] = start;
                o[pn.limit] = this.pageSize;
                this.store.load({params:o});
            },  
            listeners:{
                render:function(comp){
            	   this.addSeparator();
            	   if (bbtn.length > 0 && cbtn > -1) 
            	       this.add(bbtn[0]);
                }}
        });
        
        //  序号列
        var rowNumbererColumn = new Ext.grid.RowNumberer({
            renderer:function(value, metadata, record, rowIndex) {
                return recordStart + 1 + rowIndex;
            }
        });
        //  创建查询结果列模型
        var columnModel = function (ithis,imenu){
        	
        	
        	if (arguments.length < 2 
        			|| imenu > ithis.comboGrid.length
        			|| !ithis.comboGrid[imenu].column){
        		imenu = 0;
        	}else{
        		imenu = ithis.comboGrid[imenu].column;
        	}
        	return new Ext.grid.ColumnModel([rowNumbererColumn].concat(ithis.resultColumns[imenu]));
        }

        //  创建查询结果grid
        this.resultGrid = new Ext.grid.GridPanel({
        	id:'icenter',
            region:'center',
            border:false,
            loadMask:{
                msg:mbLocale.loadingMsg
            },
            stripeRows:true,

            selModel:this.resultRowSelectionModel,
            bbar:pagingToolbar,
            viewConfig:{
                forceFit:true,
                enableRowBody:true
            },
            store:store(this),
            colModel:columnModel(this)
        });
        
     
  
        this.items = [this.resultGrid];

        micrite.panel.ComplexSearchPanel.superclass.initComponent.apply(this, arguments);
    },
  
    listeners:{
        //  面板渲染时，初始化默认的查询条件组
        render:function() {
			var i=0;
			if (this.conNames.length == 1){
				 var toolbarItems = this.getTopToolbar().items;
		         var item = toolbarItems.last();
		         toolbarItems.remove(item);
		         item.destroy();
		         i=1;
			}
            var items = this.conCmpGroups[0];
            for (; i < items.length; i++) {
                var item = items[i];
                if (item.xtype == 'textfield') {
                    item = new Ext.form.TextField(item);
                    this.curConFields[this.curConFields.length] = item;
                } else if (item.xtype == 'checkbox') {
                    item = new Ext.form.Checkbox(item);
                    this.curConFields[this.curConFields.length] = item;
                } else if (item.xtype == 'datetimefield') {
                    item = new Ext.boco.DateTimeField(item);
                    this.curConFields[this.curConFields.length] = item;
                }
                this.getTopToolbar().add(item);
            }
        	Ext.getCmp(this.ptbar).bind(this.resultGrid.store);
        	
        }
    }
});
