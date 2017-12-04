/**
 * @author Aleksandar Atanasov - G00716250 - aatanas2@gmu.edu
 * @author Victoria Chang - G00947241 - vchang3@masonlive.gmu.edu This class
 *         represents a stretch type of exercise.
 */
public class Stretch extends Exercise {
	private String instructions;

	/**
	 * constructor that sets the ID and increments the exercise counter
	 */
	public Stretch() {
		super();
	}

	public Stretch(int ID, String description, String muscle) {
		super(ID, description, muscle);
	}

	public Stretch(int ID, String description, String muscle, String instructions) {
		this(ID, description, muscle);
		this.instructions = instructions;
	}

	/**
	 * @return the instructions
	 */
	public String getInstructions() {
		return instructions;
	}

	/**
	 * Validates and sets the instructions
	 * 
	 * @param instructions
	 *            the instructions to set
	 * @return boolean indicating whether instructions set successfully or not
	 */
	public boolean setInstructions(String instructions) {
		if (instructions.equals("")) {
			return false;
		} else {
			this.instructions = instructions;
			return true;
		}
	}

	/*
	 * Prepares a record of the stretch exercise's information and returns it
	 * 
	 * @return String containing the stretch's information
	 */
	public String toString() {
		return ("Type: " + this.getClass().getName() + ", " + super.toString() + "Instructions: " + this.instructions);
	}
}
