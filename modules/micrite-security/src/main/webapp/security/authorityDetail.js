<script type="text/javascript">
Ext.ns('micrite.security.authorityDetail');
FromPanel = function() {
    
    var RecordRole = Ext.data.Record.create([    
        {name: 'id'},{name: 'name'}
    ]); 
    var roleStore = new Ext.data.Store({
        autoLoad:true,
        //设定读取的地址
        proxy: new Ext.data.HttpProxy({url: '/' + document.location.href.split("/")[3] + '/findRolesAll.action'}),    
        //设定读取的格式    
        reader: new Ext.data.JsonReader({
            id:"0"
        }, RecordRole),
        remoteSort: true   
    });
                                    

	// turn on validation errors beside the field globally
    Ext.form.Field.prototype.msgTarget = 'side';
    
    FromPanel.superclass.constructor.call(this, {
        id: 'authorityDetail-form',
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
            title:this.authorityDetailText,
            layout:'form',
            width: 300,
            defaults: {width: 200},    // Default config options for child items
            defaultType: 'textfield',
            autoHeight: true,
            style: {
                "margin-left": "10px" // when you add custom margin in IE 6...
            },
            items: [{
                id:'authority_cid',
                fieldLabel: this.idText,
                disabled:true,
                name: 'id'
            },{
                id:'authority_cname',
                fieldLabel: this.nameText,
                name: 'name',
                allowBlank:false
            },{
                id:'authority_cvalue',
                fieldLabel: this.valueText,
                name: 'value',
                allowBlank:false
            }, new Ext.form.ComboBox({
                id:'authority_stype',
                name:'type',
                store: new Ext.data.SimpleStore({
                    fields: ['key', 'value'],
                    data : [['URL', 'URL'], ['METHOD', 'METHOD']]
                }),
                displayField:'value',
                fieldLabel: this.typeText,
                typeAhead: true,
                mode: 'local',
                triggerAction: 'all',
                emptyText:this.comboEmptyText,
                selectOnFocus:true,
                allowBlank:false,
                forceSelection:true
            }), new Ext.ux.form.CheckboxField({
                id:'authority_role',
                fieldLabel: this.roleText,
                hideOnSelect:false,
                emptyText:this.lovComboEmptyText,
                store:roleStore,
                triggerAction:'all',
                valueField:'id',
                displayField:'name',
                mode:'local',
                allowBlank:false
                })]
               
        }],
        buttons: [{
            text: this.submitText,
            handler: function(){
            Ext.getCmp("authorityDetail-form").getForm().submit({
                url: '/' + document.location.href.split("/")[3] + '/saveAuthority.action',
                method: 'POST',
                disabled:true,
                waitMsg: this.waitingMsg,
                params:{
                    'authority.id': Ext.getCmp('authority_cid').getValue(),
                    'authority.name': Ext.getCmp('authority_cname').getValue(),
                    'authority.value' : Ext.getCmp('authority_cvalue').getValue(),
                    'authority.type' : Ext.getCmp('authority_stype').getValue(),
                    'roleIdBunch' : Ext.getCmp('authority_role').getValue()
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
        renderTo: 'authorityDetail'
    });
    
}

micrite.security.authorityDetail.FormPanel=Ext.extend(FromPanel, Ext.FormPanel, {
    authorityDetailText:'Authority Detail',
    idText:'ID',
    nameText:'Name',
    valueText:'Value',
    typeText:'Type',
    roleText:'Role',
    submitText:'Save',
    cancelText:'Cancel',
    comboEmptyText:'Select a type...',
    lovComboEmptyText:'Select Roles...',
    waitingMsg:'Saving Data...'
    
});
try{ customerDetailLocale(); }
catch(e){}

Ext.onReady(function(){

    Ext.QuickTips.init();
    var formPanel = new micrite.security.authorityDetail.FormPanel();
    formPanel.render('authorityDetail');    
});
</script>