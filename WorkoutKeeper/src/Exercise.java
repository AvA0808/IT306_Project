/* */

public abstract class Exercise {
	// system-generated ID to track the number of exercises across all users
	private static int exerciseID = 0;
	// the exercise's own unique ID
	private int ID;
	private String description;
	private String muscle;
	// names of muscle groups that can be exercised within the application
	public static final String[] MUSCLE_GROUP = { "(Top of Shoulders) Deltoids", "Shoulders", "(Upper Back) Trapezius",
			"(Lower Back) Erector Spinae", "Chest", "(Lats) Latissimus Dorsi", "Biceps", "Triceps", "Forearms", "Abs",
			"Obliques", "Quadriceps", "Hamstrings", "Calves" };

	/* constructor assigns the newest ID and increments the exercise counter */
	public Exercise() {
		this.ID = exerciseID;
		exerciseID++;
	}

	public int getExerciseID() {
		return exerciseID;
	}

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * For file-reading purposes. Sets the ID that is read from the file.
	 * 
	 * @param iD
	 *            the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public boolean setDescription(String description) {
		if (description.equals("")) {
			return false;
		} else {
			this.description = description;
			return true;
		}
	}

	/**
	 * @return the muscle
	 */
	public String getMuscle() {
		return muscle;
	}

	/**
	 * Validates and sets the muscle according to corresponding index
	 * 
	 * @param muscle
	 *            the muscle to set
	 * @return boolean true indicating muscle group successfully set
	 * @throws IllegalArgumentException
	 */
	public boolean setMuscle(int musc_index) throws IllegalArgumentException {
		if (musc_index < 1 || musc_index > MUSCLE_GROUP.length) {
			throw new IllegalArgumentException("Error: Not a valid number corresponding to a muscle group!");
		} else {
			this.muscle = MUSCLE_GROUP[musc_index - 1];
			return true;
		}
	}

	/* toString method with the purpose of being called by its subclasses */
	public String toString() {
		return ("ID: " + this.ID + ", Description: " + this.description + ", Muscle Group: " + this.muscle + ", ");
	}
}
