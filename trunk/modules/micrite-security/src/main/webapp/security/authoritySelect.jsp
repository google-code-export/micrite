<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
Ext.namespace('micrite.security.authoritySelect');

micrite.security.authoritySelect.SearchPanel = function() {
    //  查询条件分组名称数组
    this.conNames = [''];
    //  查询条件分组组件组数组
    this.conCmpGroups = [
        [this.name, {xtype:'textfield', name:'authority.name', width:120},
         '',{xtype:'checkbox',boxLabel:this.onlyBinded, name:'binded'}
        ]
    ];
    //  超链菜单项数组
    this.actionButtonMenuItems =  [{
        text:this.addAuth,
        iconCls :'add-icon',
        scope:this,
        handler:function() {
	    	var win;
	    	if(!(win = Ext.getCmp('addAuthorityWindow'))){
		        win = new Ext.Window({
		        	id: 'addAuthorityWindow',
		            title    : this.addAuth,
		            closable : true,
		            autoLoad : {url: 'security/authorityDetail.js?'+(new Date).getTime(),scripts:true},
		            width    : 500,
		            height   : 360,
		            maximizable : true,
		            layout:'fit'
		        });
	    	}
	        win.show();
	        win.center();
        }
    }];    
    //  查询请求的url
    this.searchRequestURL = ['/' + document.location.href.split("/")[3] + '/security/findBindedAuths.action?roleId='+<%=request.getParameter("roleId")%>];
    
    //  查询结果数据按此格式读取
    this.resultDataFields = [[
        {name: 'name'},
        {name: 'type'},        
        {name: 'value'}
    ]];
    
    //  查询结果行选择模型
    this.resultRowSelectionModel = new Ext.grid.CheckboxSelectionModel();
    
    //  查询结果列
    this.resultColumns = [[
        {header: this.name, width: 100, sortable: true, dataIndex: 'name'},
        {header: this.type, width: 100, sortable: true, dataIndex: 'type'},        
        {header: this.value, width: 180, sortable: true, dataIndex: 'value'},
        this.resultRowSelectionModel
    ]];

    this.comboGrid = [{url:0,reader:0,column:0,button:0}];
    //  动作按钮数组
    this.resultProcessButtons = [[{
        text:mbLocale.bindButton, 
        iconCls :'bind-icon',
        scope:this, 
        handler:function() {
            //  选择的数据记录主键，形如“2, 4, 6, 10”
            var ids = this.resultGrid.selModel.selections.keys;
            if(ids.length<1){
                Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowSelectMsg);
                return;
            }
       
            var bindAuths = function(buttonId, text, opt) {
                if (buttonId == 'yes') {
                    Ext.Ajax.request({
                        url:'/' + document.location.href.split("/")[3] + '/bindAuths.action?roleId='+<%=request.getParameter("roleId")%>,
                        params:{'authIds':ids},
                        scope:this,
                        callback:function(options,success,response) {
                            if (Ext.util.JSON.decode(response.responseText).success) {
                                obj = Ext.util.JSON.decode(response.responseText);
                                showMsg('success', obj.message);
                            }else{
                                obj = Ext.util.JSON.decode(response.responseText);
                                showMsg('failure', obj.message);                                
                            }
                        }
                    });
                }
            }
            Ext.Msg.show({
                title:mbLocale.infoMsg,
                msg: mbLocale.bindConfirmMsg,
                buttons: Ext.Msg.YESNO,
                scope: this,
                fn: bindAuths,
                icon: Ext.MessageBox.QUESTION
            });
        }
    },{
        text:mbLocale.unbindButton, 
        iconCls :'unbind-icon',
        scope:this,
        handler:function() {
	        //  选择的数据记录主键，形如“2, 4, 6, 10”
	        var ids = this.resultGrid.selModel.selections.keys;
	        if(ids.length<1){
	            Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowSelectMsg);
	            return;
	        }
	   
	        var unBindAuths = function(buttonId, text, opt) {
	            if (buttonId == 'yes') {
	                Ext.Ajax.request({
	                    url:'/' + document.location.href.split("/")[3] + '/unBindAuths.action?roleId='+<%=request.getParameter("roleId")%>,
	                    params:{'authIds':ids},
	                    scope:this,
	                    callback:function(options,success,response) {
	                        if (Ext.util.JSON.decode(response.responseText).success) {
	                            obj = Ext.util.JSON.decode(response.responseText);
	                            showMsg('success', obj.message);
	                        }else{
	                            obj = Ext.util.JSON.decode(response.responseText);
	                            showMsg('failure', obj.message);                                
	                        }
	                    }
	                });
	            }
	        }
	        Ext.Msg.show({
                title:mbLocale.infoMsg,
                msg: mbLocale.unbindConfirmMsg,                
	            buttons: Ext.Msg.YESNO,
	            scope: this,
	            fn: unBindAuths,
	            icon: Ext.MessageBox.QUESTION
	        });
        }
    }]];
    
    micrite.security.authoritySelect.SearchPanel.superclass.constructor.call(this);
};

Ext.extend(micrite.security.authoritySelect.SearchPanel, micrite.panel.ComplexSearchPanel, {
    onlyBinded:'Only Binded',
    addAuth:'Add Authority',
    name:'Name',
    type:'Type',
    value:'Value'
});

//  处理多语言
try {baseLocale();} catch (e) {}
try {authoritySelectLocale();} catch (e) {}

Ext.onReady(function() {
    Ext.QuickTips.init();
//    var formPanel = new micrite.security.roleListSub.SearchPanel();
    
    Ext.getCmp('authoritySelectWindow').add(new micrite.security.authoritySelect.SearchPanel());
    Ext.getCmp('authoritySelectWindow').doLayout();
});
</script>