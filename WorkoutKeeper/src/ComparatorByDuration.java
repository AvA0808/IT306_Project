import java.util.*;

/**
 * @author Aleksandar Atanasov - G00716250 - aatanas2@gmu.edu
 * @author Victoria Chang - G00947241 - vchang3@masonlive.gmu.edu
 * A Comparator class that compares two cardiovascular exercise objects by duration.
 */
public class ComparatorByDuration implements Comparator<Cardiovascular> {
	/*
	 * Compares the duration of two cardiovascular exercise objects
	 * @param o1 First cardio object to compare
	 * @param o2 Second cardio object to compare
	 * @return integer (-1 if first's duration less than second's; 
	 * 0 if they are equal; 1 if first's is greater than second's)
	 */
	public int compare(Cardiovascular o1, Cardiovascular o2) {
		if(o1.getDuration() < o2.getDuration()) {
			return -1;
		}
		else if(o1.getDuration() > o2.getDuration()) {
			return 1;
		}
		//they are equal
		else {
			return 0;
		}
	}
	
}
