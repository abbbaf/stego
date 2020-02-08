package stego;

class StegoMessage {
	
	
		
	private StegoMessageState state;
	
	private byte b;
	private int bitPos, stateResult;
	private byte[] data;
	
	
	StegoMessage() {
		state = StegoMessageState.initialState();
	}
	

	public void encodeMessage(byte[] message) {
		data = state.encodeMessage(message);
	}
	
	public byte[] getData() {
		return data;
	}
	
	public void addBit(int bit) {
		b = (byte) ((b << 1) + bit);
		bitPos++;
		if (bitPos == 8) {
			addByte(b);
			b = 0;
			bitPos = 0;
		}
	}
	
	
	public void addByte(byte b) {
		stateResult = state.processByte(b) ? 0 : 1;
	}
	
	public boolean isComplete() {
		return stateResult == 1;
	} 
	
	public boolean continueProcessing() {
		return stateResult == 0;
	}
	
	public byte[] getMessage() {
		return state.getMessage();
	}	
	
	public boolean isValid() {
		return isComplete();
	}
	
	
}
