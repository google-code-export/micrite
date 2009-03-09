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
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Micrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Micrite.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.gaixie.micrite.crm.action;

import java.util.List;

import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.Member;
import org.gaixie.micrite.crm.service.IUserService;

import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport{ 
	
	public UserAction(IUserService userService) {
		this.userService = userService;
	}

	private Member member;
	private List<Member> members;
	private IUserService userService;
	private Integer id;
	private int memberNum = 0;
	private String telephone;
	private List<Customer> customers;
	private Integer customer_id;

	/**
	 * 用户管理
	 * 
	 * @return
	 */
	public String manage() {
		memberNum = userService.getMemberNum();
		customers = userService.findALLCustomer();
        return SUCCESS;
	}
	/**
	 * 新增/修改用户
	 * 
	 * @return
	 */
	public String save() {
		for(Customer c : customers ){
			if(c.getId().equals(customer_id))
				member.setCustomer(c);
		}
		userService.saveMember(member);
		memberNum = userService.getMemberNum();
		member = null;
        return SUCCESS;
	}
	/**
	 * 查找用户
	 * 
	 * @return
	 */
	public String find() {
		members = userService.findByTelExact(telephone);
        return SUCCESS;
	}
	/**
	 * 查找用户
	 * 
	 * @return
	 */
	public String edit() {
		for(Member m : members ){
			if(m.getId().equals(id))
				member = m;
		}
		customer_id = member.getCustomer().getId();
        return SUCCESS;
	}
	
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Member getMember() {
		return member;
	}
	public List<Member> getMembers() {
		return members;
	}
	public int getMemberNum() {
		return memberNum;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	/**
	 * @return the customers
	 */
	public List<Customer> getCustomers() {
		return customers;
	}

	/**
	 * @return the customer_id
	 */
	public Integer getCustomer_id() {
		return customer_id;
	}
	/**
	 * @param customer_id the customer_id to set
	 */
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	/**
	 * @param members the members to set
	 */
	public void setMembers(List<Member> members) {
		this.members = members;
	}
	/**
	 * @param memberNum the memberNum to set
	 */
	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}
	/**
	 * @param customers the customers to set
	 */
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	
}
