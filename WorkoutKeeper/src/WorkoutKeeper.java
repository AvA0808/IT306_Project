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
		User user = new User();
		String status = "rePrompt";

		// String checkPath = FileSys.PATH + FileSys.USER_FILE;
		// String fileLine = FileSys.readLine(checkPath);
		// System.out.println(FileSys.getSubString(fileLine, "Email: "));

		if (FileSys.fileSetup()) {
			do {
				status = Menu.welcomeScreen(status, user);
				if (status != "rePrompt" && status != "exit") {
					status = Menu.loggedIn(user, status);
				}
			} while (status == "rePrompt");
		} else {
			JOptionPane.showMessageDialog(null, "The system could not be opened.\nContact the admin for assistance.");
		}
		JOptionPane.showMessageDialog(null, "Now Exiting the App.");
	}
}