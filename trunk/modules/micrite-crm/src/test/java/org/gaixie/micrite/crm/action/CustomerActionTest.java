package org.gaixie.micrite.crm.action;

import static org.junit.Assert.*;

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

	@Test
	public void testIndex() {
		String result = customerAction.index();
		assertTrue(ActionSupport.SUCCESS.equals(result));
	}

	@Test
	public void testSave() {
		customerAction.index();
		int customerCountBeforeSave = customerAction.getCustomerNum();
		//	准备数据
		Customer customer = new Customer();
		customer.setName("customerNameTest");
		customer.setTelephone("customerTelTest");
		customerAction.setCustomer(customer);
		//	找到一个customerSourceId
		customerAction.getPartner();
		int customerSourceId = customerAction.getCustomerSource().get(0).getId();
		customerAction.setCustomerSourceId(customerSourceId);
		String result = customerAction.save();
		customerAction.index();
		int customerCountAfterSave = customerAction.getCustomerNum();
		assertTrue(customerCountAfterSave - customerCountBeforeSave == 1);
		assertNotNull(customer.getId());
		assertTrue(customer.getCustomerSource().getId() == customerSourceId);
		assertTrue(ActionSupport.SUCCESS.equals(result));
	}

	@Test
	public void testFind() {
		customerAction.setTelephone("12345678910");
		String result = customerAction.find();
		assertTrue(ActionSupport.SUCCESS.equals(result));
	}

	@Test
	public void testGetPartner() {
		String result = customerAction.getPartner();
		List<CustomerSource> customerSources = customerAction.getCustomerSource();
		assertTrue(customerSources.size() > 0);
		assertTrue(ActionSupport.SUCCESS.equals(result));
	}

}
