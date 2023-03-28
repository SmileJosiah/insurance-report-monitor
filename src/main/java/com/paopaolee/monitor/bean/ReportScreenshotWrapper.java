package com.paopaolee.monitor.bean;

import com.paopaolee.monitor.util.DateTimeUtil;
import com.paopaolee.monitor.util.ReportMailReader;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Date;
import java.util.Iterator;

/**
 * @author paopaolee
 */
public class ReportScreenshotWrapper {
    final private String name;
    final private long size;
    final private int width;
    final private int height;
    final private BufferedImage bufferedImage;

    private ReportScreenshotWrapper(String name, long size, int width, int height, BufferedImage bufferedImage) {
        this.name = name;
        this.size = size;
        this.width = width;
        this.height = height;
        this.bufferedImage = bufferedImage;
    }

    public static ReportScreenshotWrapper from(ReportMailReader.MailContentResource mailContentResource) {
        if (mailContentResource == null) {
            return null;
        }
        long size = 0L;
        int width = 0;
        int height = 0;
        String name = mailContentResource.getName();
        BufferedImage bufferedImage = null;
        ImageInputStream iis = null;
        ImageReader reader = null;
        try {
            File file = new File(mailContentResource.getPathname());
            size = file.length();
            bufferedImage = ImageIO.read(file);
//            iis = ImageIO.createImageInputStream(file);
//            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
//            if (readers.hasNext()) {
//                reader = readers.next();
//                reader.setInput(iis);
//                bufferedImage = reader.read(0);
//            }
        } catch (IOException e3) {

        } finally {
            if (reader != null) {
                reader.dispose();
            }
            try {
                if (iis != null) {
                    iis.close();
                }
            } catch (IOException e4) {
            }
        }
        if (bufferedImage != null) {
            width = bufferedImage.getWidth();
            height = bufferedImage.getHeight();
        }
        return  new ReportScreenshotWrapper(name, size, width, height, bufferedImage);
    }



//    public static ReportScreenshotWrapper from(ReportMailReader.MailContentResource mailContentResource, boolean syncToLocal) {
//        if (mailContentResource == null) {
//            return null;
//        }
//        long size = mailContentResource.getSize();
//        Date createAt = mailContentResource.getReceiveAt();
//        String fileName = mailContentResource.getName().toLowerCase();
//        InputStream resourceInputStream = mailContentResource.getResourceInputStream();
//        String format = fileName.substring(fileName.lastIndexOf("."));
//        BufferedImage bufferedImage = null;
//        File file = null;
//        int width = 0;
//        int height = 0;
//        if (syncToLocal) {
//            OutputStream out = null;
//            fileName = DateTimeUtil.formattedDateTime(createAt, "yyyyMMddHHmmss") + format;
//            try {
//                ClassPathResource resource = new ClassPathResource("/screenshots");
//                String absolutePath = resource.getFile().getAbsolutePath();
//                file = new File(absolutePath, fileName);
//                out = Files.newOutputStream(file.toPath());
//                byte[] buffer = new byte[16384];
//                int bytesRead;
//                while ((bytesRead = resourceInputStream.read(buffer)) != -1) {
//                    out.write(buffer, 0, bytesRead);
//                }
//                size = file.length();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } finally {
//                try {
//                    if (resourceInputStream != null) {
//                        resourceInputStream.close();
//                    }
//                } catch (IOException e1) {
//                }
//                try {
//                    if (out != null) {
//                        out.close();
//                    }
//                } catch (IOException e2) {
//                }
//            }
//        }
//        ImageInputStream iis = null;
//        ImageReader reader = null;
//        try {
//            iis = ImageIO.createImageInputStream(file);
//            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
//            if (readers.hasNext()) {
//                reader = readers.next();
//                reader.setInput(iis);
//                bufferedImage = reader.read(0);
//            }
//        } catch (IOException e3) {
//
//        } finally {
//            if (reader != null) {
//                reader.dispose();
//            }
//            try {
//                if (iis != null) {
//                    iis.close();
//                }
//            } catch (IOException e4) {
//            }
//        }
//        if (bufferedImage != null) {
//            width = bufferedImage.getWidth();
//            height = bufferedImage.getHeight();
//        }
//        return  new ReportScreenshotWrapper(fileName, size, width, height, format, createAt, bufferedImage);
//    }

    public long getSize() {
        return size;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getFileName() {
        return name;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    @Override
    public String toString() {
        return "ReportScreenshotWrapper{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", width=" + width +
                ", height=" + height +
                ", bufferedImage=" + bufferedImage +
                '}';
    }
}
