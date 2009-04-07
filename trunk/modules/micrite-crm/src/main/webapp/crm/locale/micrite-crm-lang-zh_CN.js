function customerListLocale(){
	if(micrite.crm.customerList.FormPanel) {
	    Ext.override(micrite.crm.customerList.FormPanel, {
	    	colModelId:'ID',
	    	colModelName:'姓名',
	    	colModelMobile:'手机号',
	    	colModelPartner:'来源',
	    	searchText:'查找',
	    	newCustomerLink:'<a href="../crm/customerDetail.jsp" id="Customer Detail" class="inner-link">增加新客户</a>'
	    });
	}
}
function customerDetailLocale(){
	if(micrite.crm.customerDetail.FormPanel) {
	    Ext.override(micrite.crm.customerDetail.FormPanel, {
	    	customerDetailText:'客户基本信息',
	    	idText:'ID',
	    	nameText:'姓名',
	    	mobileText:'手机号',
	    	partnerText:'来源',
	    	submitText:'提交',
	    	cancelText:'取消',
	    	waitingMsg:'提交数据...'
	    });
	}	
}