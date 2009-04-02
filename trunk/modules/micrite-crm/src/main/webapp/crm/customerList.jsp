<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div id="customerList">
<script type="text/javascript">
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
    var gridForm = new Ext.FormPanel({
        id: 'viewCustomerForm',
        frame: false,
        labelAlign: 'left',
        header: false,
        border: false,
        bodyBorder: false,
        layout: 'fit',	// Specifies that the items will now be arranged in columns

        items: [{
            border:false,
            layout: 'fit',
            height: 350,            
            items: {
	            xtype: 'grid',
	            border: false,
		        tbar: [{
	               text:'Search By Telephone',
	               scope: this
			    },
	               new Ext.app.SearchField({
	                   store: ds,
	                   width:220
	               }),'<a href="../crm/customerDetail.jsp" id="Customer Detail" class="inner-link">ss</a>'
			    ],	            
	            ds: ds,
	            cm: colModel,
	            sm: new Ext.grid.RowSelectionModel({
	                singleSelect: true,
	                listeners: {
	                    rowselect: function(sm, row, rec) {
	                        Ext.getCmp("viewCustomerForm").getForm().loadRecord(rec);
	            	       	alert(rec.json.customerSource.id);
	                    }
	                }
	            })
        	}
        }],


        renderTo: 'customerList'
    });
    
});


function parnterName(val){
	return val.name;
}
</script>
</div>