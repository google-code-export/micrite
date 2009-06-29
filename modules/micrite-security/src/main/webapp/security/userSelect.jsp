<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
Ext.namespace('micrite.security.userSelect');

micrite.security.userSelect.SearchPanel = function() {
    //  查询条件分组名称数组
    this.conNames = [''];
    //  查询条件分组组件组数组
    this.conCmpGroups = [
        [this.fullName, {xtype:'textfield', name:'user.fullname', width:120},
         '',{xtype:'checkbox',boxLabel:this.onlyBinded, name:'binded'}
        ]
    ];
    //  超链菜单项数组
    this.actionButtonMenuItems =  [{
        text:this.addUser,
        iconCls :'add-icon',
        scope:this,
        handler:function() {
	    	var win;
	    	if(!(win = Ext.getCmp('addUserWindow'))){
		        win = new Ext.Window({
		        	id: 'addUserWindow',
		            title    : this.addUser,
		            closable : true,
		            autoLoad : {url: 'security/userDetail.js?'+(new Date).getTime(),scripts:true},
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
    this.searchRequestURL = ['/' + document.location.href.split("/")[3] + '/security/findBindedUsers.action?roleIds='+<%=request.getParameter("roleIds")%>];
    
    //  查询结果数据按此格式读取
    this.resultDataFields = [[
        {name: 'fullname'},
        {name: 'emailaddress'},
        {name: 'enabled'}
    ]];
    
    //  查询结果行选择模型
    this.resultRowSelectionModel = new Ext.grid.CheckboxSelectionModel();
    
    //  查询结果列
    this.resultColumns = [[
        {header: this.fullName, width: 100, sortable: true, dataIndex: 'fullname'},
        {header: this.emailAddress, width: 180, sortable: true, dataIndex: 'emailaddress'},
        {header: this.enabled, width: 180, sortable: true, dataIndex: 'enabled'},        
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
       
            var bindUsers = function(buttonId, text, opt) {
                if (buttonId == 'yes') {
                    Ext.Ajax.request({
                        url:'/' + document.location.href.split("/")[3] + '/bindUsers.action?roleIds='+<%=request.getParameter("roleIds")%>,
                        params:{'userIds':ids},
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
                fn: bindUsers,
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
   
        var unBindUsers = function(buttonId, text, opt) {
            if (buttonId == 'yes') {
                Ext.Ajax.request({
                    url:'/' + document.location.href.split("/")[3] + '/unBindUsers.action?roleIds='+<%=request.getParameter("roleIds")%>,
                    params:{'userIds':ids},
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
            fn: unBindUsers,
            icon: Ext.MessageBox.QUESTION
        });
        }
    }]];
    
    micrite.security.userSelect.SearchPanel.superclass.constructor.call(this);
};

Ext.extend(micrite.security.userSelect.SearchPanel, micrite.panel.ComplexSearchPanel, {
    onlyBinded:'Only Binded',
    addUser:'Add User',
    fullName:'Full Name',
    emailAddress:'Email Address',
    enabled:'Enabled'
});

//  处理多语言
try {baseLocale();} catch (e) {}
try {userSelectLocale();} catch (e) {}

Ext.onReady(function() {
    Ext.QuickTips.init();
//    var formPanel = new micrite.security.roleListSub.SearchPanel();
    
    Ext.getCmp('userSelectWindow').add(new micrite.security.userSelect.SearchPanel());
    Ext.getCmp('userSelectWindow').doLayout();
});
</script>