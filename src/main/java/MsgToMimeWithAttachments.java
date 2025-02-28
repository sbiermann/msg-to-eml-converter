import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import org.simplejavamail.outlookmessageparser.OutlookMessageParser;
import org.simplejavamail.outlookmessageparser.model.OutlookAttachment;
import org.simplejavamail.outlookmessageparser.model.OutlookFileAttachment;
import org.simplejavamail.outlookmessageparser.model.OutlookMessage;

public class MsgToMimeWithAttachments {

    public static MimeMessage convertMsgToMimeMessage(File msgFile) throws Exception {

        OutlookMessageParser msgp = new OutlookMessageParser();
        OutlookMessage outlookMessage = msgp.parseMsg(msgFile);

        Session session = Session.getInstance(new Properties());
        MimeMessage mimeMessage = new MimeMessage(session);

        Map<String, Collection<String>> headers = outlookMessage.getHeadersMap();
        for (Map.Entry<String, Collection<String>> entry : headers.entrySet()) {
            for (String headerValue : entry.getValue()) {
                mimeMessage.addHeader(entry.getKey(), headerValue);
            }
        }

        mimeMessage.setSubject(outlookMessage.getSubject());
        mimeMessage.setSentDate(outlookMessage.getDate());

        MimeMultipart multipart = new MimeMultipart();
        MimeBodyPart bodyPart = new MimeBodyPart();
        String htmlBody = outlookMessage.getBodyHTML();
        if (htmlBody != null && !htmlBody.isEmpty()) {
            bodyPart.setContent(htmlBody, "text/html; charset=UTF-8");
        } else {
            bodyPart.setText(outlookMessage.getBodyText(), "UTF-8");
        }
        multipart.addBodyPart(bodyPart);

        List<OutlookAttachment> attachments = outlookMessage.getOutlookAttachments();
        for (OutlookAttachment attach : attachments) {
            if (attach instanceof OutlookFileAttachment) {
                OutlookFileAttachment fileAttachment = (OutlookFileAttachment) attach;
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.setFileName(fileAttachment.getFilename());
                attachmentPart.setDataHandler(new DataHandler(new OutlookAttachmentDataSource(fileAttachment)));
                multipart.addBodyPart(attachmentPart);
            }
        }
        mimeMessage.setContent(multipart);
        return mimeMessage;
    }

    public static void saveMimeMessageToFile(MimeMessage mimeMessage, File outputFile) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            mimeMessage.writeTo(fos);
        }
    }

    static class OutlookAttachmentDataSource implements DataSource {
        private final OutlookFileAttachment attachment;

        OutlookAttachmentDataSource(OutlookFileAttachment attachment) {
            this.attachment = attachment;
        }

        public InputStream getInputStream() {
            return new ByteArrayInputStream(attachment.getData());
        }

        public OutputStream getOutputStream() {
            throw new UnsupportedOperationException("Not supported");
        }

        public String getContentType() {
            return attachment.getMimeTag() != null ? attachment.getMimeTag() : "application/octet-stream";
        }

        public String getName() {
            return attachment.getFilename();
        }
    }
}
