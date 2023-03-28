package com.paopaolee.monitor.util;

import com.paopaolee.monitor.bean.MailSearchTerm;
import com.paopaolee.monitor.config.MailConnectionConfig;
import org.springframework.core.io.ClassPathResource;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.paopaolee.monitor.common.Constants.MAIL_READER_DEFAULT_FOLDER;

/**
 * @author paopaolee
 */
public class ReportMailReader {
    final static String MIMETYPE_TEXT_FLAG = "text/*";
    final static String MIMETYPE_MULTIPART_FLAG = "multipart/*";
    final static String MIMETYPE_MESSAGE_RFC822_FLAG = "message/rfc822";
    private final MailConnectionConfig mailConnectionConfig;
    private final Session session;
    private Store storeInstance;
    private Folder folderInstance;
    public ReportMailReader(MailConnectionConfig mailConnectionConfig) {
        this.mailConnectionConfig = mailConnectionConfig;
        this.session = Session.getInstance(mailConnectionConfig.loadProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailConnectionConfig.getUsername(), mailConnectionConfig.getPassword());
            }
        });
    }

    private void openConnection() throws MessagingException {
        if (storeInstance != null && storeInstance.isConnected()) {
            return;
        }
        storeInstance = session.getStore(mailConnectionConfig.getProtocol());
        storeInstance.connect(
                mailConnectionConfig.getHost(),
                mailConnectionConfig.getPort(),
                mailConnectionConfig.getUsername(),
                mailConnectionConfig.getPassword()
        );
    }

    public void silentlyClose() {
        if (folderInstance != null) {
            try {
                folderInstance.close(true);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }

        if (storeInstance != null) {
            try {
                storeInstance.close();
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void close() throws MessagingException {
        if (folderInstance != null) {
            folderInstance.close(true);
        }

        if (storeInstance != null) {
            storeInstance.close();
        }
    }

    private Folder getFolder(String folder) throws MessagingException {
        if (storeInstance == null || !storeInstance.isConnected() ) {
            openConnection();
        }
        if (folderInstance == null) {
            return storeInstance.getFolder(folder);
        } else if (folderInstance.getFullName().contains(folder)) {
            folderInstance.close(true);
            return storeInstance.getFolder(folder);
        } else {
            return folderInstance;
        }
    }

    private Message[] getFolderMails(String folder, MailSearchTerm searchTerm) throws MessagingException {
        folderInstance = getFolder(folder);
        folderInstance.open(Folder.READ_ONLY);
        return folderInstance.search(searchTerm);
    }

    public List<Message> getDefaultFolderMailList(MailSearchTerm searchTerm) throws MessagingException {
        return getFolderMailList(MAIL_READER_DEFAULT_FOLDER, searchTerm);
    }

    public List<Message> getFolderMailList(String folder, MailSearchTerm searchTerm) throws MessagingException {
        List<Message> messages = Arrays.asList(getFolderMails(folder, searchTerm));
        Collections.reverse(messages);
        return messages;
    }

    public static String getFrom(Message msg) throws MessagingException {
        String from = "";
        Address[] addresses = msg.getFrom();
        if(addresses.length < 1) {
            throw new MessagingException("Failed to mail from address!");
        }
        InternetAddress address = (InternetAddress) addresses[0];
        from = address.getAddress();
        return from;
    }

    private static void getMailContent(Part part, StringBuilder sb) throws MessagingException, IOException {
        boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
        if (part.isMimeType(MIMETYPE_TEXT_FLAG) && !isContainTextAttach) {
            sb.append(part.getContent().toString());
        } else if (part.isMimeType(MIMETYPE_MESSAGE_RFC822_FLAG)) {
            getMailContent((Part) part.getContent(), sb);
        } else if (part.isMimeType(MIMETYPE_MULTIPART_FLAG)) {
            Multipart multipart = (Multipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                getMailContent(bodyPart, sb);
            }
        }
    }

    public static String getMailCompleteTextContent(Message message) throws MessagingException, IOException {
        StringBuilder stringBuilder = new StringBuilder(200);
        getMailContent(message, stringBuilder);
        return stringBuilder.toString();
    }

    public static String getContentID(String mailCompleteTextContent, Pattern pattern) throws MessagingException {
        Matcher matcher = pattern.matcher(mailCompleteTextContent);
        if (!matcher.find()) {
            throw new MessagingException("not matched anything fro pattern!");
        }
        return matcher.group("cid");
    }

    /**
     *
     * @param message
     * @param cid
     * @return
     * @throws MessagingException
     * @throws IOException
     * 可以加个缓存
     */
    public static MimeBodyPart getMessageMimeBodyPartByCid(Message message, String cid) throws MessagingException, IOException {
        MimeBodyPart mimeBodyPart = null;
        Multipart multipart = (Multipart) message.getContent();
        int multipartCount = multipart.getCount();
        for (int i = 0; i < multipartCount; i++) {
            MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(i);
            String disposition = bodyPart.getDisposition();
            String id = bodyPart.getContentID();
            if (disposition != null && cid.equalsIgnoreCase(id)) {
                mimeBodyPart = bodyPart;
                break;
            }
        }
        return mimeBodyPart;
    }

    private static void saveMimeBodyPartResource(MimeBodyPart mimeBodyPart, File file) throws MessagingException {
        try (
                InputStream inputStream = mimeBodyPart.getDataHandler().getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ReadableByteChannel channel = Channels.newChannel(inputStream);
                FileChannel outputChannel = fileOutputStream.getChannel()
        )
        {
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 3);
            while (channel.read(buffer) != -1) {
                buffer.flip();
                outputChannel.write(buffer);
                buffer.compact();
            }
            buffer.flip();
            while (buffer.hasRemaining()) {
                outputChannel.write(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MailContentResource getMailContentResource(Message message, String cid) throws MessagingException, IOException {
        MimeBodyPart mimeBodyPart = getMessageMimeBodyPartByCid(message, cid);
        if (mimeBodyPart == null) {
            throw new MessagingException("not found Part of message for specified cid!");
        }
        // 保存内容到本地
        String name = mimeBodyPart.getFileName();
        Date receivedDate = message.getReceivedDate();
        Date sentDate = message.getSentDate();
        String format = name.substring(name.lastIndexOf("."));
        String fileName = DateTimeUtil.formattedDateTime(receivedDate, "yyyyMMddHHmmss") + format;
        ClassPathResource resource = new ClassPathResource("/screenshots");
        String absolutePath = resource.getFile().getAbsolutePath();
        File file = new File(absolutePath, fileName);
        saveMimeBodyPartResource(mimeBodyPart, file);


//        return MailContentResource.from(message, mimeBodyPart);
        return new MailContentResource(file.getAbsolutePath(), fileName, format, receivedDate, sentDate);
    }


    public static class MailContentResource {
        private final String pathname;
        private final String name;
        private final String format;
        private final Date receiveAt;
        private final Date sendAt;
        public MailContentResource(String pathname, String name, String format, Date receiveAt, Date sendAt) {
            this.pathname = pathname;
            this.name = name;
            this.format = format;
            this.receiveAt = receiveAt;
            this.sendAt = sendAt;
        }
        public String getPathname() {
            return pathname;
        }
        public String getFormat() {
            return format;
        }

        public String getName() {
            return name;
        }

        public Date getReceiveAt() {
            return receiveAt;
        }

        public Date getSendAt() {
            return sendAt;
        }

    }

//    public static class MailContentResource {
//        private String name;
//        private long size;
//        private Date receiveAt;
//        private Date sendAt;
//        private InputStream contentResourceStream;
//        private MailContentResource(String name, long size, Date receivedAt, Date sendAt, InputStream inputStream) {
//            this.name = name;
//            this.size = size;
//            this.receiveAt = receivedAt;
//            this.sendAt = sendAt;
//            this.contentResourceStream = inputStream;
//        }
//        public static MailContentResource from(Message message, MimeBodyPart mimeBodyPart) throws MessagingException, IOException {
//           if (message == null || mimeBodyPart == null) {
//               return null;
//           }
//            String fileName = mimeBodyPart.getFileName();
//            int size = mimeBodyPart.getSize();
//            Date receivedAt = message.getReceivedDate();
//            Date sendAt = message.getSentDate();
//            InputStream inputStream = mimeBodyPart.getInputStream();
//            return new MailContentResource(fileName, size, receivedAt, sendAt, inputStream);
//        }
//        public String getName() {
//            return name;
//        }
//        public long getSize() {
//            return size;
//        }
//        public Date getSendAt() {
//            return sendAt;
//        }
//        public Date getReceiveAt() {
//            return receiveAt;
//        }
//        public InputStream getResourceInputStream() {
//            return contentResourceStream;
//        }
//    }
}
