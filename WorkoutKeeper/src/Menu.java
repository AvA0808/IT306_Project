import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * @author Aleksandar
 *
 */
public class Menu {
	/**
	 * 
	 * @param email
	 * @return
	 */
	static String welcomeScreen(String email) {
		String[] options = { "Create Account", "Login", "Exit" };

		switch (JOptionPane.showOptionDialog(null, "Welcome to the Workout Keeper App", "Workout Keeper",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0])) {
		case 0:
			createAcount();
			break;
		case 1:
			email = login();
			break;
		case 2:
			email = "-1";
			break;
		default:
			break;
		}
		return email;
	}

	/**
	 * 
	 */
	private static void createAcount() {
		JTextField email = new JTextField();
		JTextField password = new JTextField();
		JTextField firstName = new JTextField();
		JTextField lastName = new JTextField();

		boolean validInput;

		Object[] inputFields = { "Email:", email, "Password:", password, "First Name:", firstName, "Last Name:",
				lastName };

		do {
			int option = JOptionPane.showConfirmDialog(null, inputFields, "Create Account",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			validInput = false;

			if (option == JOptionPane.OK_OPTION) {
				if (Validate.isEmailInList(email.getText())) {
					if (Validate.doesPasswordMatch(email.getText(), password.getText())) {
						validInput = true;
					} else {
						JOptionPane.showMessageDialog(null,
								"The Password entered is not valid.\nIt must contain at least 8 characters and at least 4 numbers\nTry again.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "The Email entered is not a valid email.\nTry again.");
				}
			} else {
				validInput = true;
			}
		} while (!validInput);
	}

	/**
	 * 
	 * @return
	 */
	public static String login() {
		JTextField email = new JTextField();
		JTextField password = new JPasswordField();
		boolean accessGranted = false;
		String setEmail = "";

		Object[] inputFields = { "Email:", email, "Password: (Minimum 8 char, at least 4 numbers)", password };

		do {
			int option = JOptionPane.showConfirmDialog(null, inputFields, "Workout Keeper Login",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				if (Validate.isEmailInList(email.getText())) {
					if (Validate.doesPasswordMatch(email.getText(), password.getText())) {
						accessGranted = true;
						setEmail = email.getText();
					} else {
						JOptionPane.showMessageDialog(null, "The Password entered is not valid.\nTry again.");
						accessGranted = false;
					}
				} else {
					JOptionPane.showMessageDialog(null, "The Email entered is not a valid email.\nTry again.");
					accessGranted = false;
				}
			} else {
				accessGranted = true;
			}

		} while (!accessGranted);
		return setEmail;
	}
}