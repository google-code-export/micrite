<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
	var config = config || {};
	Ext.apply(this, config);

	//建立表格
	this.grid = new micrite.crm.customerList.SearchResultGrid({
	});

	// 构建查询条件
	this.searchComponents = {
		'cdr' : [{
					xtype : 'textfield',
					name : 'search-all-field',
					width : 100
				}, {
					xtype : 'button',
					id : 'gavsearch-artifact',
					size : 100,
					scope : this,
					handler : this.startSearch
				}],
		'pdpsdr' : [{
					xtype : 'datetimefield',
					width : 300
				}],
		'wapsdr' : [{
					xtype : 'datetimefield',
					width : 135
				}, {
					xtype : 'tbspacer'
				}, {
					xtype : 'datetimefield',
					width : 135
				}, '&nbsp;&nbsp;', {
					xtype : 'checkbox',
					boxLabel : 'Gb',
					id : 'gb',
					width : 40
				}, {
					xtype : 'checkbox',
					boxLabel : 'Gn',
					id : 'gn',
					checked : true,
					width : 40
				}, {
					xtype : 'checkbox',
					boxLabel : 'Gi',
					id : 'gi',
					width : 40
				}, {
					xtype : 'checkbox',
					boxLabel : 'Gw',
					id : 'gw',
					width : 40
				}, {
					xtype : 'tbspacer'
				}, {
					xtype : 'button',
					text : 'Search',
					border : true
				}, {
					icon : '/images/icons/search.gif',
					cls : 'x-btn-icon',
					scope : this,
					handler : this.startSearch
				},'-',this.newCustomerLink]
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

	this.searchTypeButton = new Ext.Button(this.searchTypeButtonConfig);

	var toolbaritems = [this.searchTypeButton];

	this.searchToolbar = new Ext.Toolbar({
				ctCls : 'search-all-tbar',
				items : toolbaritems
			});

	// this.artifactInformationPanel = new
	// Sonatype.repoServer.ArtifactInformationPanel({});

	micrite.crm.customerList.SearchPanel.superclass.constructor.call(this, {
				layout:'fit',
				border:false,
				//height:'auto',
			//	hideMode : 'offsets',
				tbar : this.searchToolbar,
				items : [this.grid]
			});

	this.gavFields = [];
	this.gavParams = ['telephone'];

	this.on({
				'render' : function() {
					var items = this.searchComponents['wapsdr'];// 默认菜单
					for (var i = 0; i < items.length; i++) {
						var item = items[i];
						if (item.xtype == 'textfield') {
							item = new Ext.form.TextField(item);
							this.gavFields[this.gavFields.length] = item;
						} else if (item.xtype == 'checkbox') {
							item = new Ext.form.Checkbox(item);
							this.gavFields[this.gavFields.length] = item;
						} else if (item.xtype == 'datetimefield') {
							item = new Ext.boco.DateTimeField(item);
							this.gavFields[this.gavFields.length] = item;
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
	fetchFirst50 : function(p) {
		// p.artifactInformationPanel.collapse();
		p.grid.totalRecords = 0;
		p.grid.store.load({
					params : {
						from : 0,
						count : 50
					}
				});
	},

	switchSearchType : function(button, event) {
		this.setSearchType(this, button.value);
	},

	setSearchType : function(panel, t) {
		if (t != panel.searchTypeButton.value) {
			var items = panel.searchTypeButtonConfig.menu.items;
			panel.searchTypeButton.value = t;
			for (var i = 0; i < items.length; i++) {
				if (items[i].value == t) {
					panel.searchTypeButton.setText(items[i].text);
				}
			}

			panel.createToolbarItems(panel, t);
		}
	},

	createToolbarItems : function(panel, searchType) {
		while (panel.searchToolbar.items.length > 1) {
			var item = panel.searchToolbar.items.last();
			panel.searchToolbar.items.remove(item);
			item.destroy();
		}

		this.gavFields = [];

		var items = panel.searchComponents[searchType];
		for (var i = 0; i < items.length; i++) {
			var item = items[i];

			if (item.xtype == 'textfield') {
				item = new Ext.form.TextField(item);
				this.gavFields[this.gavFields.length] = item;
			} else if (item.xtype == 'checkbox') {
				item = new Ext.form.Checkbox(item);
				this.gavFields[this.gavFields.length] = item;
			} else if (item.xtype == 'datetimefield') {
				item = new Ext.boco.DateTimeField(item);
				this.gavFields[this.gavFields.length] = item;
			}
			panel.searchToolbar.add(item);
		}

	},

	startSearch : function() {
		this.grid.store.baseParams = {};

		var n = 0;
		for (var i = 0; i < this.gavFields.length; i++) {
			var v = null;

			if (this.gavFields[i].xtype == 'checkbox') {
				v = this.gavFields[i].checked;
			} else {
				v = this.gavFields[i].getRawValue();
			}
			console.log(v);
			this.grid.store.baseParams[this.gavParams[i]] = v;
			n++;
			//临时语句
			if (n>0) break; 
		}

		if ( n ) {
			this.grid.store.load();
		}
	},

	gavEnterHandler : function(f, e) {
		if (e.getKey() == e.ENTER) {
			this.startSearch();
		}
	}

});

micrite.crm.customerList.SearchResultGrid = function(config) {
	newCustomerLink:'<a href="../crm/customerDetail.jsp" id="Customer Detail" class="inner-link">New Customer</a>'
		  Ext.apply(this, config);
		  var resultReader = new Ext.data.JsonReader({}, [
             {name: 'id',type:'int'},
             {name: 'name'},
             {name: 'telephone'},
             {name: 'customerSource'}]);

		  var requestProxy = new Ext.data.HttpProxy({
		    url:  '/' + document.location.href.split("/")[3] + '/crm/customerFind.action',
		    method: 'GET'
		    //headers: {Accept: 'application/json'}
		  });

		  this.store = new Ext.data.Store({
		    proxy: requestProxy,
		    reader: resultReader
		  });
		    var pagingBar = new Ext.PagingToolbar({
		        pageSize: 1,
		        store: this.store,
		        displayInfo: true,
		        displayMsg: 'Displaying topics {0} - {1} of {2}',
		        emptyMsg: "No topics to display"
		    });
		    
		 // this.store.setDefaultSort('id', "ASC");

		  this.columns = [
		                  {id:'id',header: this.colModelId, width: 40, sortable: true, locked:false, dataIndex: 'id'},
		                  {header: this.colModelName, width: 100, sortable: true,  dataIndex: 'name'},
		                  {header: this.colModelMobile, width: 100, sortable: true,  dataIndex: 'telephone'},
		                  {header: this.colModelSource, width: 100, sortable: true,  dataIndex: 'customerSource',renderer:sourceType}
		   ];
	

		  micrite.crm.customerList.SearchResultGrid.superclass.constructor.call(this, {
		    //region:'center',
		      id: 'search-result-grid',
		    // height:'auto',
		     border:false,
		      loadMask: {msg:'Loading Results...'},
		      stripeRows: true,
		      sm: new Ext.grid.RowSelectionModel({
		          singleSelect: true
		      }),
		      bbar:pagingBar,

		      viewConfig: {
		          forceFit:true,
		          enableRowBody:true
		      }
		  });
		};


Ext.extend(micrite.crm.customerList.SearchResultGrid, Ext.grid.GridPanel, {
    colModelId:'ID',
    colModelName:'Name',
    colModelMobile:'Mobile',
    colModelSource:'Source',
    searchText:'Search By Telephone',
    newCustomerLink:'<a href="crm/customerDetail.jsp" id="Customer Detail" class="inner-link">New Customer</a>'
});

function sourceType(val){
    return val.name;
}

try{ customerListLocale();}catch(e){}

Ext.onReady(function(){
    Ext.QuickTips.init();
    formPanel = new micrite.crm.customerList.SearchPanel();
    if (mainPanel){
        mainPanel.getActiveTab().add(formPanel);
        mainPanel.getActiveTab().doLayout();
    }else{
        new Ext.Viewport({
        	layout:'fit',
	        items:[
	        	formPanel
	        ]
        });
    }
});
</script>