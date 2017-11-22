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
		// String fileLine = FileSys.readLine(checkPath);
		// System.out.println(FileSys.getSubString(fileLine, "Email: "));

		if (FileSys.fileSetup()) {
			do {
				email = Menu.welcomeScreen(email);
				if (email != "error" && email != "exit") {
					email = Menu.loggedIn(email);
				}
			} while (email == "error");
		} else {
			JOptionPane.showMessageDialog(null, "The system could not be opened.\nContact the admin for assistance.");
		}
		JOptionPane.showMessageDialog(null, "Now Exiting the App.");
	}
}