if(micrite.security.framework.LoginWindow) {
    Ext.override(micrite.security.framework.LoginWindow, {
        titleText:'Micrite Login',
        submitText:'Submit',
        cancelText:'Cancel',    
        usernameText:'Username:',
        passwordText:'Password:'
    });
}

if(micrite.security.framework.HeaderPanel) {
    Ext.override(micrite.security.framework.HeaderPanel, {
        homeText:'Home',
        userText:'User',
        exitText:'Exit'
    });
}


if(micrite.security.framework.MenuTreePanel) {
    Ext.override(micrite.security.framework.MenuTreePanel, {
        navPanelText:'Navigator',      
        expandText:'Expand All',
        collapseText:'Collapse All',    
        allModulesText:'All Modules'
    });
}

if(micrite.security.framework.msgPanel) {
    Ext.override(micrite.security.framework.msgPanel, {
        messageText:'Message'
    });
}

if(micrite.security.framework.MainPanel) {
    Ext.override(micrite.security.framework.MainPanel, {
        centerPanelText:'Center Panel'
    });
}

function userListLocale() {
    if(micrite.security.userList.SearchPanel) {
        Ext.override(micrite.security.userList.SearchPanel, {
            byUsername:'By User Name',
            username:'User Name',
            addUser:'Add User',
            fullname:'Full Name',
            emailaddress:'Email Address',
            enabled:'Enabled'
        });
    }
}

function userSettingLocale(){
    if(micrite.security.userSetting.FormPanel) {
        Ext.override(micrite.security.userSetting.FormPanel, {
        	fullName : 'Full Name',
            email : 'E-mail',
            userName: 'User Name',
            password: 'Password',
            passwordRepeat: 'Re-enter password',
        	userInformation:'User Information',
        	settings: 'Personal Settings',
        	skin : 'Skin',
        	rowsPerPage : 'Rows Per Page',
        	confirmPassword: 'Passwords do not match'
        });
    }    
}
function authorityListLocale() {
    if(micrite.security.authorityList.SearchPanel) {
        Ext.override(micrite.security.authorityList.SearchPanel, {
            byName:'By Name',
            name:'Name',
            addAuthority:'Add Authority',
            value:'Value',
            roles:'RolesString',
            type:'Type',
            deleteButton:'Delete',
            closeButton:'Close'
        });
    }
}
function authorityDetailLocale(){
    if(micrite.security.authorityDetail.FormPanel) {
        Ext.override(micrite.security.authorityDetail.FormPanel, {
            authorityDetailText:'Authority Detail',
            idText:'ID',
            nameText:'Name',
            valueText:'Value',
            typeText:'Type',
            roleText:'Role',
            comboEmptyText:'Select a type...',
            lovComboEmptyText:'Select Roles...'
        });
    }    
}
function roleListLocale(){
    if(micrite.security.roleList.SearchPanel) {
        Ext.override(micrite.security.roleList.SearchPanel, {
            bindUser:'Bind User',
            bindAuthority:'Bind Authority',
            addRole:'Add Role',
            name:'Name',
            description:'Description'
        });
    }    
}
function userSelectLocale(){
    if(micrite.security.userSelect.SearchPanel) {
        Ext.override(micrite.security.userSelect.SearchPanel, {
            onlyBinded:'Only Binded',
            addUser:'Add User',
            fullName:'Full Name',
            emailAddress:'Email Address',
            enabled:'Enabled'
        });
    }    
}
function authoritySelectLocale(){
    if(micrite.security.authoritySelect.SearchPanel) {
        Ext.override(micrite.security.authoritySelect.SearchPanel, {
            onlyBinded:'Only Binded',
            addAuth:'Add Authority',
            name:'Name',
            type:'Type',
            value:'Value'
        });
    }    
}