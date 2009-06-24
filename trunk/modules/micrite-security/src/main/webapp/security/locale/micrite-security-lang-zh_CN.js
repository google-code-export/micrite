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

function userSettingLocale(){
    if(micrite.security.userSetting.FormPanel) {
        Ext.override(micrite.security.userSetting.FormPanel, {
        	fullName : '真实姓名',
            email : '电子邮件',
            userName: '用户名',
            password: '密码',
            passwordRepeat: '再次输入密码',
        	userInformation:'用户个人信息',
        	settings: '个性化设置',
        	skin : '界面样式',
        	rowsPerPage : '每页行数',
        	confirmPassword: '两次输入的密码不匹配'
        });
    }    
}
function authorityListLocale() {
    if(micrite.security.authorityList.SearchPanel) {
        Ext.override(micrite.security.authorityList.SearchPanel, {
            byName:'按名称',
            name:'名称',
            addAuthority:'增加授权',
            value:'值',
            roles:'所属角色',
            type:'类型',
            deleteButton:'删除',
            closeButton:'关闭'
        });
    }
}
function authorityDetailLocale(){
    if(micrite.security.authorityDetail.FormPanel) {
        Ext.override(micrite.security.authorityDetail.FormPanel, {
            authorityDetailText:'增加资源',
            idText:'ID',
            nameText:'名称',
            valueText:'值',
            typeText:'类型',
            roleText:'角色',
            comboEmptyText:'选择一个类型...',
            lovComboEmptyText:'选择角色...'
        });
    }    
}
