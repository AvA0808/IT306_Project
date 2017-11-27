import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;
import java.util.*;

public class SortSearchTest {

	@Test
	public void test() {
		Counter counter = new Counter();
		//testing the createExerciseFromFile() method first: is it properly creating an object out every record I pass in
		Exercise exercise_1 = SortSearch.createExerciseFromFile("Email: admin@email.com, Type: Cardiovascular, ID: 0, Description: Test, Muscle Group: (Top of Shoulders) Deltoids, Duration: 43, Setting: 43", counter);
		Exercise exercise_2 = SortSearch.createExerciseFromFile("Email: admin@email.com, Type: Cardiovascular, ID: 1, Description: blabber, Muscle Group: (Top of Shoulders) Deltoids, Duration: 99, Setting: floor", counter);
		Exercise exercise_3 = SortSearch.createExerciseFromFile("Email: admin@email.com, Type: Cardiovascular, ID: 2, Description: more blabber, Muscle Group: (Top of Shoulders) Deltoids, Duration: 10, Setting: Treadmill", counter);
		System.out.println(exercise_1.toString());
		System.out.println(exercise_2.toString());
		System.out.println(exercise_3.toString());
		/* ABOVE WORKS!! */
		
		//testing sortingMethod()
		LinkedList<Exercise> list = null;
		try {
			 list = SortSearch.readExercise(new User("a", "b", "test@email.com", "password"), counter);
			 list = SortSearch.readExercise(new User("a", "b", "test@email.com", "password"), counter);
			 System.out.println(SortSearch.sortingMethod(list, "Type") + "\n\n");
			 System.out.println(SortSearch.sortingMethod(list, "Muscle Group"));
			 
			//testing sortDuration()
			System.out.println(SortSearch.sortingMethod(list, "Duration"));
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
