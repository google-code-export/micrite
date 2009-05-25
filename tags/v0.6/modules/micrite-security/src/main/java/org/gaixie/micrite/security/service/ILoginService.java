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

package org.gaixie.micrite.security.service;

import java.util.Set;
import java.util.Map;
import org.gaixie.micrite.beans.User;

/**
 * 提供与登录有关的服务。
 * 
 */
public interface ILoginService {

    /**
     * lazy 方式获取用户菜单数据，每次只加载当前菜单节点的下一级菜单数据。
     * <p>
     * 用户成功登录后自动调用此方法，加载菜单根节点的下一级数据，此后只有当用户点击菜单， 才调用此方法，加载点击菜单节点的下一级数据。
     * 
     * @see org.gaixie.micrite.beans.User
     * @param user
     *            User实体
     * @param node
     *            菜单上当前节点id
     * @return 下一级菜单数据集合
     */
    public Set<Map<String, Object>> loadChildNodes(User user, String node);

}
