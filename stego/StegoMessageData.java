package stego;

class StegoMessageData extends StegoMessageState {
	
	private byte[] message;
	
	StegoMessageData() {
		super(null);
	}
	
	public boolean processByte(byte b) {
		message[getPos()] = b;
		return true;
	}
	
	public void setMaxPos(int maxPos) {
		super.setMaxPos(maxPos);
		message = new byte[maxPos];
	}	
	
	public byte[] getMessage() {
		return message;
	}
	
	protected void appendMessageBytes(BitAndByteArray array, byte[] message) {
		array.addBytes(message);
	}

}


