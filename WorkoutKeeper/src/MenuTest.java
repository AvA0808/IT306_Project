import static org.junit.Assert.*;

import org.junit.Test;

public class MenuTest {

	@Test
	public void test() {
		User user = new User("Abraham", "van Helsing", "ahelsing@email.com", "1234ABcde");
		Menu.profileSubMenu(user);
	}

}
