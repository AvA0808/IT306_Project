import javax.swing.JOptionPane;

/**
 * @author Aleksandar
 *
 */
public class WorkoutKeeper {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String email = "";

		if (MaintainData.fileSetup()) {
			do {
				email = Menu.welcomeScreen(email);
			} while (email != "-1");

			if (email != "-1") {

			}
		} else {
			JOptionPane.showMessageDialog(null, "The System could not be opened.\nContact the admin for assistance.");
		}
	}
}
