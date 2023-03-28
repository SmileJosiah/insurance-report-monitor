package com.paopaolee.monitor.bean;

import com.paopaolee.monitor.common.MailFilter;

import javax.mail.Message;
import javax.mail.search.SearchTerm;

/**
 * @author paopaolee
 */
public final class MailSearchTerm extends SearchTerm {
    private final MailFilter filter;

    public MailSearchTerm(MailFilter mailFilter) {
        this.filter = mailFilter;
    }

    @Override
    public boolean match(Message message) {
        return filter.test(message);
    }
}
