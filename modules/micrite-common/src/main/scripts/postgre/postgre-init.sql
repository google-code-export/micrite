insert  into t_user(name,username,password,email, disabled) values
     ('管理员','admin','e10adc3949ba59abbe56e057f20f883e','admin@gaixie.org',false);
insert  into t_user(name,username,password,email, disabled) values
     ('一般用户','user','e10adc3949ba59abbe56e057f20f883e','user@gaixie.org',false);
insert  into t_role(name,description) values 
     ('ROLE_USER','ROLE_USER');
insert  into t_role(name,description) values 
     ('ROLE_ADMIN','ROLE_ADMIN');
insert  into t_resource(type,value) values ('URL','/**');
insert  into t_role_resource(role_id,resource_id) values (1,1);
insert  into t_role_resource(role_id,resource_id) values (2,1);
insert  into t_user_role(user_id,role_id) values (1,1);
insert  into t_user_role(user_id,role_id) values (1,2);
insert  into t_user_role(user_id,role_id) values (2,1);
insert  into t_customer(name) values('中国移动');
insert  into t_customer(name) values('招商银行'); 