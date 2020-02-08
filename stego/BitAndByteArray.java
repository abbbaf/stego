package stego;


class BitAndByteArray {
	
	private int pos, rPos, count, bitPos, rBitPos;
	private byte[] buffer;
	private byte bits;
	
	
	BitAndByteArray() {
		pos = 0;
		rPos = 0;
		rBitPos = 0;
		buffer = new byte[10];
	}
	
	public byte getByte() {
		if (rPos == count) throw new IndexOutOfBoundException();
		return buffer[rPos++];
	}

	public int getBit() {
		if (rBitPos == 0) bits = getByte();
		int result = bits >> 7;
		bits <<= 1;
		if (rBitsPos++ == 8) rBitPos = 0;
		return result; 
	}
	
	public void addByte(byte b) {
		checkBuffer();
		buffer[pos++] = b;
		count++;
	}
	
	public void addBytes(byte bytes[]) {
		for (byte b : bytes) addByte(b);
	}
	
	public void addBit(int bit) {
		if (bitPos == 8) {
			bitPos = 0;
			pos++;
			checkBuffer();
		}
		buffer[pos] = (buffer[pos] << 1) + bit;
		bitPos++;
		
	}
	
	private void checkBuffer() {
		if (count == buffer.length) {
			byte[] tmp = new buf[2*count];
			System.arraycopy(buffer,0,tmp,0,count);
			buffer = tmp;
		}
	}
	
	public byte[] getByteArray() {
		byte[] result = new byte[count];
		System.arraycopy(buffer,0,result,0,count);
		return result;
	}
	

}
