package com.paopaolee.monitor.common;

import java.util.regex.Pattern;

/**
 * @author paopaolee
 */
public interface Constants {
    String ENG = "eng";
    String CHI_SIM = "chi_sim";

    String MAIL_READER_DEFAULT_FOLDER = "INBOX";

    Pattern MAIL_SCREENSHOT_MATCH_PATTERN = Pattern.compile("<img src='cid:(?<cid>.+)'>$");

}
