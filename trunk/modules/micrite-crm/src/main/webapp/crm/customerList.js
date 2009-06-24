<script type="text/javascript">
/**
 * 初始化命名空间，其实就是通过a={}来建立一个对象
 */
(function() {
    Ext.namespace('micrite.crm.customerList');
})();

/**
 * 查询面板配置项
 * 
 * @param {}
 *            config
 */
micrite.crm.customerList.SearchPanel = function(config) {
    // 建立一个配置对象，将当前对象的父类的配置项复制给当前当前对象
    var config = config || {};
    Ext.apply(this, config);

    //多个查询条件分组时，可以通过调用searchButton共享
    var searchButton = {
            text: mbLocale.searchButton,
            cls: 'x-btn-text-icon details',
            scope : this,
            handler : this.startSearch
        };
    //共享action menu，集中设置
    var actionMenu ={
            xtype : 'actionmenu',
            items : [{
                        text : this.newCustomer,
                        handler : this.openTab
                    },{
                        text : this.chart,
                        handler : this.openWindow
                    }]
        };
    /**
     * 构建查询条件,以下是以JSON形式建立的数组对象，每一个字符串开始是一组查询条件
     * 组建的排列以文本框优先，下拉列表，然后是复选框，后面是button，如果有链接，居右放置
     * 为了提高页面的显示效率，采用xType，只有当需要显示的时候才会建立对象
     */
     this.searchComponents = {
         // 三组查询条件，每组查询条件都因该有同样的searchButton和actionMenu
         // 注意之间的分栏符号
         'con1' : ['-',this.searchCellphone,{
                     xtype : 'textfield',
                     name : 'telephone',
                     width : 100
                 },'-', searchButton,'->',actionMenu    ],
         'con2' : ['-',this.searchStartTime,{
                     xtype : 'datetimefield',
                     width : 135
                 },'-', searchButton,'->',    actionMenu],
         'con3' : ['-',this.searchStartTime,{
                     xtype : 'datetimefield',
                     name  : 'startTime',
                     width : 135
                 },this.searchEndTime, {
                     xtype : 'tbspacer'
                 }, {
                     xtype : 'datetimefield',
                     name  : 'endTime',
                     width : 135
                 },{
                     xtype : 'tbspacer'
                 }, {
                     xtype : 'tbspacer'
                 },{
                     xtype : 'checkbox',
                     boxLabel : 'Gb',
                     name  : 'gb',
                     width : 40,
                     height: 20
                 }, {
                     xtype : 'checkbox',
                     boxLabel : 'Gn',
                     name : 'gn',
                     checked : true,
                     width : 40,
                     height: 20
                 }, {
                     xtype : 'checkbox',
                     boxLabel : 'Gi',
                     name : 'gi',
                     width : 40,
                     height: 20
                 }, {
                     xtype : 'checkbox',
                     boxLabel : 'Gw',
                     name : 'gw',
                     width : 40,
                     height: 20
                 }, {
                     xtype : 'tbspacer'
                 },'-', searchButton,'->',
                 actionMenu]
     };

    // 构建查询组合条件菜单
    this.searchTypeButtonConfig = {
        // 默认的查询条件组合
        text : 'CDR',
        value : 'con1',
        tooltip : 'Click for more search options',
        handler : this.switchSearchType,
        scope : this,
        menu : {
            items : [{
                        text : 'CDR',
                        value : 'con1',
                        scope : this,
                        handler : this.switchSearchType
                    }, {
                        text : 'PdpSDR',
                        value : 'con2',
                        scope : this,
                        handler : this.switchSearchType
                    }, {
                        text : 'WapSDR',
                        value : 'con3',
                        scope : this,
                        handler : this.switchSearchType
                    }]
        }
    };

    // 建立一个button，用来触发组合查询条件的菜单
    this.searchTypeButton = new Ext.Button(this.searchTypeButtonConfig);

    var toolbaritems = [this.searchTypeButton];

    this.searchToolbar = new Ext.Toolbar({
                ctCls : 'search-all-tbar',
                items : toolbaritems
            });

    // 建立查询结果表格
    this.grid = new micrite.crm.customerList.SearchResultGrid({});
    
    micrite.crm.customerList.SearchPanel.superclass.constructor.call(this, {
                layout : 'border',
                border : false,
                tbar : this.searchToolbar,
                items : [this.grid, new micrite.crm.customerList.actionButton()]
            });

    this.curFields = [];
    /**
     * 根据定义的组件数组，获取组件类型，建立相应的组件， 同时将每个菜单的对应的组件放入一个数组,为了在提交查选时，便于获得当前组件的值
     */
    this.on({
                'render' : function() {
                    var items = this.searchComponents['con1'];// 默认菜单

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
                        } else if (item.xtype == 'actionmenu') {
                            item = new micrite.crm.customerList.actionMenu(item);
                            var tmp = {
                                text : 'Action',
                                menu : item
                            }
                            item = tmp;
                        }
                        this.searchToolbar.add(item);
                    }
                },
                scope : this
            });

    // this.grid.on( 'rowclick', this.displayArtifactInformation, this );
    // this.grid.clearButton.on( 'click', this.clearArtifactInformation, this );
};

