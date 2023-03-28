package com.paopaolee.monitor.config;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Properties;

/**
 * @author paopaolee
 */
@Primary
@Configuration
public class MailConnectionConfig extends MailProperties {
    public Properties loadProperties() {
        Properties props = new Properties();
        props.put("mail.imap.host", super.getHost());
        props.put("mail.imap.port", super.getPort());
        props.put("mail.imap.auth", "true");
        props.setProperty("mail.store.protocol", super.getProtocol());
        props.put("mail.imap.ssl.enable", "true");
        props.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imap.socketFactory.fallback","false");
        props.setProperty("mail.imap.socketFactory.port", super.getPort().toString());
        return props;
    }
}
