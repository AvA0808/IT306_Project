import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * @author Aleksandar new Line
 */
public class Menu {
	/**
	 * 
	 * @param email
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	static String welcomeScreen(String email) throws FileNotFoundException, IOException {
		String[] options = { "Create Account", "Login", "Exit" };

		switch (JOptionPane.showOptionDialog(null, "Welcome to the Workout Keeper App", "Workout Keeper",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0])) {
		case 0:
			createAcount();
			email = "error";
			break;
		case 1:
			email = login();
			break;
		case 2:
			email = "exit";
			break;
		default:
			email = "error";
			break;
		}
		return email;
	}

	/**
	 * @throws IOException
	 * @throws FileNotFoundException
	 * 
	 */
	private static void createAcount() throws FileNotFoundException, IOException {
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
								FileSys.append(FileSys.PATH, FileSys.USER_FILE, user.toString());
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
	public static String login() throws HeadlessException, FileNotFoundException {
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
				userEmail = "error";
			}

		} while (!accessGranted);
		return userEmail;
	}

	public static String loggedIn(String email) {
		String[] options = { "Exercise", "My Workouts", "Shared Workouts", "My Profile", "Logout" };
		boolean exit = false;

		do {
			switch (JOptionPane.showOptionDialog(null, "User Menu", "Workout Keeper", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0])) {
			case 0:
				exerciseSubMenu(email);
				break;
			case 1:// TODO Auto-generated method stub
				break;
			case 2:// TODO Auto-generated method stub
				break;
			case 3:// TODO Auto-generated method stub
				break;
			case 4:
				exit = true;
				email = "error";
				break;
			default:
				exit = true;
				JOptionPane.showMessageDialog(null, "No Option Selected.");
				break;
			}
		} while (!exit);

		return email;
	}

	private static void exerciseSubMenu(String email) {
		String[] options = { "Create New Exercise", "Sort Exercise", "Search Cardio\nExercise", "Cancel" };
		boolean exit = false;

		do {
			switch (JOptionPane.showOptionDialog(null, "Exercise Menu", "Workout Keeper", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0])) {
			case 0:
				createExercise(email);
				break;
			case 1:// TODO Auto-generated method stub
				break;
			case 2:// TODO Auto-generated method stub
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

	private static void createExercise(String email) {
		boolean exit = false;
		int userType = -1;
		String userDesc = "", userMuscle = "";

		JTextField description = new JTextField();
		JComboBox muscle = new JComboBox(Exercise.MUSCLE_GROUP);
		JComboBox type = new JComboBox(Exercise.EXERCISE_TYPE);
		Object[] inputFields = { "Description:", description, "Muscle Group", muscle, "Exercise Type", type };

		do {
			int option = JOptionPane.showConfirmDialog(null, inputFields, "Create New Exercise",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				if (description.getText() != null) {
					userDesc = description.getText();
					userMuscle = Exercise.MUSCLE_GROUP[muscle.getSelectedIndex()];
					userType = type.getSelectedIndex();
					exit = true;
				} else {
					JOptionPane.showMessageDialog(null, "The Description of the Exercise cannot be empty.\nTry Again.");
				}
			} else {
				exit = true;
				userType = 3;
			}

		} while (!exit);

		switch (userType) {
		case 0:
			createStretch(userDesc, userMuscle);
			break;
		case 1:
			createCardio(userDesc, userMuscle);
			break;
		case 2:
			createWeightTraining(userDesc, userMuscle);
			break;
		case 3:
			JOptionPane.showMessageDialog(null, "Returning to Exercise Menu.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Unexpected Error.");
		}
	}

	private static void createStretch(String userDesc, String userMuscle) {
		// TODO Auto-generated method stub

	}

	private static void createCardio(String userDesc, String userMuscle) {
		// TODO Auto-generated method stub

	}

	private static void createWeightTraining(String userDesc, String userMuscle) {
		// TODO Auto-generated method stub
		boolean exit = false;

		JTextField weight = new JTextField();
		JTextField reps = new JTextField();
		Object[] inputFields = { "Enter Weight (lbs) ", weight, "Enter Number of Reps", reps };

		do {
			int option = JOptionPane.showConfirmDialog(null, inputFields, "Create Weight Training Exercise",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {

			} else {
				exit = true;
			}

		} while (!exit);
	}
}