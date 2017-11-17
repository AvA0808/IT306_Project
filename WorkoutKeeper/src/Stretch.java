/* */

public class Stretch extends Exercise {
	private String instructions;
	
	/* 
	 * constructor that sets the ID and increments the exercise counter
	 * */
	public Stretch() {
		super();
	}

	/**
	 * @return the instructions
	 */
	public String getInstructions() {
		return instructions;
	}

	/**
	 * @param instructions the instructions to set
	 */
	public boolean setInstructions(String instructions) {
		if(instructions.equals("")) {
			return false;
		}
		else {
			this.instructions = instructions;
			return true;
		}
	}
	
	/* Prepares a record of the stretch exercise's information and returns it */
	public String toString() {
		return("Type: " + this.getClass().getName() + ", " + super.toString() + ", Instructions: " + this.instructions);
	}
}
