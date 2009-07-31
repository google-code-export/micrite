function customerListLocale(){
    if(micrite.crm.customerList.SearchPanel) {
        Ext.override(micrite.crm.customerList.SearchPanel, {
        	searchCondition1:'Condition 1',
        	searchCondition2:'Condition 2',
        	searchCondition3:'Condition 3',
        	searchCellphone:'Cellphone',        	
        	searchStartTime:'StartTime',
        	searchEndTime:'EndTime',	        	
        	newCustomer:'New Customer',
    		customerSourceBarChart:'Customer Source BarChart',
    		customerSourcePieChart:'Customer Source PieChart',
    		colModelId:'ID',
            colModelName:'Name',
            colModelMobile:'Cellphone',
            colModelSource:'Source',
            colCreation_ts:'Create Date'
        });
    }	
    if(micrite.crm.customerList.SearchResultGrid) {
        Ext.override(micrite.crm.customerList.SearchResultGrid, {
            colModelId:'ID',
            colModelName:'Name',
            colModelMobile:'Cellphone',
            colModelSource:'Source'
        });
        
    }
}
function customerDetailLocale(){
    if(micrite.crm.customerDetail.FormPanel) {
        Ext.override(micrite.crm.customerDetail.FormPanel, {
            customerDetailText:'Customer Detail',
            idText:'ID',
            nameText:'Name',
            mobileText:'Mobile',
            sourceText:'Source',
            comboEmptyText:'Select a source...'
        });
    }    
}