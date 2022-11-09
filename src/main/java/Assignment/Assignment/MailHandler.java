package Assignment.Assignment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

class MailHandler {
	 
	// method to serialize the Array list of SendMail objects
	static void serialize(ArrayList<SendMail> sentMail) {
		try {
			FileOutputStream outputStream = new FileOutputStream( "sentmail.ser" );
			ObjectOutputStream obj = new ObjectOutputStream ( outputStream);
			obj.writeObject(sentMail);
			obj.close();
			outputStream.close();
		} catch (IOException e) {
			System.out.println("INVALID OPERATION");
		}
	}
	
	@SuppressWarnings("unchecked")
	// method to deserialize the Arraylist of mail objects
	static ArrayList<SendMail> deserialize () throws FileNotFoundException {
		ArrayList<SendMail> sentMail ;
		try {
			File file = new File("sentmail.ser");
			if ( file.exists()) {
				FileInputStream inputStream = new FileInputStream(file);
				if (inputStream.available() > 0) {
					ObjectInputStream obj = new ObjectInputStream( inputStream);
					sentMail = (ArrayList<SendMail>) obj.readObject();
					obj.close();
				}
				else {
					inputStream.close();
					sentMail = new ArrayList<>();
				}
			}
			else {
				file.createNewFile();
				sentMail = new ArrayList<>();
			}
		} catch (IOException | ClassNotFoundException e) {
			sentMail = new ArrayList<>();
		}
		return sentMail;
		}
	}
	

