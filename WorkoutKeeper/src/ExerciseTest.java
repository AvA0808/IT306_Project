/* NOTE: This JUnit test case tests all exercise! */

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Aleksandar Atanasov - G00716250 - aatanas2@gmu.edu
 * @author Victoria Chang - G00947241 - vchang3@masonlive.gmu.edu
 * 
 */
public class ExerciseTest {
	@Test
	public void createExercise() {
		Cardiovascular cardio = new Cardiovascular(1, "Test", "Muscle", 5, "Floor");
		Stretch stretch = new Stretch(2, "Test", "Muscle", "Do");
		WeightTraining training = new WeightTraining(3, "Leg", "desc", 5, 4);
		
		System.out.println(cardio.toString());
		System.out.println(stretch.toString());
		System.out.println(training.toString());
		System.out.println();
		
		assertNotNull(cardio);
		assertNotNull(stretch);
		assertNotNull(training);
	}
	
	
	@Test
	public void test() {
		//instantiate an object for all Exercise subclasses
		Cardiovascular cardio = new Cardiovascular();
		Stretch stretch = new Stretch();
		WeightTraining training = new WeightTraining();
		
		//set values to cardio's fields and print toString()
		assertEquals(cardio.setDescription("Test cardio exercise"), true);
		cardio.setMuscle(8); //should choose "Triceps"
		assertEquals(cardio.getMuscle(), "Triceps");
		try { //should throw the Exception and leave duration not set (0)
			cardio.setDuration(-10);
		}
		catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(cardio.toString());
		
		//set values to stretch's fields and print toString()
		System.out.println(stretch.toString()); //should be in same format as cardio's toString
		
		//set values to training's fields and print toString()
		
	}

}
