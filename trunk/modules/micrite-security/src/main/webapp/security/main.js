Ext.onReady(function(){
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    var viewheader = new Ext.Panel({
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
                text:'Home',
                tooltip: 'home',  // <-- i
                iconCls :'home'
            },'-',{
                text:'Menu',
                tooltip: 'menu',
                iconCls :'menu'
            },'-',{
                text:'Email',
                tooltip: 'open email',
                iconCls :'email'
            },'->',{
                text:'User',
                tooltip: 'user infomation',
                iconCls :'user'
            },'-',{
                text:'Exit',
                tooltip: 'exit',
                iconCls :'exit'
            }]
        })
        ]
    });

    var navPanel = new NavPanel();
    var mainPanel = new MainPanel();

    navPanel.on('click', function(node, e){
        if(node.isLeaf()){
           e.stopEvent();
           mainPanel.loadFeed(node.attributes.url, node.attributes.text);
        }
   });
    
    var viewport = new Ext.Viewport({
        layout:'border',
        items:[
            viewheader,
            navPanel,
            mainPanel
        ]
    });

});
