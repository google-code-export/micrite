Ext.onReady( function() {
		Ext.QuickTips.init();

		// Create a variable to hold our EXT Form Panel. 
			// Assign various config options as seen.	 
			var login = new Ext.FormPanel(
					{
						labelWidth :90,
						url :'j_spring_security_check',
						frame :true,
						title :'Please Login',
						defaultType :'textfield',
						monitorValid :true,
						// Specific attributes for the text fields for username / password. 
						// The "name" attribute defines the name of variables sent to the server.
						items : [ {
							fieldLabel :'Username',
							name :'j_username',
							labelStyle :'text-align:right',
							allowBlank :false
						}, {
							fieldLabel :'Password',
							name :'j_password',
							inputType :'password',
							labelStyle :'text-align:right',
							allowBlank :false
						}, {

							name :'_spring_security_remember_me',
							inputType :'checkbox',
							fieldLabel :'Remember me',
							labelStyle :'padding-right:70px; float: right;',
							ctCls :'micrite',
							labelSeparator :'',
							allowBlank :true
						} ],

						// All the magic happens after the user clicks the button     
						buttons : [ {
							text :'登录',
							formBind :true,
							// Function that fires when user clicks the button 
							handler : function() {
								login
										.getForm()
										.submit(
												{
													method :'POST',
													waitTitle :'Connecting',
													waitMsg :'Sending data...',

													success : function() {
														//Ext.Msg.alert('Status', 'Login Successful!', function(btn, text){
														//   if (btn == 'ok'){
														// var redirect = 'hello.jsp'; 
														//window.location = redirect;
														document
																.getElementById(
																		'loginForm')
																.submit();
														//   }
														// });
													},

													failure : function(form,
															action) {
														if (action.failureType == 'server') {
															obj = Ext.util.JSON
																	.decode(action.response.responseText);
															Ext.Msg
																	.alert(
																			'Login Failed!',
																			obj.errors.reason);
														} else {
															Ext.Msg
																	.alert(
																			'Warning!',
																			'Authentication server is unreachable : ' + action.response.responseText);
														}
														login.getForm().reset();
													}
												});
							}
						} ]
					});

			// This just creates a window to wrap the login form. 
			// The login object is passed to the items collection.       
			var win = new Ext.Window( {
				layout :'fit',
				width :300,
				height :160,
				closable :false,
				resizable :false,
				plain :true,
				border :false,
				items : [ login ]
			});
			win.show();
		});