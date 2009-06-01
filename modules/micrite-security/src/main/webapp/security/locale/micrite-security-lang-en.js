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