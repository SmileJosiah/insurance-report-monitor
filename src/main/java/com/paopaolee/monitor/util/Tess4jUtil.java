package com.paopaolee.monitor.util;

import com.paopaolee.monitor.common.Constants;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author paopaolee
 */
public class Tess4jUtil {
    public static String take(BufferedImage bufferedImage, Rectangle rect) {
        Tesseract tesseract = new Tesseract();
        File tessResources = LoadLibs.extractTessResources("tessdata");
        tesseract.setDatapath(tessResources.getAbsolutePath());
        tesseract.setLanguage(Constants.ENG);
        return getOCRText(tesseract, bufferedImage, rect);
    }

    private static String getOCRText(Tesseract tesseract, BufferedImage bufferedImage, Rectangle rect) {
        String result = null;
        try {
            result = tesseract.doOCR(bufferedImage, rect);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return result;
    }
}
