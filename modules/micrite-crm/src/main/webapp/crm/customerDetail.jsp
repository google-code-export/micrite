<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
Ext.ns('micrite.crm.customerDetail');
micrite.crm.customerDetail.FormPanel = function() {
    var RecordDef = Ext.data.Record.create([    
            {name: 'id'},{name: 'name'}                   
    ]); 
    var store = new Ext.data.Store({    
        autoLoad:true,
        //设定读取的地址
        proxy: new Ext.data.HttpProxy({url: '/' + document.location.href.split("/")[3] + '/crm/GetCustomerPartner.action'}),    
        //设定读取的格式    
        reader: new Ext.data.JsonReader({    
            id:"id"
        }, RecordDef),    
        remoteSort: true   
    });
    
    // turn on validation errors beside the field globally
    Ext.form.Field.prototype.msgTarget = 'side';
    
    micrite.crm.customerDetail.FormPanel.superclass.constructor.call(this, {
        id: 'customerDetail-form',
        frame: false,
        labelAlign: 'left',
        header: false,
        border: false,
        bodyBorder: false,
        autoHeight:true,
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
            defaults: {width: 170},    // Default config options for child items
            defaultType: 'textfield',
            autoHeight: true,
            style: {
                "margin-left": "10px" // when you add custom margin in IE 6...
            },
            items: [{
                id:'cid',
                fieldLabel: this.idText,
                disabled:true,
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
            text: mbLocale.submitButton,
            handler: function(){
            Ext.getCmp("customerDetail-form").getForm().submit({
                url: '/' + document.location.href.split("/")[3] + '/crm/saveCustomer.action',
                method: 'POST',
                disabled:true,
                waitMsg: mbLocale.waitingMsg,
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
            text: mbLocale.cancelButton
        }],
        buttonAlign:'left'
    });
    
}

Ext.extend(micrite.crm.customerDetail.FormPanel, Ext.FormPanel, {
    customerDetailText:'Customer Detail',
    idText:'ID',
    nameText:'Name',
    mobileText:'Mobile',
    sourceText:'Source',
    comboEmptyText:'Select a source...'
    
});
try{ customerDetailLocale(); } catch(e){}
try {baseLocale();} catch (e) {}

Ext.onReady(function(){

    Ext.QuickTips.init();
    var formPanel = new micrite.crm.customerDetail.FormPanel();
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
