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

package org.gaixie.micrite.crm.service.impl;

import java.util.List;

import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.Member;
import org.gaixie.micrite.crm.dao.IUserDao;
import org.gaixie.micrite.crm.service.IUserService;

public class UserServiceImpl implements IUserService {

	private IUserDao userDao;
    
	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	public List<Customer> findALLCustomer(){
		List<Customer> customers = userDao.findAll(Customer.class);
		return customers;
	}

	public void saveMember(Member member){
		if(member.getId() == null){
			userDao.save(member);
		}
		else{
			userDao.update(member);
		}
	}
	public int getMemberNum(){
		List<Member> members = userDao.findAll(Member.class);
		return members == null ? 0 : members.size();
	}
	public List<Member> findByTelExact(String telephone){
		List<Member> list = userDao.findSingleExact(Member.class, "telephone", telephone);
		return list;
	}
	
	public Member findByIdExact(int id){
		return (Member)userDao.getEntity(Member.class, id);
	}


}
