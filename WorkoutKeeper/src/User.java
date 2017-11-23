/* The User class holds all fields relevant to ... */

public class User {
	//none of these fields can be null
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	//default constructor
	public User() { }
	
	public User(String fn, String ln, String e, String p) {
		this.firstName = fn;
		this.lastName = ln;
		this.email = e;
		this.password = p;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public boolean setFirstName(String firstName) {
		if(firstName.equals("")) {
			return false;
		}
		else {
			this.firstName = firstName;
			return true;
		}
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public boolean setLastName(String lastName) {
		if(lastName.equals("")) {
			return false;
		}
		else {
			this.lastName = lastName;
			return true;
		}
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Validates and sets the email address of the user.
	 * Valid email format: [username]@[domain].[extension]
	 * @param email the email to set
	 * @return boolean indicating whether the email has been set or not
	 */
	public boolean setEmail(String email) {
		//set the flag to true initially
		boolean valid = true;
		/* will be flagged false if at least one condition below for invalidity is met */
		//first, locate @ sign (-1 if nonexistent in input)
		if(email.indexOf("@") == -1) {
			valid = false;
		}
		//second, locate the period after the @ sign
		else if(email.indexOf(".") == -1) {
			valid = false;
		}
		//third, make sure @ sign comes before the period
		else if(email.indexOf("@") > email.indexOf(".")) {
			valid = false;
		}
		//fourth, make sure at least one character before and after the @ sign
		else if((email.indexOf("@")+1) == email.indexOf(".") || email.indexOf("@") == 0) {
			valid = false;
		}
		//fifth, make sure 2-4 characters after period
		else if(email.substring(email.indexOf(".") + 1).length() < 2 || email.substring(email.indexOf(".") + 1).length() > 4) {
			valid = false;
		}
		//if still valid in the end, set the email
		if(valid) {
			this.email = email;
		}
		return valid;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Validates and sets the password of the user.
	 * Valid password contains: at least 4 digits, at least 9 characters in total
	 * @param password the password to set
	 * return boolean indicating whether the password has been set or not
	 */
	public boolean setPassword(String password) {
		char[] pass_char = password.toCharArray();
		boolean valid = true;
		//first, check length of password to be at least 9 characters
		if(pass_char.length < 9) {
			valid = false;
		}
		else {
			//second, check if at least one upper case character
			boolean upper = false;
			//third, check if at least one lower case character
			boolean lower = false;
			//lastly, loop through the characters and make sure at least 4 digits
			int digits = 0;
			for(int i = 0; i < pass_char.length; i++) {
				if(Character.isDigit(pass_char[i])) {
					digits++;
				}
				else if(Character.isUpperCase(pass_char[i])) {
					upper = true;
				}
				else if(Character.isLowerCase(pass_char[i])) {
					lower = true;
				}
			}
			if(digits < 4 | !upper | !lower) {
				valid = false;
			}
		}
		if(valid) {
			this.password = password;
		}
		return valid;
	}
	
	/* Prepares a record of the user's information and returns it */
	public String toString() {
		return ("First Name: " + this.firstName + ", Last Name: " + this.lastName + ", Email: " + this.email + ", Password: " + this.password);
	}
}
