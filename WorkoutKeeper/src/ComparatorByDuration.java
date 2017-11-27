import java.util.*;
public class ComparatorByDuration implements Comparator<Cardiovascular> {
	/*
	 * Compares the duration of two cardiovascular exercise objects
	 * @param
	 * @param
	 * @return
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
