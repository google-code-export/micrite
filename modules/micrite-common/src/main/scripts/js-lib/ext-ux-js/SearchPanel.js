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
     * @cfg {Array} conditionGroupNames 查询条件组名称数组，形式如下：
     * ['条件1','条件2',...]
     */
    conditionGroupNames:[],

    /**
     * @cfg {Array} conditionGroupComponentGroups 查询条件组组件组数组，形式如下：
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
    conditionGroupComponentGroups:[],

    /**
     * @cfg {Array} linkerMenuItems 超链菜单项数组，形式如下：
     * [
     *     {
     *         text:'增加新用户',
     *         handler:function() {}
     *     },
     *     ...
     * ]
     */
    linkerMenuItems:[],
    
    /**
     * @cfg {String} requestURL 查询请求的url
     */
    requestURL:'',
    
    /**
     * @cfg {Reader} resultReader 查询结果格式读取器
     */
    resultReader:null,

    /**
     * @cfg {Array} resultColumns 查询结果列
     */
    resultColumns:[],

    /**
     * @cfg {Array} actionButtons 动作按钮数组
     */
    actionButtons:[],
    // 配置项结束

    //  当前Field数组
    curFields:[],

    layout:'border',
    border:false,
    
    initComponent:function() {
    
        //  分页显示行号用
        var record_start = 0;
        //  查询函数（点击查询按钮执行）
        var startSearch = function() {
            record_start = 0;
            this.resultGrid.store.baseParams = {};
            var n = 0;
            for (var i = 0; i < this.curFields.length; i++) {
                var v = null;
                var name = null;   
                if (this.curFields[i].xtype == 'checkbox') {
                    v = this.curFields[i].checked;   
                } else {
                    v = this.curFields[i].getRawValue();
                }
                name = this.curFields[i].getName();
                this.resultGrid.store.baseParams[name] = v;
                n++;
            }
            if (n) {
                this.resultGrid.store.load({params:{start:0, limit:5}});
            }
        };         
        //  查询按钮
        var searchButton = {
            text:mbLocale.searchButton,
            cls:'x-btn-text-icon details',
            scope:this,
            handler:startSearch
        };
        
        //  超链菜单（位于工具栏右面）
        var linkerMenu = {
            text:'Action',
            menu:{xtype:'menu', items:this.linkerMenuItems}
        };
        
        //  向查询条件组组件组中加入共享组件，主要有“查询按钮”和“超链菜单”两个组件
        for (var i = 0; i < this.conditionGroupComponentGroups.length; i++) {
            var temp = ['-'].concat(this.conditionGroupComponentGroups[i]);
            this.conditionGroupComponentGroups[i] = temp.concat(['-', searchButton, '->', linkerMenu]);
        }

        //  切换查询条件组调用函数
        var switchConditionGroup = function(menuItem) {
            //  修改切换按钮属性
            var modifySwitchButton = function(panel, menuItemSelectedValue) {
                var menuItems = panel.conditionGroupSwitchButton.menu.items;
                panel.conditionGroupSwitchButton.value = menuItemSelectedValue;
                panel.conditionGroupSwitchButton.setText(panel.conditionGroupNames[menuItemSelectedValue]); 
            };
            //  删除ToolbarItems
            var removeToolbarItems = function(panel) {
                var toolbarItems = panel.getTopToolbar().items;
                while (toolbarItems.length > 1) {
                    var item = toolbarItems.last();
                    toolbarItems.remove(item);
                    item.destroy();
                }
            };
            //  重建ToolbarItems
            var reCreateToolbarItems = function(panel, menuItemSelectedValue) {
                var items = panel.conditionGroupComponentGroups[menuItemSelectedValue];
                for (var i = 0; i < items.length; i++) {
                    var item = items[i];
                    if (item.xtype == 'textfield') {
                        item = new Ext.form.TextField(item);
                        panel.curFields[panel.curFields.length] = item;
                    } else if (item.xtype == 'checkbox') {
                        item = new Ext.form.Checkbox(item);
                        panel.curFields[panel.curFields.length] = item;
                    } else if (item.xtype == 'datetimefield') {
                        item = new Ext.boco.DateTimeField(item);
                        panel.curFields[panel.curFields.length] = item;
                    }
                    panel.getTopToolbar().add(item);
                }
            };            
            var menuItemSelectedValue = menuItem.value;
            //  若改变了菜单项
            if (menuItemSelectedValue != this.conditionGroupSwitchButton.value) {
                //  修改切换按钮属性
                modifySwitchButton(this, menuItemSelectedValue);
                //  删除ToolbarItems
                removeToolbarItems(this);
                //  重建ToolbarItems
                reCreateToolbarItems(this, menuItemSelectedValue);
            }
        };
        //  创建查询条件组切换按钮上的菜单
        var conditionGroupSwitchMenu = new Ext.menu.Menu();
        for (var i = 0; i < this.conditionGroupNames.length; i++) {
            conditionGroupSwitchMenu.add({
                text:this.conditionGroupNames[i],
                value:i,
                scope:this,
                handler:switchConditionGroup
            });
        }
        
        //  创建查询条件组切换按钮
        this.conditionGroupSwitchButton = new Ext.Button({
            text:this.conditionGroupNames[0],
            value:0,
            tooltip:'Click for more search options',
            menu:conditionGroupSwitchMenu
        });            

        //  创建tbar
        this.tbar = new Ext.Toolbar({
            ctCls:'search-all-tbar',
            items:[this.conditionGroupSwitchButton]
        });

        //  创建store
        var resultStore = new Ext.data.Store({
            proxy:new Ext.data.HttpProxy({url:this.requestURL}),
            reader:this.resultReader
        });
        
        //  序号列
        var rowNumbererColumn = new Ext.grid.RowNumberer({
            header : mbLocale.indexColumnHeader,
            width : 40,
            renderer:function(value, metadata, record, rowIndex) {
                return record_start + 1 + rowIndex;
            }
        });
        //  创建查询结果列模型
        var resultColumnModel = new Ext.grid.ColumnModel([rowNumbererColumn].concat(this.resultColumns));
        
        //  创建分页工具栏
        var pagingToolbar = new Ext.PagingToolbar({
            pageSize:5,
            store:resultStore,
            displayInfo:true,
            doLoad : function(start) {
                record_start = start;
                var o = {}, 
                pn = this.paramNames;
                o[pn.start] = start;
                o[pn.limit] = this.pageSize;
                this.store.load({params:o});
            }
        });
        //  创建查询结果grid
        this.resultGrid = new Ext.grid.GridPanel({
            region:'center',
            border:false,
            loadMask:{
                msg:mbLocale.loadingMsg
            },
            stripeRows:true,
            sm:new Ext.grid.RowSelectionModel({
                    singleSelect:true
                }),
            bbar:pagingToolbar,
            viewConfig:{
                forceFit:true,
                enableRowBody:true
            },
            ds:resultStore,
            cm:resultColumnModel
        });
        
        //  创建动作按钮Panel
        var actionButtonsPanel = new Ext.Panel({
            region:'south',
            border:false,
            buttons:this.actionButtons,
            viewConfig:{
                forceFit:true
            }
        });
        
        this.items = [this.resultGrid, actionButtonsPanel];
        micrite.panel.SearchPanel.superclass.initComponent.apply(this, arguments);
    },
    
    listeners:{
        //  面板渲染时，初始化默认的查询条件组
        render:function() {
            var items = this.conditionGroupComponentGroups[0];
            for (var i = 0; i < items.length; i++) {
                var item = items[i];
                if (item.xtype == 'textfield') {
                    item = new Ext.form.TextField(item);
                    this.curFields[this.curFields.length] = item;
                } else if (item.xtype == 'checkbox') {
                    item = new Ext.form.Checkbox(item);
                    this.curFields[this.curFields.length] = item;
                } else if (item.xtype == 'datetimefield') {
                    item = new Ext.boco.DateTimeField(item);
                    this.curFields[this.curFields.length] = item;
                }
                this.getTopToolbar().add(item);
            }
        }
    }
});