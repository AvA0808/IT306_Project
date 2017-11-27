/**
 * @author Aleksandar Atanasov - G00716250 - aatanas2@gmu.edu
 * @author Victoria Chang - G00947241 - vchang3@masonlive.gmu.edu
 * 
 */
public class Stretch extends Exercise {
	private String instructions;

	/*
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
	 * @param instructions
	 *            the instructions to set
	 */
	public boolean setInstructions(String instructions) {
		if (instructions.equals("")) {
			return false;
		} else {
			this.instructions = instructions;
			return true;
		}
	}

	/* Prepares a record of the stretch exercise's information and returns it */
	public String toString() {
		return ("Type: " + this.getClass().getName() + ", " + super.toString() + "Instructions: "
				+ this.instructions);
	}
}
