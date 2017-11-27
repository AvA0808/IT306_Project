/**
 * @author Aleksandar Atanasov - G00716250 - aatanas2@gmu.edu
 * @author Victoria Chang - G00947241 - vchang3@masonlive.gmu.edu
 * 
 */
public class WeightTraining extends Exercise {
	// the weight required for the exercise equipment in pounds (lbs)
	private int weight;
	// the number of repetitions the weight will be lifted
	private int reps;

	/*
	 * constructor that sets the ID and increments the exercise counter
	 */
	public WeightTraining() {
		super();
	}

	public WeightTraining(int ID, String description, String muscle) {
		super(ID, description, muscle);
	}

	public WeightTraining(int ID, String muscle, String description, int weight, int reps) {
		this(ID, description, muscle);
		this.weight = weight;
		this.reps = reps;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Validates and sets the weight required for the exercise equipment
	 * 
	 * @param weight
	 *            the weight to set
	 * @return boolean true indicating that setting weight was successful
	 * @throws IllegalArgumentException
	 */
	public boolean setWeight(int weight){
		if (weight <= 0) {
			return false;
		} else {
			this.weight = weight;
			return true;
		}
	}

	/**
	 * @return the reps
	 */
	public int getReps() {
		return reps;
	}

	/**
	 * Validates and sets the number of repetitions the weight will be lifted
	 * 
	 * @param reps
	 *            the reps to set
	 * @return boolean true indicating that setting reps was successful
	 * @throws IllegalArgumentException
	 */
	public boolean setReps(int reps) {
		if (reps <= 0) {
			return false;
		} else {
			this.reps = reps;
			return true;
		}
	}

	/*
	 * Prepares a record of the weight training exercise's information and
	 * returns it
	 */
	public String toString() {
		return ("Type: " + this.getClass().getName() + ", " + super.toString() + "Weight: " + this.weight + ", Reps: "
				+ this.reps);
	}
}
