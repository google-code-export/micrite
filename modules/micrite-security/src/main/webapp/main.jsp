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
<script type="text/javascript" src="js-lib/ext-js/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="js-lib/ext-js/ext-all-debug.js"></script>
<script type="text/javascript" src="js-lib/ext-ux-js/DateTime.js"></script>
<script type="text/javascript" src="js-lib/ext-ux-js/DDView.js"></script>
<script type="text/javascript" src="js-lib/ext-ux-js/Multiselect.js"></script>
<script type="text/javascript" src="js-lib/ext-ux-js/CheckboxField.js"></script>
<link rel="stylesheet" type="text/css" href="js-lib/ext-ux-js/resources/css/DateTime.css">
<link rel="stylesheet" type="text/css" href="js-lib/ext-ux-js/resources/css/Multiselect.css">
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
    padding-bottom:1px;
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
                    tooltip: 'home',  // <-- i
                    iconCls :'home'
                },'->',{
                    text:this.userText,
                    tooltip: 'user infomation',
                    iconCls :'user'
                },'-',{
                    text:this.exitText,
                    tooltip: 'exit',
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

NavPanel = function() {

    // 显式调用父类构造器    
    NavPanel.superclass.constructor.call(this, {
        id:'module-tree',
        region:'west',
        title:this.navPanelText,
        split:true,
        width: 225,
        minSize: 175,
        maxSize: 400,
        collapsible: true,
        margins:'0 0 5 5',
        cmargins:'0 5 5 5',
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

    // 选择节点前先判断是否叶子节点
    this.getSelectionModel().on({
        'beforeselect' : function(sm, node){
             return node.isLeaf();
        },
        scope:this
    });

}
// 指明NavPanel的父类
micrite.security.framework.NavPanel=Ext.extend(NavPanel, Ext.tree.TreePanel, {

    navPanelText:'Navigator',
    expandText:'Expand All',
    collapseText:'Collapse All',    
    allModulesText:'All Modules'
    
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

}

micrite.security.framework.MainPanel=Ext.extend(MainPanel, Ext.TabPanel, {
    centerPanelText:'Center Panel',
    initEvents : function(){
        MainPanel.superclass.initEvents.call(this);
        this.body.on('click', this.onClick, this);
//        this.on("remove", function (e, tabItem){
//            tabItem.cancel = true;
//        });

    },
    // 点击tab上的链接，创建新的tab页并显示，class必须为inner-link，id为新tab的名字
    onClick: function(e, target){
        e.stopEvent();
        if(target.className == 'inner-link'){
            this.loadModule(target.href,target.id);
        }
    },


    loadModule : function(href,tabTitle){
        var tab;
        if(!(tab = this.getItem(tabTitle))){
            var autoLoad = {url: href,scripts:true};
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


Ext.onReady(function(){
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    var headerPanel = new micrite.security.framework.HeaderPanel();
    var navPanel = new micrite.security.framework.NavPanel();
    mainPanel = new micrite.security.framework.MainPanel();

    navPanel.on('click', function(node, e){
        if(node.isLeaf()){
           e.stopEvent();
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

</head>

<body>
<div id="header"></div>
</body>
</html>