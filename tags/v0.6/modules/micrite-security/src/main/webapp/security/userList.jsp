<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
    paramName : 'user.loginname',

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

Ext.BLANK_IMAGE_URL = 'ext/resources/images/default/s.gif';

Ext.onReady(function() {
    Ext.QuickTips.init();

    var dataStore = new Ext.data.Store({
        //设定读取的地址
        proxy: new Ext.data.HttpProxy({url: '/' + document.location.href.split("/")[3] + '/userFindUsers.action'}),    
        //设定读取的格式    
        reader: new Ext.data.JsonReader({
            id:"id"
        }, [
            {name: 'id', type: 'int'},
            {name: 'fullname'},
            {name: 'emailaddress'},
            {name: 'loginname'},
            {name: 'isenabled', type: 'boolean'}
        ]),
        remoteSort: true
    });

    var colModel = new Ext.grid.ColumnModel([
        {dataIndex: 'id', hidden: true},
        {header: "Full Name", width: 100, sortable: true, dataIndex: 'fullname'},
        {id: "emailaddress", header: "Email Address", width: 180, sortable: true, dataIndex: 'emailaddress'},
        {header: "User Name", width: 90, sortable: true, dataIndex: 'loginname'},
        {header: "Isenabled", width: 70, sortable: true, dataIndex: 'isenabled'}
    ]);

    var formPanel = new Ext.Panel({
        frame: true,
        labelAlign: 'left',
        title: 'User Data',
        bodyStyle:'padding:5px',
        width: 750,
        layout: 'column',
        items: [{
            columnWidth: 0.6,
            layout: 'fit',
            items: {
                xtype: 'grid',
                tbar: [{text:'User Name'}, 
                        new Ext.app.SearchField({
                            store: dataStore,
                            width: 150
                        })
                      ],
                ds: dataStore,
                cm: colModel,
                sm: new Ext.grid.RowSelectionModel({
                    singleSelect: true,
                    listeners: {
                        rowselect: function(sm, row, rec) {
                            Ext.getCmp("user-form").getForm().loadRecord(rec);
                        }
                    }
                }),
                autoExpandColumn: 'emailaddress',
                height: 350,
                border: true
            }
        },{
            columnWidth: 0.4,
            xtype: 'form',
            id: 'user-form',
            items:[{
                xtype: 'fieldset',
                labelWidth: 110,
                title:'Modify User Name or Password',
                defaults: {width: 110},
                defaultType: 'textfield',
                autoHeight: true,
                bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
                border: false,
                style: {
                    "margin-left": "10px",
                    "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"
                },
                items: [{
                    xtype: 'hidden',
                    name: 'id'
                },{
                    fieldLabel: 'Full Name',
                    name: 'fullname',
                    disabled: true
                },{
                    fieldLabel: 'User Name',
                    name: 'loginname'
                },{
                    fieldLabel: 'Password',
                    name: 'password'
                },{
                    fieldLabel: 'Password Again',
                    name: 'PasswordAgain'
                }]
            }],
            buttons: [{
                text: 'Submit'
            },{
                text: 'Cancel'
            }]
        }],
    });

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
    
})
</script>
