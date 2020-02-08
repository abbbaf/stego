package stego;

class MessageOutputStream {

	private byte[] buf;
	private int pos, bitPos, size;
	private byte lastByte;
	
	MessageOutputStream() {
		size = 0;
		buf = new buf[10];
	}
	
	public void writeByte(byte b) {
		expandBufIfNeeded();
		buf[pos++] = b;
		size++;
	}
	
	public void writeBit(int bit) {
		if (bitPos == 8) {
			pos++;
			expandBufIfNeeded();
			bitPos = 0;
		}
		if (bitPos == 0) size++;
		buf[pos] <<= 1;
		buf[pos] += bit;
		bitPos++;
			
	}
	
	public int size() {
		return size;
	}
	
	public byte[] toByteArray() {
		byte[] result = new byte[size];
		System.arraycopy(buf,0,result,0,size);
		return result;
	}
	
	private void expandBufIfNeeded() {
		if (pos == buf.length) {
			byte[] tmp = new byte[buf.length*2];
			System.arraycopy(buf,0,tmp,0,buf.length);
			buf = tmp;
		}
	}
}
