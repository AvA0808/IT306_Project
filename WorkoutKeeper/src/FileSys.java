import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * @author Aleksandar Atanasov - G00716250 - aatanas2@gmu.edu
 * @author Victoria Chang - G00947241 - vchang3@masonlive.gmu.edu
 * 
 */
public class FileSys {
	final static String PATH = "./src/";
	final static String USER_FILE = "user.txt";
	final static String WORKOUT_FILE = "workout.txt";
	final static String EXERCISE_FILE = "exercise.txt";
	final static String SHARED_FILE = "shared.txt";
	final static String EXERCISE_ID = "exerciseID.txt";
	final static String WORKOUT_ID = "workoutID.txt";
	final static String[] FILES = { "user.txt", "workout.txt", "exercise.txt", "shared.txt", "exerciseID.txt",
			"workoutID.txt" };

	public static boolean fileSetup() throws IOException {
		String fileName;
		for (String name : FILES) {
			fileName = PATH + name;
			File file = new File(fileName);
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		BufferedReader exID = new BufferedReader(new FileReader(PATH + EXERCISE_ID));     
		if (exID.readLine() == null) {
			BufferedWriter writer = new BufferedWriter(new FileWriter(PATH + EXERCISE_ID));
		    writer.write("0");
		     
		    writer.close();
		}
		
		exID.close();
		
		BufferedReader wkID = new BufferedReader(new FileReader(PATH + WORKOUT_ID));     
		if (wkID.readLine() == null) {
			BufferedWriter writer = new BufferedWriter(new FileWriter(PATH + WORKOUT_ID));
		    writer.write("0");
		     
		    writer.close();
		}
		
		wkID.close();
			
		return true;
	}
	
	public static void incrementID(String file) throws IOException {
		String tempID = FileSys.readLine(PATH + file);
		int exerciseID = Integer.parseInt(tempID);
		
		PrintWriter pw = new PrintWriter(new FileWriter(new File(PATH + file), false));
		pw.print(Integer.toString(++exerciseID));
		pw.close();
	}

	public static boolean append( String file, String newLine) throws FileNotFoundException, IOException {
		PrintWriter pw = null;
		String path = PATH + file;
		try { 
			pw = new PrintWriter(new FileOutputStream(new File(path), true));
			pw.println(newLine);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
		return false;
	}

	public static String readLine(String filePath) {
		Scanner inputStream;
		String readLine = "";
		try {
			inputStream = new Scanner(new FileInputStream(filePath));
			readLine = inputStream.nextLine();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "The File could not be read.");
			e.printStackTrace();
		}

		return readLine;
	}
	
	/*
	 * Returns the entry following the inputted delimiter
	 * @param readLine The String to search the delimiter for
	 * @param searchValue The delimiter to search for within the record/line
	 * @return String following the delimiter in the record, or null if not found
	 */
	public static String getSubString(String readLine, String searchValue) {
		String findValue = "";
		String tempReadLine = readLine.toLowerCase();
		String tempSearchValue = searchValue.toLowerCase();

		if (tempReadLine.contains(tempSearchValue)) {
			findValue = readLine.substring(tempReadLine.indexOf(tempSearchValue), readLine.length());

			if (findValue.indexOf(",") == -1) {
				findValue = findValue.substring(0, findValue.length());
			} else {
				findValue = findValue.substring(0, findValue.indexOf(","));
			}

			findValue = findValue.substring(findValue.indexOf(':') + 1, findValue.length());
			return findValue.trim();
		} else {
			return null;
		}
	}
	
	
    //Searches for a value in the whole text file, return true if found
	public static boolean isFoundInList(String file, String value, String searchFor) throws FileNotFoundException {
		boolean found = false;
		Scanner scanner = null;
		String lineItem = "";
		
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(new File(PATH + file))));
			String nextLine;
			
			while (scanner.hasNextLine()) {
				nextLine = scanner.nextLine();
				if (searchFor == FileSys.getSubString(nextLine, value)) {
					found = true;
					break;
				}
			}
		}
		catch (FileNotFoundException e) {
			throw e;
		}
		scanner.close();
		return found;
	}
}