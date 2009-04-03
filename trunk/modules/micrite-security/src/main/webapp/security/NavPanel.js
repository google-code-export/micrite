NavPanel = function() {
    // 显式调用父类构造器    
    NavPanel.superclass.constructor.call(this, {
        id:'module-tree',
        region:'west',
        title:'Navigator',
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
            text:'Expand All',
            handler: function(){this.root.expand(true); },
            scope: this
       },{
            iconCls:'icon-collapse-all',
            text:'Collapse All',
            handler: function(){this.root.collapse(true); },
            scope: this
        }]
    });

    // 增加根节点，实际是模块的一个分组节点
    this.favoritesRoot = this.root.appendChild(
        new Ext.tree.TreeNode({
        	id:'favoritesRoot',
            text:'My Favorites',
            cls:'modules-node',
            expanded:true
        })
    );
    this.allModulesRoot = this.root.appendChild(
            new Ext.tree.TreeNode({
            	id:'allModulesRoot',
                text:'All Modules',
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
        url:'../crm/customerList.jsp',
        text: 'Customer List'
    }, '01','01.001',true,true);

    this.initModule({
        url:'../crm/customerDetail.jsp',
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
Ext.extend(NavPanel, Ext.tree.TreePanel, {

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
