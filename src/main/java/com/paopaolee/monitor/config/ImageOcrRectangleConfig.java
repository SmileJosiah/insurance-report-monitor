package com.paopaolee.monitor.config;

import com.paopaolee.monitor.bean.OcrRectangle;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author paopaolee
 */
@Configuration
public class ImageOcrRectangleConfig {

    @Bean("premiumCurRectangle")
    @ConfigurationProperties(prefix = "ocr-rectangle.premium.cur")
    public OcrRectangle premiumCurRectangle() {
        return new OcrRectangle();
    }

    @Bean("premiumMonthRectangle")
    @ConfigurationProperties(prefix = "ocr-rectangle.premium.month")
    public OcrRectangle premiumMonthRectangle() {
        return new OcrRectangle();
    }

    @Bean("premiumYearRectangle")
    @ConfigurationProperties(prefix = "ocr-rectangle.premium.year")
    public OcrRectangle premiumYearRectangle() {
        return new OcrRectangle();
    }

    @Bean("ecoCurRectangle")
    @ConfigurationProperties(prefix = "ocr-rectangle.eco.cur")
    public OcrRectangle ecoCurRectangle() {
        return new OcrRectangle();
    }

    @Bean("ecoMonthRectangle")
    @ConfigurationProperties(prefix = "ocr-rectangle.eco.month")
    public OcrRectangle ecoMonthRectangle() {
        return new OcrRectangle();
    }

    @Bean("ecoYearRectangle")
    @ConfigurationProperties(prefix = "ocr-rectangle.eco.year")
    public OcrRectangle ecoYearRectangle() {
        return new OcrRectangle();
    }

}
