/**
 * @author Aleksandar Atanasov - G00716250 - aatanas2@gmu.edu
 * @author Victoria Chang - G00947241 - vchang3@masonlive.gmu.edu
 * 
 */
public class Counter {
	private int stretchCount;
	private int cardioCount;
	private int weightCount;
	
	public Counter() {
		this.stretchCount = 0;
		this.cardioCount = 0;
		this.weightCount = 0;
	}

	public int getStretchCount() {
		return this.stretchCount;
	}

	public void incStretchCount() {
		this.stretchCount++;
	}

	public int getCardioCount() {
		return this.cardioCount;
	}

	public void incCardioCount() {
		this.cardioCount++;
	}

	public int getWeightCount() {
		return this.weightCount;
	}

	public void incWeightCount() {
		this.weightCount++;
	}
}
