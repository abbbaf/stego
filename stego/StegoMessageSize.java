package stego;

class StegoMessageSize extends StegoMessageState {
	
	private int size;
	
	StegoMessageSize() {
		super(new StegoMessageData());
		setMaxPos(2);
	}
	
	protected boolean processByte(byte b) {
		size = (size << 8) ^ ((int) b & 0xff);
		if (getPos() == getMaxPos() - 1) { 
			nextState.setMaxPos(size);
			return true;
		}
		return false;
	}
	
	protected void appendMessageBytes(BitAndByteArray array,byte[] message) {
		size = message.length & 0xffff;
		array.addByte((byte) (size >> 4));
		array.addByte((byte) (size & 0xff));
	}	
}
