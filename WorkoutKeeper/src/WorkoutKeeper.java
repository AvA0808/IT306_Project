import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * 
 */

/**
 * @author Aleksandar
 *
 */
public class WorkoutKeeper {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String email = "";

		email = welcomeScreen();
		System.out.println(email);

	}

	private static String welcomeScreen() {
		String[] options = { "Create Account", "Login" };

		switch (JOptionPane.showOptionDialog(null, "Welcome to the Workout Keeper App", "Workout Keeper",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0])) {
		case 0:
			createAcount();
			break;
		case 1:
			break;
		default:
			break;
		}
		return login();
	}

	private static void createAcount() {
		JTextField email = new JTextField();
		JTextField password = new JTextField();
		JTextField firstName = new JTextField();
		JTextField lastName = new JTextField();

		Object[] inputFields = { "Email:", email, "Password:", password, "First Name:", firstName, "Last Name:",
				lastName };

		do {
			int option = JOptionPane.showConfirmDialog(null, inputFields, "Create Account",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				if (Validate.validateEmail(email.getText())) {
					if (password.getText().isEmpty()) {

					}
				} else {
					JOptionPane.showMessageDialog(null, "The Email entered is not a valid email.\nTry again.");
					email = null;
				}

			}
		} while (email == null);
	}

	public static String login() {
		JTextField email = new JTextField();
		JTextField password = new JPasswordField();

		Object[] inputFields = { "Email:", email, "Password:", password };

		do {
			int option = JOptionPane.showConfirmDialog(null, inputFields, "Workout Keeper Login",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if (option == JOptionPane.OK_OPTION) {
				if (Validate.validateEmail(email.getText())) {
					if (Validate.validatePassword(password.getText())) {

					} else {
						JOptionPane.showMessageDialog(null, "The Password entered is not valid.\nTry again.");
						email = null;
					}
				} else {
					JOptionPane.showMessageDialog(null, "The Email entered is not a valid email.\nTry again.");
					email = null;
				}

			}
		} while (email == null);
		return email.getText();
	}
}
