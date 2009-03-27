package org.gaixie.micrite.crm.action;

import static org.junit.Assert.*;

import java.util.List;

import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.CustomerSource;
import org.gaixie.micrite.crm.service.impl.CustomerServiceImplMock;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionSupport;

public class CustomerActionUnitTest {

	private CustomerAction customerAction;
	private CustomerServiceImplMock customerServiceMock;

	@Before
	public void setUp() throws Exception {
		customerServiceMock = new CustomerServiceImplMock();
		customerAction = new CustomerAction(customerServiceMock);
	}

	@Test
	public void testIndex() {
		//	调用待测试方法
		String result = customerAction.index();
		int customerNum = customerAction.getCustomerNum();
		assertTrue(customerServiceMock.getCustomers().size() == customerNum);
		assertTrue(ActionSupport.SUCCESS.equals(result));
	}

	@Test
	public void testSave() {
		//	测试save新增功能
		int customerCountBeforeSave = customerServiceMock.getCustomers().size();
		//	准备数据
		Customer customer = new Customer();
		customer.setName("customerName1");
		customer.setTelephone("customerTel1");
		customerAction.setCustomer(customer);
		//	找到一个customerSource的id
		int customerSourceId = customerServiceMock.getCustomerSources().get(0).getId();
		customerAction.setCustomerSourceId(customerSourceId);
		//	调用待测试方法
		String result = customerAction.save();
		int customerCountAfterSave = customerServiceMock.getCustomers().size();
		assertTrue(customerCountAfterSave - customerCountBeforeSave == 1);
		assertNotNull(customer.getId());
		assertTrue(customer.getCustomerSource().getId() == customerSourceId);
        assertTrue(ActionSupport.SUCCESS.equals(result));
	}

	@Test
	public void testFind() {
		//	准备数据
		String telephone = customerServiceMock.getCustomers().get(0).getTelephone();
		customerAction.setTelephone(telephone);
		//	调用待测试方法
		String result = customerAction.find();
		List<Customer> customers = customerAction.getCustomers();
		assertTrue(customers.size() > 0);
		for (Customer customer:customers)
		{
			assertTrue(customer.getTelephone().equals(telephone));
		}
        assertTrue(ActionSupport.SUCCESS.equals(result));
	}

	@Test
	public void testGetPartner() {
		List<CustomerSource> customerSourcesWanted = customerServiceMock.getCustomerSources();
		//	调用待测试方法
		String result = customerAction.getPartner();
		List<CustomerSource> customerSources = customerAction.getCustomerSource();
		assertTrue(customerSources.size() > 0);
		for (int i = 0;i < customerSources.size();i++)
		{
			assertEquals(customerSourcesWanted.get(i).getId(), customerSources.get(i).getId());
			assertEquals(customerSourcesWanted.get(i).getName(), customerSources.get(i).getName());
		}
		assertTrue(ActionSupport.SUCCESS.equals(result));
	}
}
