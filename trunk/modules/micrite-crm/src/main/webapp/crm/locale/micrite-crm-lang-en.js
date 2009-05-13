function customerListLocale(){
    if(micrite.crm.customerList.SearchPanel) {
        Ext.override(micrite.crm.customerList.SearchPanel, {
            searchText:'Search By',
            newCustomerLink:'<a href="crm/customerDetail.jsp" id="Customer Detail" class="inner-link">New Customer</a>'
        });
    }	
    if(micrite.crm.customerList.SearchResultGrid) {
        Ext.override(micrite.crm.customerList.SearchResultGrid, {
            colModelId:'ID',
            colModelName:'Name',
            colModelMobile:'Mobile',
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