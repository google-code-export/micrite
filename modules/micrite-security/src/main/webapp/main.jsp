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

    // 增加根节点，实际是模块的一个分组节点
    this.favoritesRoot = this.root.appendChild(
        new Ext.tree.TreeNode({
        	id:'favoritesRoot',
            text:this.favoritesText,
            cls:'modules-node',
            expanded:true
        })
    );
    this.allModulesRoot = this.root.appendChild(
            new Ext.tree.TreeNode({
            	id:'allModulesRoot',
                text:this.allModulesText,
                cls:'modules-node',
                expanded:false
            })
        );
    
    // allModuleRoot 父节点id
    // 01            设定当前节点的id
    // false         不是叶子节点
    // false         不是favorite的节点
    this.initModule({
        url:'crm',
        text: 'CRM Modules'
    }, 'allModulesRoot','01',false,false);
    
    this.initModule({
        url:'crm/customerList.jsp',
        text: 'Customer List'
    }, '01','01.001',true,true);

    this.initModule({
        url:'crm/customerDetail.jsp',
        text: 'Customer Detail'
    }, '01','01.002',true,true);

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
	favoritesText:'My Favorites',
	allModulesText:'All Modules',
	
    // 创建favorites module，已经在initModule被创建，这里加fav区分为不同的id
    addFavorite : function(attrs,cnodeId){
        var exists = this.getNodeById(cnodeId+'.fav');
        if(exists){
            exists.select();
            return;
        }    	
        Ext.apply(attrs, {
            leaf:true,
            cls:'feed',
            id: cnodeId+'.fav'
        });
        var node = new Ext.tree.TreeNode(attrs);
        this.favoritesRoot.appendChild(node);
        return node;
    },

    // 载入全部module，如果added为true，需要增加到favorites
    initModule : function(attrs, parent,cnodeId,isLeaf,added){
        var exists = this.getNodeById(cnodeId);
        if(exists){
            return;
        }
        Ext.apply(attrs, {
        	leaf:isLeaf,
            cls:'feed',
            id: cnodeId
        });
        var parentNode = this.getNodeById(parent);
        var node = new Ext.tree.TreeNode(attrs);
        parentNode.appendChild(node);
        if(added){
        	this.addFavorite(attrs,cnodeId);
        }
        return node;
    }
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
//	    this.on("remove", function (e, tabItem){
//	    	tabItem.cancel = true;
//	    });

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
                border:false
            });
            this.add(tab);
        }
        this.setActiveTab(tab);
    }
});


Ext.onReady(function(){
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    var hearderPanel = new micrite.security.framework.HeaderPanel();
    var navPanel = new micrite.security.framework.NavPanel();
    var mainPanel = new micrite.security.framework.MainPanel();

    navPanel.on('click', function(node, e){
        if(node.isLeaf()){
           e.stopEvent();
           mainPanel.loadModule(node.attributes.url, node.attributes.text);
        }
   });
    
    var viewport = new Ext.Viewport({
        layout:'border',
        items:[
            hearderPanel,
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