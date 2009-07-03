Ext.namespace('micrite.panel');

/** 
 * @class micrite.panel.SearchPanel 
 * micrite的查询面板
 */ 
micrite.panel.SearchPanel = function(config) {
    Ext.apply(this, config);
    micrite.panel.SearchPanel.superclass.constructor.call(this);
};

Ext.extend(micrite.panel.SearchPanel, Ext.Panel, {

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
    // 配置项结束
    

    //  自定义变量
    //  当前查询条件Field数组
    curConFields:[],
    //  查询结果grid
    resultGrid:null,
    //  查询函数
    searchFun:null,

    layout:'border',
    border:false,
    
    initComponent:function() {
    
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
                } else if (this.curConFields[i].xtype == 'combo') {
                    v = this.curConFields[i].getValue();
                }else{
                    v = this.curConFields[i].getRawValue();
                }
                name = this.curConFields[i].getName();
                this.resultGrid.store.baseParams[name] = v;
                n++;
            }
            if (n) {
                this.resultGrid.store.load({params:{start:0, limit:Ext.get('pageSize').dom.value}});
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
                } else if (item.xtype == 'combo') {
                    item = new Ext.form.ComboBox(item);
                    panel.curConFields[panel.curConFields.length] = item;
                } else if (item.xtype == 'datetimefield') {
                    item = new Ext.boco.DateTimeField(item);
                    panel.curConFields[panel.curConFields.length] = item;
                }
                panel.getTopToolbar().add(item);
            }
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
        
        var reader = new Ext.data.JsonReader({totalProperty:'totalCount', root:'data', id:'id'},
                                             this.resultDataFields);
        //  创建store
        var store = new Ext.data.Store({
            proxy:new Ext.data.HttpProxy({url:this.searchRequestURL,
                                          listeners:{
                                              loadexception:function(proxy, options, response, error) {
                                                  obj = Ext.util.JSON.decode(response.responseText);
                                                  showMsg('failure', obj.message);
                                              }
                                          }}),
            reader:reader
        });
        
        //  创建分页工具栏
        var pagingToolbar = new Ext.PagingToolbar({
            pageSize:parseInt(Ext.get('pageSize').dom.value),
            store:store,
            displayInfo:true,
            doLoad : function(start) {
                recordStart = start;
                var o = {totalCount:this.store.getTotalCount()};
                pn = this.paramNames;
                o[pn.start] = start;
                o[pn.limit] = this.pageSize;
                this.store.load({params:o});
            }
        });
        
        //  序号列
        var rowNumbererColumn = new Ext.grid.RowNumberer({
            renderer:function(value, metadata, record, rowIndex) {
                return recordStart + 1 + rowIndex;
            }
        });
        //  创建查询结果列模型
        var columnModel = new Ext.grid.ColumnModel([rowNumbererColumn].concat(this.resultColumns));

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
            store:store,
            colModel:columnModel
        });
        
        if (this.resultProcessButtons.length > 0) {
            //  查询结果处理按钮面板
            var resultProcessButtonsPanel = new Ext.Panel({
                region:'south',
                border:false,
                buttons:this.resultProcessButtons,
                viewConfig:{
                    forceFit:true
                }
            });
            this.items = [this.resultGrid, resultProcessButtonsPanel];
        } else {
            this.items = [this.resultGrid];
        }        
        
        micrite.panel.SearchPanel.superclass.initComponent.apply(this, arguments);
    },
    
    listeners:{
        //  面板渲染时，初始化默认的查询条件组
        render:function() {
            var items = this.conCmpGroups[0];
            for (var i = 0; i < items.length; i++) {
                var item = items[i];
                if (item.xtype == 'textfield') {
                    item = new Ext.form.TextField(item);
                    this.curConFields[this.curConFields.length] = item;
                } else if (item.xtype == 'checkbox') {
                    item = new Ext.form.Checkbox(item);
                    this.curConFields[this.curConFields.length] = item;
                } else if (item.xtype == 'combo') {
                    item = new Ext.form.ComboBox(item);
                    this.curConFields[this.curConFields.length] = item;                    
                } else if (item.xtype == 'datetimefield') {
                    item = new Ext.boco.DateTimeField(item);
                    this.curConFields[this.curConFields.length] = item;
                }
                this.getTopToolbar().add(item);
            }
        }
    }
});