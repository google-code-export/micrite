<script type="text/javascript">
/**
 * micrite.security.roleDetail 角色信息，增加新角色
 * 
 * @author zhaom.chen
 */
Ext.ns('micrite.security.roleDetail');

//  FormPanel构造函数
micrite.security.roleDetail.FormPanel = function() {
    //  这里定义Panel的外观，内部控件等
    micrite.security.roleDetail.FormPanel.superclass.constructor.call(this, {
        id: 'roleDetailForm',
        bodyBorder: false,
        frame: true,
        style:'padding:1px',
        items: [{
            xtype: 'fieldset',
            labelWidth: 150,
            title: this.roleDetailText,
            layout: 'form',
            collapsible: true,
            defaults: {width: 210},    
            defaultType: 'textfield',
            autoHeight: true,
            items: [{
                fieldLabel: this.rolenameText,
                name: 'role.name',
                allowBlank:false
            },{
                fieldLabel: this.descriptionText,
                name: 'role.description'
            }]
        },{
        	buttonAlign:'center',
            buttons: [{
                text: mbLocale.submitButton,
		        scope:this,
		        formBind:true,
                handler: function() {
    	            // form提交
    	            this.getForm().submit({
    	                url: '/' + document.location.href.split("/")[3] + '/addRole.action',
    	                method: 'POST',
    	                disabled:true,
    	                waitMsg: mbLocale.waitingMsg,
    	                success: function(form, action) {
    	                    obj = Ext.util.JSON.decode(action.response.responseText);
    	                    showMsg('success', obj.message);	                
    	                },
    	                failure: function(form, action) {
    	                    obj = Ext.util.JSON.decode(action.response.responseText);
    	                    showMsg('failure', obj.message);
    	                }
    	            });
    	        }                    
            },{
                text: mbLocale.closeButton,
                handler: function() {Ext.getCmp('addRoleWindow').close()}
            }]
        }]
    });
};// FormPanel构造函数结束

//  页面上的字符串在这里定义
micrite.security.roleDetail.FormPanel = Ext.extend(micrite.security.roleDetail.FormPanel, Ext.FormPanel, {
	roleDetailText: 'Role Detail',
	rolenameText: 'Role Name',
	descriptionText: 'Description'
});

try{roleDetailLocale();} catch(e){}
try{baseLocale();} catch (e) {}

Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    Ext.getCmp('addRoleWindow').add(new micrite.security.roleDetail.FormPanel());
    Ext.getCmp('addRoleWindow').doLayout();
});
</script>