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
import java.util.ArrayList;
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
		
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(new File(PATH + file))));
			String nextLine;
			
			while (scanner.hasNextLine()) {
				nextLine = scanner.nextLine();
				String inFile = FileSys.getSubString(nextLine, value);
				if (searchFor.equalsIgnoreCase(inFile) ) {
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
	
	/* 
	   * Reads workout records from the workout database(text file) belonging to a single user and returns them in an array list 
	   * @param 
	   * @return 
	   */ 
	  public static ArrayList<String> readWorkouts(User user) throws FileNotFoundException { 
	    ArrayList<String> list = new ArrayList<String>(); 
	    Scanner scanner = null; 
	    try {
	      scanner = new Scanner(new BufferedReader(new FileReader(new File(FileSys.PATH + FileSys.WORKOUT_FILE)))); 
	      //if no exception thrown, proceed to file reading 
	      String nextLine = ""; 
	      //keep iterating while there are more records to read 
	      while(scanner.hasNextLine()) { 
	        //grab next record 
	        nextLine = scanner.nextLine(); 
	        //add to array list if it belongs to the input user 
	        if(FileSys.getSubString(nextLine, "Email: ").equals(user.getEmail())) { 
	          list.add(nextLine); 
	        } 
	      } 
	    } 
	    catch(FileNotFoundException e) { 
	      throw e; 
	    } 
	    scanner.close();
	    return list; 
	  }
	  
	  /* 
	   * Reads User records from the User database(text file) and returns all emails in an array list 
	   * @param 
	   * @return 
	   */ 
	  public static ArrayList<String> getAllEmails() throws FileNotFoundException { 
	    ArrayList<String> list = new ArrayList<String>(); 
	    Scanner scanner = null; 
	    try {
	      scanner = new Scanner(new BufferedReader(new FileReader(new File(FileSys.PATH + FileSys.USER_FILE)))); 
	      //if no exception thrown, proceed to file reading 
	      String nextLine = ""; 
	      //keep iterating while there are more records to read 
	      while(scanner.hasNextLine()) { 
	        //grab next record 
	        nextLine = scanner.nextLine(); 
	        //add to array list if it belongs to the input user  
	        list.add(FileSys.getSubString(nextLine, "Email: ")); 
	      } 
	    } 
	    catch(FileNotFoundException e) { 
	      throw e; 
	    } 
	    scanner.close();
	    return list; 
	  }

	public static String findInShared(String userEmail, String searchValue) throws FileNotFoundException {
		String list = ""; 
	    Scanner scanner = null; 
	    try {
	      scanner = new Scanner(new BufferedReader(new FileReader(new File(FileSys.PATH + FileSys.SHARED_FILE)))); 
	      //if no exception thrown, proceed to file reading 
	      String nextLine = ""; 
	      //keep iterating while there are more records to read 
	      while(scanner.hasNextLine()) { 
	        //grab next record 
	        nextLine = scanner.nextLine();
	        String foundInLine = FileSys.getSubString(nextLine, searchValue);
	        
	        if(foundInLine.equalsIgnoreCase(userEmail)) {
	        	list += "\n" + nextLine; 
	        }
	      }
	      
	      if(list == "") {
	    	  list = "None";
	      }
	    } 
	    catch(FileNotFoundException e) { 
	      throw e; 
	    } 
	    scanner.close();
	    return list; 
	}
}