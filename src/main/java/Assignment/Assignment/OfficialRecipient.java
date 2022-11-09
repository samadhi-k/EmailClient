package Assignment.Assignment;

class OfficialRecipient extends Recipient{
	String designation;
	
	
	OfficialRecipient(String details) {
		this.name = details.split(",")[0].strip();
		this.emailAddress = details.split(",")[1].strip();
		this.designation = details.split(",")[2].strip();
	}

}
