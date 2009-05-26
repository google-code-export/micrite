function customerListLocale(){
    if(micrite.crm.customerList.SearchPanel) {
        Ext.override(micrite.crm.customerList.SearchPanel, {
        	searchButton:'Search',	        	
        	searchCondition1:'Condition 1',
        	searchCondition2:'Condition 2',
        	searchCondition3:'Condition 3',
        	searchCellphone:'Cellphone',        	
        	searchStartTime:'StartTime',
        	searchEndTime:'EndTime',	        	
        	newCustomer:'New Customer'
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
            submitText:'Save',
            cancelText:'Cancel',
            comboEmptyText:'Select a source...',
            waitingMsg:'Saving Data...'
        });
    }    
}