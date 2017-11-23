import static org.junit.Assert.*;
import org.junit.Test;
import java.io.*;

public class ValidateTest {

	@Test
	public void test() {
		try {
			//test the email validation method
			assertEquals(Validate.isEmailInList("test2@email.com"), true); //works!!
			assertEquals(Validate.isEmailInList("test@email.com"), true); //works!!
			assertEquals(Validate.isEmailInList("abraham@email.com"), false); //works!!
			
			//test the password validation method
			assertEquals(Validate.doesPasswordMatch("test2@email.com", "1234ABcd"), true); //works!!
			assertEquals(Validate.doesPasswordMatch("test2@email.com", "1234ABCd"), false); //c is not upper case; works!!
		}
		catch(FileNotFoundException e) {
			System.out.println("Error: File could not be found!");
		}
	}

}
