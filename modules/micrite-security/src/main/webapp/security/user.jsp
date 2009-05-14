<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<div id="user">
<script type="text/javascript">
Ext.onReady(function() {
    var searchPanel = new Ext.grid.GridPanel({
        title:"mySearchPanel",
        width:200,
        height:300,
        region:"west"
    });
    var formPanel = new Ext.FormPanel({
        title:"myFormPanel",
        width:300,
        height:400,
        region:"east"
    });
    var userPanel = new Ext.Panel({
        layout:"border",
        items:[
            searchPanel,
            formPanel
        ]
    });
    userPanel.render("user");
})
</script>
</div>