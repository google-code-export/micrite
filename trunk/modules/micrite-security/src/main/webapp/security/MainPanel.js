MainPanel = function() {
    // 显式调用父类构造器    
    MainPanel.superclass.constructor.call(this, {
        id:'main-tabs',
        region:'center',
        margins:'0 0 5 0',
        activeTab:0,
        items:[{
            title: 'Center Panel',
            autoScroll:true
        }]
    });
}
Ext.extend(MainPanel, Ext.TabPanel, {
    initEvents : function(){
	    MainPanel.superclass.initEvents.call(this);
	    this.body.on('click', this.onClick, this);
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

