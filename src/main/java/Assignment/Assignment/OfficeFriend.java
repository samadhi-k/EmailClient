package Assignment.Assignment;

import javax.mail.MessagingException;

public class OfficeFriend extends Recipient implements Wishable{
	String designation;
	
	OfficeFriend(String details) {
		this.name = details.split(",")[0].strip();
		this.emailAddress = details.split(",")[1].strip();
		this.designation = details.split(",")[2].strip();
		this.birthday = details.split(",")[3].strip();
	}

	@Override
	// method to send birthday wishes
	public void birthdayWish() throws MessagingException {
		SendMail sendmail =  new SendMail();
		sendmail.sendMail(this.emailAddress, "Birthday wish", "Wish you a Happy Birthday \n - Samadhi-");
		
	}

}
