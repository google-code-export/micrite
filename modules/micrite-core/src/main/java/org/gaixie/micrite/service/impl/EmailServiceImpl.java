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

package org.gaixie.micrite.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.gaixie.micrite.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.MailException;

/**
 * 接口 <code>IEmailService</code> 的实现类。
 * 
 */
public class EmailServiceImpl implements IEmailService { 
    @Autowired
    private JavaMailSender mailSender;
    
    public void sendEmail(String from, List<String> recipients, String subj, String text) {
        
        final String fm = from;
        final String s = subj;
        final String tpl = text;
       
        for(final String to : recipients){
            // 使用线程解决业务与邮件发送异步，例如域名无法解析造成的延迟。
            Runnable thread=new Runnable(){
                public void run(){
                    try {
                        mailSender.send(new MimeMessagePreparator() {
                            public void prepare(MimeMessage mimeMessage) throws Exception {
                                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"UTF-8");
                                if(fm!=null)  helper.setFrom(fm);
                                else helper.setFrom("micrite-noreply@gmail.com");
                                if(s!=null) helper.setSubject(s);
                                else helper.setSubject("no subject");
                                if(tpl!=null) helper.setText(tpl);
                                else helper.setText("no text");
                                helper.setTo(to);
                            }
                        });
                    }
                    catch (MailException ex) {
                        // simply log it and go on...
                        System.err.println(ex.getMessage());            
                    }
                }
            };
            new Thread(thread).start();                    
        }
    }
    
    public void sendEmail(String from, String recipient, String subj, String text) {
        List recipients = new ArrayList <String>();
        recipients.add(recipient); 
        
        sendEmail(from, recipients, subj, text);
    }    
}

