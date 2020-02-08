package stego;

class Coefficient {


	private int value, numOfZerosSkipped, numOfBlocksSkipped;

	Coefficient(int value, int numOfZerosSkipped, int numOfBlocksSkipped) {
		this.value = value;
		this.numOfZerosSkipped = numOfZerosSkipped;
		this.numOfBlocksSkipped = numOfBlocksSkipped;
	}

	public int getValue() {
		return value;
	}
	
	public int getNumOfZerosSkipped() {
		return numOfZerosSkipped;
	}
	
	public int getNumOfBlocksSkipped() {
		return numOfBlocksSkipped;
	}

}
