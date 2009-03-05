package org.gaixie.micrite.crm.action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.crm.service.IUserService;
import org.gaixie.micrite.crm.service.impl.UserServiceImplMock;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionSupport;

public class UserActionUnitTest {
	
	@Test
    public void testManageWhenSomeUsers() {
		//	准备serviceMock
    	List<User> usersMock = new ArrayList<User>();
    	User userMock = new User();
    	userMock.setName("userName");
    	usersMock.add(userMock);
    	IUserService userServiceMock = new UserServiceImplMock();
    	((UserServiceImplMock)userServiceMock).setUsers(usersMock);
    	
    	UserAction userAction = new UserAction(userServiceMock);
    	//	调用待测试方法
        String result = userAction.manage();
        List<User> users = userAction.getUsers();
        //	断言查询结果长度为1
        assertEquals(1, users.size());
        User user = users.get(0);
        //	断言查询结果用户的名字
        assertTrue("userName".equals(user.getName()));
        //    断言是否正确返回结果
        assertTrue(ActionSupport.SUCCESS.equals(result));
    }

	@Test
    public void testManageWhenNoUsers() {
		//	准备serviceMock
    	IUserService userServiceMock = new UserServiceImplMock();
    	((UserServiceImplMock)userServiceMock).setUsers(new ArrayList<User>());

    	UserAction userAction = new UserAction(userServiceMock);
    	//	调用待测试方法
		String result = userAction.manage();
        List<User> users = userAction.getUsers();
        assertNull(users);
        assertTrue(ActionSupport.SUCCESS.equals(result));
    }

	@Test
    public void testDisable() {
		//	准备serviceMock
    	List<User> usersMock = new ArrayList<User>();
    	User userMock = new User();
    	userMock.setId(1);
    	userMock.setDisabled(false);
    	usersMock.add(userMock);
    	IUserService userServiceMock = new UserServiceImplMock();
    	((UserServiceImplMock)userServiceMock).setUsers(usersMock);	
		
    	UserAction userAction = new UserAction(userServiceMock);
    	userAction.setId(1);
        //	调用待测试方法
        String result = userAction.disabled();
        assertTrue(userMock.isDisabled());
        //    断言是否正确返回结果
        assertTrue(ActionSupport.SUCCESS.equals(result));
    }
}
