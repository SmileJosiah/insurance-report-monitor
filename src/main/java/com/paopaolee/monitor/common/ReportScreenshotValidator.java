package com.paopaolee.monitor.common;

import com.paopaolee.monitor.bean.ReportScreenshotWrapper;

/**
 * @author paopaolee
 */

@FunctionalInterface
public interface ReportScreenshotValidator {
    /**
     * 截图基本信息校验
     * @param reportScreenshotWrapper
     * @return boolean
     */
    boolean validate(ReportScreenshotWrapper reportScreenshotWrapper);
}
