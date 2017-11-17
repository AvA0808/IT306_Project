/* */

public class Cardiovascular extends Exercise {
	//number of minutes the exercise will be performed for
	private int duration;
	//the place where the exercise will be performed
	private String setting;
	
	/* 
	 * constructor that sets the ID and increments the exercise counter
	 * */
	public Cardiovascular() {
		super();
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Validates and sets the duration (minutes).
	 * @param duration the duration to set
	 * @return boolean true indicating that the duration was successfully set
	 * @throws IllegalArgumentException
	 */
	public boolean setDuration(int duration) throws IllegalArgumentException {
		if(duration <= 0) {
			throw new IllegalArgumentException("Error: not a valid time in minutes!");
		}
		else {
			this.duration = duration;
			return true;
		}
	}

	/**
	 * @return the setting
	 */
	public String getSetting() {
		return setting;
	}

	/**
	 * Validates and sets the setting.
	 * @param setting the setting to set
	 * @return boolean indicating whether the setting was successfully set or not
	 */
	public boolean setSetting(String setting) {
		if(setting.equals("")) {
			return false;
		}
		else {
			this.setting = setting;
			return true;
		}
	}
	
	/* Prepares a record of the cardiovascular exercise's information and returns it */
	public String toString() {
		return("Type: " + this.getClass().getName() + ", " + super.toString() + "Duration: " + this.duration + ", Setting: " + this.setting);
	}
}
