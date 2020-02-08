package stego;

import java.io.IOException;
import java.nio.ByteBuffer;

class HuffmanTable extends Decompression {

	private int[] min, max, firstSymbolIndex;
	private byte[] symbols;

	HuffmanTable(ByteBuffer buffer) {
		int[] symbolsCount = new byte[16];
		firstSymbolIndex = new int[16];
		int j = 0;
		int total = 0;
		for (int i = 0; i < 16; i++) {
			symbolsCount[i] = (int) buffer.get();
			firstSymbolIndex[i] = total;
			total += symbolCount[i]; 
		}
		minCode = new int[16];
		maxCode = new int[16];
		int min = 0;
		for (int i = 0; i < 16; i++) {
			if (symbolsCount[i] > 0) {
				minCode[i] = min;
				maxCode[i] = min + symbolsCount[i] - 1;
				min = (maxCode[i] + 1) << 1;
			}
			else {
				minCode[i] = -1;
				maxCode[i] = -1;
				min = (min+1) << 1;
			}
		}
		symbols = new symbols[total];
		buffer.get(symbols);
	}
	
	private int getSymbol(int code, int numOfBits) {
		int i = numOfBits-1;
		if (code > maxCode[i] || code < minCode[i]) 
			return -1;
		else
			return (int) symbols[firstSymbolIndex[i] + code - minCode[i]];
		
	}
	
	public Coefficient decompress(JpegInputStream stream) throws IOException {
		int code, data = -1;
		int bitCount = 1;
		while (data == -1) {
			code = (code << 1) + stream.readBit();
			data = getSymbol(code,numOfBits++);
			if (bitCount > 16) throw new IOException(Errors.InvalidDecompression);
		}
		return getCoefficient(data, stream);
	}
	
	protected abstract Coefficient getCoefficient(int data, JpegStream stream) throws IOException;
	
	
	
}
