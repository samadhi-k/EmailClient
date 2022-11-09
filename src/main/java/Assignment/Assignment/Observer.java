package Assignment.Assignment;

import javax.mail.Message;
import javax.mail.MessagingException;

abstract class Observer {
	
	abstract void update(Message msg) throws MessagingException;
}
