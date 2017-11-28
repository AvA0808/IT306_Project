/**
 * @author Aleksandar Atanasov - G00716250 - aatanas2@gmu.edu
 * @author Victoria Chang - G00947241 - vchang3@masonlive.gmu.edu
 * 
 */
public class Workout {
	//system-generated ID to track workouts
	private static int workoutID;
	//the workout's own unique ID
	private int ID;
	//can never be partially filled, so does not need a counter
	private Exercise[] workout;
	//constants indicating the exact number of each exercise type needed in each workout
	public static final int MAX_CARDIO = 1;
	public static final int MAX_STRETCH = 3;
	public static final int MAX_WEIGHT = 5;
	
	/*constructor assigns the newest ID and increments the workout counter */
	public Workout(int ID, Exercise[] exercises) {
		this.ID = ID;
		this.workout = exercises;
	}
	
	public int getWorkoutID() {
		return workoutID;
	}
	
	public int getID() {
		return ID;
	}
	
	/* Returns a copy of the array of exercises constituting the workout
	 * @return copy A copy of the array of exercises constituting the workout */
	public Exercise[] getWorkout() {
		//copy the array and its elements
		Exercise[] copy = new Exercise[this.workout.length];
		for(int i = 0; i < this.workout.length; i++) {
			copy[i] = this.workout[i];
		}
		return copy;
	}
	
	/* Copies over the contents of inputted array of exercises
	 * @param workout The inputted array of exercises to create a workout using */
	public void setWorkout(Exercise[] workout) {
		for(int i = 0; i < this.workout.length; i++) {
			this.workout[i] = workout[i];
		}
	}
	
	public String printExercises() {
		String list = "Workout ID: " + this.ID + "\n";
		for(int i = 0; i < this.workout.length; i++) {
			list += this.workout[i] + "\n";
		}
		return list;
	}
	
	/* Prepares a record for the workout */
	public String toString() {
		String list = "Workout ID: " + this.ID;
		for(int i = 0; i < this.workout.length; i++) {
			list += ", Exercise #" + (i + 1) + " ID: " + this.workout[i].getID();
		}
		return list;
	}
}
