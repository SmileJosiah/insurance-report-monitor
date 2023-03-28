package com.paopaolee.monitor.service;

import com.paopaolee.monitor.bean.MailSearchTerm;
import com.paopaolee.monitor.bean.ReportScreenshotWrapper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;

/**
 * @author paopaolee
 */
public interface ReportMailReaderService {
    /**
     * 获取邮件
     *
     * @param searchTerm MailSearchTerm
     * @return
     * @throws MessagingException
     * @throws IOException
     */
    ReportScreenshotWrapper getMailScreenshotHandler(MailSearchTerm searchTerm) throws MessagingException, IOException;

    MimeBodyPart getReportMailMimeBodyPart(MailSearchTerm searchTerm) throws MessagingException, IOException;
}
