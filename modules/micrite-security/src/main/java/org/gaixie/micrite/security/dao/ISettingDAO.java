package org.gaixie.micrite.security.dao;

import java.util.List;

import org.gaixie.micrite.beans.Setting;
import org.gaixie.micrite.dao.IGenericDAO;

public interface ISettingDAO extends IGenericDAO<Setting, Integer>{
    /**
     * 查询系统给用户的默认设置
     * @return <code>UserSetting</code>对象列表
     */
    public List<Setting> findAllDefault();
    
    /**
     * 根据配置项名称查询可用配置属性
     * @return <code>UserSetting</code>对象列表
     */
    public List<Setting> findSettingByName(String name);
    
}
