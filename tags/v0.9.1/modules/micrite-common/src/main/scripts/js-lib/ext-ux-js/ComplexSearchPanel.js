Ext.override(Ext.grid.GridView, {
    initTemplates : function(){
        var ts = this.templates || {};
        if(!ts.master){
            ts.master = new Ext.Template(
                '<div class="x-grid3" hidefocus="true">',
                    '<div class="x-grid3-viewport">',
                        '<div class="x-grid3-header"><div class="x-grid3-header-inner"><div class="x-grid3-header-offset" style="{ostyle}">{header}</div></div><div class="x-clear"></div></div>',
                        '<div class="x-grid3-scroller"><div class="x-grid3-body" style="{bstyle}">{body}</div><a href="#" class="x-grid3-focus" tabIndex="-1"></a></div>',
                    '</div>',
                    '<div class="x-grid3-resize-marker">&nbsp;</div>',
                    '<div class="x-grid3-resize-proxy">&nbsp;</div>',
                '</div>'
            );
        }
        if(!ts.header){
            ts.header = new Ext.Template(
                '<table border="0" cellspacing="0" cellpadding="0" style="{tstyle}">',
                '<thead><tr class="x-grid3-hd-row">{cells}</tr></thead>',
                '</table>'
            );
        }
        if(!ts.hcell){
            ts.hcell = new Ext.Template(
                '<td class="x-grid3-hd x-grid3-cell x-grid3-td-{id} {css}" style="{style}"><div {tooltip} {attr} class="x-grid3-hd-inner x-grid3-hd-{id}" unselectable="on" style="{istyle}">', this.grid.enableHdMenu ? '<a class="x-grid3-hd-btn" href="#"></a>' : '',
                '{value}<img class="x-grid3-sort-icon" src="', Ext.BLANK_IMAGE_URL, '" />',
                '</div></td>'
            );
        }
        if(!ts.body){
            ts.body = new Ext.Template('{rows}');
        }
        if(!ts.row){
            ts.row = new Ext.Template(
                '<div class="x-grid3-row {alt}" style="{tstyle}"><table class="x-grid3-row-table" border="0" cellspacing="0" cellpadding="0" style="{tstyle}">',
                '<tbody><tr>{cells}</tr>',
                (this.enableRowBody ? '<tr class="x-grid3-row-body-tr" style="{bodyStyle}"><td colspan="{cols}" class="x-grid3-body-cell" tabIndex="0" hidefocus="on"><div class="x-grid3-row-body">{body}</div></td></tr>' : ''),
                '</tbody></table></div>'
            );
        }
        if(!ts.cell){
            ts.cell = new Ext.Template(
                '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} {css}" style="{style}" tabIndex="0" {cellAttr}>',
                '<div class="x-grid3-cell-inner x-grid3-col-{id}" unselectable="on" {attr}>{value}</div>',
                '</td>'
            );
        }
        for(var k in ts){
            var t = ts[k];
            if(t && typeof t.compile == 'function' && !t.compiled){
                t.disableFormats = true;
                t.compile();
            }
        }
        this.templates = ts;
        this.colRe = new RegExp("x-grid3-td-([^\\s]+)", "");
    },
    updateAllColumnWidths : function(){
        var tw = this.getTotalWidth();
        var clen = this.cm.getColumnCount();
        var ws = [];
        for(var i = 0; i < clen; i++){
            ws[i] = this.getColumnWidth(i);
        }
        this.innerHd.firstChild.style.width = this.getOffsetWidth();
        this.innerHd.firstChild.firstChild.style.width = tw;
        this.mainBody.dom.style.width = tw;
        for(var i = 0; i < clen; i++){
            var hd = this.getHeaderCell(i);
            hd.style.width = ws[i];
        }
        var ns = this.getRows(), row, trow;
        for(var i = 0, len = ns.length; i < len; i++){
            row = ns[i];
            row.style.width = tw;
            if(row.firstChild){
                row.firstChild.style.width = tw;
                trow = row.firstChild.rows[0];
                for (var j = 0; j < clen; j++) {
                    trow.childNodes[j].style.width = ws[j];
                }
            }
        }
        this.onAllColumnWidthsUpdated(ws, tw);
    },
    updateColumnWidth : function(col, width){
        var w = this.getColumnWidth(col);
        var tw = this.getTotalWidth();
        this.innerHd.firstChild.style.width = this.getOffsetWidth();
        this.innerHd.firstChild.firstChild.style.width = tw;
        this.mainBody.dom.style.width = tw;
        var hd = this.getHeaderCell(col);
        hd.style.width = w;
        var ns = this.getRows(), row;
        for(var i = 0, len = ns.length; i < len; i++){
            row = ns[i];
            row.style.width = tw;
            if(row.firstChild){
                row.firstChild.style.width = tw;
                row.firstChild.rows[0].childNodes[col].style.width = w;
            }
        }
        this.onColumnWidthUpdated(col, w, tw);
    },
    updateColumnHidden : function(col, hidden){
        var tw = this.getTotalWidth();
        this.innerHd.firstChild.style.width = this.getOffsetWidth();
        this.innerHd.firstChild.firstChild.style.width = tw;
        this.mainBody.dom.style.width = tw;
        var display = hidden ? 'none' : '';
        var hd = this.getHeaderCell(col);
        hd.style.display = display;
        var ns = this.getRows(), row;
        for(var i = 0, len = ns.length; i < len; i++){
            row = ns[i];
            row.style.width = tw;
            if(row.firstChild){
                row.firstChild.style.width = tw;
                row.firstChild.rows[0].childNodes[col].style.display = display;
            }
        }
        this.onColumnHiddenUpdated(col, hidden, tw);
        delete this.lastViewWidth;
        this.layout();
    },
    afterRender: function(){
        this.mainBody.dom.innerHTML = this.renderRows() || '&nbsp;';
        this.processRows(0, true);
        if(this.deferEmptyText !== true){
            this.applyEmptyText();
        }
    },
    renderUI : function(){
        var header = this.renderHeaders();
        var body = this.templates.body.apply({rows: '&nbsp;'});
        var html = this.templates.master.apply({
            body: body,
            header: header,
            ostyle: 'width:'+this.getOffsetWidth()+';',
            bstyle: 'width:'+this.getTotalWidth()+';'
        });
        var g = this.grid;
        g.getGridEl().dom.innerHTML = html;
        this.initElements();
        Ext.fly(this.innerHd).on("click", this.handleHdDown, this);
        this.mainHd.on("mouseover", this.handleHdOver, this);
        this.mainHd.on("mouseout", this.handleHdOut, this);
        this.mainHd.on("mousemove", this.handleHdMove, this);
        this.scroller.on('scroll', this.syncScroll, this);
        if(g.enableColumnResize !== false){
            this.splitZone = new Ext.grid.GridView.SplitDragZone(g, this.mainHd.dom);
        }
        if(g.enableColumnMove){
            this.columnDrag = new Ext.grid.GridView.ColumnDragZone(g, this.innerHd);
            this.columnDrop = new Ext.grid.HeaderDropZone(g, this.mainHd.dom);
        }
        if(g.enableHdMenu !== false){
            if(g.enableColumnHide !== false){
                this.colMenu = new Ext.menu.Menu({id:g.id + "-hcols-menu"});
                this.colMenu.on("beforeshow", this.beforeColMenuShow, this);
                this.colMenu.on("itemclick", this.handleHdMenuClick, this);
            }
            this.hmenu = new Ext.menu.Menu({id: g.id + "-hctx"});
            this.hmenu.add(
                {id:"asc", text: this.sortAscText, cls: "xg-hmenu-sort-asc"},
                {id:"desc", text: this.sortDescText, cls: "xg-hmenu-sort-desc"}
            );
            if(g.enableColumnHide !== false){
                this.hmenu.add('-',
                    {id:"columns", text: this.columnsText, menu: this.colMenu, iconCls: 'x-cols-icon'}
                );
            }
            this.hmenu.on("itemclick", this.handleHdMenuClick, this);
        }
        if(g.trackMouseOver){
            this.mainBody.on("mouseover", this.onRowOver, this);
            this.mainBody.on("mouseout", this.onRowOut, this);
        }
        if(g.enableDragDrop || g.enableDrag){
            this.dragZone = new Ext.grid.GridDragZone(g, {
                ddGroup : g.ddGroup || 'GridDD'
            });
        }
        this.updateHeaderSortState();
    },
    onColumnWidthUpdated : function(col, w, tw){
        // empty
    },
    onAllColumnWidthsUpdated : function(ws, tw){
        // empty
    },
    onColumnHiddenUpdated : function(col, hidden, tw){
        // empty
    },
    getOffsetWidth: function() {
        return (this.cm.getTotalWidth() + this.scrollOffset) + 'px';
    },
    renderBody : function(){
        var markup = this.renderRows() || '&nbsp;';
        return this.templates.body.apply({rows: markup});
    },
    hasRows : function(){
        var fc = this.mainBody.dom.firstChild;
        return fc && fc.nodeType == 1 && fc.className != 'x-grid-empty';
    },
    updateHeaders : function(){
        this.innerHd.firstChild.innerHTML = this.renderHeaders();
        this.innerHd.firstChild.style.width = this.getOffsetWidth();
        this.innerHd.firstChild.firstChild.style.width = this.getTotalWidth();
    }
});

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
                this.resultGrid.store.rejectChanges(); 
            	this.resultGrid.store.removeAll();
                this.resultGrid.store.load({params:{start:0, limit:Ext.get('pageSize').dom.value},callback:function(r,o,s){
//                	if (s&&r.length>0)
//                		Ext.getCmp(ptbar).enable();
//                	else
//                		Ext.getCmp(ptbar).disable();
                    
                }});
            }
        };
        this.refresh = function(){
        	Ext.getCmp(this.ptbar).fireEvent("refresh");
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
            text:mbLocale.actionMenu,
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
        	panel.resultGrid.reconfigure(bstore,
        			columnModel(panel,menuItemSelectedValue));
        	Ext.getCmp(panel.ptbar).bind(bstore);
        	
         
        	
        	var toolbarItems = Ext.getCmp(panel.ptbar).items;
        	 while (toolbarItems.length > 12) {
        		var item = toolbarItems.last();
        		toolbarItems.remove(item);
        		 item.destroy();
        	}
                if (panel.resultProcessButtons.length > 0) {
                	var i = panel.comboGrid[menuItemSelectedValue].button;
                
                	if (i>-1){
                       Ext.getCmp(panel.ptbar).add(panel.resultProcessButtons[i]);
                	}else{
                        panel.prevMenu.button = -1;
                    }
                }
      
             Ext.apply(panel.prevMenu,panel.comboGrid[menuItemSelectedValue]);
        };
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
                if (this.prevMenu.url != this.comboGrid[menuItemSelectedValue].url ||
                		this.prevMenu.button != this.comboGrid[menuItemSelectedValue].button ||
                		this.prevMenu.column != this.comboGrid[menuItemSelectedValue].column ||
                		this.prevMenu.reader != this.comboGrid[menuItemSelectedValue].reader){
                    reCreateGrid(this, menuItemSelectedValue);
                }
            }
        };
        //  创建查询条件按钮上的菜单
        var conButtonMenu = new Ext.menu.Menu();
        for (i = 0; i < this.conNames.length; i++) {
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
          
        	if (arguments.length < 2 ||
        			imenu > ithis.comboGrid.length ||
        			 !ithis.comboGrid[imenu].reader){
        		imenu = 0;
        	}else{
        		imenu = ithis.comboGrid[imenu].reader;
        	}
        	return new Ext.data.JsonReader({totalProperty:'totalCount', root:'data', id:'id'},
                                             ithis.resultDataFields[imenu]);
        };
        //  创建store
        var store = function (ithis,imenu){
        	if (arguments.length < 2 ||
        			 imenu > ithis.comboGrid.length ||
        			 !ithis.comboGrid[imenu].url){
        		imenu = 0;
        	}else{
        		imenu = ithis.comboGrid[imenu].url;
        	}
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
            pageSize: parseInt(Ext.get('pageSize').dom.value,10),
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
            	refresh:function(){
	                var o = {totalCount:0};
	                pn = this.paramNames;
	                o[pn.start] = recordStart;
	                o[pn.limit] = this.pageSize;
	                this.store.load({params:o});
            	},
                render:function(comp){
            	   this.addSeparator();
            	   if (bbtn.length > 0 && cbtn > -1) {
            	       this.add(bbtn[0]);
            	   }
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
        	
        	
        	if (arguments.length < 2 ||
        			 imenu > ithis.comboGrid.length ||
        			 !ithis.comboGrid[imenu].column){
        		imenu = 0;
        	}else{
        		imenu = ithis.comboGrid[imenu].column;
        	}
        	return new Ext.grid.ColumnModel([rowNumbererColumn].concat(ithis.resultColumns[imenu]));
        };

        if (this.edit){
            //  创建查询结果grid
            this.resultGrid = new Ext.grid.EditorGridPanel({
	            region:'center',
	            clicksToEdit:2,
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
        } else{
	        	     //  创建查询结果grid
	        this.resultGrid = new Ext.grid.GridPanel({
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
        }
     
  
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
                item = items[i];
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
