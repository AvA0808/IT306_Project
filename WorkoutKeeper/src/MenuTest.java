import java.io.FileNotFoundException;

import org.junit.Test;

/**
 * @author Aleksandar Atanasov - G00716250 - aatanas2@gmu.edu
 * @author Victoria Chang - G00947241 - vchang3@masonlive.gmu.edu
 * 
 */
public class MenuTest {

//	@Test
//	public void test() {
//		User user = new User("Abraham", "van Helsing", "ahelsing@email.com", "1234ABcde");
//		Menu.profileSubMenu(user);
//	}
	
//	@Test
//	public void testremoveSharedWorkouts() throws FileNotFoundException {
//		User user = new User("A", "B", "test@email.com", "1");
//		Object[] fileWorkouts = FileSys.readWorkouts(user).toArray();
//		
//		Menu.removeSharedWorkouts(user, "a@e.co", fileWorkouts);
//	}
	
	@Test
	public void testduplicateWorkout() throws FileNotFoundException {
		User user = new User("A", "B", "test@email.com", "1");
		Counter counter = new Counter();
		Menu.createWorkout(user, SortSearch.readExercise(user, counter), true);
	}

}
