
import org.junit.Test;

/**
 * @author Aleksandar Atanasov - G00716250 - aatanas2@gmu.edu
 * @author Victoria Chang - G00947241 - vchang3@masonlive.gmu.edu
 * 
 */
public class MenuTest {

	@Test
	public void test() {
		User user = new User("Abraham", "van Helsing", "ahelsing@email.com", "1234ABcde");
		Menu.profileSubMenu(user);
	}

}
