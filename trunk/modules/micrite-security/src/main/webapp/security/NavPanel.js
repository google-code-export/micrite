NavPanel = function() {
    // 显式调用父类构造器    
    NavPanel.superclass.constructor.call(this, {
        id:'module-tree',
        region:'west',
        title:'Modules',
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
            iconCls:'add-feed',
            text:'Add Module',
            handler: this.showWindow,
            scope: this
        },{
            id:'delete',
            iconCls:'delete-icon',
            text:'Remove',
            handler: function(){
                var s = this.getSelectionModel().getSelectedNode();
                if(s){
                    this.removeFeed(s.attributes.url);
                }
            },
            scope: this
        }]
    });

    // 增加根节点，实际是模块的一个分组节点
    this.feeds = this.root.appendChild(
        new Ext.tree.TreeNode({
            text:'My Favorites',
            cls:'feeds-node',
            expanded:true
        })
    );
    // add some default feeds
    this.addFeed({
        url:'../crm/customerList.jsp',
        text: 'Customer List'
    }, true);

    this.addFeed({
        url:'../crm/customerDetail.jsp',
        text: 'Customer Detail'
    }, true);

    // 选择节点前先判断是否叶子节点
    this.getSelectionModel().on({
        'beforeselect' : function(sm, node){
             return node.isLeaf();
        },
        scope:this
    });

    //this.addEvents({moduleselect:true});

    this.on('contextmenu', this.onContextMenu, this);
}
// 指明NavPanel的父类
Ext.extend(NavPanel, Ext.tree.TreePanel, {

    onContextMenu : function(node, e){
        if(!this.menu){ // create context menu on first right click
            this.menu = new Ext.menu.Menu({
                id:'feeds-ctx',
                items: [{
                    id:'load',
                    iconCls:'load-icon',
                    text:'Load Module',
                    scope: this,
                    handler:function(){
                        this.ctxNode.select();
                    }
                },{
                    text:'Remove',
                    iconCls:'delete-icon',
                    scope: this,
                    handler:function(){
                        this.ctxNode.ui.removeClass('x-node-ctx');
                        this.removeFeed(this.ctxNode.attributes.url);
                        this.ctxNode = null;
                    }
                },'-',{
                    iconCls:'add-feed',
                    text:'Add Module',
                    handler: this.showWindow,
                    scope: this
                }]
            });
            this.menu.on('hide', this.onContextHide, this);
        }
        if(this.ctxNode){
            this.ctxNode.ui.removeClass('x-node-ctx');
            this.ctxNode = null;
        }
        if(node.isLeaf()){
            this.ctxNode = node;
            this.ctxNode.ui.addClass('x-node-ctx');
            this.menu.items.get('load').setDisabled(node.isSelected());
            this.menu.showAt(e.getXY());
        }
    },

    onContextHide : function(){
        if(this.ctxNode){
            this.ctxNode.ui.removeClass('x-node-ctx');
            this.ctxNode = null;
        }
    },

    showWindow : function(btn){
        if(!this.win){
            this.win = new FeedWindow();
            this.win.on('validfeed', this.addFeed, this);
        }
        this.win.show(btn);
    },

    selectFeed: function(url){
    	alert(url);
        this.getNodeById(url).select();
    },

    removeFeed: function(url){
        var node = this.getNodeById(url);
        if(node){
            node.unselect();
            Ext.fly(node.ui.elNode).ghost('l', {
                callback: node.remove, scope: node, duration: .4
            });
        }
    },

    addFeed : function(attrs, inactive, preventAnim){
        var exists = this.getNodeById(attrs.url);
        if(exists){
            if(!inactive){
                exists.select();
                exists.ui.highlight();
            }
            return;
        }
        Ext.apply(attrs, {
            iconCls: 'feed-icon',
            leaf:true,
            cls:'feed',
            id: attrs.url
        });
        var node = new Ext.tree.TreeNode(attrs);
        this.feeds.appendChild(node);
        if(!inactive){
            if(!preventAnim){
                Ext.fly(node.ui.elNode).slideIn('l', {
                    callback: node.select, scope: node, duration: .4
                });
            }else{
                node.select();
            }
        }
        return node;
    },

    // prevent the default context menu when you miss the node
    afterRender : function(){
        NavPanel.superclass.afterRender.call(this);
        this.el.on('contextmenu', function(e){
            e.preventDefault();
        });
    }
});
