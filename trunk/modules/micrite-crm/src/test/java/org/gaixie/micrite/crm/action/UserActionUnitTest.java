package org.gaixie.micrite.crm.action;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.opensymphony.xwork2.ActionSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-crm-bean-test.xml"})
public class UserActionUnitTest {

    @Autowired
    private UserAction userAction;

    @Test
    public void testManage() {
        String result = userAction.manage();
        assertTrue(ActionSupport.SUCCESS.equals(result));
    }

    @Test
    public void testDisable() {
        //    调用待测试方法
        String result = userAction.disabled();
        //    断言是否正确返回结果
        assertTrue(ActionSupport.SUCCESS.equals(result));
    }
}
