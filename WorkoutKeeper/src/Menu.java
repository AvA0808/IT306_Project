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
	 * @param status
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	static String welcomeScreen(String status, User user) throws FileNotFoundException, IOException {
		String[] options = { "Create Account", "Login", "Exit" };

		switch (JOptionPane.showOptionDialog(null, "Welcome to the Workout Keeper App", "Workout Keeper",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0])) {
		case 0:
			createAcount();
			status = "rePrompt";
			break;
		case 1:
			status = login(user, status);
			break;
		case 2:
			status = "exit";
			break;
		default:
			status = "rePrompt";
			break;
		}
		return status;
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
								FileSys.append(FileSys.USER_FILE, user.toString());
							} else {
								JOptionPane.showMessageDialog(null, "The Last Name entered is not valid.\nTry again.");
							}
						} else {
							JOptionPane.showMessageDialog(null, "The First Name entered is not valid.\nTry again.");
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"The Password entered is not valid.\nIt must contain at least 9 characters, at least 1 upper case and 1 lower case, and at least 4 numbers\nTry again.");
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
	public static String login(User user, String status) throws HeadlessException, FileNotFoundException {
		JTextField email = new JTextField();
		JTextField password = new JPasswordField();
		boolean accessGranted = false;

		Object[] inputFields = { "Email:", email,
				"Password: (Minimum 9 char, 1 Upper Case and 1 lower case, and at least 4 numbers)", password };

		do {
			int option = JOptionPane.showConfirmDialog(null, inputFields, "Workout Keeper Login",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				if (Validate.isEmailInList(email.getText())) {
					if (Validate.doesPasswordMatch(email.getText(), password.getText())) {
						accessGranted = true;
						createUser(user, email.getText());
						status = "login";
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
				status = "rePrompt";
			}

		} while (!accessGranted);
		return status;
	}

	private static void createUser(User user, String email) {
		// TODO Auto-generated method stub
		user.setEmail(email);
	}

	public static String loggedIn(User user, String status) {
		String[] options = { "Exercise", "My Workouts", "Shared Workouts", "My Profile", "Logout" };
		boolean exit = false;
		System.out.println(user.getEmail());
		do {
			switch (JOptionPane.showOptionDialog(null, "User Menu", "Workout Keeper", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0])) {
			case 0:
				exerciseSubMenu(user.getEmail());
				break;
			case 1:// TODO Auto-generated method stub
				break;
			case 2:// TODO Auto-generated method stub
				break;
			case 3:
				profileSubMenu(user);
			case 4:
				exit = true;
				status = "rePrompt";
				break;
			default:
				exit = true;
				JOptionPane.showMessageDialog(null, "No Option Selected.");
				break;
			}
		} while (!exit);

		return status;
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
	
	/*
	 * Displays the profile sub-menu and processes user selections and inputs
	 * @param
	 * @return
	 */
	public static void profileSubMenu(User user) {
		//instantiate the text field objects (not allowing new email)
		JTextField new_password = new JTextField();
		JTextField new_firstName = new JTextField();
		JTextField new_lastName = new JTextField();

		boolean validInput;
		//instantiate what will be displayed in the dialog box
		Object[] inputFields = { "*Leave text field(s) empty if no change desired", "Email: " + user.getEmail(), "Password:", new_password, "First Name: " + user.getFirstName(), new_firstName, "Last Name: " + user.getLastName(),
				new_lastName };
		//validate entries into text boxes
		do {
			int option = JOptionPane.showConfirmDialog(null, inputFields, "Update Profile",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			validInput = false;

			if (option == JOptionPane.OK_OPTION) {
				//if (user.setEmail(new_email.getText())) {
					if (user.setPassword(new_password.getText()) || new_password.getText().equals("")) {
						if (user.setFirstName(new_firstName.getText()) || new_firstName.getText().equals("")) {
							if (user.setLastName(new_lastName.getText()) || new_lastName.getText().equals("")) {
								validInput = true;
								System.out.println("New record: " + user.getEmail() + ", " + user.getPassword() + ", " + user.getFirstName() + ", " + user.getLastName());
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
				/*} else {
					JOptionPane.showMessageDialog(null, "The Email entered is not a valid email.\nTry again.");
				}*/
			} else {
				validInput = true;
			}
		} while (!validInput);
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
			
			//TODO add a check to make sure the description provided is not already in their system
			if (option == JOptionPane.OK_OPTION) {
				if (!description.getText().isEmpty()) {
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
			createStretch(email, userDesc, userMuscle);
			break;
		case 1:
			createCardio(email, userDesc, userMuscle);
			break;
		case 2:
			createWeightTraining(email, userDesc, userMuscle);
			break;
		case 3:
			JOptionPane.showMessageDialog(null, "Returning to Exercise Menu.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Unexpected Error.");
		}
	}

	private static void createStretch(String email, String userDesc, String userMuscle) {
		boolean exit = false;
		String userInstructions = "";
		JTextField instructions = new JTextField();
		Object[] inputFields = { "Instructions:", instructions };
		Stretch newExercise = new Stretch(userDesc, userMuscle, userInstructions);
		
		do {
			int option = JOptionPane.showConfirmDialog(null, inputFields, "Create Stretch Exercise",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				//TODO change to use the .set method
				if (!instructions.getText().isEmpty()) {
					userInstructions = instructions.getText();
					exit = true;
					try {
						FileSys.append(FileSys.EXERCISE_FILE, "Email: " + email + ", " + newExercise.toString());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "The Instructions of the Exercise cannot be empty.\nTry Again.");
				}
			} else {
				exit = true;
			}
		} while (!exit);
		
		
	}

	private static void createCardio(String email, String userDesc, String userMuscle) throws IllegalArgumentException{
		boolean exit = false;
		
		Cardiovascular newExercise = new Cardiovascular(userDesc, userMuscle);
		
		JTextField duration = new JTextField();
		JTextField setting = new JTextField();
		Object[] inputFields = { "Duration:", duration, "Setting:", setting };
		
		do {
			int option = JOptionPane.showConfirmDialog(null, inputFields, "Create Cardiovascular Exercise",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				if (!duration.getText().isEmpty() && duration.getText().chars().allMatch( Character::isDigit ) && newExercise.setDuration(Integer.parseInt(duration.getText()))) {
					if(!setting.getText().isEmpty() && newExercise.setSetting(setting.getText())) {
						exit = true;
						try {
							FileSys.append(FileSys.EXERCISE_FILE, "Email: " + email + ", " + newExercise.toString());
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "The Setting of the Exercise cannot be empty.\nTry Again.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "The Duration of the Exercise must be a number greater than 0.\nTry Again.");
				}
			} else {
				exit = true;
			}
		} while (!exit);
		
		
		
		 
	}

	private static void createWeightTraining(String email, String userDesc, String userMuscle) {
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