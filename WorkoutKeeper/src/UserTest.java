import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Aleksandar Atanasov - G00716250 - aatanas2@gmu.edu
 * @author Victoria Chang - G00947241 - vchang3@masonlive.gmu.edu
 * 
 */
public class UserTest {

	@Test
	public void test() {
		//instantiate a User object
		User user = new User();
		/* test more complex setter methods (the ones harder to fix) */
		//test setEmail()
		assertNotEquals(user.setEmail("daniel"), true);
		assertNotEquals(user.setEmail("ranzy@."), true);
		assertNotEquals(user.setEmail("pemberton@alcove.e"), true);
		assertEquals(user.setEmail("xander@fates.com"), true);
		//test setPassword()
		assertNotEquals(user.setPassword("daniel"), true);
		assertNotEquals(user.setPassword("1234"), true);
		assertNotEquals(user.setPassword("FerGenand123"), true);
		assertEquals(user.setPassword("BillyBop9800"), true);
	}

}
