import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Aleksandar Atanasov - G00716250 - aatanas2@gmu.edu
 * @author Victoria Chang - G00947241 - vchang3@masonlive.gmu.edu This class
 *         contains methods supporting the complete user GUI experience,
 *         including display and the more front-end processes.
 */
public class Menu {
	/**
	 * THe first screen the user sees when they launch the application. Prompts them
	 * to Create and Account, Login, or Exit the program.
	 * 
	 * @param status,
	 *            keeps track of what menu to show the user next, based on their
	 *            selection
	 * @return status, keeps track of what menu to show the user next, based on
	 *         their selection
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static String welcomeScreen(String status, User user) throws FileNotFoundException, IOException {
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
	 * An input for that allows the user to enter their email, password, first and
	 * last name, to create an account. A validation checks if this email is already
	 * being used, and all inputs are validated by the object.
	 * 
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
					if (!Validate.isEmailInList(user.getEmail())) {
						if (user.setPassword(password.getText())) {
							if (user.setFirstName(firstName.getText())) {
								if (user.setLastName(lastName.getText())) {
									validInput = true;
									FileSys.append(FileSys.USER_FILE, user.toString());
								} else {
									JOptionPane.showMessageDialog(null,
											"The Last Name entered is not valid.\nTry again.");
								}
							} else {
								JOptionPane.showMessageDialog(null, "The First Name entered is not valid.\nTry again.");
							}
						} else {
							JOptionPane.showMessageDialog(null,
									"The Password entered is not valid.\nIt must contain at least 9 characters, at least 1 upper case and 1 lower case, and at least 4 numbers\nTry again.");
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"The Email entered is already in use.\nYou must enter a new email.");
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
	 * A login screen that allows the user to enter their email and password. The
	 * inputs are validated with the user file to make sure they match
	 * 
	 * @param user
	 *            The object of the user
	 * @param status
	 *            Keeps track of what menu the user needs to see next.
	 * @return status Keeps track of which menu the user needs to see next
	 * @throws HeadlessException
	 * @throws FileNotFoundException
	 */
	private static String login(User user, String status) throws HeadlessException, FileNotFoundException {
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
						createUserObject(user, email.getText());
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

	/**
	 * Create user object using input email, which will also be used to read user
	 * file to find record with the same email
	 * 
	 * @param user
	 *            An empty user object to be filled logged-in user's info
	 * @return The logged-in user's email
	 * @throws FileNotFoundException
	 */
	private static void createUserObject(User user, String email) throws FileNotFoundException {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(new File(FileSys.PATH + FileSys.USER_FILE))));
			String nextLine;
			// search for record with matching email
			while (scanner.hasNextLine()) {
				nextLine = scanner.nextLine();
				// if matching, set the user object's fields using corresponding information
				// from file record
				if (FileSys.getSubString(nextLine, "Email: ").equalsIgnoreCase(email)) {
					user.setEmail(email);
					user.setFirstName(FileSys.getSubString(nextLine, "First Name: "));
					user.setLastName(FileSys.getSubString(nextLine, "Last Name: "));
					user.setPassword(FileSys.getSubString(nextLine, "Password: "));
					break;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			throw e;
		}
	}

