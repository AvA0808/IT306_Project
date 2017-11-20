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
		String path = "./src/";
		String userFile = "user.txt";
		String workoutFile = "workout.txt";
		String exerciseFile = "exercise.txt";
		String sharedFile = "shared.txt";
		String[] files = { "user.txt", "workout.txt", "exercise.txt", "shared.txt" };

		String email = "";

		if (FileSys.fileSetup(path, files)) {
			do {
				email = Menu.welcomeScreen(email);
				if (email != "-1") {
					Menu.loggedIn();
				}
			} while (email == "-1");
		} else {
			JOptionPane.showMessageDialog(null, "The system could not be opened.\nContact the admin for assistance.");
		}
		JOptionPane.showMessageDialog(null, "Now Exiting the App.");
	}
}