import java.util.Properties;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author kadan
 */
public class EmailSender {
    public static boolean sendMail(String from, String password, String itemName, String message, String to[]){
        String host = "smtp.gmail.com";
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.ssl.trust",host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.auth", true);

        Session session = Session.getDefaultInstance(props, null);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(from));
            //now get the Address of the Receipients
            InternetAddress[] toAddress = new InternetAddress[to.length];
            for(int i = 0; i<to.length; i++ ){
                toAddress[i] = new InternetAddress(to[i]);
            }
            //now Add all the addresses
            for(int i = 0; i<to.length; i++ ){
                mimeMessage.addRecipient(RecipientType.TO, toAddress[i]);
            }
            //add Subject
            mimeMessage.setSubject("New Buyer for your " + itemName);
//            set to
            mimeMessage.setText(message);
            try (Transport transport = session.getTransport("smtp")) {
                transport.connect(host, from, password);
                transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            }
            return true;

        } catch (MessagingException ex) {
            System.err.println("Error : " + ex);
        }
        return false;
    }
}
