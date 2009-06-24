<script type="text/javascript">
Ext.ns('micrite.security.authorityDetail');
FromPanel = function() {
    
    var RecordRole = Ext.data.Record.create([    
        
    ]); 
    var roleStore = new Ext.data.Store({
        autoLoad:true,
        proxy: new Ext.data.HttpProxy({url: '/' + document.location.href.split("/")[3] + '/findRolesAll.action'}),    
        reader: new Ext.data.JsonReader({id: "id"}, [{name: 'id'},{name: 'name'}])
    });
                                    

	// turn on validation errors beside the field globally
    Ext.form.Field.prototype.msgTarget = 'side';
    
  //先用Ext.apply方法添加自定义的password验证函数（也可以取其他的名字）
    Ext.apply(Ext.form.VTypes,{
    	authorityFormat:function(v){//val指这里的文本框值，field指这个文本框组件，大家要明白这个意思
            var alpha = /^[a-zA-Z_]+$/;
            return alpha.test(v);
        }
    });

	FromPanel.superclass.constructor.call(this, {
        id: 'authorityDetailForm',
        bodyBorder: false,
        frame: true,
        style:'padding:1px',
        items: [{
            xtype: 'fieldset',
            labelWidth: 150,
            title: this.authorityDetailText,
            layout: 'form',
            collapsible: true,
            defaults: {width: 210},    
            defaultType: 'textfield',
            autoHeight: true,
            items: [{
                name:'authority.id',
                fieldLabel: this.idText,
                disabled:true
            },{
                name:'authority.name',
                fieldLabel: this.nameText,
                allowBlank:false
            },{
            	name:'authority.value',
                fieldLabel: this.valueText,
                //vtype:"authorityFormat",//自定义的验证类型
                //vtypeText:"请输入合法的格式！",
                //confirmTo:"authority.type",//要比较的另外一个的组件的id
                allowBlank:false
            },  new Ext.form.ComboBox({
                id:'authority.type',
            	name:'authority.type',
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
                //blankText:'此项为必选项',
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
                })
            ]
               
        },{
            buttonAlign:'center',
            buttons: [{
	            text: mbLocale.submitButton,
	            scope:this,
	            formBind:true,
	            handler: function(){
	                // 构建form的提交参数
	                var params = { 'roleIdBunch': this.getForm().findField('authority_role').getValue() };      
	                // form提交
	                this.getForm().submit({
	                    url: '/' + document.location.href.split("/")[3] + '/saveAuthority.action',
	                    method: 'POST',
	                    disabled:true,
	                    waitMsg: mbLocale.waitingMsg,
	                    params:params,
	                    success: function(form, action) {
	                        obj = Ext.util.JSON.decode(action.response.responseText);
	                        showMsg('success', obj.message);                    
	                    },
	                    failure: function(form, action) {
	                        try{
	                            obj = Ext.util.JSON.decode(action.response.responseText);
	                            showMsg('failure', obj.message);
	                        }catch(ex){return;}
	                    }
	                });
	            }                    
	        },{
	            text: mbLocale.closeButton
            }],
        }],
    });
}

micrite.security.authorityDetail.FormPanel=Ext.extend(FromPanel, Ext.FormPanel, {
    authorityDetailText:'Authority Detail',
    idText:'ID',
    nameText:'Name',
    valueText:'Value',
    typeText:'Type',
    roleText:'Role',
    comboEmptyText:'Select a type...',
    lovComboEmptyText:'Select Roles...'
    
});

try{ authorityDetailLocale(); } catch(e){}
try {baseLocale();} catch (e) {}

Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    
    var formPanel = new micrite.security.authorityDetail.FormPanel();
    
    if (mainPanel) {
        mainPanel.getActiveTab().add(formPanel);
        mainPanel.getActiveTab().doLayout();
    } else {
        new Ext.Viewport({layout:'fit',items:[formPanel]});
    }
});
</script>