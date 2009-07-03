<script type="text/javascript">
Ext.ns('micrite.crm.customerDetail');
micrite.crm.customerDetail.FormPanel = function() {
    var RecordDef = Ext.data.Record.create([    
            {name: 'id'},{name: 'name'}                   
    ]); 
    var store = new Ext.data.Store({    
        autoLoad:true,
        //设定读取的地址
        proxy: new Ext.data.HttpProxy({url: '/' + document.location.href.split("/")[3] + '/crm/getCustomerPartner.action'}),    
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
        frame: true,
        labelAlign: 'left',
    	style:'padding:1px',
        items: [
        {
            xtype: 'fieldset',
			labelWidth: 150,
            title:this.customerDetailText,
            layout:'form',
            collapsible: true,
            defaults: {width: 170},    // Default config options for child items
            defaultType: 'textfield',
            autoHeight: true,
            items: [{
                id:'cid',
                fieldLabel: this.idText,
                disabled:true,
                name: 'id'
            },{
                id:'cname',
                fieldLabel: this.nameText,
                name: 'name',
                allowBlank:false
            },{
                id:'ctelephone',
                fieldLabel: this.mobileText,
                name: 'telephone',
                allowBlank:false
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
    			lazyRender : true,
                store:store,
                typeAhead: true
            })]
               
        },{
        	buttonAlign:'center',
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
	            text: mbLocale.closeButton,
	        	handler: function() {Ext.getCmp('addCusetomerWindow').close()}
	        }]
        }]
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
     Ext.form.Field.prototype.msgTarget = 'side';
    Ext.getCmp('addCusetomerWindow').add(new micrite.crm.customerDetail.FormPanel());
    Ext.getCmp('addCusetomerWindow').doLayout();

});
</script>
