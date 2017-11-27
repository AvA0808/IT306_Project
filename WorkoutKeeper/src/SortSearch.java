import java.io.*;
import java.util.*;
public class SortSearch {	
	/*
	 * Reads the logged-in user's exercises from a file and returns them as objects stored in a linked list
	 * @param user The logged in user
	 * @return list A list of all of the user's exercises
	 */
	public static LinkedList<Exercise> readExercise(User user, Counter counter) throws FileNotFoundException {
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
					Exercise exercise = createExerciseFromFile(nextLine, counter);
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
	public static Exercise createExerciseFromFile(String record, Counter counter) {
		Exercise exercise = null;
		//get the exercise's type for setting unique type fields
		String type = FileSys.getSubString(record, "Type: ");
		//first grab fields common to all exercises: ID, description, muscle
		int id = Integer.parseInt(FileSys.getSubString(record, "ID: "));
		String description = FileSys.getSubString(record, "Description: ");
		String muscle = FileSys.getSubString(record, "Muscle Group: ");
		
		if(type.equalsIgnoreCase("Cardiovascular")) {
			exercise = new Cardiovascular(id, description, muscle);
			//downcast and set cardiovascular specific fields
			if(exercise instanceof Cardiovascular) {
				Cardiovascular cardio = (Cardiovascular) exercise;
				//call cardio specific methods
				cardio.setDuration(Integer.parseInt(FileSys.getSubString(record, "Duration: ")));
				cardio.setSetting(FileSys.getSubString(record, "setting: "));
				//upcast back again to return as exercise
				exercise = (Exercise) cardio;
				counter.incCardioCount();
			}
		}
		else if(type.equalsIgnoreCase("Stretch")) {
			exercise = new Stretch(id, description, muscle);
			if(exercise instanceof Stretch) {
				Stretch stretch = (Stretch) exercise;
				//call Stretch specific methods
				stretch.setInstructions(FileSys.getSubString(record, "Instructions: "));
				//upcast back again to return as exercise
				exercise = (Exercise) stretch;
				counter.incStretchCount();
			}
		} else if(type.equalsIgnoreCase("WeightTraining")) {
			exercise = new WeightTraining(id, description, muscle);
			if(exercise instanceof WeightTraining) {
				WeightTraining weight = (WeightTraining) exercise;
				//call WeightTraining specific methods
				weight.setWeight(Integer.parseInt(FileSys.getSubString(record, "Weight: ")));
				weight.setReps(Integer.parseInt(FileSys.getSubString(record, "Reps: ")));
				//upcast back again to return as exercise
				exercise = (Exercise) weight;
				counter.incWeightCount();
			}
		}
		
		return exercise;
	}
	
	/*
	 * Sorts the exercises stored in the input linked list based on input sorting method
	 * @param 
	 * @param
	 * @return display String made by concatenating toString() returns of appropriate exercise objects
	 */
	public static String sortingMethod(LinkedList<Exercise> list, String howSort) throws FileNotFoundException {
		String display = "";
		//for ease of sorting, turn linked list into array (length is 1 more than last element index)
		Object[] array_exerc = list.toArray();
		//concatenate toString() returns of appropriate exercise objects by type
		if(howSort.equalsIgnoreCase("Type")) {
			display += "SORTED BY EXERCISE TYPE\n\n";
			String cardio = "";
			String stretch = "";
			String training = "";
			for(int i = 0; i < array_exerc.length; i++) {
				if(array_exerc[i] instanceof Cardiovascular) {
					//downcast to call toString()
					Cardiovascular c = (Cardiovascular)array_exerc[i];
					cardio += array_exerc[i].toString() + "\n";
				}
				else if(array_exerc[i] instanceof Stretch) {
					//downcast to call toString()
					Stretch s = (Stretch)array_exerc[i];
					stretch += array_exerc[i].toString() + "\n";
				}
				else if(array_exerc[i] instanceof WeightTraining) {
					//downcast to call toString()
					WeightTraining c = (WeightTraining)array_exerc[i];
					training += array_exerc[i].toString() + "\n";
				}
			}
			//concatenate list of cardio exercises first, then stretches, then weight training
			display += cardio + "\n" + stretch + "\n" + training;
		}
		//concatenate toString() returns of appropriate exercise objects by muscle group: bucket sort
		else if(howSort.equalsIgnoreCase("Muscle Group")) {
			display += "SORTED BY EXERCISE MUSCLE GROUP\n\n";
			//an array, providing indexes corresponding to the muscle groups, 
			//holding linked lists in each cell to hold exercises toString() that fall under each muscle group
			ArrayList<LinkedList<String>> list_muscle = new ArrayList<LinkedList<String>>();
			//add the number of buckets (linked list) as there are muscle groups to the array
			for(int x = 0; x < Exercise.MUSCLE_GROUP.length; x++) {
				list_muscle.add(new LinkedList<String>());
			}
			
			//open the proper file to read the exercises from (more efficient than downcasting from input list)
			Scanner scanner = null;
			try {
				scanner = new Scanner(new BufferedReader(new FileReader(new File(FileSys.PATH + FileSys.EXERCISE_FILE))));
				//if no exception thrown, proceed to file reading
				while(scanner.hasNextLine()) {
					//grab the next line
					String nextLine = scanner.nextLine();
					//discard the email portion
					nextLine = nextLine.substring(nextLine.indexOf("Type: "));
					//grab the muscle group listed in that line
					String current_muscle = FileSys.getSubString(nextLine, "Muscle Group: ");
					//check which bucket muscle group belongs in
					if(current_muscle.equalsIgnoreCase(Exercise.MUSCLE_GROUP[0])) {
						//add record to the corresponding bucket
						list_muscle.get(0).add(nextLine);
					}
					else if(current_muscle.equalsIgnoreCase(Exercise.MUSCLE_GROUP[1])) {
						//add record to the corresponding bucket
						list_muscle.get(1).add(nextLine);
					}
					else if(current_muscle.equalsIgnoreCase(Exercise.MUSCLE_GROUP[2])) {
						//add record to the corresponding bucket
						list_muscle.get(2).add(nextLine);
					}
					else if(current_muscle.equalsIgnoreCase(Exercise.MUSCLE_GROUP[3])) {
						//add record to the corresponding bucket
						list_muscle.get(3).add(nextLine);
					}
					else if(current_muscle.equalsIgnoreCase(Exercise.MUSCLE_GROUP[4])) {
						//add record to the corresponding bucket
						list_muscle.get(4).add(nextLine);
					}
					else if(current_muscle.equalsIgnoreCase(Exercise.MUSCLE_GROUP[9])) {
						//add record to the corresponding bucket
						list_muscle.get(9).add(nextLine);
					}
				}
				scanner.close();
				
				//loop through every single cell in the array list
				for(int y = 0; y < Exercise.MUSCLE_GROUP.length; y++) {
					//within every cell, iterate through the linked list if not empty
					if(!list_muscle.get(y).isEmpty()) {
						Iterator it = list_muscle.get(y).iterator();
						while(it.hasNext()) {
							//grab the next record as a String
							String current = (String)it.next();
							//concatenate it to variable we are going to return
							display += current + "\n";
						}
						//add an extra line between muscle groups in display
						display += "\n";
					}
				}
			}
			catch(FileNotFoundException e) {
				throw e;
			}
		}
		return display;
	}
}
