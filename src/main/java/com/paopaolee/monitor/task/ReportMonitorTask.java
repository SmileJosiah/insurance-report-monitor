package com.paopaolee.monitor.task;

import com.paopaolee.monitor.bean.MailSearchTerm;
import com.paopaolee.monitor.bean.ReportDataStatistic;
import com.paopaolee.monitor.bean.ReportScreenshotWrapper;
import com.paopaolee.monitor.common.ReportScreenshotValidator;
import com.paopaolee.monitor.service.ReportMailReaderService;
import com.paopaolee.monitor.service.ReportStatisticService;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;

/**
 * @author paopaolee
 */
public class ReportMonitorTask {
    @Resource
    private ReportStatisticService reportStatisticService;
    @Resource
    private ReportMailReaderService reportMailReaderService;
    @Resource
    private MailSearchTerm mailSearchTerm;
    @Resource
    ReportScreenshotValidator screenshotValidator;

    private int[] getRealReportData() {
        ReportDataStatistic gexianStatistic = reportStatisticService.getGexianStatistic();
        ReportDataStatistic tuanyixianStatistic = reportStatisticService.getTuanyixianStatistic();
        ReportDataStatistic baoyuntongStatistic = reportStatisticService.getBaoyuntongStatistic();
        int realSettlementPriceTotal = gexianStatistic.getRealSettlementPrice()
                + tuanyixianStatistic.getRealSettlementPrice()
                + baoyuntongStatistic.getRealSettlementPrice();

        int realEcoPremiumTotal = gexianStatistic.getEcoPremium()
                + tuanyixianStatistic.getEcoPremium()
                + baoyuntongStatistic.getEcoPremium();
        return new int[]{realSettlementPriceTotal, realEcoPremiumTotal};
    }

//    public void monitor() {
//        try {
//            Message message = reportMailReaderService.getReportMailMessage(mailSearchTerm);
//            ReportScreenshotWrapper reportScreenshotWrapper = reportMailReaderService.getReportImageHandler(message);
//            boolean isValid = screenshotValidator.validate(reportScreenshotWrapper);
//            if (isValid) {
//
//            }
//
//        } catch (MessagingException | IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }




//    private boolean validateReportScreenshot(BufferedImage bufferedImage) {
//        if (bufferedImage != null) {
//            bufferedImage
//        }
//        return false;
//    }

//    public void monitor() {
//
//    }
}
