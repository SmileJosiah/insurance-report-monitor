
package com.paopaolee.monitor;

import com.paopaolee.monitor.bean.MailSearchTerm;
import com.paopaolee.monitor.bean.ReportScreenshotWrapper;
import com.paopaolee.monitor.service.ReportMailReaderService;
import com.sun.mail.util.BASE64DecoderStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.event.annotation.AfterTestMethod;

import java.io.*;
import java.util.List;
import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import java.awt.*;


@SpringBootTest
class Tess4UtilTest {

    @Qualifier("premiumYearRectangle")
    @Resource
    private Rectangle rectangle;

    @Resource
    private MailSearchTerm mailSearchTerm;

    @Resource
    private ReportMailReaderService reportMailReaderService;


    @Test
    void searchReportMail() throws MessagingException, IOException {
//        ReportScreenshotWrapper mailScreenshotHandler = reportMailReaderService.getMailScreenshotHandler(mailSearchTerm);
//        System.out.println("XXXXXX" + mailScreenshotHandler);
        MimeBodyPart reportMailMimeBodyPart = reportMailReaderService.getReportMailMimeBodyPart(mailSearchTerm);
        BASE64DecoderStream base64DecoderStream = (BASE64DecoderStream) reportMailMimeBodyPart.getContent();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(base64DecoderStream);
//        base64DecoderStrea
        byte[] temp = new byte[1024*100];
        int len = 0;
        int i = 0 ;
        while ((len= bufferedInputStream.read(temp))!=-1){
            i++;
        }
        System.out.println(i);


//        byte[] bytes = new BufferedInputStream(base64DecoderStream)
//        byte[] byteArray = IOUtils.toByteArray(bufferedInputStream);
//        System.out.println(byteArray.length);
//        byte[] encodedBase64 = Base64.encodeBase64(byteArray);
//        String base64String =  new String(encodedBase64, "UTF-8");
//        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(byteArray));
//        BufferedImage image = null;
//        File file = new File(filepath);
//        System.out.println("size: " + file.length());
//        BufferedImage bufferedImage = ImageIO.read(file);
//        System.out.println("height: " + bufferedImage.getHeight());

    }


//    @AfterTestMethod
//    public void close() {
//        reportMailReaderService.releaseResource();
//    }
}