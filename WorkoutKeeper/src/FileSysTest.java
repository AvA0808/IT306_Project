import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

public class FileSysTest {

	@Test
	public void testIsFoundInList() throws FileNotFoundException {
		assertTrue(FileSys.isFoundInList(FileSys.EXERCISE_FILE, "Description: ", "Testing"));
	}

}
