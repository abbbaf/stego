package stego;

class StegoMessageHeader extends StegoMessageState {
	
	private int pos;
	private byte[] header = new byte[]{-1,-1};
	
	StegoMessageHeader() {
		super(new StegoMessageSize());
		setMaxPos(header.length);
	}
	
	protected boolean processByte(byte b) {
		return b == header[getPos()]; 
	}	
	
	protected void appendMessageBytes(BitAndByteArray array, byte[] message) {
		array.addBytes(header);
	}
	
	
}
