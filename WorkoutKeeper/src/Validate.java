import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Validate {

	/*
	 * Checks to see if the email provided is already in the database (text
	 * file)
	 * @param
	 * @return
	 */
	public static boolean isEmailInList(String email) throws FileNotFoundException {
		boolean flag = false;
		Scanner scanner = null;
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(new File(FileSys.PATH + FileSys.USER_FILE))));
			// if no Exception thrown, proceed to reading lines
			String nextLine;
			while (scanner.hasNextLine()) {
				nextLine = scanner.nextLine();
				// examine the email listed in that line

				String read_email = FileSys.getSubString(nextLine, "Email: ");

				// if match found, set flag to true
				if (email.equalsIgnoreCase(read_email)) {
					flag = true;
					break;
				}
			}
		}
		// if Exception caught, throw back to calling method to handle in main
		// method
		catch (FileNotFoundException e) {
			throw e;
		}

		return flag;
	}
	
	/*
	 * Check to see if the password provided matches the email it is being
	 * provided with.
	 * @param
	 * @return
	 */
	public static boolean doesPasswordMatch(String email, String password) throws FileNotFoundException {
		boolean flag = false;
		Scanner scanner = null;
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(new File(FileSys.PATH + FileSys.USER_FILE))));
			// if no Exception thrown, proceed to reading lines
			String nextLine;
			while (scanner.hasNextLine()) {
				nextLine = scanner.nextLine();
				// examine the email listed in that line
				String read_email = FileSys.getSubString(nextLine, "Email: ");
				// if match found, check if password matches input password
				if (email.equalsIgnoreCase(read_email)) {
					//grab the password
					String read_pass = FileSys.getSubString(nextLine, "Password: ");
					//if both email and password match (case must match), set flag to true
					if(password.equals(read_pass)) {
						flag = true;
						break;
					}
				}
			}
		}
		// if Exception caught, throw back to calling method to handle in main
		// method
		catch (FileNotFoundException e) {
			throw e;
		}
		return flag;
	}
}
