import java.io.*;
import java.util.*;

/**
 * @author Aleksandar Atanasov - G00716250 - aatanas2@gmu.edu
 * @author Victoria Chang - G00947241 - vchang3@masonlive.gmu.edu
 * This class contains methods that handle searching and sorting tasks accomplished throughout the program.
 */
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
	 * @counter A counter to keep track of number of exercise objects instantiated
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
	 * @param list A linked list containing all of the user's exercises
	 * @param howSort The sorting method as a String (type, muscle group, duration)
	 * @return display String made by concatenating toString() returns of appropriate exercise objects
	 */
	public static String sortingMethod(LinkedList<Exercise> list, String howSort) {
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
					cardio += array_exerc[i].toString() + "\n";
				}
				else if(array_exerc[i] instanceof Stretch) {
					stretch += array_exerc[i].toString() + "\n";
				}
				else if(array_exerc[i] instanceof WeightTraining) {
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
			//iterate through input linked list, which contains all exercises belonging to the logged-in user
			Iterator<Exercise> it = list.iterator();
			while(it.hasNext()) {
				//grab the next exercise in the list
				Exercise current = (Exercise)it.next();
				//check what muscle group it belongs to
				for(int i = 0; i < Exercise.MUSCLE_GROUP.length; i++) {
					//place it in appropriate bucket
					if(current.getMuscle().equalsIgnoreCase(Exercise.MUSCLE_GROUP[i])) {
						//safe to downcast to specific exercise type
						if(current instanceof Cardiovascular) {
							Cardiovascular cardio = (Cardiovascular)current;
							list_muscle.get(i).add(cardio.toString());
						}
						else if(current instanceof Stretch) {
							Stretch stretch = (Stretch)current;
							list_muscle.get(i).add(stretch.toString());
						}
						else if(current instanceof WeightTraining) {
							WeightTraining training = (WeightTraining)current;
							list_muscle.get(i).add(training.toString());
						}
						break;
					}
				}
			}
			
			//once input list is finished being processed, proceed to concatenate all toString() to display
			for(int x = 0; x < list_muscle.size(); x++) {
				if(!list_muscle.get(x).isEmpty()) {
					//for each bucket, iterate through if not empty
					Iterator<?> it_2 = list_muscle.get(x).iterator();
					while(it_2.hasNext()) {
						//concatenate each toString to display
						display += (String)it_2.next() + "\n";
					}
					display += "\n";
				}
			}
		}
		//only sorts cardio exercises based on duration
		else if(howSort.equalsIgnoreCase("Duration")) {
			display += "SORTED BY CARDIOVASCULAR DURATION\n\n";
			//get a linked list of only cardio exercises
			LinkedList<Exercise> subList = returnTypeList(list, "Cardiovascular");
			//pass the sublist to be sorted based on duration and returned as a String
			display += sortDuration(subList);
		}
		return display;
	}
	
	/*
	 * Sorts the input sublist of cardiovascular exercises belonging to one user by duration
	 * @param subList A sub-linked list containing only cardio exercises belonging to one user
	 * @return display A String created from concatenating information across all cardio exercises
	 */
	public static String sortDuration(LinkedList<Exercise> subList) {
		String display = "";
		//create a tree set to pass in Comparator that compares by duration
		TreeSet<Cardiovascular> tree = new TreeSet<Cardiovascular>(new ComparatorByDuration());
		//add each item in the sublist of cardio exercises into the tree, which will sort by duration automatically
		Iterator<Exercise> it = subList.iterator();
		while(it.hasNext()) {
			Object temp = it.next();
			if(temp instanceof Cardiovascular) {
				//safe to downcast
				Cardiovascular current = (Cardiovascular)temp;
				//add item to tree
				tree.add(current);
			}
		}
		//turn tree into array so iteration can be done in order
		Object[] tree_array = tree.toArray();
		//iterate through the tree's contents and concatenate toString() together
		for(int i = 0; i < tree_array.length; i++) {
			//safe to downcast
			Cardiovascular curr_cardio = (Cardiovascular)tree_array[i];
			display += curr_cardio.toString() + "\n";
		}
		
		if(display == "") {
			display = "YOu do not have any Cardiovascular Exercises to sort.";
		}
		
		return display;
	}
	
	/*
	 * Returns a linked list (sublist of input linked list) of a specific type of exercise
	 * @param list A linked list of all of the logged-in user's exercises
	 * @param type The type of exercises to filter out
	 * @return subList A sublist of the input list containing a specific type of exercise
	 */
	public static LinkedList<Exercise> returnTypeList(LinkedList<Exercise> list, String type) {
		LinkedList<Exercise> subList = new LinkedList<Exercise>();
		//iterate through the linked list and extract every record that falls under the input type
		Iterator<Exercise> it = list.iterator();
		while(it.hasNext()) {
			Exercise current = (Exercise)it.next();
			if(current.getClass().getName().equals(type)) {
				subList.add(current);
			}
		}
		return subList;
	}
	
	/*
	 * Searches the cardiovascular exercises belonging to the user based on a input duration
	 * @param list A linked list of all of the logged-in user's exercises
	 * @param duration The input duration to search for
	 * @return display The display of results by concatenating the toString() of all applicable exercises
	 */
	public static String searchDuration(LinkedList<Exercise> list, int duration) {		
		String display = "SEARCH BY DURATION RESULTS\n\n";
		boolean found = false;
		//retrieve a sublist of the user's cardiovascular exercises
		LinkedList<Exercise> subList = returnTypeList(list, "Cardiovascular");
		Iterator<Exercise> it = subList.iterator();
		//iterate through and examine each record's duration (may be multiple matches)
		while(it.hasNext()) {
			Cardiovascular current = (Cardiovascular)it.next();
			if(current.getDuration() == duration) {
				//if match, concatenate its toString() to display String
				display += current.toString() + "\n";
				found = true;
			}
		}
		
		if(!found) {
			display = "You do not have any Exercises with the Duration of " + duration + " minutes.";
		}
		return display;
	}
}
