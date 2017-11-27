import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;
import java.util.*;

public class SortSearchTest {

	@Test
	public void test() {
		//testing the createExerciseFromFile() method first: is it properly creating an object out every record I pass in
		Exercise exercise_1 = SortSearch.createExerciseFromFile("Email: admin@email.com, Type: Cardiovascular, ID: 0, Description: Test, Muscle Group: (Top of Shoulders) Deltoids, Duration: 43, Setting: 43", 0, 0, 0);
		Exercise exercise_2 = SortSearch.createExerciseFromFile("Email: admin@email.com, Type: Cardiovascular, ID: 1, Description: blabber, Muscle Group: (Top of Shoulders) Deltoids, Duration: 99, Setting: floor", 0, 0, 0);
		Exercise exercise_3 = SortSearch.createExerciseFromFile("Email: admin@email.com, Type: Cardiovascular, ID: 2, Description: more blabber, Muscle Group: (Top of Shoulders) Deltoids, Duration: 10, Setting: Treadmill", 0, 0, 0);
		System.out.println(exercise_1.toString());
		System.out.println(exercise_2.toString());
		System.out.println(exercise_3.toString());
		/* ABOVE WORKS!! */
		
		//testing sortingMethod()
		LinkedList<Exercise> list = null;
		try {
			 list = SortSearch.readExercise(new User("a", "b", "test@email.com", "password"), 0, 0, 0);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(SortSearch.sortingMethod(list, "Type"));
	}
}
