<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
/**
 * 初始化命名空间，其实就是通过a={}来建立一个对象
 */
(function() {
	Ext.namespace('micrite.crm.customerList', 'micrite');
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

	// 建立查询结果表格
	this.grid = new micrite.crm.customerList.SearchResultGrid({});

	/**
	 * 构建查询条件,以下是以JSON形式建立的数组对象，每一个字符串开始是一组查询条件
	 * 组建的排列以文本框优先，下拉列表，然后是复选框，后面是button，如果有链接，居右放置
	 * 为了提高页面的显示效率，采用xType，只有当需要显示的时候才会建立对象
	 */
	this.searchComponents = {
		'cdr' : [{
					xtype : 'textfield',
					name : 'telephone',
					width : 100
				}, {
					xtype : 'tbspacer'
				}, {
					text : 'Search',
					cls : 'x-btn-text-icon details',
					scope : this,
					handler : this.startSearch
				}],
		'pdpsdr' : [{
					xtype : 'datetimefield',
					width : 300
				}],
		'wapsdr' : [{
					xtype : 'datetimefield',
					name : 'startTime',
					width : 135
				}, 'To', {
					xtype : 'tbspacer'
				}, {
					xtype : 'datetimefield',
					name : 'endTime',
					width : 135
				}, {
					xtype : 'tbspacer'
				}, {
					xtype : 'tbspacer'
				}, {
					xtype : 'checkbox',
					boxLabel : 'Gb',
					name : 'gb',
					width : 40,
					height : 20
				}, {
					xtype : 'checkbox',
					boxLabel : 'Gn',
					name : 'gn',
					checked : true,
					width : 40,
					height : 20
				}, {
					xtype : 'checkbox',
					boxLabel : 'Gi',
					name : 'gi',
					width : 40,
					height : 20
				}, {
					xtype : 'checkbox',
					boxLabel : 'Gw',
					name : 'gw',
					width : 40,
					height : 20
				}, {
					xtype : 'tbspacer'
				}, {
					text : 'Search',
					cls : 'x-btn-text-icon details',
					scope : this,
					handler : this.startSearch
				}, {
					text : 'Advance',
					cls : 'x-btn-text-icon details',
					width : 100,
					scope : this,
					handler : this.startSearch
				}, '->', {
					xtype : 'actionmenu',
					text : 'New Customer',
					items : [{
								text : 'New Customer',
								handler : this.openTab
							}]
				}]
	};

	// 构建查询组合条件菜单
	this.searchTypeButtonConfig = {
		text : 'WapSDR',
		value : 'wapsdr',
		tooltip : 'Click for more search options',
		handler : this.switchSearchType,
		scope : this,
		menu : {
			items : [{
						text : 'CDR',
						value : 'cdr',
						scope : this,
						handler : this.switchSearchType
					}, {
						text : 'PdpSDR',
						value : 'pdpsdr',
						scope : this,
						handler : this.switchSearchType
					}, {
						text : 'WapSDR',
						value : 'wapsdr',
						scope : this,
						handler : this.switchSearchType
					}]
		}
	};

	// 建立一个button
	this.searchTypeButton = new Ext.Button(this.searchTypeButtonConfig);

	var toolbaritems = [this.searchTypeButton];

	this.searchToolbar = new Ext.Toolbar({
				ctCls : 'search-all-tbar',
				items : toolbaritems
			});

	micrite.crm.customerList.SearchPanel.superclass.constructor.call(this, {
				layout : 'border',
				border : false,
				tbar : this.searchToolbar,
				items : [this.grid, new micrite.actionButton()]
			});

	this.curFields = [];
	/**
	 * 根据定义的组件数组，获取组件类型，建立相应的组件， 同时将每个菜单的对应的组件放入一个数组,为了在提交查选时，便于获得当前组件的值
	 */
	this.on({
				'render' : function() {
					var items = this.searchComponents['wapsdr'];// 默认菜单

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
							item = new micrite.actionMenu(item);
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
	searchText : 'Search By Telephone',
	newCustomerLink : '<a href="crm/customerDetail.jsp" id="Customer Detail" class="inner-link">New Customer</a>',

	switchSearchType : function(button, event) {
		this.setSearchType(this, button.value);
	},
	/**
	 * * 切换菜单时，调用该方法，重置当前button的显示文本, * 只有当选择菜单项与当前菜单不一致时，函数才会继续执行
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
	 * * 切换菜单时，调用该方法，首先将原有的组件删除，然后通过配置建立新的组件
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
				item = new micrite.actionMenu(item);
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
	 * * 遍历当前菜单的组件，获取值，提交查询字符串并载入查询结果
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
		mainPanel.loadModule('crm/customerDetail.jsp', 'Customer Detail');
	}

});

/**
 * 这是grid类
 */
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
				displayInfo : true,
				displayMsg : 'Displaying topics {0} - {1} of {2}',
				emptyMsg : "No topics to display"
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
					msg : 'Loading Results...'
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
micrite.actionMenu = function(a) {
	micrite.actionMenu.superclass.constructor.call(this, a);
};
Ext.extend(micrite.actionMenu, Ext.menu.Menu, {

});
Ext.reg("actionmenu", micrite.actionMenu);

/**
 * 这个类用于建立当界面需要更新数据库时使用
 */
micrite.actionButton = function(config) {
	Ext.apply(this, config);
	micrite.actionButton.superclass.constructor.call(this, {
				region : 'south',
				border : false,
				buttons : [{
							text : 'Submit',
							disabled : true
						}, {
							text : 'Close',
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
Ext.extend(micrite.actionButton, Ext.Panel, {

});

// 载入多语言
try {
	customerListLocale();
} catch (e) {
}

Ext.onReady(function() {
			Ext.QuickTips.init();
			formPanel = new micrite.crm.customerList.SearchPanel();
			/**
			 * * 这里的mainPanel判断用于判断程序是在单元环境还是集成环境运行
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