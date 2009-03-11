/* ===========================================================
 * $Id$
 * This file is part of Micrite
 * ===========================================================
 *
 * (C) Copyright 2009, by Gaixie.org and Contributors.
 * 
 * Project Info:  http://micrite.gaixie.org/
 *
 * Micrite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Micrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Micrite.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.gaixie.micrite.crm.action;

import static org.junit.Assert.*;

import java.util.List;


import org.gaixie.micrite.beans.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.opensymphony.xwork2.ActionSupport;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-crm-bean.xml","classpath:databaseResource-hibernate.xml"})
public class UserActionTest {

    @Autowired
    private UserAction userAction;

    @Test
    public void testManage() {
        String result = userAction.manage();
        assertTrue(ActionSupport.SUCCESS.equals(result));
    }

    @Test
    public void testDisable() {
    	//	查到一个user，取出id
    	userAction.manage();
    	int id = -1;
    	List<User> users = userAction.getUsers();
    	for (User user:users)
    	{
    		id = user.getId();
    	}
    	if (id != -1)
    	{
    		userAction.setId(id);
	        //    调用待测试方法
	        String result = userAction.disabled();
	        //    断言是否正确返回结果
	        assertTrue(ActionSupport.SUCCESS.equals(result));
    	}
    }
}
