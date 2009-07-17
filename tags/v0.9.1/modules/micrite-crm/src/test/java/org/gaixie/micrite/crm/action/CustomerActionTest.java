package org.gaixie.micrite.crm.action;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.CustomerSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.opensymphony.xwork2.ActionSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-crm-bean.xml","classpath:databaseResource-hibernate.xml"})
public class CustomerActionTest {

    @Autowired
    private CustomerAction customerAction;

//    @Test
//    public void testAdd() {
//        //  准备数据
//        Customer customer = new Customer();
//        customer.setName("customerNameTest");
//        customer.setTelephone("customerTelTest");
//        customerAction.getPartner();
//        customer.setCustomerSource(customerAction.getCustomerSource().get(0));        
//        customerAction.setCustomer(customer);
//        //开始
//        String result = customerAction.add();
////        assertNotNull(customer.getId());
//        assertTrue(ActionSupport.SUCCESS.equals(result));
//    }

//    @Test
//    public void testFindByTelVague() {
//        Customer customer = new Customer();
//        customer.setTelephone("customerTelTest");
//        customerAction.setCustomer(customer);
//        customerAction.setTotalCount(0);
//        customerAction.setStart(0);
//        customerAction.setLimit(20);        
//        String result = customerAction.findByTelVague();
//        assertTrue(ActionSupport.SUCCESS.equals(result));
//    }

   
    @Test
    public void testGetPartner() {
        String result = customerAction.getPartner();
        List<CustomerSource> customerSources = customerAction.getCustomerSource();
        assertTrue(customerSources.size() > 0);
        assertTrue(ActionSupport.SUCCESS.equals(result));
    }

}