/**
 * 定义查询面板的方法
 */
Ext.extend(micrite.crm.customerList.SearchPanel, Ext.Panel, {
    searchCondition1:'Condition 1',
    searchCondition2:'Condition 2',
    searchCondition3:'Condition 3',
    searchCellphone:'Cellphone',    
    searchStartTime:'StartTime',
    searchEndTime:'EndTime',
    newCustomer :'New Customer',
    chart :'chart',
    
    switchSearchType : function(button, event) {
        this.setSearchType(this, button.value);
    },
    /**
     * 切换菜单时，调用该方法，重置当前button的显示文本, 
     * 只有当选择菜单项与当前菜单不一致时，函数才会继续执行
     */
    setSearchType : function(panel, t) {
        if (t != panel.searchTypeButton.value) {
            var items = panel.searchTypeButtonConfig.menu.items;
            // 设置button的值
            panel.searchTypeButton.value = t;
            for (var i = 0; i < items.length; i++) {
                if (items[i].value == t) {
                    // 设置button的显示文本
                    panel.searchTypeButton.setText(items[i].text);
                }
            }

            panel.createToolbarItems(panel, t);
        }
    },
    /**
     * 
     * 切换菜单时，调用该方法，首先将原有的组件删除，然后通过配置建立新的组件
     */
    createToolbarItems : function(panel, searchType) {
        while (panel.searchToolbar.items.length > 1) {
            var item = panel.searchToolbar.items.last();
            panel.searchToolbar.items.remove(item);
            item.destroy();
        }

        this.curFields = [];
        // 根据组合查询条件下拉列表获得组合条件对象
        var items = panel.searchComponents[searchType];
        // 遍历组合条件对象，创建组件，并加入到工具栏
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
            } else if (item.xtype == 'actionmenu') {
                item = new micrite.crm.customerList.actionMenu(item);
                var tmp = {
                    text : 'Action',
                    menu : item
                }
                item = tmp;
            }
            panel.searchToolbar.add(item);
        }

    },
    /**
     * 
     * 遍历当前菜单的组件，获取值，提交查询字符串并载入查询结果
     */
    startSearch : function() {
        this.grid.store.baseParams = {};

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
            this.grid.store.baseParams[name] = v;
            n++;

        }
        // 如果有条件为真，才提交查询
        if (n) {
            this.grid.store.load();
        }
    },
    // 支持回车提交查询的函数
    gavEnterHandler : function(f, e) {
        if (e.getKey() == e.ENTER) {
            this.startSearch();
        }
    },

    openTab : function() {
        var win;
        if(!(win = Ext.getCmp('addCusetomerWindow'))){
            win = new Ext.Window({
                id: 'addCusetomerWindow',
                title    : this.addUser,
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
//        mainPanel.loadModule('crm/customerDetail.js', 'Customer Detail');
    },
    openWindow : function() {
        var tabs = new Ext.TabPanel({
            margins   : '0', 
            activeTab : 0,
            defaults  : {
                bodyStyle: 'background-color: #EEE;',            
                autoScroll : true
            },
            items     : [{
                title    : 'Bar Chart',
                html     : '<img src = "'+'/' + document.location.href.split("/")[3]
                                                                                  + '/crm/getCustomerSourceBarChart.action'+'">'
             },{
                title    : 'Pie Chart',
                html     : '<img src = "'+'/' + document.location.href.split("/")[3]
                                                                                  + '/crm/getCustomerSourcePieChart.action'+'">'
             }]
        });
        var win = new Ext.Window({
            title    : 'chart display',
            closable : true,
            width    : 630,
            height   : 520,
            //border : false,
            plain    : true,
            maximizable: true,
            renderTo : mainPanel.getActiveTab().items.itemAt(0).getId(),
            layout   : 'fit',
            items    : [tabs]
        });
        win.show();
    }
});

/***********************************************************************
 * 
 * Grid类配置
 ***********************************************************************/
micrite.crm.customerList.SearchResultGrid = function(config) {
    Ext.apply(this, config);
    var resultReader = new Ext.data.JsonReader({}, [{
                        name : 'id',
                        type : 'int'
                    }, {
                        name : 'name'
                    }, {
                        name : 'telephone'
                    }, {
                        name : 'customerSource'
                    }]);

    var requestProxy = new Ext.data.HttpProxy({
        url : '/' + document.location.href.split("/")[3]
                + '/crm/findCustomer.action',
        method : 'GET'
            // headers: {Accept: 'application/json'}
        });

    this.store = new Ext.data.Store({
                proxy : requestProxy,
                reader : resultReader
            });
    // 设置一个分页栏，这是封装好的一个分页button，只需要坐下配置
    var pagingBar = new Ext.PagingToolbar({
                pageSize : 1,
                store : this.store,
                displayInfo : true
            });

    // this.store.setDefaultSort('id', "ASC");

    this.columns = [{
                id : 'id',
                header : this.colModelId,
                width : 40,
                sortable : true,
                locked : false,
                dataIndex : 'id'
            }, {
                header : this.colModelName,
                width : 100,
                sortable : true,
                dataIndex : 'name'
            }, {
                header : this.colModelMobile,
                width : 100,
                sortable : true,
                dataIndex : 'telephone'
            }, {
                header : this.colModelSource,
                width : 100,
                sortable : true,
                dataIndex : 'customerSource',
                renderer : sourceType
            }];

    /**
     * 调用超类，同时将配置项传入。
     * 
     */
    micrite.crm.customerList.SearchResultGrid.superclass.constructor.call(this,
            {
                region : 'center',
                id : 'search-result-grid',
                // height:'auto',
                border : false,
                loadMask : {
                    msg : mbLocale.loadingMsg
                },
                stripeRows : true,
                sm : new Ext.grid.RowSelectionModel({
                            singleSelect : true
                        }),
                bbar : pagingBar,

                viewConfig : {
                    forceFit : true,
                    enableRowBody : true
                }
            });
};
// eof SearchResultGrid

/**
 * 这里是为了方便多语言的实现，而写的一个函数
 */
Ext.extend(micrite.crm.customerList.SearchResultGrid, Ext.grid.GridPanel, {
            colModelId : 'ID',
            colModelName : 'Name',
            colModelMobile : 'Mobile',
            colModelSource : 'Source'
        });
/**
 * 由于收到的数据是个数组，所以需要使用该函数提取值
 * 
 * @param val
 * @return
 */
function sourceType(val) {
    return val.name;
}
/**
 * 用于调用模块相关页面
 */
micrite.crm.customerList.actionMenu = function(a) {
    micrite.crm.customerList.actionMenu.superclass.constructor.call(this, a);
};
Ext.extend(micrite.crm.customerList.actionMenu, Ext.menu.Menu, {

});
Ext.reg("actionmenu", micrite.crm.customerList.actionMenu);

/**
 * 这个类用于建立当界面需要更新数据库时使用(grid下方的一组button)
 */
 micrite.crm.customerList.actionButton = function(config) {
    Ext.apply(this, config);
    micrite.crm.customerList.actionButton.superclass.constructor.call(this, {
                region : 'south',
                border : false,
                buttons : [{
                            text : mbLocale.submitButton,
                            disabled : true
                        }, {
                            text : mbLocale.closeButton,
                            handler : function() {
                                win.hide();
                            }
                        }],
                viewConfig : {
                    forceFit : true
                }
            });
}
/**
 * actionButton类的多语言实现
 */
Ext.extend(micrite.crm.customerList.actionButton, Ext.Panel, {
});

// 载入多语言
// 因为采用autoload模式，不能用默认的国际化模式，只能显式的通过方法调用去加载国际化
// 采用此方式，如果没有相应的locale文件，会报错，catch它，用重载前的类变量也可以正常运行
// 参见js-lib/ext-us-js/BaseLocale.js，有详细的国际化策略
try {customerListLocale();} catch (e) {}
try {baseLocale();} catch (e) {}

Ext.onReady(function() {
            Ext.QuickTips.init();
            formPanel = new micrite.crm.customerList.SearchPanel();
            /**
             * 
             * 这里的mainPanel判断用于判断程序是在单元环境还是集成环境运行
             */
            if (mainPanel) {
                // 将当前新的面板放入tabpanel
                mainPanel.getActiveTab().add(formPanel);
                // 重画这个面板，显示新的刚装入的组件面板
                mainPanel.getActiveTab().doLayout();
            } else {
                new Ext.Viewport({
                            layout : 'fit',
                            items : [formPanel]
                        });
            }
        });
</script>