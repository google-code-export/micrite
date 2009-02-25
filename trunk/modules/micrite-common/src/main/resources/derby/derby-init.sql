insert  into t_user(id,name,username,password,email, disabled) values
					(1,'一般用户','user','e10adc3949ba59abbe56e057f20f883e','user@gaixie.org',0),
					(2,'管理员','admin','e10adc3949ba59abbe56e057f20f883e','admin@gaixie.org',0);
insert  into t_role(id,name,description) values 
					(1,'ROLE_USER','ROLE_USER'),
					(2,'ROLE_ADMIN','ROLE_ADMIN');
insert  into t_resource(id,type,value) values (1,'URL','/**');
insert  into t_role_resource(role_id,resource_id) values (1,1),(2,1);
insert  into t_user_role(user_id,role_id) values (1,1),(1,2),(2,1);
