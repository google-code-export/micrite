<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div id="customerDetail">
<script type="text/javascript">
Ext.ns('micrite.crm.customerDetail');
FromPanel = function() {
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
	
	var myData = [
	    [1,'Joker','1390203023023','中国移动'],
	    [2,'terry','1340444023023','中国联通']
	];
	
    FromPanel.superclass.constructor.call(this, {
	    id: 'customerDetail-form',
	    frame: false,
	    labelAlign: 'left',
	    header: false,
	    border: false,
	    bodyBorder: false,
	    style: {
	        "margin-top": "10px" // when you add custom margin in IE 6...
	    },        
	
	    items: [{
	        border:false
	    },{
	        xtype: 'fieldset',
	        labelWidth: 40,
	        title:this.customerDetailText,
	        layout:'form',
	        width: 300,
	        defaults: {width: 170},	// Default config options for child items
	        defaultType: 'textfield',
	        autoHeight: true,
	        style: {
	            "margin-left": "10px" // when you add custom margin in IE 6...
	        },
	        items: [{
	        	id:'cid',
	            fieldLabel: this.idText,
	            readOnly:true,
	            name: 'id'
	        },{
	        	id:'cname',
	            fieldLabel: this.nameText,
	            name: 'name'
	        },{
	        	id:'ctelephone',
	            fieldLabel: this.mobileText,
	            name: 'telephone'
	        }, new Ext.form.ComboBox({
	        	id:'sourceSelect',
	        	name:'customerSource',
	            selectOnFocus:true,
	            valueField:'id',
	            hiddenName:'id',
	            displayField:'name',
	            fieldLabel: this.sourceText,
	            emptyText:this.comboEmptyText,
	            editable:false,
	            width: 170,
	            forceSelection:true,
	            triggerAction:'all',
	            lazyInit:false,
	
	            store:store,
	            typeAhead: true
	
	            
	        })]
	           
	    }],
	    buttons: [{
	        text: this.submitText,
	        handler: function(){
	    	Ext.getCmp("customerDetail-form").getForm().submit({
	            url: '/' + document.location.href.split("/")[3] + '/crm/saveData.action',
	            method: 'POST',
	            disabled:true,
	            waitMsg: this.waitingMsg,
	            params:{
					customerSourceId: Ext.getCmp('sourceSelect').getValue(),
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
	        text: this.cancelText
	    }],
	    buttonAlign:'left',
	    renderTo: 'customerDetail'
	});
	
}

micrite.crm.customerDetail.FormPanel=Ext.extend(FromPanel, Ext.FormPanel, {
	customerDetailText:'Customer Detail',
	idText:'ID',
	nameText:'Name',
	mobileText:'Mobile',
	sourceText:'Source',
	submitText:'Save',
	cancelText:'Cancel',
	comboEmptyText:'Select a source...',
	waitingMsg:'Saving Data...'
	
});
try{ customerDetailLocale(); }
catch(e){}

Ext.onReady(function(){

    Ext.QuickTips.init();
    var formPanel = new micrite.crm.customerDetail.FormPanel();
    formPanel.render('customerDetail');    
});
</script>
</div>