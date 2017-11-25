import java.io.*;
import java.util.*;
public class SortSearch {	
	/*
	 * Reads the logged-in user's exercises from a file and returns them as objects stored in a linked list
	 * @param user The logged in user
	 * @return list A list of all of the user's exercises
	 */
	public static LinkedList<Exercise> readExercise(User user) throws FileNotFoundException {
		//a linked list containing all the stored exercises for logged-in user
		LinkedList<Exercise> list = new LinkedList<Exercise>();
		
		Scanner scanner = null;
		//try to open the exercise file for reading
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(new File(FileSys.PATH + FileSys.EXERCISE_FILE))));
			//if no exception thrown, proceed to file reading
			while(scanner.hasNextLine()) {
				//grab the next line
				String nextLine = scanner.nextLine();
				//grab the email listed in that line
				String current_email = FileSys.getSubString(nextLine, "Email: ");
				//check if user's email matches the email listed; 
				//if they match create exercise into an object to be stored in the linked list
				if(current_email.equalsIgnoreCase(user.getEmail())) {
					Exercise exercise = createExerciseFromFile(nextLine);
					//add the exercise to the list
					list.add(exercise);
				}
			}
			scanner.close();
		}
		catch(FileNotFoundException e) {
			throw e;
		}
		return list;
	}
	
	/*
	 * Creates the appropriate exercise object (Cardiovascular, Stretch, WeightTraining) and returns it
	 * @param record The String line to parse for object fields
	 * @return exercise The exercise object created from the input record
	 */
	public static Exercise createExerciseFromFile(String record) {
		Exercise exercise = null;
		//get the exercise's type for setting unique type fields
		String type = FileSys.getSubString(record, "Type: ");
		//first grab fields common to all exercises: ID, description, muscle
		int id = Integer.parseInt(FileSys.getSubString(record, "ID: "));
		String description = FileSys.getSubString(record, "Description: ");
		String muscle = FileSys.getSubString(record, "Muscle Group: ");
		
		if(type.equalsIgnoreCase("Cardiovascular")) {
			exercise = new Cardiovascular(description, muscle);
			exercise.setID(id);
			//downcast and set cardiovascular specific fields
			if(exercise instanceof Cardiovascular) {
				Cardiovascular cardio = (Cardiovascular) exercise;
				//call cardio specific methods
				cardio.setDuration(Integer.parseInt(FileSys.getSubString(record, "Duration: ")));
				cardio.setSetting(FileSys.getSubString(record, "setting: "));
				//upcast back again to return as exercise
				exercise = (Exercise) cardio;
			}
		}
		/*else if(type.equalsIgnoreCase("Stretch")) {
			
		}*/
		/*else if(type.equalsIgnoreCase("Weight Training")) {
			
		}*/
		return exercise;
	}
	
	/*
	 * Sorts the input array based on input sorting method
	 * @param
	 * @param
	 */
	/*public static void sortingMethod(Exercise[] array_exerc, String howSort) {
		//concatenate the toString() of each exercise type to respective String variable (all cardio, all stretches, etc. as Alex suggested)
		if(howSort.equalsIgnoreCase("Type")) {
			for
		}
	}*/
}
