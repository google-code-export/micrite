Ext.onReady( function() {
var win;
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	Ext.QuickTips.init();
   var menu = new Ext.menu.Menu({
        id: 'mainMenu',
        items: [
            {
                text: 'My workshop',
                checked: false
            },'-', {
                text: 'All',
                menu: {        // <-- submenu by nested config object
                    items: [
                        {
                            text: 'CRM'
                        }, {
                            text: 'Security Manage'
                        }
                    ]
                }
            }
        ]
    });
  

	// add a combobox to the toolbar
		var store = new Ext.data.SimpleStore( {
			fields : [ 'action', 'actionName', 'desc' ],
			data : [ [ 'createOne', 'Employee', 'adding new employee' ],
					[ 'createTwo', 'Order', 'adding new order' ] ]
		});
		var combo = new Ext.form.ComboBox(
				{
					store :store,
					tpl :'<tpl for="."><div ext:qtip="{actionName}. {desc}" class="x-combo-list-item">{actionName}</div></tpl>',
					displayField :'actionName',
					typeAhead :true,
					mode :'local',
					forceSelection :true,
					triggerAction :'all',
					emptyText :'Add...',
					selectOnFocus :true,
					applyTo :'local-states'
				});

		var hd = new Ext.Panel( {
			border :false,
			layout :'anchor',
			region :'north',
			cls :'docs-header',
			height :60,
			items : [
					{
						xtype :'box',
						el :'north',
						border :false,
						anchor :'none -25'
					},
					new Ext.Toolbar( {
						cls :'top-toolbar',
						items : [
								' ',
							{
								 text:'Home',
            					iconCls: 'home'  // <-- i
							},
								{
								 text:'Menu',
            					iconCls: 'menu',  // <-- i
            					menu:menu
							},
								' ',
								' ',
								{
										tooltip :'open email',
									iconCls :'email',
							
									handler : function() {
										api.root.expand(true);
									}
								},
								'->',
								{
										iconCls :'user',
									tooltip :'user infomation',
									enableToggle :true,
									toggleHandler : function(b, pressed) {
										mainPanel[pressed ? 'addClass'
												: 'removeClass']
												('hide-inherited');
									}
								},
								'-',
								{
									tooltip :'exit',
									iconCls :'exit',
									handler : function() {
									window.location = '../j_spring_security_logout';
									}
								} ]
					}) ]
		});

		var viewport = new Ext.Viewport( {
			layout :'border',
			items : [ hd, {
				region :'west',
				id :'west-panel',
				title :' ',
				split :true,
				width :200,
				minSize :175,
				maxSize :400,
				collapsible :true,
				margins :'0 0 0 5',
				items : [ {
					contentEl :'c3',
					title :'create new',
					border :false,
					split :true,
					tools : [ {
						id :'plus',
						qtip :'modify create new',
						handler : function() {
  var myData = [
		["adding new employee"],
		["adding new order"]
	];
    var xg = Ext.grid;

    // shared reader
    var reader = new Ext.data.ArrayReader({}, [
       {name: 'company'}
    ]);

var sm2 = new xg.CheckboxSelectionModel();
    var grid4 = new xg.GridPanel({
        id:'button-grid',
        store: new Ext.data.Store({
            reader: reader,
            data: myData
        }),
        cm: new xg.ColumnModel([
            sm2,
            {id:'company',header: "Action name", width: 40, sortable: true, dataIndex: 'company'}
        ]),
        sm: sm2,

        viewConfig: {
            forceFit:true
        },


        width:300,
        height:300,
        frame:true,
        region:'center',
        iconCls:'icon-grid'
    });



//     if(!win){
							win = new Ext.Window( {
								title :'Layout Window',
								closable :true,
								width :300,
								height :350,
								//border : false,
								plain :true,
								layout :'border',
								 items    : [grid4]
							});
							
//     }
     win.show();
						}
						
					} ]
				}, {
					title :'favorites',
					contentEl :'favo',
					border :false,
					split :true,
					tools : [ {
						id :'plus',
						qtip :'modify favorite',
						handler : function() {
							alert(1);
						}
					} ]

				} ]
			}, new Ext.TabPanel( {
				id :'tabp',
				region :'center',
				resizeTabs:true,
				 enableTabScroll:true,
				deferredRender :false,
				 minTabWidth: 115,
				defaults: {autoScroll:true},
				activeTab :0,
				items : [  {
					html :'Welcome!',
					title :'Center Panel',
					autoScroll :true
				} ]
			}) ]
		});

	});

function addTab() {
	var tabs = Ext.ComponentMgr.get('tabp');
    if (!tabs.getItem('crmTab')){
	var tab = tabs.add( {
        id:'crmTab',
		title :'CRM',
		iconCls :'tabs',
		contentEl :'c4',
		closable :true
	}).show();

   gridForm.applyToMarkup('c4');
}else{
   tabs.getItem('crmTab').show();

}
 
}