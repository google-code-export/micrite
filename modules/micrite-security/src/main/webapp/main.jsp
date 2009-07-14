<%--
/* ===========================================================
 * $Id$
 * This file is part of Micrite
 * ===========================================================
 *
 * (C) Copyright 2009, by Gaixie.org and Contributors.
 * 
 * Project Info:  http://micrite.gaixie.org/
 *
 * Micrite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Micrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Micrite.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Welcome to Micrite!</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="js-lib/ext-js/resources/css/ext-all.css">
<script type="text/javascript" src="js-lib/ext-js/adapter/ext/ext-base-debug.js"></script>
<script type="text/javascript" src="js-lib/ext-js/ext-all-debug.js"></script>
<script type="text/javascript" src="js-lib/ext-ux-js/CheckboxField.js"></script>
<script type="text/javascript" src="js-lib/ext-ux-js/Spinner.js"></script>
<script type="text/javascript" src="js-lib/ext-ux-js/SpinnerStrategy.js"></script>
<script type="text/javascript" src="js-lib/ext-ux-js/locale/micrite-base-lang-<%=session.getAttribute("WW_TRANS_I18N_LOCALE")%>.js"></script>
<script type="text/javascript" src="js-lib/ext-ux-js/util.js"></script>
<link rel="stylesheet" type="text/css" href="js-lib/ext-ux-js/resources/css/micrite-all.css">
<link rel="stylesheet" type="text/css" href="js-lib/ext-ux-js/resources/css/CheckboxField.css">
<style type="text/css">
.main-header .x-panel-body {
    background:transparent;
}
.icon-expand-all {
    background-image: url(security/images/framework/expand-all.gif) !important;
}
.icon-collapse-all {
    background-image: url(security/images/framework/collapse-all.gif) !important;
}
.x-tree-node div.modules-node{
    background:#eee url(extjs/examples/feed-viewer/images/cmp-bg.gif) repeat-x;
    margin-top:1px;
    border-top:1px solid #ddd;
    border-bottom:1px solid #ccc;
    padding-top:2px;
    padding-bottom:3px;
}
.home {
    background-image: url(security/images/framework/home.gif) !important;
}
.user {
    background-image: url(security/images/framework/user.gif) !important;
}
.exit {
    background-image: url(security/images/framework/exit.gif) !important;
}
</style>
<script type="text/javascript">
Ext.BLANK_IMAGE_URL = "js-lib/ext-js/resources/images/default/s.gif";

Ext.ns('micrite.security.framework');
micrite.security.framework.HeaderPanel = Ext.extend(Ext.Panel, {

    homeText:'Home',
    userText:'User',
    exitText:'Exit',
    
    initComponent:function() {
        Ext.apply(this, {
            border :false,
            layout :'anchor',
            region :'north',
            cls: 'main-header',
            height :60,
            items:[{
                xtype :'box',
                border :false,
                el: 'header',
                height :30
            },
            new Ext.Toolbar( {
                border:true,
                items : [{
                    text:this.homeText,
                    tooltip: 'Home',  // <-- i
                    iconCls :'home'
                },'->',{
                    text:this.userText,
                    tooltip: 'User Setting',
                    iconCls :'user',
                    handler : function() {
                        mainPanel.loadModule('security/userSetting.js', 'User Setting');
                    }
                },'-',{
                    text:this.exitText,
                    tooltip: 'Exit',
                    iconCls :'exit',
                    handler : function() {
                        window.location = 'j_spring_security_logout';
                    }
                }]
            })
            ]
        }); // eo apply
        micrite.security.framework.HeaderPanel.superclass.initComponent.apply(this, arguments);
    } // eo function initComponent
}); // eo Tutorial.LocalizationWin

msgPanel = function(){
       // 显式调用父类构造器    
 msgPanel.superclass.constructor.call(this, {
     id:'msg-panel',
   //  region:'center',
     layout:'fit',
     border:false,
     title:this.messageText,
     split:true,
     bodyStyle:'padding:4px',
     collapsible: false,
     renderHidden:true,
     autoScroll:true
 });
};
micrite.security.framework.msgPanel = Ext.extend(msgPanel, Ext.Panel, {
    messageText:'Message'
});

MenuTreePanel = function() {

    // 显式调用父类构造器    
    MenuTreePanel.superclass.constructor.call(this, {
        id:'module-tree',
       // region:'north',
        layout:'fit',
        border:false,
        floatable: false,
        title:this.navPanelText,
        split:true,
        minSize: 175,
        maxSize: 400,
        collapsible: true,
        minHeight:240,
        rootVisible:false,
        lines:false,
        autoScroll:true,
        loader: new Ext.tree.TreeLoader({
            url:'loadMenu.action'
        }),
        root: new Ext.tree.TreeNode('Module Viewer'),
        collapseFirst:false,

        tbar: [{
            iconCls:'icon-expand-all',
            text:this.expandText,
            handler: function(){this.root.expand(true); },
            scope: this
       },{
            iconCls:'icon-collapse-all',
            text:this.collapseText,
            handler: function(){this.root.collapse(true); },
            scope: this
        }]
    });

    this.root.appendChild(
           new Ext.tree.AsyncTreeNode({
               id:'allModulesRoot',
            text:this.allModulesText,
            cls:'modules-node',
            leaf:false,
            expanded:true
        })
    );


    // session 过期的处理
    this.getLoader().on({
        'load' : function(sm, node,r){
	        var res = Ext.decode(r.responseText);
	        if (res.message){
                showMsg('failure',res.message);
	        }
        },        
        scope:this
    });    

};
//指明NavPanel的父类
micrite.security.framework.MenuTreePanel=Ext.extend(MenuTreePanel, Ext.tree.TreePanel, {
    navPanelText:'Navigator',
    expandText:'Expand All',
    collapseText:'Collapse All',    
    allModulesText:'All Modules'
    
});


NavPanel = function() {

    // 显式调用父类构造器    
   NavPanel.superclass.constructor.call(this, {
        id:'nav-panel',
        region:'west',
        layout:'anchor',
        margins:'0 0 5 5',
        cmargins:'0 5 5 5',
        split:true,
        width: 225,
        minSize: 175,
        maxSize: 400,
        minHeight:600,
        collapsible: true,
        collapseMode:'mini',
        items:[new MenuTreePanel(),new msgPanel()]
    });
};
micrite.security.framework.NavPanel = Ext.extend(NavPanel, Ext.Panel, {
    
});




MainPanel = function() {
    // 显式调用父类构造器    
    MainPanel.superclass.constructor.call(this, {
        id:'main-tabs',
        region:'center',
        margins:'0 0 5 0',
        activeTab:0,
        items:[{
            title: this.centerPanelText,
            autoScroll:true
        }]

    });

};

micrite.security.framework.MainPanel=Ext.extend(MainPanel, Ext.TabPanel, {
    centerPanelText:'Center Panel',
    loadModule : function(href,tabTitle){
        var tab;
        if(!(tab = this.getItem(tabTitle))){
            var autoLoad = micrite.util.autoLoad({url: href,scripts:true});
            tab = new Ext.Panel({
                id: tabTitle,
                title: tabTitle,
                closable:true,
                autoLoad: autoLoad,
                layout:"fit",
                border:false
            });
            this.add(tab);
        }
        this.setActiveTab(tab);
    }
});

function showMsg(msgType,msg) {
    var detailEl = Ext.DomHelper.insertFirst(Ext.getCmp('msg-panel').body, {id:'msg-div',cls : msgType=='failure'?'errorMsg':'infoMsg'}, true);
    var failureMsg = msg;
    if (msgType=='failure'){
        // 如果session-expired，显式确认框，如果Yes，清空session并返回到登录页面
        if (msg.indexOf('session expired')!=-1){
            Ext.Msg.show({
                title:mbLocale.infoMsg,
                msg: mbLocale.sessionExpiredMsg,
                buttons: Ext.Msg.YESNO,
                scope: this,
                fn: function(buttonId, text, opt) {
            		if (buttonId == 'yes') {
                    window.location = 'j_spring_security_logout';
            		}
                },
                icon: Ext.MessageBox.QUESTION
            }); 
            failureMsg = mbLocale.sessionExpiredMsg;
        }else{
      		Ext.Msg.alert('failure',msg);
        }
    }
    var dt = new Date();
    dt = '<em>&nbsp;' + dt.format('g:i a') + '</em>';  
    detailEl.hide().update(failureMsg+dt).slideIn('t');
}

Ext.onReady(function(){
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    var headerPanel = new micrite.security.framework.HeaderPanel();
    var navPanel = new micrite.security.framework.NavPanel();
    mainPanel = new micrite.security.framework.MainPanel();

    Ext.getCmp('module-tree').on('click', function(node, e){
        if(node.isLeaf()){
           mainPanel.loadModule(node.attributes.url, node.attributes.text);
        }
   });
    
    var viewport = new Ext.Viewport({
        layout:'border',
        items:[
            headerPanel,
            navPanel,
            mainPanel
        ]
    });

});
</script>
<script type="text/javascript" src="security/locale/micrite-security-lang-<%=session.getAttribute("WW_TRANS_I18N_LOCALE")%>.js"></script>
<script type="text/javascript" src="crm/locale/micrite-crm-lang-<%=session.getAttribute("WW_TRANS_I18N_LOCALE")%>.js"></script>
<s:if test="%{skin=='Gray'}" >
<link rel="stylesheet" type="text/css" href="js-lib/ext-ux-js/resources/css/xtheme-gray-extend.css">
</s:if>
</head>

<body>
<s:hidden id="pageSize" name="pageSize"></s:hidden>
<script type="text/javascript" src="js-lib/ext-ux-js/ComplexGrid.js"></script>
<div id="header"></div>
</body>
</html>