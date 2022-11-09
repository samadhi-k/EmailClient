package Assignment.Assignment;

import javax.mail.MessagingException;

class PersonalRecipient extends Recipient implements Wishable {
	String nickname;
	
	PersonalRecipient(String details) {
		this.name = details.split(",")[0].strip();
		this.nickname = details.split(",")[1].strip();
		this.emailAddress = details.split(",")[2].strip();
		this.birthday = details.split(",")[3].strip();
	}

	@Override
	// method to send bithday wishes
	public void birthdayWish() throws MessagingException {
		SendMail sendmail =  new SendMail();
		sendmail.sendMail(this.emailAddress, "Birthday wish", "Hugs and Love on your Birthday \n - Samadhi-");
		
	}

}
