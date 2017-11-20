import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * @author Aleksandar
 *
 */
public class FileSys {
	// private static String path = "./src/";
	// private static String userFile = "user.txt";
	// private static String workoutFile = "workout.txt";
	// private static String exerciseFile = "exercise.txt";
	// private static String sharedFile = "shared.txt";
	// private static String[] files = { "user.txt", "workout.txt",
	// "exercise.txt", "shared.txt" };

	public static boolean fileSetup(String path, String[] files) {
		String fileName;
		for (String name : files) {
			fileName = path + name;
			File file = new File(fileName);
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public static boolean append(String path, String file, String newLine) {
		PrintWriter pw = null;
		try {
			path += file;
			pw = new PrintWriter(new FileOutputStream(new File(path), true));
			pw.println(newLine);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
		return false;
	}

	public static String readLine(String filePath) throws FileNotFoundException {
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

	public static String getSubString(String readLine, String searchValue) {
		String findValue = "";
		String tempReadLine = readLine.toLowerCase();
		String tempSearchValue = searchValue.toLowerCase();

		findValue = readLine.substring(tempReadLine.indexOf(tempSearchValue), readLine.length());

		if (findValue.indexOf(",") == -1) {
			findValue = findValue.substring(0, findValue.length());
		} else {
			findValue = findValue.substring(0, findValue.indexOf(","));
		}

		findValue = findValue.substring(findValue.indexOf(':') + 1, findValue.length());

		return findValue.trim();
	}
}