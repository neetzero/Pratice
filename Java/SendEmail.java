/**
 * 參考資料
 * http://www.journaldev.com/2532/javamail-example-send-mail-in-java-smtp
 * http://www.rgagnon.com/javadetails/java-0321.html
 * https://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
 * https://examples.javacodegeeks.com/enterprise-java/mail/send-file-via-mail-using-filedatasource-example/
 */

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
public class SendEmail {

	private static String toMail = "";
	private static String fromMail = "";
	private static String bccMail = "";
	private static String ccMail = "";
	private static String replyToMail = "";
	private static String file = "";

    private static Properties TlsProperties(){
        Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
        return props;
    }

    private static Properties SslProperties(){
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.port", "465");
        return props;
    }

	/* 其他SMTP Server設定 */
	private static Properties SimpleProperties() {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", "mail.server");
		props.setProperty("mail.user", "emailuser");
		props.setProperty("mail.password", "");
		return props;
	}
	private static Session getSession(Properties props) {
        return Session.getDefaultInstance(props);
    }

    private static Session getSession(Properties props, String username, String password) {
        return Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
        });
    }

	public static void main(String[] args) {

        String username = "gmail_user";
		String password = "gmail_password";
		
		//設定Host Server Properties
		Properties tslprops = TlsProperties();
		Properties sslprops = SslProperties();
		//取Session
		//Session session = Session.getDefaultInstance(auerprop);
		Session session = getSession(sslprops, username, password); //Google帳號密碼驗證
	}

	public void sendSimpleMail(Session session) {
		String toMail = "test1@gmail.com test2@gmail.com";
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			//msg.addHeader("format", "flowed");
			msg.setFrom(new InternetAddress(fromMail, "寄件人名稱"));
			msg.setReplyTo(InternetAddress.parse(replyToMail, false));
			msg.setSubject("標題", "UTF-8");
			//msg.setText("純文字信件", "UTF-8"); //純文字
			msg.setContent("<div>測試<p style='color:red;'>測試 p tag</p></div>", "text/html; charset=UTF-8"); //HTML
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail, false));
			msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bccMail, false));
			msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccMail, false));

			Transport.send(msg);
			
			System.out.println("Done");
		} catch(UnsupportedEncodingException e){
			e.printStackTrace();
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public void sendMailAttachment(Session session) throws Exception {
		//session.setDebug(debug); //開啟debug訊息
		
		MimeMessage message = new MimeMessage(session);
		message.setSubject("標題");

		//信件內文
		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setContent("<h1>HTML內文</h1>", "text/html charset=UTF-8");

		//附加檔案
		MimeBodyPart attachFilePart = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(file);//檔案路徑
		//attachFilePart.setDataHandler(new DataHandler(fds));
		attachFilePart.attachFile(file);
		attachFilePart.setFileName(fds.getName());

		MimeMultipart mp = new MimeMultipart();
		mp.addBodyPart(textPart);
		mp.addBodyPart(attachFilePart);

		message.setContent(mp);
		message.setSentDate(new Date());
		message.setFrom(new InternetAddress(fromMail, "寄件人名稱"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail));

		Transport.send(message);
	}

	/**
	 * 內嵌圖片: 將附加檔案的圖片以html連結方式顯示在郵件
	 */
	public static void sendImageMail(Session session){
		try{
	         MimeMessage msg = new MimeMessage(session);
	         msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		     msg.addHeader("format", "flowed");
		     msg.addHeader("Content-Transfer-Encoding", "8bit");
		     msg.setFrom(new InternetAddress(fromMail, "寄件人名稱"));
		     msg.setReplyTo(InternetAddress.parse(toMail, false));
		     msg.setSubject("標題", "UTF-8");
		     msg.setSentDate(new Date());
		     msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail, false));
		      
	         MimeBodyPart messageBodyPart = new MimeBodyPart();
			 messageBodyPart.setText("文字");
			 
	         MimeMultipart multipart = new MimeMultipart();
	         multipart.addBodyPart(messageBodyPart);

	         //將圖片加入附件
	         messageBodyPart = new MimeBodyPart();
	         String filename = "image.png";
	         FileDataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName(filename);
	         //加入Content ID
	         messageBodyPart.setHeader("Content-ID", "image_id");
			 multipart.addBodyPart(messageBodyPart);
			 
	         //在mail內顯示
	         messageBodyPart = new MimeBodyPart();
	         messageBodyPart.setContent("<h1>附加圖片</h1>" +
	        		     "<img src='cid:image_id'>", "text/html charset=UTF-8");
	         multipart.addBodyPart(messageBodyPart);
	         
	         msg.setContent(multipart);

	         Transport.send(msg);

	      }catch (MessagingException e) {
	         e.printStackTrace();
	      } catch (UnsupportedEncodingException e) {
			 e.printStackTrace();
		}
	}
}