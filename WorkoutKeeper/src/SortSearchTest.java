import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

public class SortSearchTest {

	@Test
	public void test() {
		//testing the createExerciseFromFile() method first: is it properly creating an object out every record I pass in
		Exercise exercise_1 = SortSearch.createExerciseFromFile("Email: admin@email.com, Type: Cardiovascular, ID: 0, Description: Test, Muscle Group: (Top of Shoulders) Deltoids, Duration: 43, Setting: 43");
		Exercise exercise_2 = SortSearch.createExerciseFromFile("Email: admin@email.com, Type: Cardiovascular, ID: 1, Description: blabber, Muscle Group: (Top of Shoulders) Deltoids, Duration: 99, Setting: floor");
		Exercise exercise_3 = SortSearch.createExerciseFromFile("Email: admin@email.com, Type: Cardiovascular, ID: 2, Description: more blabber, Muscle Group: (Top of Shoulders) Deltoids, Duration: 10, Setting: Treadmill");
		System.out.println(exercise_1.toString());
		System.out.println(exercise_2.toString());
		System.out.println(exercise_3.toString());
		/* ABOVE WORKS!! */
		
		
	}

}
