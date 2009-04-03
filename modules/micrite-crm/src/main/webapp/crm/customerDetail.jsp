<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div id="customerDetail">
<script type="text/javascript">
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

//   Define the Grid data and create the Grid
    var myData = [
        [1,'Joker','1390203023023','中国移动'],
        [2,'terry','1340444023023','中国联通']
    ];
//    ds.loadData(myData);



/*
 *	Here is where we create the Form
 */
    var gridForm = new Ext.FormPanel({
        id: 'company-form',
        frame: false,
        labelAlign: 'left',
        header: false,
        border: false,
        bodyBorder: false,
        style: {
            "margin-top": "10px" // when you add custom margin in IE 6...
        },        
//        layout: 'fit',	// Specifies that the items will now be arranged in columns

        items: [{
            border:false,
        },{
            xtype: 'fieldset',
            labelWidth: 40,
            title:'Customer Detail',
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
                width: 170,
                forceSelection:true,
                triggerAction:'all',
                lazyInit:false,

                store:store,
                typeAhead: true

                
            })]
               
        }],
        buttons: [{
            text: 'Save',
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
            });},	                
        },{
            text: 'Cancel'
        }],
        buttonAlign:'left',
        renderTo: 'customerDetail'
    });
    
});
</script>
</div>