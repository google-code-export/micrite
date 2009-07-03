package org.gaixie.micrite.crm.action;

import static org.junit.Assert.assertTrue;

import java.util.List;

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
	public void testGetPartner() {
		String result = customerAction.getPartner();
		List<CustomerSource> customerSources = customerAction.getCustomerSource();
		assertTrue(customerSources.size() > 0);
		assertTrue(ActionSupport.SUCCESS.equals(result));
	}

}
