package Assignment.Assignment;

import javax.mail.Message;
import javax.mail.MessagingException;

class EmailStatRecoder extends Observer{

	// overridden method to print a message on console whenever a message is recieved
	@Override
	void update(Message mail) throws MessagingException {
		System.out.println("AN EMAIL IS RECIEVED AT " + mail.getReceivedDate().toString().substring(11, 19));
		
	}

	
}
