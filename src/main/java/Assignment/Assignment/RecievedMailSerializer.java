package Assignment.Assignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.mail.Message;
import javax.mail.MessagingException;

class RecievedMailSerializer extends Thread{
	MyBlockingQueue queue;
	Boolean appRunning;
	
	 void setAppRunning(Boolean appRunning) {
		this.appRunning = appRunning;
	}
	 
	RecievedMailSerializer(MyBlockingQueue queue ,Boolean appRunning) {
		this.appRunning = appRunning;
		this.queue = queue;
	}
	
	//run method for the thread
	 public void run() {
		 while (appRunning) {
			
			try {
				
			 Message msg = queue.dequeue(appRunning);
			 
			 if ( msg != null) {
				// create Email object for each received email
				 String from = msg.getFrom()[0].toString();
				 String subject = msg.getSubject().toString();
				 String content = msg.getContent().toString();
				 String recievedDate = msg.getReceivedDate().toString().substring(11, 19);
				 Email email = new Email(recievedDate, from, subject, content );
				 serialize(email);
			 }
			 
			} catch (MessagingException | IOException e) {
				System.out.println();	
			}
				
			 
			 
	 } 
		 
	 }
	private void serialize(Email mail) {
		try {
			File file = new File("recievedmails.ser");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream outputStream = new FileOutputStream(file ,true);
			ObjectOutputStream obj = new ObjectOutputStream ( outputStream);
			obj.writeObject(mail);
			obj.close();
			outputStream.close();	
			
		
		}
		catch( IOException e) {
			System.out.println();
		}
	}
}
