package Assignment.Assignment;
	import java.io.Serializable;
	import java.util.Properties;
	import javax.mail.*;
	import javax.mail.internet.InternetAddress;
	import javax.mail.internet.MimeMessage;
	import com.sun.mail.util.MailConnectException;
	import javax.mail.MessagingException;

	class SendMail  implements Serializable{
		
		private static final long serialVersionUID = 1L;
		String day;
		String emailAddress;
		String subject;
		String content;
		
		// method to send mails
		void sendMail(String recepient , String subject , String content ) throws MessagingException {
			 
		Properties properties = new Properties();
		
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		final String myEmailAccount = "username@gmail.com";
		final String password = "password";
		
		try {
			//login
			Authenticator auth = new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(myEmailAccount, password);
				}
			};
			Session session = Session.getInstance(properties, auth);
			Message message = prapareMessage(session, myEmailAccount, recepient, subject, content);
			Transport.send(message);
			System.out.println("EMAIL SENT");
		} catch (MailConnectException e) {
			 System.out.println("CHECK YOUR NETWORK CONNECTION");
		}
	}

	private static Message prapareMessage(Session session, String myEmailAccount , String recipient , String subject , String content) {
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(myEmailAccount));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject(subject);
			message.setText(content);
			return message;
		} catch (MessagingException e) {
			System.out.println("INVALID ADDRESS");
		}
		
		return null;
	}

	}
