package Assignment.Assignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class FileHandler {

// method to add details about contacts to the text file
static void recipientAddition(String details) {
	File recipientFile = new File ("clientList.txt");

	try {
		if ( !recipientFile.exists())
			recipientFile.createNewFile();
		FileWriter fw = new FileWriter(recipientFile , true);
		fw.write(details + "\n");
		fw.close();
	} catch (IOException e) {
		System.out.println();
	}
}

// method to traverse through the file and create a list of recipient objects 
static ArrayList<Recipient>  loadRecipients() {
	ArrayList<Recipient> recipientList = new ArrayList<>();
	if (new File("clientList.txt").exists()) {
		BufferedReader br = null;
		try {
			br=new BufferedReader(new FileReader("clientList.txt"));
			String line;
			while((line=br.readLine())!= null) {
				try {
				String recType = line.split(":")[0].strip().toLowerCase();
				String details = line.split(":")[1].strip();
				if (recType.equals("official"))
					recipientList.add(new OfficialRecipient(details));
				else if (recType.equals("personal"))
					recipientList.add(new PersonalRecipient(details));
				else if(recType.equals("office_friend")) 
					recipientList.add(new OfficeFriend(details));
				else 
					System.out.println("CONTACT FILE IS CORRUPTED");
				
				
				}
				
				catch (IndexOutOfBoundsException e) {
					System.out.println("YOU HAVE GIVEN AN INVALID INPUT");
					continue;
				}
				}
			}
			
		catch(IOException e){
			System.out.println();
		}
		
	
	}
	return recipientList;

}
}
