package stego;

abstract class StegoMessageState  {


	public static StegoMessageState initialState() {
		return new StegoMessageHeader();
	}

	private int maxPos, pos;
	protected StegoMessageState nextState;
	
	StegoMessageState(StegoMessageState nextState) {
		this.nextState = nextState;
	}

	/*
		Return -1 for invalid processing
			0 for valid but not complete
			1 for complete processing
			
	*/
	public final int process(byte b) {
		int result;
		if (pos == maxPos) {
			pos = -1;
			if (nextState != null) result = nextState.process(b);
			else return 1;
		}
		else result = processByte(b) ? 0 : -1;
		pos++;
		return result;
	}
	
	protected abstract boolean processByte(byte b); 

	
	public int getPos() {
		return pos;
	}
	
	public StegoMessageState nextState() {
		return nextState;
	}
	
	public void setMaxPos(int maxPos) {
		this.maxPos = maxPos;
	}
	
	public int getMaxPos() {
		return maxPos;
	}
	
	public byte[] getMessage() {
		return nextState.getMessage();
	}
	
	protected abstract void appendMessageBytes(BitAndByteArray array,byte[] message);
	
	
	public byte[] encodeMessage(byte[] message) {
		BitAndByteArray array = new BitAndByteArray();
		appendMessageBytes(array,message);
		if (nextState != null) return nextState.encodeMessage(message);
		else return array.getByteArray();
	}
}
