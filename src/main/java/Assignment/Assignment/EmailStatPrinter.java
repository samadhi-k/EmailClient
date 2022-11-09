package Assignment.Assignment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.mail.Message;
import javax.mail.MessagingException;

class EmailStatPrinter extends Observer{
	
	//overridden method to update the text file 
	@Override
	void update(Message mail) throws MessagingException {
		updateFile( mail.getReceivedDate().toString().substring(11, 19));
		
	}

	private void updateFile(String date) {
		File mailfile = new File ("recievedmail.txt");
			try {
				if ( !mailfile.exists())
					mailfile.createNewFile();
				FileWriter fw = new FileWriter(mailfile, true);
				fw.write("AN EMAIL IS RECIEVED AT " + date + "\n");
				fw.close();
			} catch (IOException e) {
				System.out.println();
			}
		}
	}
