Ext.ns('micrite.base');
micrite.base.locale = function(config) {}
Ext.extend(micrite.base.locale, {
    searchButton:'Search',                
    submitButton:'Save',
    closeButton:'Close',
    cancelButton:'Cancel',
    waitingMsg:'Saving Data...',
    loadingMsg:'Loading Data...'    
});
mbLocale = new micrite.base.locale();