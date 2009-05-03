function customerListLocale(){
    if(micrite.crm.customerList.FormPanel) {
        Ext.override(micrite.crm.customerList.FormPanel, {
            colModelId:'ID',
            colModelName:'Name',
            colModelMobile:'Mobile',
            colModelSource:'Source',
            searchText:'Search By Telephone',
            newCustomerLink:'<a href="../crm/customerDetail.jsp" id="Customer Detail" class="inner-link">New Customer</a>'
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