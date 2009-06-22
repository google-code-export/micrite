package org.gaixie.micrite.security.dao;

import java.util.List;

import org.gaixie.micrite.beans.Setting;

public interface ISettingDao {
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
    
    /**
     * 根据配置项id查询可用配置属性
     * @return <code>UserSetting</code>对象列表
     */
    public Setting getSetting(int id);
}
