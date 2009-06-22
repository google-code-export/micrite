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

package org.gaixie.micrite.security.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.gaixie.micrite.beans.Authority;
import org.gaixie.micrite.beans.Role;
import org.gaixie.micrite.beans.Setting;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.dao.IUserDao;
import org.gaixie.micrite.security.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 接口 <code>ILoginService</code> 的实现类。
 * 
 * @see org.gaixie.micrite.security.service.ILoginService
 */
public class LoginServiceImpl implements ILoginService {
	
	private final static Logger logger = Logger.getLogger(LoginServiceImpl.class);

    @Autowired
    private IUserDao userDao;

    public Set<Map<String, Object>> loadChildNodes(User user, String node) {
        Set<Map<String, Object>> menu = new HashSet<Map<String, Object>>();

        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            Set<Authority> auths = role.getAuthorities();
            for (Authority auth : auths) {
                // 如果没有"/"，表示不会用于menu中显示
                if (StringUtils.indexOf(auth.getName(), "/") >= 0) {
                    String tmp = "";
                    /*
                     * 根据选定节点，匹配拥有的权限串，生成下级节点的id和text， 例：当前User拥有3个权限串(
                     * "/N1/N2/N3" , "/N1/N4" 和 "/N5" )
                     * 选定根节点：全部3个权限串的处理都直接split，取第一个token，最后得到"N1"和"N5"
                     * 会得到2次"N1"，没关系，因为使用Set类型，所以重复的对象不会被add，
                     * 注意map要put相同的值(比如url)，如果有不同的值被认为不同的对象 选定中间节点N1:
                     * 第1,2个权限串先substring，得到"/N2/N3"和"N4"，然后再split，最后得到"N2"和"N4"
                     * 第3个权限串不做处理，直接continue
                     */
                    if ("allModulesRoot".equals(node)) {
                        tmp = auth.getName();
                    } else if (StringUtils.indexOf(auth.getName(), node) >= 0) {
                        tmp = StringUtils.substringAfter(auth.getName(), node);
                    } else
                        continue;

                    Map<String, Object> map = new HashMap<String, Object>();
                    String[] names = StringUtils.split(tmp, "/");
                    map.put("text", names[0]);
                    map.put("id", "/" + names[0]);
                    if (names.length > 1) {
                        map.put("url", names[0]);
                        map.put("leaf", false);
                    } else {
                        map.put("url", StringUtils
                                .substring(auth.getValue(), 1));
                        map.put("leaf", true);
                    }
                    menu.add(map);
                }
            }
        }
        
        List<Setting> settings = user.getSetting();
        for (Setting setting : settings) {
        	logger.debug(setting.getName());
        	logger.debug(setting.getValue());
        }
        return menu;
    }

}
