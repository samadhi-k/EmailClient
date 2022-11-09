package Assignment.Assignment;

import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

class RecieveMail  extends Thread{
	
	Observer[] observers; 
	Boolean appRunning;
	Boolean emailRecieved;
	MyBlockingQueue queue;
	
	
	void setAppRunning(Boolean appRunning) {
		this.appRunning = appRunning;
	}

	RecieveMail(Boolean appRunning , MyBlockingQueue queue ){
		this.queue = queue;
		this.appRunning = appRunning;
		observers  = new Observer[2];
		observers[0] = new EmailStatRecoder();
		observers[1] = new EmailStatPrinter();
		
	}
	
	public void run() {
		
		try {
		//set properties
		Properties properties = System.getProperties();
		properties.setProperty("mail.store.protocols","imaps");
		
		final String myEmailAccount = "divyanjaleekes.190159d@gmail.com";
		final String password = "peterbishop#1";
		
		// create a session and connect to the server
			Session session = Session.getDefaultInstance(properties,null);
			Store store = session.getStore("imaps");
			store.connect("imap.gmail.com", myEmailAccount, password);
			
			// search term to search for unread messages
			FlagTerm searchTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
			
			
			while (appRunning) {
				//open the inbox
				Folder emailFolder = store.getFolder("INBOX");
				// open folder in the read write mode because the flags are being changed by the application
				emailFolder.open(Folder.READ_WRITE);

				//retrieve only unread messages
				Message[] message = emailFolder.search(searchTerm);
				
				//traverse through the list of received messages
				for (int i = 0, n = message.length; i < n; i++) {
					Message msg = message[i];
					//store each message object on the queue
					queue.enqueue(msg);
					//notify the observers
					notifyObservers(msg);
					//raise the SEEN flag of the messages read
					msg.setFlag(Flags.Flag.SEEN,true);
		      }
				emailFolder.close(true);
			}
			
		    store.close();
			
		} catch (NoSuchProviderException e) {
			System.out.println("SERVER ERROR");
		} catch (MessagingException e) {
			System.out.println();
		} 
		
		
	}
	
	void notifyObservers(Message msg) throws MessagingException {
		for ( Observer observer : observers )
			observer.update( msg);
	}

}
