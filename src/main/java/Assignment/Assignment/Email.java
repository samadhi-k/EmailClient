package Assignment.Assignment;

import java.io.Serializable;

//the email class to create objects for each recieved email
class Email implements Serializable{

	String address;
	String subject;
	String content;
	String Date;
	private static final long serialVersionUID = 1L;
	
	
	Email(String Date,String address,String subject,String content){
		this.Date = Date;
		this.address = address;
		this.subject = subject;
		this.content = content;
	}
}
