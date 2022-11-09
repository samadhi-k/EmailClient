package Assignment.Assignment;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.mail.MessagingException;

class EmailClient  {
	private  static int recipientCount; // number of recipients added in one session
	
	
public static void main(String[] args) throws MessagingException, FileNotFoundException {
	
	//ArrayList of SendMail objects is loaded to the application
	ArrayList<SendMail> sentmail = MailHandler.deserialize();
	
	//the queue to store Message objects 
	MyBlockingQueue queue = new MyBlockingQueue(1);
	
	// producer to message objects
	RecieveMail retriever = new RecieveMail(true , queue);
	//consumer of message objects
	RecievedMailSerializer serializer = new RecievedMailSerializer(queue , true);
	retriever.start();
	serializer.start();
	
	// all recipient objects
	// creates a recipient object for each person in the list and loads on to the list
	ArrayList<Recipient> recipientList = FileHandler.loadRecipients();

	// get the todays date
	LocalDate x =  LocalDate.now();
	DateTimeFormatter x1 = DateTimeFormatter.ofPattern("MM/dd");
	DateTimeFormatter x2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	String today =  x.format(x1); //month/date
	String today1 = x.format(x2); //year/month/date
	
	// check for recipients who have the birthday today and add to the list
	ArrayList<Wishable> birthdaysToday = new ArrayList<>();
	
	for ( Recipient r : recipientList ) {
		if ( r instanceof Wishable) {
			if ( r.birthday.contains(today)  ) {
				birthdaysToday.add((Wishable)r);
					
		}		
		}
	}
	// start email client
	// sends birthday wishes to all recipients who celebrate their birthday today
	// makes the recipient count 0
	start( birthdaysToday);	
	Scanner scanner = new Scanner(System.in);
	while (true)	{
	// 
    System.out.println("OPTIONS: \n"
           + "1 - NEW RECIPIENT\n"
           + "2 - SEND A MAIL\n"
           + "3 - BIRTHDAYS ON A GIVEN DATE\n"
           + "4 - EMAILS SENT ON A GIVEN DATE\n"
           + "5 - NEW RECIPIENTS ADDED\n"
           + "6 - EXIT" );

    int option = scanner.nextInt();
    scanner.nextLine();
    // ArrayList will be serialized only after you exit
    if ( option == 6) {
    	
    	// serialize the ArrayList of SendMail objects
    	MailHandler.serialize(sentmail);
    	
    	retriever.setAppRunning(false);
    	serializer.setAppRunning(false);
    	serializer.interrupt();
    	scanner.close();
    	
    	break;
	}
    else {
    switch(option){
    	case 1:
    		// input format - Official: nimal,nimal@gmail.com,ceo
    		// Use a single input to get all the details of a recipient
    		System.out.println("FORMAT TO TYPE IN ONE LINE"
    				+ " OFFICIAL: <NAME> , <EMAIL> , <DESIGNATION>\n"
    				+ " PERSONAL: <NAME> , <NICKNAME> , <EMAIL> , <BIRTHDAY>\n"
    				+ " OFFICE_FRIEND: <NAME> , <EMAIL> , <DESIGNATION> , <BIRTHDAY>");
    		// code to add a new recipient
            // store details in clientList.txt file
    		String recipientDetail = scanner.nextLine();
        	FileHandler.recipientAddition(recipientDetail.strip());
        	recipientCount ++;
        	break;
      
    	case 2:
    		// input format - email, subject, content
    		   SendMail sendmail = new SendMail();
    		   System.out.println("COMPOSE ");
    		   System.out.println(" <EMAIL> , <SUBJECT> , <CONTENT>");
        	   String[] details = scanner.nextLine().split(",");
        	   sendmail.emailAddress  = details[0].strip();
        	   sendmail.subject = details[1].strip();
        	   String content = "";
        	  
        	   for ( int i = 2; i < details.length; i++)
        		   content += ( details[i] + ",");
        	   
        	   sendmail.content = content.substring(0,content.length()-1);
        	 
        	   sendmail.day = today1;
        	   sentmail.add(sendmail);
       		// code to send an email
        	   sendmail.sendMail(sendmail.emailAddress , sendmail.subject , sendmail.content );
              
               break;
    	case 3:
    		// input format - yyyy/MM/dd (ex: 2018/09/17)
        	   System.out.println("DATE : YYYY/MM/DD");
        	   String date1 = scanner.next().substring(5);
        	   System.out.println(date1);
        	// code to print recipients who celebrate birthdays on the given date
        	   for (  Recipient recipient : recipientList) {
        		   if ( recipient instanceof Wishable  && recipient.birthday.contains(date1))
        			   System.out.println(recipient.name);
        		   }
               break;
    	case 4:
    		 //traverse through the deserialized arraylist and prints the details about mails sent on the specified date
    		 // input format - yyyy/MM/dd (ex: 2018/09/17)
        	   System.out.println("DATE : YYYY/MM/DD");
        	   String date2 = scanner.next();
        	   // code to print the details of all the emails sent on the input date
        		   for ( SendMail mail1 : sentmail ) {
        			   if (mail1.day.equals(date2)) {
        				   System.out.print( mail1.emailAddress+ " : " );
        			   	   System.out.println( mail1.subject);
        			   }
        		   }
               
               break;
    	case 5:
    		// prints the number of recipients added in one session 
               System.out.println("NUMBER OF RECIPIENTS ADDED TODAY : " +EmailClient.recipientCount);
               break;
    	
        	   }
    }
	}
     }

static void start( ArrayList<Wishable> birthdaysToday) throws MessagingException {
	recipientCount=0;
	
	for( Wishable recipient : birthdaysToday) {
		recipient.birthdayWish(); 
	}
}
     
     
    
 
}
