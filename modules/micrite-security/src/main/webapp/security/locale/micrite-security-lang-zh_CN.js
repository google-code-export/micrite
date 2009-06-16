if(micrite.security.framework.LoginWindow) {
   Ext.override(micrite.security.framework.LoginWindow, {
   	titleText:'Micrite登录',
       submitText:'登录',
       cancelText:'取消',    
       usernameText:'用户名：',
       passwordText:'密码：'
   });
}

if(micrite.security.framework.HeaderPanel) {
   Ext.override(micrite.security.framework.HeaderPanel, {
   	homeText:'首页',
   	userText:'用户',
   	exitText:'退出'
   });
}

if(micrite.security.framework.MenuTreePanel) {
   Ext.override(micrite.security.framework.MenuTreePanel, {
   	navPanelText:'导航',  
   	expandText:'展开',
   	collapseText:'收缩',	
   	allModulesText:'所有模块'
   });
}

if(micrite.security.framework.msgPanel) {
   Ext.override(micrite.security.framework.msgPanel, {
   	messageText:'提示信息'
   });
}

if(micrite.security.framework.MainPanel) {
   Ext.override(micrite.security.framework.MainPanel, {
   	centerPanelText:'欢迎'
   });
}

function userListLocale() {
    if(micrite.security.userList.SearchPanel) {
        Ext.override(micrite.security.userList.SearchPanel, {
            byUsername:'按用户名',
            username:'用户名',
            addUser:'增加用户',
            fullname:'名称',
            emailaddress:'电子邮件',
            enabled:'是否可用'
        });
    }
}