	public static <E> String loggedIn(User user, String status) throws IOException {
		String[] options = { "Exercise", "My Workouts", "Shared Workouts", "My Profile", "Logout" };
		boolean exit = false;
		Counter counter = new Counter();

		LinkedList<Exercise> exercises = SortSearch.readExercise(user, counter);

		do {
			switch (JOptionPane.showOptionDialog(null, "User Menu", "Workout Keeper", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0])) {
			case 0:
				exerciseSubMenu(user, exercises, counter);
				break;
			case 1:
				workoutSubMenu(user, exercises, counter);
				break;
			case 2:
				sharedWorkoutsSubMenu(user);
				break;
			case 3:
				profileSubMenu(user);
				break;
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

	/**
	 * A menu to allow the user to share workouts and view shared workouts
	 * 
	 * @param user
	 *            an object of the user
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void sharedWorkoutsSubMenu(User user) throws FileNotFoundException, IOException {
		String[] options = { "Share a Workout", "Shared With Me", "Workouts I Shared", "Return" };
		boolean exit = false;

		do {
			JTextArea textArea = new JTextArea(30, 80);

			switch (JOptionPane.showOptionDialog(null, "Shared Workouts Sub-Menu", "Workout Keeper",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0])) {
			case 0:
				shareWorkout(user);
				break;
			case 1:
				String longMessage = "Workouts shared with me:\n\n";
				longMessage += FileSys.findInShared(user.getEmail(), "Receiver Email: ");
				if (longMessage.split("\r\n|\r|\n").length > 30) {
					textArea.setText(longMessage);
					textArea.setEditable(false);
					JScrollPane scrollPane = new JScrollPane(textArea);
					JOptionPane.showMessageDialog(null, scrollPane);
				} else {
					JOptionPane.showMessageDialog(null, longMessage);
				}
				break;
			case 2:
				String longMSG = "Workouts I have Shared:\n\n";
				longMSG += FileSys.findInShared(user.getEmail(), "Sharer Email: ");
				if (longMSG.split("\r\n|\r|\n").length > 30) {
					textArea.setText(longMSG);
					textArea.setEditable(false);
					JScrollPane scrollPane = new JScrollPane(textArea);
					JOptionPane.showMessageDialog(null, scrollPane);
				} else {
					JOptionPane.showMessageDialog(null, longMSG);
				}
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

	/**
	 * A prompt that asks the user which other user they want to share a workout
	 * with. Their email will not show up as a option to select. Once a user is
	 * selected, a method is called to remove workouts that have already been shared
	 * with that user and returns them, and prompts the user for which workout to
	 * share.
	 * 
	 * @param user
	 *            An object of the user
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void shareWorkout(User user) throws FileNotFoundException, IOException {
		boolean exit = false;
		Object[] fileWorkouts = FileSys.readWorkouts(user).toArray();
		// fileWorkouts = removeSharedWorkouts
		Object[] fileEmails = FileSys.getAllEmails().toArray();

		// Remove this users emails as an option, so the user cannot share a workout
		// with them selves.
		ArrayList<Object> tempEmails = new ArrayList<Object>(Arrays.asList(fileEmails));
		tempEmails.remove(user.getEmail());
		fileEmails = tempEmails.toArray();

		JComboBox<?> emails = new JComboBox<Object>(fileEmails);
		Object[] getEmail = { "Select the person you want to share a workout with:", "User Email:", emails, };

		if (fileWorkouts.length == 0) {
			JOptionPane.showMessageDialog(null, "You do not have any workouts to share.");
		} else {
			do {
				int option = JOptionPane.showConfirmDialog(null, getEmail, "Create New Exercise",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

				if (option == JOptionPane.OK_OPTION) {
					String shareWithEmail = (String) fileEmails[emails.getSelectedIndex()];

					fileWorkouts = removeSharedWorkouts(user, shareWithEmail, fileWorkouts);
					JComboBox<?> workouts = new JComboBox<Object>(fileWorkouts);
					Object[] getWorkout = {
							"Select the workout you want to share with " + fileEmails[emails.getSelectedIndex()] + ":",
							"Workout:", workouts };

					do {
						int option2 = JOptionPane.showConfirmDialog(null, getWorkout, "Create New Exercise",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

						if (option2 == JOptionPane.OK_OPTION) {
							String newLine = "Sharer Email: " + user.getEmail();
							newLine += ", Workout ID Shared: "
									+ findInArray(fileWorkouts, workouts.getSelectedIndex(), "Workout ID: ");
							newLine += ", Receiver Email: " + shareWithEmail;
							FileSys.append(FileSys.SHARED_FILE, newLine);
							JOptionPane.showMessageDialog(null, "You have successfully shared the workout.");
							exit = true;
						} else {
							exit = true;
						}
					} while (!exit);
				} else {
					exit = true;
				}
			} while (!exit);
		}
	}

	/**
	 * A method to remove workouts that have already been share with this user. It
	 * will return a new list of workouts that have not already been shared
	 * 
	 * @param user
	 *            an object of the user
	 * @param shareWithEmail
	 *            the email of the person that the workout will be shared with
	 * @param userWorkouts
	 *            All the workouts for this user
	 * @return userWorkouts A list of the users workouts without the workouts that
	 *         have already been shared with the user that the workout will be
	 *         shared with
	 * @throws FileNotFoundException
	 */
	private static Object[] removeSharedWorkouts(User user, String shareWithEmail, Object[] userWorkouts)
			throws FileNotFoundException {
		Scanner scanner = null;
		ArrayList<Object> workoutToRemove = new ArrayList<Object>();
		ArrayList<String> tempIDs = new ArrayList<String>();

		try {
			scanner = new Scanner(new BufferedReader(new FileReader(new File(FileSys.PATH + FileSys.SHARED_FILE))));
			// if no exception thrown, proceed to file reading
			String nextLine = "";
			// keep iterating while there are more records to read
			while (scanner.hasNextLine()) {
				// grab next record
				nextLine = scanner.nextLine();
				if (nextLine.contains(user.getEmail())) {
					if (nextLine.contains(shareWithEmail)) {
						String ID = nextLine;
						ID = ID.substring(ID.indexOf("Workout ID Shared: "), ID.length());
						ID = ID.substring(0, ID.indexOf(","));
						ID = ID.substring(ID.indexOf(':') + 1, ID.length());
						ID = ID.trim();

						tempIDs.add(ID);
					}
				}
			}
		} catch (FileNotFoundException e) {
			throw e;
		}
		scanner.close();

		Object[] sharedIDs = new Object[tempIDs.size()];
		sharedIDs = (Object[]) tempIDs.toArray();
		for (int i = 0; i < userWorkouts.length; i++) {
			String workout = (String) userWorkouts[i];
			int x = 0;
			boolean found = false;
			while (x < sharedIDs.length && !found) {
				if (workout.contains("Workout ID: " + sharedIDs[x] + ",")) {
					workoutToRemove.add(userWorkouts[i]);
					found = true;
				}
				x++;
			}
		}

		Object[] tempWorkoutToRemove = new Object[workoutToRemove.size()];
		tempWorkoutToRemove = (Object[]) workoutToRemove.toArray();
		ArrayList<Object> tempUserWorkouts = new ArrayList<Object>(Arrays.asList(userWorkouts));
		for (int i = 0; i < tempWorkoutToRemove.length; i++) {
			if (tempUserWorkouts.contains(tempWorkoutToRemove[i])) {
				tempUserWorkouts.remove(tempWorkoutToRemove[i]);
			}
		}
		userWorkouts = tempUserWorkouts.toArray();
		return userWorkouts;
	}

	/**
	 * A method that returns the value of a defined field
	 * 
	 * @param list
	 *            The list that contains the field we are looking for
	 * @param selectedIndex
	 *            The index of the list element we will check
	 * @param searchValue
	 *            The attribute we are looking for
	 * @return The value of the attribute being searched for
	 */
	private static String findInArray(Object[] list, int selectedIndex, String searchValue) {
		String readLine = (String) list[selectedIndex];
		String findValue = readLine.substring(readLine.indexOf(searchValue), readLine.length());

		if (findValue.indexOf(",") == -1) {
			findValue = findValue.substring(0, findValue.length());
		} else {
			findValue = findValue.substring(0, findValue.indexOf(","));
		}

		findValue = findValue.substring(findValue.indexOf(':') + 1, findValue.length());
		return findValue.trim();
	}

	/**
	 * A menu that will allow th user to Create an exercise, Access the Sort
	 * Exercise Menu, or Search a Cardio Exercise
	 * 
	 * @param user
	 *            an Object of the user
	 * @param exercises
	 *            The exercises for this user
	 * @param counter
	 *            an object that keeps track of the number of stretch, cardio, and
	 *            weight training exercises made
	 * @throws HeadlessException
	 * @throws FileNotFoundException
	 */
	private static void exerciseSubMenu(User user, LinkedList<Exercise> exercises, Counter counter)
			throws HeadlessException, FileNotFoundException {
		String[] options = { "Create New Exercise", "Sort Exercise", "Search CardioExercise", "Cancel" };
		boolean exit = false;

		do {
			switch (JOptionPane.showOptionDialog(null, "Exercise Menu", "Workout Keeper", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0])) {
			case 0:
				createExercise(user, exercises, counter);
				break;
			case 1:
				sortExercise(exercises);
				break;
			case 2:
				if (exercises.size() == 0) {
					JOptionPane.showMessageDialog(null, "You do not have any Exercises.");
				} else {
					int duration = 0;
					do {
						try {
							duration = Integer.parseInt(
									JOptionPane.showInputDialog("Enter the Duration you would like to search for."));
						} catch (NumberFormatException e) {
							duration = 0;
							JOptionPane.showMessageDialog(null, "The Duration cannot be empty");
						}
					} while (duration == 0);
					JOptionPane.showMessageDialog(null, SortSearch.searchDuration(exercises, duration));
				}
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

	/**
	 * A menu that allows the user to pick how to sort their exercises.
	 * 
	 * @param exercises
	 *            The exercises this user has
	 * @throws HeadlessException
	 * @throws FileNotFoundException
	 */
	private static void sortExercise(LinkedList<Exercise> exercises) throws HeadlessException, FileNotFoundException {
		String[] options = { "Sort by Type", "Sort by Muscle Group", "Sort by Duration", "Cancel" };
		boolean exit = false;

		do {
			JTextArea textArea = new JTextArea(30, 80);

			switch (JOptionPane.showOptionDialog(null, "How would you like to sort your Exercises?", "Workout Keeper",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0])) {
			case 0:
				if (exercises.size() == 0) {
					JOptionPane.showMessageDialog(null, "You do not have any Exercises.");
				} else if (exercises.size() > 30) {
					String longMessage = SortSearch.sortingMethod(exercises, "Type");
					textArea.setText(longMessage);
					textArea.setEditable(false);
					JScrollPane scrollPane = new JScrollPane(textArea);
					JOptionPane.showMessageDialog(null, scrollPane);
				} else {
					JOptionPane.showMessageDialog(null, SortSearch.sortingMethod(exercises, "Type"));
				}
				break;
			case 1:
				if (exercises.size() == 0) {
					JOptionPane.showMessageDialog(null, "You do not have any Exercises.");
				} else if (exercises.size() > 30) {
					String longMessage2 = SortSearch.sortingMethod(exercises, "Muscle Group");
					textArea.setText(longMessage2);
					textArea.setEditable(false);
					JScrollPane scrollPane2 = new JScrollPane(textArea);
					JOptionPane.showMessageDialog(null, scrollPane2);
				} else {
					JOptionPane.showMessageDialog(null, SortSearch.sortingMethod(exercises, "Muscle Group"));
				}
				break;
			case 2:
				if (exercises.size() == 0) {
					JOptionPane.showMessageDialog(null, "You do not have any Exercises.");
				} else if (exercises.size() > 30) {
					String longMessage3 = SortSearch.sortDuration(exercises);
					textArea.setText(longMessage3);
					textArea.setEditable(false);
					JScrollPane scrollPane2 = new JScrollPane(textArea);
					JOptionPane.showMessageDialog(null, scrollPane2);
				} else {
					JOptionPane.showMessageDialog(null, SortSearch.sortDuration(exercises));
				}
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

	/**
	 * Displays the profile sub-menu for logged-in user, and processes user
	 * selections and inputs to update their profile
	 * 
	 * @param user
	 *            The logged-in user
	 * @throws FileNotFoundException
	 */
	private static void profileSubMenu(User user) throws FileNotFoundException {
		// instantiate the text field objects (not allowing new email)
		JTextField new_password = new JTextField();
		JTextField new_firstName = new JTextField();
		JTextField new_lastName = new JTextField();

		boolean validInput;
		// instantiate what will be displayed in the dialog box
		Object[] inputFields = { "*Leave text field(s) empty if no change desired\n", "Email: " + user.getEmail(),
				"Password:", new_password, "First Name: " + user.getFirstName(), new_firstName,
				"Last Name: " + user.getLastName(), new_lastName };
		// validate entries into text boxes
		do {
			int option = JOptionPane.showConfirmDialog(null, inputFields, "Update Profile",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			validInput = false;

			if (option == JOptionPane.OK_OPTION) {
				if (user.setPassword(new_password.getText()) || new_password.getText().equals("")) {
					if (user.setFirstName(new_firstName.getText()) || new_firstName.getText().equals("")) {
						if (user.setLastName(new_lastName.getText()) || new_lastName.getText().equals("")) {
							// by this point in runtime, user object already contains updated fields
							// System.out.println("New record based on user fields: First Name: " +
							// user.getFirstName() + ", Last Name: " + user.getLastName() + ", Email: " +
							// user.getEmail() + ", Password: " + user.getPassword() + "\n\n");
							validInput = true;
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
				validInput = true;
			}
		} while (!validInput);

		// if changes were made, overwrite old record; if no changes, old record remains
		// in the user database and within user object fields
		if (!new_password.getText().equals("") || !new_firstName.getText().equals("")
				|| !new_lastName.getText().equals("")) {
			// pass in the updated user object
			rewriteUserRecord(user);
		}
	}

	/**
	 * Rewrites the logged-in user's record in the user database if there has been a
	 * change in their information
	 * 
	 * @param user
	 *            The updated user object (email can not be changed)
	 * @throws FileNotFoundException
	 */
	private static void rewriteUserRecord(User user) throws FileNotFoundException {
		// meant to hold new file contents that include new record
		String newFile = "";
		Scanner scanner = null;
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(new File(FileSys.PATH + FileSys.USER_FILE))));
			// if no exception thrown, proceed: read entire file into a String
			while (scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();
				// if the next line contains the old record, insert new record into new file
				// String instead of it
				if (FileSys.getSubString(nextLine, "Email: ").equalsIgnoreCase(user.getEmail())) {
					// System.out.println("EMAIL MATCHED: \n");
					nextLine = "First Name: " + user.getFirstName() + ", Last Name: " + user.getLastName() + ", Email: "
							+ user.getEmail() + ", Password: " + user.getPassword();
				}
				newFile += nextLine + "\n";
			}
			scanner.close();
			// test output of variable
			// System.out.println("NEW USER.TXT FILE CONTENTS:\n\n" + newFile);

			// overwrite the old file contents
			PrintWriter writer = new PrintWriter(new FileOutputStream(new File(FileSys.PATH + FileSys.USER_FILE)),
					false);
			writer.write(newFile);
			writer.close();
		} catch (FileNotFoundException e) {
			throw e;
		}
	}

	/**
	 * A menu that allows the user to create an exercise. Based on the Exercise type
	 * the user chooses, it will call another method to create that exercise type
	 * 
	 * @param user
	 *            an object of the user
	 * @param exercises
	 *            The exercises of this user
	 * @param counter
	 *            the number of stretch, cardio, and weight training exercises this
	 *            user has
	 * @throws HeadlessException
	 * @throws FileNotFoundException
	 */
	private static void createExercise(User user, LinkedList<Exercise> exercises, Counter counter)
			throws HeadlessException, FileNotFoundException {
		boolean exit = false;
		int userType = -1;
		int exerciseID = Integer.parseInt(FileSys.readLine(FileSys.PATH + FileSys.EXERCISE_ID));
		String userDesc = "", userMuscle = "";

		JTextField description = new JTextField();
		JComboBox<?> muscle = new JComboBox<Object>(Exercise.MUSCLE_GROUP);
		JComboBox<?> type = new JComboBox<Object>(Exercise.EXERCISE_TYPE);
		Object[] inputFields = { "Description:", description, "Muscle Group", muscle, "Exercise Type", type };

		do {
			int option = JOptionPane.showConfirmDialog(null, inputFields, "Create New Exercise",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				if (!FileSys.userHasExercise(user.getEmail(), description.getText())) {
					if (!description.getText().isEmpty()) {
						userDesc = description.getText();
						userMuscle = Exercise.MUSCLE_GROUP[muscle.getSelectedIndex()];
						userType = type.getSelectedIndex();
						exit = true;
					} else {
						JOptionPane.showMessageDialog(null,
								"The Description of the Exercise cannot be empty.\nTry Again.");
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"You have already entered this Description for another Exercise.\nYou must enter a different Description for a new Exercise.\n Try again.");
				}
			} else {
				exit = true;
				userType = 3;
			}
		} while (!exit);

		switch (userType) {
		case 0:
			createStretch(user, userDesc, userMuscle, exerciseID, exercises, counter);
			break;
		case 1:
			createCardio(user, userDesc, userMuscle, exerciseID, exercises, counter);
			break;
		case 2:
			createWeightTraining(user, userDesc, userMuscle, exerciseID, exercises, counter);
			break;
		case 3:
			JOptionPane.showMessageDialog(null, "Returning to Exercise Menu.");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Unexpected Error.");
		}
	}

	/**
	 * A method to create a stretch exercise.
	 * 
	 * @param user
	 *            An Object of the user
	 * @param userDesc
	 *            The description of the exercise that the user has given
	 * @param userMuscle
	 *            The muscle group the user has selected of this exercise
	 * @param exerciseID
	 *            The IF of this exercise
	 * @param exercises
	 *            All the exercises for this user
	 * @param counter
	 *            Keeps track of all the Stretch, cardio, and weight training
	 *            exercises of this user
	 */
	private static void createStretch(User user, String userDesc, String userMuscle, int exerciseID,
			LinkedList<Exercise> exercises, Counter counter) {
		boolean exit = false;

		JTextField instructions = new JTextField();
		Object[] inputFields = { "Instructions:", instructions };
		Stretch newExercise = new Stretch(exerciseID, userDesc, userMuscle);

		do {
			int option = JOptionPane.showConfirmDialog(null, inputFields, "Create Stretch Exercise",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				if (newExercise.setInstructions(instructions.getText())) {
					exit = true;
					try {
						FileSys.append(FileSys.EXERCISE_FILE,
								"Email: " + user.getEmail() + ", " + newExercise.toString());
						exercises.add(newExercise);
						counter.incStretchCount();
						FileSys.incrementID(FileSys.EXERCISE_ID);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"The Instructions of the Exercise cannot be empty.\nTry Again.");
				}
			} else {
				exit = true;
			}
		} while (!exit);
	}

	/**
	 * A method to create a cardiovascular exercise.
	 * 
	 * @param user
	 *            An Object of the user
	 * @param userDesc
	 *            The description of the exercise that the user has given
	 * @param userMuscle
	 *            The muscle group the user has selected of this exercise
	 * @param exerciseID
	 *            The IF of this exercise
	 * @param exercises
	 *            All the exercises for this user
	 * @param counter
	 *            Keeps track of all the Stretch, cardio, and weight training
	 *            exercises of this user
	 * @throws IllegalArgumentException
	 */
	private static void createCardio(User user, String userDesc, String userMuscle, int exerciseID,
			LinkedList<Exercise> exercises, Counter counter) throws IllegalArgumentException {
		boolean exit = false;

		Cardiovascular newExercise = new Cardiovascular(exerciseID, userDesc, userMuscle);

		JTextField duration = new JTextField();
		JTextField setting = new JTextField();
		Object[] inputFields = { "Duration:", duration, "Setting:", setting };

		do {
			int option = JOptionPane.showConfirmDialog(null, inputFields, "Create Cardiovascular Exercise",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				if (!duration.getText().isEmpty() && duration.getText().chars().allMatch(Character::isDigit)
						&& newExercise.setDuration(Integer.parseInt(duration.getText()))) {
					if (!setting.getText().isEmpty() && newExercise.setSetting(setting.getText())) {
						exit = true;
						try {
							FileSys.append(FileSys.EXERCISE_FILE,
									"Email: " + user.getEmail() + ", " + newExercise.toString());
							exercises.add(newExercise);
							counter.incCardioCount();
							FileSys.incrementID(FileSys.EXERCISE_ID);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "The Setting of the Exercise cannot be empty.\nTry Again.");
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"The Duration of the Exercise must be a number greater than 0.\nTry Again.");
				}
			} else {
				exit = true;
			}
		} while (!exit);
	}

	/**
	 * A method to create a weight training exercise.
	 * 
	 * @param user
	 *            An Object of the user
	 * @param userDesc
	 *            The description of the exercise that the user has given
	 * @param userMuscle
	 *            The muscle group the user has selected of this exercise
	 * @param exerciseID
	 *            The IF of this exercise
	 * @param exercises
	 *            All the exercises for this user
	 * @param counter
	 *            Keeps track of all the Stretch, cardio, and weight training
	 *            exercises of this user
	 */
	private static void createWeightTraining(User user, String userDesc, String userMuscle, int exerciseID,
			LinkedList<Exercise> exercises, Counter counter) {
		boolean exit = false;
		WeightTraining newExercise = new WeightTraining(exerciseID, userDesc, userMuscle);

		JTextField weight = new JTextField();
		JTextField reps = new JTextField();
		Object[] inputFields = { "Enter Weight (lbs) ", weight, "Enter Number of Reps", reps };

		do {
			int option = JOptionPane.showConfirmDialog(null, inputFields, "Create Weight Training Exercise",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				if (!weight.getText().isEmpty() && weight.getText().chars().allMatch(Character::isDigit)
						&& newExercise.setWeight(Integer.parseInt(weight.getText()))) {
					if (!reps.getText().isEmpty() && reps.getText().chars().allMatch(Character::isDigit)
							&& newExercise.setReps(Integer.parseInt(reps.getText()))) {
						exit = true;
						try {
							FileSys.append(FileSys.EXERCISE_FILE,
									"Email: " + user.getEmail() + ", " + newExercise.toString());
							exercises.add(newExercise);
							counter.incWeightCount();
							FileSys.incrementID(FileSys.EXERCISE_ID);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "The reps provided was invalid.\nTry again.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "The weight provided was invalid.\nTry again.");
				}
			} else {
				exit = true;
			}
		} while (!exit);
	}

	/**
	 * A menu that allows the user to Generate a random workout, create a new
	 * workout, or view their workouts
	 * 
	 * @param user
	 *            an object of the user
	 * @param exercises
	 *            all the exercises of this user
	 * @param counter
	 *            Keeps track of all the Stretch, cardio, and weight training
	 *            exercises of this user
	 * @throws FileNotFoundException
	 */
	private static void workoutSubMenu(User user, LinkedList<Exercise> exercises, Counter counter)
			throws FileNotFoundException {
		String[] options = { "Generate Random Workout", "Create Custom Workout", "View Workouts", "Return" };
		boolean exit = false;

		do {
			JTextArea textArea = new JTextArea(30, 80);

			switch (JOptionPane.showOptionDialog(null, "My Workouts Sub-Menu", "Workout Keeper",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0])) {
			case 0:
				generateRandomWorkout(user, exercises, Validate.canMakeWorkout(counter));
				break;
			case 1:
				createWorkout(user, exercises, Validate.canMakeWorkout(counter));
				break;
			case 2:
				String longMessage = getUserWorkouts(user);
				if (longMessage.length() < 60) {
					JOptionPane.showMessageDialog(null, "You do not have any Workouts.");
				} else if (exercises.size() > 30) {
					textArea.setText(longMessage);
					textArea.setEditable(false);
					JScrollPane scrollPane = new JScrollPane(textArea);
					JOptionPane.showMessageDialog(null, scrollPane);
				} else {
					JOptionPane.showMessageDialog(null, getUserWorkouts(user));
				}
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

	/**
	 * A method that prompts the user to select exercises to build a workout. If the
	 * same workout exists for this user, the workout will not be made, and they
	 * will see a message explaing workouts cannot duplicate
	 * 
	 * @param user
	 *            an object of the user
	 * @param exercises
	 *            all the exercises for this user
	 * @param canMakeWorkout
	 *            A check to validate of the user has enough exercises to make a
	 *            workout
	 * @throws HeadlessException
	 * @throws FileNotFoundException
	 */
	private static void createWorkout(User user, LinkedList<Exercise> exercises, boolean canMakeWorkout)
			throws HeadlessException, FileNotFoundException {
		if (canMakeWorkout) {
			int workoutID = Integer.parseInt(FileSys.readLine(FileSys.PATH + FileSys.WORKOUT_ID));

			LinkedList<Exercise> workout = new LinkedList<Exercise>();
			LinkedList<Exercise> stretches = SortSearch.returnTypeList(exercises, Exercise.EXERCISE_TYPE[0]);
			LinkedList<Exercise> cardios = SortSearch.returnTypeList(exercises, Exercise.EXERCISE_TYPE[1]);
			LinkedList<Exercise> weights = SortSearch.returnTypeList(exercises, Exercise.EXERCISE_TYPE[2]);

			workout.add(pickExercise(stretches, "Pick your First Stretch Exercise", workout));
			removeSelection(stretches, workout.peekLast());
			workout.add(pickExercise(stretches, "Pick your Second Stretch Exercise", workout));
			removeSelection(stretches, workout.peekLast());
			workout.add(pickExercise(stretches, "Pick your Third Stretch Exercise", workout));
			removeSelection(stretches, workout.peekLast());

			workout.add(pickExercise(cardios, "Pick your First and only Cardio Exercise", workout));
			removeSelection(cardios, workout.peekLast());

			workout.add(pickExercise(weights, "Pick your First Weight Training Exercise", workout));
			removeSelection(weights, workout.peekLast());
			workout.add(pickExercise(weights, "Pick your Second Weight Training Exercise", workout));
			removeSelection(weights, workout.peekLast());
			workout.add(pickExercise(weights, "Pick your Third Weight Training Exercise", workout));
			removeSelection(weights, workout.peekLast());
			workout.add(pickExercise(weights, "Pick your Fourth Weight Training Exercise", workout));
			removeSelection(weights, workout.peekLast());
			workout.add(pickExercise(weights, "Pick your Fifth Weight Training Exercise", workout));
			removeSelection(weights, workout.peekLast());

			Exercise[] temp = new Exercise[workout.size()];
			Iterator<Exercise> it = workout.iterator();
			int x = 0;
			while (it.hasNext()) {
				temp[x++] = it.next();
			}
			Workout newWorkout = new Workout(workoutID, temp);
			if (duplicateWorkout(user, newWorkout)) {
				JOptionPane.showMessageDialog(null,
						"Duplicate Workouts cannot be created, this workouts already exsists.\nTo make a new workout, it must be unique and have different Exercises from other workouts.");
			} else {
				addWorkoutToFile(user, newWorkout);
				JOptionPane.showMessageDialog(null, "The following workout has been saved:\n" + newWorkout.toString()
						+ "\n\n" + newWorkout.printExercises());
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"You do not currently have enough Exercises created inorder to generate a Random Workout.\nYou must return to the Exercise menu to Create new Exercises.");
		}
	}

	/**
	 * A method to check if the workout passed in, is already in the file for this
	 * user
	 * 
	 * @param user
	 *            an object of this user
	 * @param newWorkout
	 *            the new workout the user has created
	 * @return true if the workout is publicated, false if this workout is unique
	 * @throws FileNotFoundException
	 */
	private static boolean duplicateWorkout(User user, Workout newWorkout) throws FileNotFoundException {
		Object[] fileWorkouts = FileSys.readWorkouts(user).toArray();
		ArrayList<Object> tempWorkouts = new ArrayList<Object>(Arrays.asList(fileWorkouts));
		String exercises = newWorkout.toString();
		exercises = exercises.substring(16, exercises.length());

		ListIterator<Object> it = tempWorkouts.listIterator();
		String temp = "";
		while (it.hasNext()) {
			temp = it.next().toString();
			if (temp.contains("Email: " + user.getEmail()) && temp.contains(exercises)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * A method that will remove the selected exercise, so the user does not see
	 * again when picking other exercises for their workout
	 * 
	 * @param exercise
	 *            all the exercises for this user
	 * @param picked
	 *            the Exercise the user has picked to add to the workout
	 */
	private static void removeSelection(LinkedList<Exercise> exercise, Exercise picked) {
		Iterator<Exercise> it = exercise.iterator();
		boolean found = false;
		while (it.hasNext() && !found) {
			if (it.next().equals(picked)) {
				it.remove();
			}
		}
	}

	/**
	 * A method that adds a workout to the Workouts file
	 * 
	 * @param user
	 *            an object of the user
	 * @param workout
	 *            the newly created workout by the user
	 */
	private static void addWorkoutToFile(User user, Workout workout) {
		try {
			FileSys.append(FileSys.WORKOUT_FILE, "Email: " + user.getEmail() + ", " + workout.toString());
			FileSys.incrementID(FileSys.WORKOUT_ID);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * An input menu that will ask the user to pick an exercise, that will be added
	 * to a workout
	 * 
	 * @param exercises
	 *            all the exercises of this user
	 * @param msg
	 *            a message that will prompt the user to enter an exercise
	 * @param workout
	 *            A list of all the workouts of this user
	 * @return the exercise the user has picked to add to the workout
	 */
	private static Exercise pickExercise(LinkedList<Exercise> exercises, String msg, LinkedList<Exercise> workout) {
		boolean exit = false;
		Exercise selection = null;
		String alreadySelected = "";
		if (workout == null) {
			alreadySelected = "Select your first Exercise.";
		} else {
			Iterator<Exercise> iter = workout.iterator();
			alreadySelected = "Exercise already Added:\n";
			while (iter.hasNext()) {
				alreadySelected += iter.next() + "\n";
			}
			alreadySelected += "\n";
		}

		Object[] array_exerc = exercises.toArray();
		JComboBox<?> dropDown = new JComboBox<Object>(array_exerc);
		Object[] inputFields = { alreadySelected, msg, dropDown, };

		do {
			int option = JOptionPane.showConfirmDialog(null, inputFields, "Select an Exercise",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				selection = (Exercise) array_exerc[dropDown.getSelectedIndex()];
				exit = true;
			} else {
				exit = false;
			}
		} while (!exit);

		return selection;
	}

	/**
	 * A method that generates a random workout
	 * 
	 * @param user
	 *            an object of the user
	 * @param exercises
	 *            all the exercises of the user
	 * @param canMakeWorkout
	 *            a check to see if the user has enough exercises to make a workout
	 * @throws FileNotFoundException
	 */
	private static void generateRandomWorkout(User user, LinkedList<Exercise> exercises, boolean canMakeWorkout)
			throws FileNotFoundException {
		if (canMakeWorkout) {
			int workoutID = Integer.parseInt(FileSys.readLine(FileSys.PATH + FileSys.WORKOUT_ID));

			LinkedList<Exercise> workout = new LinkedList<Exercise>();
			LinkedList<Exercise> stretches = SortSearch.returnTypeList(exercises, Exercise.EXERCISE_TYPE[0]);
			LinkedList<Exercise> cardios = SortSearch.returnTypeList(exercises, Exercise.EXERCISE_TYPE[1]);
			LinkedList<Exercise> weights = SortSearch.returnTypeList(exercises, Exercise.EXERCISE_TYPE[2]);

			workout.add(pickRandomWorkout(stretches, workout));
			removeSelection(stretches, workout.peekLast());
			workout.add(pickRandomWorkout(stretches, workout));
			removeSelection(stretches, workout.peekLast());
			workout.add(pickRandomWorkout(stretches, workout));
			removeSelection(stretches, workout.peekLast());

			workout.add(pickRandomWorkout(cardios, workout));
			removeSelection(cardios, workout.peekLast());

			workout.add(pickRandomWorkout(weights, workout));
			removeSelection(weights, workout.peekLast());
			workout.add(pickRandomWorkout(weights, workout));
			removeSelection(weights, workout.peekLast());
			workout.add(pickRandomWorkout(weights, workout));
			removeSelection(weights, workout.peekLast());
			workout.add(pickRandomWorkout(weights, workout));
			removeSelection(weights, workout.peekLast());
			workout.add(pickRandomWorkout(weights, workout));
			removeSelection(weights, workout.peekLast());

			Exercise[] temp = new Exercise[workout.size()];
			Iterator<Exercise> it = workout.iterator();
			int x = 0;
			while (it.hasNext()) {
				temp[x++] = it.next();
			}

			Workout newWorkout = new Workout(workoutID, temp);
			if (duplicateWorkout(user, newWorkout)) {
				JOptionPane.showMessageDialog(null,
						"Duplicate Workouts cannot be created, this workouts already exsists.\nTo make a new workout, it must be unique and have different Exercises from other workouts.");
			} else {
				addWorkoutToFile(user, newWorkout);
				JOptionPane.showMessageDialog(null, "The following workout has been saved:\n" + newWorkout.toString()
						+ "\n\n" + newWorkout.printExercises());
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"You do not currently have enough Exercises created inorder to generate a Random Workout.\nYou must return to the Exercise menu to Create new Exercises.");
		}
	}

	/**
	 * A method that returns a random workout
	 * 
	 * @param exercises
	 *            all the workouts of this user
	 * @param workout
	 *            a list of all the workouts for this user
	 * @return
	 */
	private static Exercise pickRandomWorkout(LinkedList<Exercise> exercises, LinkedList<Exercise> workout) {
		Object[] array_exerc = exercises.toArray();

		int len = exercises.size();
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(len);

		Exercise selection = (Exercise) array_exerc[randomInt];
		return selection;
	}

	/**
	 * Reads workout records from the workout database(text file) belonging to a
	 * single user and returns them in an array list
	 * 
	 * @param user,
	 *            an object of the user
	 * @return a list of all the workouts for this user
	 */
	private static String getUserWorkouts(User user) throws FileNotFoundException {
		String userWorkouts = "Workouts for user: " + user.getEmail();
		Scanner workoutFile = null;
		Scanner exerciseFile = null;
		try {
			workoutFile = new Scanner(
					new BufferedReader(new FileReader(new File(FileSys.PATH + FileSys.WORKOUT_FILE))));

			// if no exception thrown, proceed to file reading
			String workoutLine = "";
			String exerciseLine = "";
			// keep iterating while there are more records to read
			while (workoutFile.hasNextLine()) {
				// grab next record
				workoutLine = workoutFile.nextLine();
				// add to array list if it belongs to the input user
				if (FileSys.getSubString(workoutLine, "Email: ").equals(user.getEmail())) {
					// int workoutID = Integer.parseInt(FileSys.getSubString(workoutLine, "Workout
					// ID: "));
					userWorkouts += "\n\nWorkout ID: " + FileSys.getSubString(workoutLine, "Workout ID: ");
					boolean stretchHeader = false, cardioHeader = false, weightHeader = false;
					for (int i = 1; i <= 9; i++) {
						boolean IDfound = false;
						exerciseFile = new Scanner(
								new BufferedReader(new FileReader(new File(FileSys.PATH + FileSys.EXERCISE_FILE))));
						while (exerciseFile.hasNextLine() && !IDfound) {
							// Get the exercise ID from the line for this user in the workout file
							String exerciseID = FileSys.getSubString(workoutLine, "Exercise #" + i + " ID: ");
							exerciseLine = exerciseFile.nextLine();
							if (FileSys.getSubString(exerciseLine, "ID: ").equals(exerciseID)) {
								if (FileSys.getSubString(exerciseLine, "Type: ").equals(Exercise.EXERCISE_TYPE[0])) {
									if (!stretchHeader) {
										userWorkouts += "\n" + FileSys.getSubString(exerciseLine, "Type: ")
												+ " Exercises:";
										stretchHeader = true;
									}
								}
								if (FileSys.getSubString(exerciseLine, "Type: ").equals(Exercise.EXERCISE_TYPE[1])) {
									if (!cardioHeader) {
										userWorkouts += "\n" + FileSys.getSubString(exerciseLine, "Type: ")
												+ " Exercises:";
										cardioHeader = true;
									}
								}
								if (FileSys.getSubString(exerciseLine, "Type: ").equals(Exercise.EXERCISE_TYPE[2])) {
									if (!weightHeader) {
										userWorkouts += "\n" + FileSys.getSubString(exerciseLine, "Type: ")
												+ " Exercises:";
										weightHeader = true;
									}
								}
								userWorkouts += "\n\t"
										+ exerciseLine.substring(exerciseLine.indexOf("ID: "), exerciseLine.length());
								IDfound = true;
							}
						}
						exerciseFile.close();
					}
				}
			}
		} catch (FileNotFoundException e) {
			throw e;
		}
		workoutFile.close();

		return userWorkouts;
	}
}