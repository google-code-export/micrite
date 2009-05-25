/*
 * Ext JS Library 2.2.1
 * Copyright(c) 2006-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
/*
 * Ext JS Library 2.2.1
 * Copyright(c) 2006-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

Ext.app.SearchField = Ext.extend(Ext.form.TwinTriggerField, {
    initComponent : function(){
        Ext.app.SearchField.superclass.initComponent.call(this);
        this.on('specialkey', function(f, e){
            if(e.getKey() == e.ENTER){
                this.onTrigger2Click();
            }
        }, this);
    },

    validationEvent:false,
    validateOnBlur:false,
    trigger1Class:'x-form-clear-trigger',
    trigger2Class:'x-form-search-trigger',
    hideTrigger1:true,
    width:180,
    hasSearch : false,
    paramName : 'telephone',

    onTrigger1Click : function(){
        if(this.hasSearch){
            this.el.dom.value = '';
            var o = {start: 0};
            this.store.baseParams = this.store.baseParams || {};
            this.store.baseParams[this.paramName] = '';
            this.store.reload({params:o});
            this.triggers[0].hide();
            this.hasSearch = false;
        }
    },

    onTrigger2Click : function(){
        var v = this.getRawValue();
        if(v.length < 1){
            this.onTrigger1Click();
            return;
        }
        var o = {start: 0};
        this.store.baseParams = this.store.baseParams || {};
        this.store.baseParams[this.paramName] = v;
        this.store.reload({params:o});
        this.hasSearch = true;
        this.triggers[0].show();
    }
});
var gridForm;
Ext.onReady(function(){

    Ext.QuickTips.init();

//    var store = new Ext.data.Store({
//    	url: 'getPartner.action',
//    	fields: ['abbr', 'state'],
//    	reader:new Ext.data.JsonReader({
//    		id:'id'
//    	})
//    });
    var RecordDef = Ext.data.Record.create([    
            {name: 'id'},{name: 'name'}                   
        ]); 
    var store = new Ext.data.Store({    
    	autoLoad:true,
        //设定读取的地址
        proxy: new Ext.data.HttpProxy({url: '/' + document.location.href.split("/")[3] + '/crm/getPartner.action'}),    
        //设定读取的格式    
        reader: new Ext.data.JsonReader({    
            id:"id"
        }, RecordDef),    
        remoteSort: true   
    });

    // turn on validation errors beside the field globally
    Ext.form.Field.prototype.msgTarget = 'side';

    var bd = Ext.getBody();

//   Define the Grid data and create the Grid
    var myData = [
        [1,'Joker','1390203023023','中国移动'],
        [2,'terry','1340444023023','中国联通']
    ];

    var ds = new Ext.data.Store({
     	proxy: new Ext.data.HttpProxy({
        	url: '/' + document.location.href.split("/")[3] + '/crm/find.action', // serverside script to post to
        	method: 'POST' // method of posting .. GET or POST .. I've used POST
    	}),
        reader: new Ext.data.JsonReader({}, [
               {name: 'id',type:'int'},
               {name: 'name'},
               {name: 'telephone'},
               {name: 'customerSource'}
          ])
    });
//    ds.loadData(myData);



    // the DefaultColumnModel expects this blob to define columns. It can be extended to provide
    // custom or reusable ColumnModels
    var colModel = new Ext.grid.ColumnModel([
        {id:'id',header: "ID", width: 40, sortable: true, locked:false, dataIndex: 'id'},
        {header: "Name", width: 100, sortable: true,  dataIndex: 'name'},
        {header: "Mobile", width: 100, sortable: true,  dataIndex: 'telephone'},
        {header: "Partner", width: 100, sortable: true,  dataIndex: 'customerSource',renderer:parnterName}
    ]);

//    bd.createChild({tag: 'h2', html: 'Using a Grid with a Form'});

/*
 *	Here is where we create the Form
 */
    gridForm = new Ext.FormPanel({
        id: 'company-form',
        frame: true,
        labelAlign: 'left',
        title: 'Customer',
        bodyStyle:'padding:5px',
        width: 650,
        layout: 'column',	// Specifies that the items will now be arranged in columns

        items: [{
            columnWidth: 0.6,
            layout: 'fit',
            
            items: {
	            xtype: 'grid',
	            ds: ds,
	            cm: colModel,
	            sm: new Ext.grid.RowSelectionModel({
	                singleSelect: true,
	                listeners: {
	                    rowselect: function(sm, row, rec) {
//	            	alert(rec.json.customerSource.id);
	                        Ext.getCmp("company-form").getForm().loadRecord(rec);
	            	
	            	Ext.getCmp('partnerSelect').setValue(rec.json.customerSource.id);
	                       
	                        
	                    }
	                }
	            }),
	         //   autoExpandColumn: 'company',
	            height: 350,
//	            title:'客户数据',
	            border: true,
		        listeners: {
		        	render: function(g) {
		        		g.getSelectionModel().selectRow(0);
		        	},
		        	delay: 10 // Allow rows to be rendered.
		        },
		        tbar: [
		               'Search: ', ' ',
		               new Ext.app.SearchField({
		                   store: ds,
		                   width:320
		               })
		           ]


        	}
        },{
        	columnWidth: 0.4,
            xtype: 'fieldset',
            labelWidth: 40,
            title:'Customer Detail',
            defaults: {width: 170},	// Default config options for child items
            defaultType: 'textfield',
            autoHeight: true,
            bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
            border: false,
            style: {
                "margin-left": "10px", // when you add custom margin in IE 6...
                "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"  // you have to adjust for it somewhere else
            },
            items: [{
            	id:'cid',
                fieldLabel: 'ID',
                 readOnly:true,
                name: 'id'
            },{
            	id:'cname',
                fieldLabel: 'Name',
                name: 'name'
            },{
            	id:'ctelephone',
                fieldLabel: 'Mobile',
                name: 'telephone'
            }, new Ext.form.ComboBox({
            	id:'partnerSelect',
            	name:'customerSource',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'id',
                displayField:'name',
                fieldLabel: 'Partner',
                emptyText:'Select a partner...',
                editable:false,
                anchor:'90%',
                forceSelection:true,
                triggerAction:'all',
                lazyInit:false,

                store:store,
                typeAhead: true

                
            })],
            buttons: [{
                text: 'Modify',
                handler: function(){
            	gridForm.form.submit({
                    url: '/' + document.location.href.split("/")[3] + '/crm/edit.action',
                    method: 'POST',
                    disabled:true,
                    waitMsg: 'Saving Data...',
                    params:{
            		customerSourceId: Ext.getCmp('partnerSelect').getValue(),
    				'customer.id': Ext.getCmp('cid').getValue(),
    				'customer.name': Ext.getCmp('cname').getValue(),
    				'customer.telephone' : Ext.getCmp('ctelephone').getValue()
        			},
                    success: function(form, action){
                        Ext.MessageBox.alert('Message', 'Plan saved.');
                    },
                    failure: function(form, action){
                        Ext.MessageBox.alert('Message', 'Save failed');
                    }
                });}
            },{
                text: 'Add',
                handler: function(){
            	gridForm.form.submit({
                    url: '/' + document.location.href.split("/")[3] + '/crm/saveData.action',
                    method: 'POST',
                    disabled:true,
                    waitMsg: 'Saving Data...',
                    params:{
        				customerSourceId: Ext.getCmp('partnerSelect').getValue(),
        				'customer.id': Ext.getCmp('cid').getValue(),
        				'customer.name': Ext.getCmp('cname').getValue(),
        				'customer.telephone' : Ext.getCmp('ctelephone').getValue()
        			},
                    success: function(form, action){
                        Ext.MessageBox.alert('Message', 'Plan saved.');
                    },
                    failure: function(form, action){
                        Ext.MessageBox.alert('Message', 'Save failed');
                    }
                });
            }

            }]

        }],


        //renderTo: bd
    });
    
});


function parnterName(val){
	return val.name;
}
