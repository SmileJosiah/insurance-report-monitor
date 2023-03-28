package com.paopaolee.monitor.service.impl;

import com.paopaolee.monitor.bean.MailSearchTerm;
import com.paopaolee.monitor.bean.ReportScreenshotWrapper;
import com.paopaolee.monitor.config.MailConnectionConfig;
import com.paopaolee.monitor.service.ReportMailReaderService;
import com.paopaolee.monitor.util.ReportMailReader;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;

import static com.paopaolee.monitor.common.Constants.MAIL_SCREENSHOT_MATCH_PATTERN;


/**
 * @author paopaolee
 */
@Service
public class ReportMailReaderServiceImpl implements ReportMailReaderService {

    private ReportMailReader reportMailReader;

    public ReportMailReaderServiceImpl(MailConnectionConfig mailConnectionConfig) {
        reportMailReader = new ReportMailReader(mailConnectionConfig);
    }

    public MimeBodyPart getReportMailMimeBodyPart(MailSearchTerm searchTerm) throws MessagingException, IOException {
        List<Message> messageList = reportMailReader.getDefaultFolderMailList(searchTerm);
        if (messageList.isEmpty()) {
            throw new MessagingException("no search to any mail!");
        }
        Message message = messageList.get(0);
        String mailCompleteTextContent = ReportMailReader.getMailCompleteTextContent(message);
        Matcher matcher = MAIL_SCREENSHOT_MATCH_PATTERN.matcher(mailCompleteTextContent);
        if (!matcher.find()) {
            throw new MessagingException("not found screenshot resource in message!");
        }
        String cid = matcher.group("cid");
        return ReportMailReader.getMessageMimeBodyPartByCid(message, cid);
    }

    @Override
    public ReportScreenshotWrapper getMailScreenshotHandler(MailSearchTerm searchTerm) throws MessagingException, IOException {
        List<Message> messageList = reportMailReader.getDefaultFolderMailList(searchTerm);
        if (messageList.isEmpty()) {
            throw new MessagingException("no search to any mail!");
        }
        Message message = messageList.get(0);
        String mailCompleteTextContent = ReportMailReader.getMailCompleteTextContent(message);
        Matcher matcher = MAIL_SCREENSHOT_MATCH_PATTERN.matcher(mailCompleteTextContent);
        if (!matcher.find()) {
            throw new MessagingException("not found screenshot resource in message!");
        }
        String cid = matcher.group("cid");
        ReportMailReader.MailContentResource mailContentResource = ReportMailReader.getMailContentResource(message, cid);
        reportMailReader.silentlyClose();
        return ReportScreenshotWrapper.from(mailContentResource);
    }

}
