
import java.io.FileNotFoundException;

import org.junit.Test;

/**
 * @author Aleksandar Atanasov - G00716250 - aatanas2@gmu.edu
 * @author Victoria Chang - G00947241 - vchang3@masonlive.gmu.edu
 * 
 */
public class MenuTest {

	@Test
	public void test() {
		User user = new User("Far", "Bys", "farbys@example.com", "UPPERlower1234");
		try {
			Menu.profileSubMenu(user); //profileSubMenu() works, but what about rewriteUserRecord()?
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
