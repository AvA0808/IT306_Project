/**
 * @author Aleksandar Atanasov - G00716250 - aatanas2@gmu.edu
 * @author Victoria Chang - G00947241 - vchang3@masonlive.gmu.edu An object that
 *         will keep track of the number of stretch, cardio, and weight training
 *         exercises a user has
 */
public class Counter {
	private int stretchCount;
	private int cardioCount;
	private int weightCount;

	/**
	 * Default Constructor, sets all counters to 0
	 */
	public Counter() {
		this.stretchCount = 0;
		this.cardioCount = 0;
		this.weightCount = 0;
	}

	public int getStretchCount() {
		return this.stretchCount;
	}

	/**
	 * Increments the stretchCount variable
	 */
	public void incStretchCount() {
		this.stretchCount++;
	}

	public int getCardioCount() {
		return this.cardioCount;
	}

	/**
	 * Increments the cardioCount variable
	 */
	public void incCardioCount() {
		this.cardioCount++;
	}

	public int getWeightCount() {
		return this.weightCount;
	}

	/**
	 * Increments the weightCount variable
	 */
	public void incWeightCount() {
		this.weightCount++;
	}
}
