import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * @author Aleksandar
 *
 */
public class WorkoutKeeper {
	/**
	 * @param args
	 * @throws IOException
	 * @throws HeadlessException
	 */
	public static void main(String[] args) throws HeadlessException, IOException {

		String email = "";

		// String checkPath = FileSys.PATH + FileSys.USER_FILE;
		// while(exit?){
		// String fileLine = Get next line FileSys.readLine(checkPath);
		//
		// if user email matches FileSys.getSubString(fileLine, "Email: ");
		// }

		if (FileSys.fileSetup(FileSys.PATH, FileSys.FILES)) {
			do {
				email = Menu.welcomeScreen(email);
				if (email != "-1") {
					Menu.loggedIn(email);
				}
			} while (email == "-1");
		} else {
			JOptionPane.showMessageDialog(null, "The system could not be opened.\nContact the admin for assistance.");
		}
		JOptionPane.showMessageDialog(null, "Now Exiting the App.");
	}
}