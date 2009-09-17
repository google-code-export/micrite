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

package org.gaixie.micrite.service;

import java.util.List;
import java.util.Map;

/**
 * 邮件服务接口，为系统提供邮件通知功能
 * 
 */
public interface IEmailService {
    
    /**
     * 发送邮件至多个接收者
     * @param from 邮件发送方，能否修改与邮件服务器设置有关
     * @param recipients 邮件的接收方(多个)
     * @param subj 邮件的标题
     * @param text 邮件的正文
     */
    public void sendEmail(String from, List<String> recipients, String subj, String text);
     
    /**
     * 发送邮件至单个接收者
     * @param from 邮件发送方，能否修改与邮件服务器设置有关
     * @param recipient 邮件的接收方(单个)
     * @param subj 邮件的标题
     * @param text 邮件的正文
     */    
    public void sendEmail(String from, String recipient, String subj, String text);
}

