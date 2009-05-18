<!-- Do NOT put any DOCTYPE here unless you want problems in IEs. -->
<html>
 
<!-- Each valid html page must have a head; let's create one. -->
<head>
  <!-- The following line defines content type and utf-8 as character set. -->
  <!-- If you want your application to work flawlessly with various local -->
  <!-- characters, just make ALL strings, on the page, json and database utf-8. -->
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 
  <!-- Ext relies on its default css so include it here. -->
  <!-- This must come BEFORE javascript includes! -->
  <link rel="stylesheet" type="text/css" href="ext/resources/css/ext-all.css">
  <link rel="stylesheet" type="text/css" href="multiselectitemselector/Multiselect.css">
  <!-- Include here your own css files if you have them. -->
 
  <!-- First of javascript includes must be an adapter... -->
  <script type="text/javascript" src="ext/adapter/ext/ext-base.js"></script>
 
  <!-- ...then you need the Ext itself, either debug or production version. -->
  <script type="text/javascript" src="ext/ext-all-debug.js"></script>
  <script type="text/javascript" src="multiselectitemselector/DDView.js"></script> 
  <script type="text/javascript" src="multiselectitemselector/Multiselect.js"></script> 
  <!-- Include here your extended classes if you have some. -->
 
  <!-- Include here you application javascript file if you have it. -->
 
  <!-- Set a title for the page (id is not necessary). -->
  <title id="page-title">Title</title>
 
  <!-- You can have onReady function here or in your application file. -->
  <!-- If you have it in your application file delete the whole -->
  <!-- following script tag as we must have only one onReady. -->
  <script type="text/javascript">
 
  // Path to the blank image must point to a valid location on your server
  Ext.BLANK_IMAGE_URL = 'ext/resources/images/default/s.gif';

  // Main application entry point
Ext.onReady(function() {    
    new Ext.FormPanel({
            id: 'user-form',
            border: false,
            items:[{
                xtype: 'fieldset',
                labelWidth: 110,
                title:'User Detail',
                defaults: {width: 140},
                defaultType: 'textfield',
                autoHeight: true,
                border: false,
                items: [{
                    fieldLabel: 'Full Name',
                    name: 'fullname',
                },{
                    fieldLabel: 'Email Address',
                    name: 'emailaddress',
                },{
                    fieldLabel: 'User Name',
                    name: 'loginname'
                },{
                    fieldLabel: 'Password',
                    name: 'password'
                },{
                     xtype:"multiselect",
                     fieldLabel:"Roles",
                     name:"multiselect",
                     dataFields:["code", "desc"], 
                     data:[[123,"One Hundred Twenty Three"],
                      ["1", "One"], ["2", "Two"], ["3", "Three"], ["4", "Four"], ["5", "Five"],
                      ["6", "Six"], ["7", "Seven"], ["8", "Eight"], ["9", "Nine"]],
                     valueField:"code",
                     displayField:"desc",
                     width:250,
                     height:200,
                     allowBlank:true,
                     legend:"Multiselect"
                }]
            }],
            buttons: [{
                text: 'Submit'
            },{
                text: 'Cancel'
            }],
            buttonAlign:'left',
            renderTo: 'userDetail'
    });
})
</script>
 
<!-- Close the head -->  
</head>
 
<!-- You can leave the body empty in many cases, or you write your content in it. -->
<body>
<div id="userDetail"></div>
</body>
 
<!-- Close html tag at last -->
</html>