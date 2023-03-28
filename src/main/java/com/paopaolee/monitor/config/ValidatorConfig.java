package com.paopaolee.monitor.config;

import com.paopaolee.monitor.bean.ReportScreenshotWrapper;
import com.paopaolee.monitor.common.ReportDataValidator;
import com.paopaolee.monitor.common.ReportScreenshotValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @author paopaolee
 */
public class ValidatorConfig {
    private final static double UNIT = 1024;

    @Bean
    public ReportScreenshotValidator reportScreenshotValidator(@Value("${monitor.screenshot-min-size}") double minSize, @Value("${monitor.screenshot-width}") int width, @Value("${monitor.screenshot-height}") int height) {
        return (ReportScreenshotWrapper reportScreenshotWrapper) -> {
            if (reportScreenshotWrapper == null) {
                return false;
            }
            long size = reportScreenshotWrapper.getSize();
            return Double.compare(size / UNIT / UNIT, minSize) > 0 && width == reportScreenshotWrapper.getWidth() && height == reportScreenshotWrapper.getSize();
        };
    }

    @Bean
    public ReportDataValidator<Integer> reportDataValidator(@Value("${monitor.report-error-value}") int error) {
        return (Integer realData, Integer reviewData) -> Math.abs(realData - reviewData) <= error;
    }
}
