function customerListLocale(){
    if(micrite.crm.customerList.SearchPanel) {
        Ext.override(micrite.crm.customerList.SearchPanel, {
        	searchCondition1:'条件分组 1',
        	searchCondition2:'条件分组 2',
        	searchCondition3:'条件分组 3',  
        	searchCellphone:'手机号',        	
        	searchStartTime:'起始时间',
        	searchEndTime:'截至时间',	        	
            newCustomer:'增加新客户',
            customerSourceBarChart:'客户来源分析(柱图)',
            customerSourcePieChart:'客户来源分析(饼图)',
            colModelId:'ID',
            colModelName:'姓名',
            colModelMobile:'手机号',
            colModelSource:'来源',
            colCreation_ts:'创建时间',
            searchCustomerSourceType:'用户类型'
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
            sourceText:'来源',
            comboEmptyText:'选择客户来源...'
        });
    }    
}