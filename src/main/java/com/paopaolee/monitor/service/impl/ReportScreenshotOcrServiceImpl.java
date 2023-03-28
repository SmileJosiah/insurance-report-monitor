package com.paopaolee.monitor.service.impl;

import com.paopaolee.monitor.service.ReportScreenshotOcrService;
import com.paopaolee.monitor.util.Tess4jUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author paopaolee
 */
public class ReportScreenshotOcrServiceImpl implements ReportScreenshotOcrService {
    @Override
    public String doOCR(BufferedImage bufferedImage, Rectangle rectangle) {
        return Tess4jUtil.take(bufferedImage, rectangle);
    }
}
