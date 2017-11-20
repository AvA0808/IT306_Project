import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * @author Aleksandar
 *
 */
public class Menu {
	/**
	 * 
	 * @param email
	 * @return
	 */
	static String welcomeScreen(String email) {
		String[] options = { "Create Account", "Login", "Exit" };

		switch (JOptionPane.showOptionDialog(null, "Welcome to the Workout Keeper App", "Workout Keeper",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0])) {
		case 0:
			createAcount();
			email = "-1";
			break;
		case 1:
			email = login();
			break;
		case 2:
			email = "0";
			break;
		default:
			email = "-1";
			break;
		}
		return email;
	}

	/**
	 * 
	 */
	private static void createAcount() {
		JTextField email = new JTextField();
		JTextField password = new JTextField();
		JTextField firstName = new JTextField();
		JTextField lastName = new JTextField();

		boolean validInput;
		User user = new User();

		Object[] inputFields = { "Email:", email, "Password:", password, "First Name:", firstName, "Last Name:",
				lastName };

		do {
			int option = JOptionPane.showConfirmDialog(null, inputFields, "Create Account",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			validInput = false;

			if (option == JOptionPane.OK_OPTION) {
				if (user.setEmail(email.getText())) {
					if (user.setPassword(password.getText())) {
						if (user.setFirstName(firstName.getText())) {
							if (user.setLastName(lastName.getText())) {
								validInput = true;
								FileSys.append("./src/", "user.txt", user.toString());
							} else {
								JOptionPane.showMessageDialog(null, "The Last Name entered is not valid.\nTry again.");
							}
						} else {
							JOptionPane.showMessageDialog(null, "The First Name entered is not valid.\nTry again.");
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"The Password entered is not valid.\nIt must contain at least 8 characters, at least 1 upper case and 1 lower case, and at least 4 numbers\nTry again.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "The Email entered is not a valid email.\nTry again.");
				}
			} else {
				validInput = true;
			}
		} while (!validInput);
	}

	/**
	 * 
	 * @return
	 */
	public static String login() {
		JTextField email = new JTextField();
		JTextField password = new JPasswordField();
		boolean accessGranted = false;
		String userEmail = "";

		Object[] inputFields = { "Email:", email,
				"Password: (Minimum 8 char, 1 Upper Case and 1 lower case, and at least 4 numbers)", password };

		do {
			int option = JOptionPane.showConfirmDialog(null, inputFields, "Workout Keeper Login",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				if (Validate.isEmailInList(email.getText())) {
					if (Validate.doesPasswordMatch(email.getText(), password.getText())) {
						accessGranted = true;
						userEmail = email.getText();
					} else {
						JOptionPane.showMessageDialog(null, "The Password entered is not valid.\nTry again.");
						accessGranted = false;
					}
				} else {
					JOptionPane.showMessageDialog(null, "The Email entered is not a valid email.\nTry again.");
					accessGranted = false;
				}
			} else {
				accessGranted = true;
				userEmail = "-1";
			}

		} while (!accessGranted);
		return userEmail;
	}

	public static void loggedIn() {
		String[] options = { "Exercise", "My Workouts", "Shared Workouts", "My Profile", "Logout" };
		boolean exit = false;

		do {
			switch (JOptionPane.showOptionDialog(null, "User Menu", "Workout Keeper", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0])) {
			case 0:
				exerciseSubMenu();
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				exit = true;
				break;
			default:
				exit = true;
				JOptionPane.showMessageDialog(null, "No Option Selected.");
				break;
			}
		} while (!exit);
	}

	private static void exerciseSubMenu() {
		String[] options = { "Create New Exercise", "Sort Exercise", "Search Cardio\nExercise", "Cancel" };
		boolean exit = false;

		do {
			switch (JOptionPane.showOptionDialog(null, "Exercise Menu", "Workout Keeper", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0])) {
			case 0:
				createExercise();
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				exit = true;
				break;
			default:
				exit = true;
				JOptionPane.showMessageDialog(null, "No Option Selected.");
				break;
			}
		} while (!exit);
	}

	private static void createExercise() {
		// TODO Auto-generated method stub

	}
}