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
 
  <!-- Include here your own css files if you have them. -->
 
  <!-- First of javascript includes must be an adapter... -->
  <script type="text/javascript" src="ext/adapter/ext/ext-base.js"></script>
 
  <!-- ...then you need the Ext itself, either debug or production version. -->
  <script type="text/javascript" src="ext/ext-all-debug.js"></script>
  <script type="text/javascript" src="searchfield.js"></script>
 
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
    var myData = [
        [3,'wangyang','tommy.wang@golferpass.com','wy',true],
        [8,'yubo','bo.yu@golferpass.com','yub',true],
        [9,'yujingui','jingui.yu@golferpass.com','yjg',false],
        [15,'yebo','bo.ye@golferpass.com','yeb',true],
        [30,'chenzhaoming','zhaoming.chen@golferpass.com','czhm',true],
        [62,'xingzhaoyong','xzy.xing@golferpass.com','xzhy',false],
    ];
    var ds = new Ext.data.Store({
        reader: new Ext.data.ArrayReader({}, [
               {name: 'id', type: 'int'},
               {name: 'fullname'},
               {name: 'emailaddress'},
               {name: 'loginname'},
               {name: 'isenabled', type: 'boolean'}
          ])
    });
    ds.loadData(myData);

    var colModel = new Ext.grid.ColumnModel([
        {dataIndex: 'id', hidden: true},
        {header: "Full Name", width: 100, sortable: true, dataIndex: 'fullname'},
        {id: "emailaddress", header: "Email Address", width: 180, sortable: true, dataIndex: 'emailaddress'},
        {header: "User Name", width: 90, sortable: true, locked:false, dataIndex: 'loginname'},
        {header: "Isenabled", width: 70, sortable: true, dataIndex: 'isenabled'}
    ]);

    new Ext.Panel({
        frame: true,
        labelAlign: 'left',
        title: 'User Data',
        bodyStyle:'padding:5px',
        width: 750,
        layout: 'column',
        items: [{
            columnWidth: 0.6,
            layout: 'fit',
            items: {
                xtype: 'grid',
                tbar: [{text:'User Name'}, 
                        new Ext.app.SearchField({
                            store: ds,
                            width: 150
                        })
                      ],
                ds: ds,
                cm: colModel,
                sm: new Ext.grid.RowSelectionModel({
                    singleSelect: true,
                    listeners: {
                        rowselect: function(sm, row, rec) {
                            Ext.getCmp("user-form").getForm().loadRecord(rec);
                        }
                    }
                }),
                autoExpandColumn: 'emailaddress',
                height: 350,
                border: true
            }
        },{
            columnWidth: 0.4,
            xtype: 'form',
            id: 'user-form',
            items:[{
                xtype: 'fieldset',
                labelWidth: 110,
                title:'Modify User Name or Password',
                defaults: {width: 110},
                defaultType: 'textfield',
                autoHeight: true,
                bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
                border: false,
                style: {
                    "margin-left": "10px",
                    "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"
                },
                items: [{
                    xtype: 'hidden',
                    name: 'id'
                },{
                    fieldLabel: 'Full Name',
                    name: 'fullname',
                    disabled: true
                },{
                    fieldLabel: 'User Name',
                    name: 'loginname'
                },{
                    fieldLabel: 'Password',
                    name: 'password'
                },{
                    fieldLabel: 'Password Again',
                    name: 'PasswordAgain'
                }]
            }],
            buttons: [{
                text: 'Submit'
            },{
                text: 'Cancel'
            }]
        }],
        renderTo: 'userList'
    });
})
</script>
 
<!-- Close the head -->  
</head>
 
<!-- You can leave the body empty in many cases, or you write your content in it. -->
<body>
<div id="userList"></div>
</body>
 
<!-- Close html tag at last -->
</html>