package stego;

class BitReader {

	private int bits, maxBits, count, maxPowerOfTwo;
	
	public void add(byte b) {
		bits <<= 8;
		bits += b;
		maxBits += 8;
		maxPowerOfTwo = 1 << (maxBits-1);
	}
	
	public int read() {
		if (count == maxBits) return -1;
		else {
			int bit = 1;
			if (bits & maxPowerOfTwo == 0) bit = 0;
			bits <<= 1;
			count++;
			if (count == maxBits) {
				bits = 0;
				maxBits = 0;
			}
			return bit;
		}
	}
	
	public int count() {
		return count;
	}
}
