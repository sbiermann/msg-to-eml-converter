import jakarta.mail.internet.MimeMessage;
import java.io.File;

public class ConverterApp {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Verwendung: java -jar msg-to-eml-converter.jar <input.msg> <output.eml>");
            System.exit(1);
        }

        File msgFile = new File(args[0]);
        File emlFile = new File(args[1]);

        MimeMessage mimeMessage = MsgToMimeWithAttachments.convertMsgToMimeMessage(msgFile);
        MsgToMimeWithAttachments.saveMimeMessageToFile(mimeMessage, emlFile);

        System.out.println("Konvertierung erfolgreich: " + emlFile.getAbsolutePath());
    }
}
