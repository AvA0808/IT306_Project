import java.io.*;
import java.util.*;
public class Validate {
	
	/*
	 * Checks to see if the email provided is already in the database (text file)
	 * @param
	 * @return
	 */
	public static boolean isEmailInList(String email) throws FileNotFoundException {
		 boolean flag = false;
		 Scanner scanner = null;
		 try {
			 scanner = new Scanner(new BufferedReader(new FileReader(new File(FileSys.PATH + FileSys.USER_FILE))));
			 //if no Exception thrown, proceed to reading lines
			 String nextLine = scanner.nextLine();
			 while(nextLine != null) {
				 //examine the email listed in that line
				 String read_email = FileSys.getSubString(nextLine, "Email: ");
				 //if match found, set flag to true
				 if(email.equalsIgnoreCase(read_email)) {
					 flag = true;
					 break;
				 }
				 nextLine = scanner.next();
			 }
		 }
		 //if Exception caught, throw back to calling method to handle in main method
		 catch(FileNotFoundException e) {
			 throw e;
		 }
		 
		 return flag;
	}

	public static boolean doesPasswordMatch(String email, String password) {
		// Check to see if the password provided matches the email it is being
		// provided with.

		/*
		 * Loop, use FileSys.readLine to pull out a line, use
		 * FileSys.getSubString to find the value provided for email If the
		 * subString returned matches the email passed into this method, check
		 * if the password provided matches the password on that line. If it
		 * matches return true, otherwise return false.
		 */
		return true;
	}
}
