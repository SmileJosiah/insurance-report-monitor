package com.paopaolee.monitor.config;

import com.paopaolee.monitor.bean.MailSearchTerm;
import com.paopaolee.monitor.util.DateTimeUtil;
import com.paopaolee.monitor.util.ReportMailReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.MessagingException;
import javax.mail.search.SearchTerm;
import java.util.Date;

/**
 * @author paopaolee
 */
@Configuration
public class MailSearchConfig {

    @Bean
    public SearchTerm mailSearchTerm(
            @Value("${monitor.mail-received-start}") String startTime,
            @Value("${monitor.mail-received-end}") String endTime,
            @Value("${monitor.mail-keyword}") String keyword,
            @Value("${monitor.mail-sender}") String sender
    ) {
        Date[] dateRange = DateTimeUtil.getDateRange(startTime, endTime);
        final Date start = dateRange[0];
        final Date end = dateRange[1];
        return new MailSearchTerm(message -> {
               try {
                   String subject = message.getSubject();
                   if (!subject.contains(keyword)) {
                       return  false;
                   };
                   String from = ReportMailReader.getFrom(message);
                   if (!from.contains(sender)) {
                       return  false;
                   };
                   Date receivedDate = message.getReceivedDate();
                   return receivedDate.after(start) && receivedDate.before(end);
               } catch (MessagingException e) {
                   return false;
               }
       });
    }
}
