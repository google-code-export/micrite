package org.gaixie.micrite.crm.action;

import static org.junit.Assert.*;

import java.util.List;

import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.CustomerSource;
import org.junit.Before;
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
		List<CustomerSource> customerSources = customerAction.getCustomerSource();
		assertNotNull(customerSources);
		assertTrue(ActionSupport.SUCCESS.equals(result));
	}

	@Test
	public void testSave() {
		//	准备数据
		Customer customer = new Customer();
		customer.setName("customerName1");
		customer.setTelephone("customerTel1");
		customerAction.setCustomer(customer);
		//	找到一个customerSourceId
		customerAction.index();
		int customerSourceId = customerAction.getCustomerSource().get(0).getId();
		customerAction.setCustomerSourceId(customerSourceId);
		String result = customerAction.save();
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
	public void testEdit() {
		customerAction.setCustomerId(1);
		String result = customerAction.edit();
		assertTrue(ActionSupport.SUCCESS.equals(result));
	}
}
