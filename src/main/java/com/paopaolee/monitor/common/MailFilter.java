package com.paopaolee.monitor.common;

import javax.mail.Message;

/**
 * @author paopaolee
 */
@FunctionalInterface
public interface MailFilter {
    /**\
     * 过滤邮件
     * @param message
     * @return boolean
     */
    boolean test(Message message);
}
