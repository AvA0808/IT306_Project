import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * @author Aleksandar Atanasov - G00716250 - aatanas2@gmu.edu
 * @author Victoria Chang - G00947241 - vchang3@masonlive.gmu.edu
 * 
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
					if(!Validate.isEmailInList(user.getEmail())) {
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
					}else {
						JOptionPane.showMessageDialog(null, "The Email entered is already in use.\nYou must enter a new email.");
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
		// TODO using the email passed in, read user file and build a user object from it, for the row that matches this email.
		user.setEmail(email);
	}

	public static <E> String loggedIn(User user, String status) throws FileNotFoundException {
		String[] options = { "Exercise", "My Workouts", "Shared Workouts", "My Profile", "Logout" };
		boolean exit = false;
		Counter counter = new Counter();
		
		LinkedList<Exercise> exercises = SortSearch.readExercise(user, counter);
		//TODO remove
		//Prints all elements in the LinkedList
//		exercises.forEach(System.out::println);
		
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
				// TODO sharedWorkouts(user);
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

	private static void exerciseSubMenu(User user, LinkedList<Exercise> exercises, Counter counter) throws HeadlessException, FileNotFoundException {
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
				// TODO cardioSearch();
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
	
	private static void sortExercise(LinkedList<Exercise> exercises) throws HeadlessException, FileNotFoundException {
		String[] options = { "Sort by Type", "Sort by Muscle Group", "Cancel" };
		boolean exit = false;

		do {
			switch (JOptionPane.showOptionDialog(null, "How would you like to sort your Exercises?", "Workout Keeper", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0])) {
			case 0:
				JOptionPane.showMessageDialog(null, SortSearch.sortingMethod(exercises, "Type"));
				break;
			case 1:
				JOptionPane.showMessageDialog(null, SortSearch.sortingMethod(exercises, "Muscle Group"));
				break;
			case 2:
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

			//TODO the first and last name are not being displayed or set with the new user input. password too maybe
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

	private static void createExercise(User user, LinkedList<Exercise> exercises, Counter counter) {
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
			
			//TODO add a check to make sure the description provided is not already in their system
			if (option == JOptionPane.OK_OPTION) {
				if(true) {	
					if (!description.getText().isEmpty()) {
						userDesc = description.getText();
						userMuscle = Exercise.MUSCLE_GROUP[muscle.getSelectedIndex()];
						userType = type.getSelectedIndex();
						exit = true;
					} else {
						JOptionPane.showMessageDialog(null, "The Description of the Exercise cannot be empty.\nTry Again.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "You have already entered this Description for another Exercise.\nYou must enter a different Description for a new Exercise.\n Try again.");
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

	private static void createStretch(User user, String userDesc, String userMuscle, int exerciseID, LinkedList<Exercise> exercises, Counter counter) {
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
						FileSys.append(FileSys.EXERCISE_FILE, "Email: " + user.getEmail() + ", " + newExercise.toString());
						exercises.add(newExercise);
						counter.incStretchCount();
						FileSys.incrementID(FileSys.EXERCISE_ID);
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

	private static void createCardio(User user, String userDesc, String userMuscle, int exerciseID, LinkedList<Exercise> exercises, Counter counter) throws IllegalArgumentException{
		boolean exit = false;
		
		Cardiovascular newExercise = new Cardiovascular(exerciseID, userDesc, userMuscle);
		
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
							FileSys.append(FileSys.EXERCISE_FILE, "Email: " + user.getEmail() + ", " + newExercise.toString());
							exercises.add(newExercise);
							counter.incCardioCount();
							FileSys.incrementID(FileSys.EXERCISE_ID);
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

	private static void createWeightTraining(User user, String userDesc, String userMuscle, int exerciseID, LinkedList<Exercise> exercises, Counter counter) {
		boolean exit = false;
		WeightTraining newExercise = new WeightTraining(exerciseID, userDesc, userMuscle);
		
		JTextField weight = new JTextField();
		JTextField reps = new JTextField();
		Object[] inputFields = { "Enter Weight (lbs) ", weight, "Enter Number of Reps", reps };

		do {
			int option = JOptionPane.showConfirmDialog(null, inputFields, "Create Weight Training Exercise",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				if (!weight.getText().isEmpty() && weight.getText().chars().allMatch( Character::isDigit ) && newExercise.setWeight(Integer.parseInt(weight.getText()))) {
					if (!reps.getText().isEmpty() && reps.getText().chars().allMatch( Character::isDigit ) && newExercise.setReps(Integer.parseInt(reps.getText()))) {
						exit = true;
						try {
							FileSys.append(FileSys.EXERCISE_FILE, "Email: " + user.getEmail() + ", " + newExercise.toString());
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

	private static void workoutSubMenu(User user, LinkedList<Exercise> exercises, Counter counter) throws FileNotFoundException  {
		String[] options = { "Generate Random Workout", "Create Custom Workout", "View Workouts", "Return" };
		boolean exit = false;
		
		do {
			switch (JOptionPane.showOptionDialog(null, "My Workouts Sub-Menu", "Workout Keeper", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0])) {
			case 0:
				generateRandomWorkout(user, exercises, Validate.canMakeWorkout(counter));
				break;
			case 1:
				createWorkout(user, exercises, Validate.canMakeWorkout(counter));
				break;
			case 2:
				//TODO viewWorkouts();
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

	private static void createWorkout(User user, LinkedList<Exercise> exercises, boolean canMakeWorkout) {
		if(canMakeWorkout) {
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
			while(it.hasNext()) {
				temp[x++] = it.next();
			}
			Workout newWorkout = new Workout(workoutID, temp);
			addWorkoutToFile(user, newWorkout);
			JOptionPane.showMessageDialog(null, "The following workout has been saved:\n" + newWorkout.toString() + "\n\n" + newWorkout.printExercises());
		} else {
			JOptionPane.showMessageDialog(null, "You do not currently have enough Exercises created inorder to generate a Random Workout.\nYou must return to the Exercise menu to Create new Exercises.");
		}
	}

	private static void removeSelection(LinkedList<Exercise> exercise, Exercise picked) {
		Iterator<Exercise> it = exercise.iterator();
		boolean found = false;
		while(it.hasNext() && !found) {
			if(it.next().equals(picked)) { 
				it.remove();
			}
		}
	}

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
	
	private static Exercise pickExercise(LinkedList<Exercise> exercises, String msg, LinkedList<Exercise> workout) {
		boolean exit = false;
		Exercise selection = null;
		String alreadySelected = "";
		if(workout == null) {
			alreadySelected = "Select your first Exercise.";
		} else {
			Iterator<Exercise> iter = workout.iterator();
			alreadySelected = "Exercise already Added:\n";
			while(iter.hasNext()) {
				alreadySelected += iter.next() + "\n";
			}
			alreadySelected += "\n";
		}
		
		Object[] array_exerc = exercises.toArray();
		JComboBox<?> dropDown = new JComboBox<Object>(array_exerc);
		Object[] inputFields = { alreadySelected, msg, dropDown,};
		
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
	
	private static void generateRandomWorkout(User user, LinkedList<Exercise> exercises, boolean canMakeWorkout) throws FileNotFoundException {
		if(canMakeWorkout) {
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
			while(it.hasNext()) {
				temp[x++] = it.next();
			}
			Workout newWorkout = new Workout(workoutID, temp);
			addWorkoutToFile(user, newWorkout);
			JOptionPane.showMessageDialog(null, "The following workout has been saved:\n" + newWorkout.toString() + "\n\n" + newWorkout.printExercises());

		} else {
			JOptionPane.showMessageDialog(null, "You do not currently have enough Exercises created inorder to generate a Random Workout.\nYou must return to the Exercise menu to Create new Exercises.");
		}
	}
	
	private static Exercise pickRandomWorkout(LinkedList<Exercise> exercises, LinkedList<Exercise> workout) {		
		Object[] array_exerc = exercises.toArray();
		
		int len = exercises.size(); 
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(len);
		
		Exercise selection = (Exercise) array_exerc[randomInt];
		return selection;
	}
	
	
	public static ArrayList<String> viewWorkouts(User user) throws FileNotFoundException {
		Scanner scanner = new Scanner(new BufferedReader(new FileReader(new File(FileSys.PATH + FileSys.WORKOUT_FILE))));
		try {
			
		}
		catch( ) {
			
		}
	}
}