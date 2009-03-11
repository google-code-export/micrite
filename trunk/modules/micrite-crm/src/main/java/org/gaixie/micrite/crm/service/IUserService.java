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

package org.gaixie.micrite.crm.service;

import java.util.List;

import org.gaixie.micrite.beans.Customer;
import org.gaixie.micrite.beans.Member;
import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.beans.User;

/**
 * @author Administrator
 *
 */
public interface IUserService {
	/**
	 * 新增用户
	 * @param  member
	 * @return
	 */
	public void saveMember(Member member);
	
	/**
	 * 取得用户数量
	 * @param  user
	 * @return
	 */
	public int getMemberNum();
	
	/**
	 * 根据电话精确查找
	 * @param  telephone
	 * @return
	 */
	public List<Member> findByTelExact(String telephone);

	/**
	 * 根据ID精确查找
	 * @param  telephone
	 * @return
	 */
	public Member findByIdExact(int id);
	
	/**
	 * 取得所有大客户信息
	 * @return
	 */
	public List<Customer> findALLCustomer();

}
