package com.paopaolee.monitor.service;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author paopaolee
 */
public interface ReportScreenshotOcrService {

    /**
     * ocr识别图片区域内容
     * @param bufferedImage
     * @param rectangle
     * @return string
     */
    String doOCR(BufferedImage bufferedImage, Rectangle rectangle);
}
