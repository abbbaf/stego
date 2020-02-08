package stego;

import java.io.IOException;
import java.nio.ByteBuffer;

class DCHuffmanTable extends HuffmanTable {

	DCHuffmanTable(ByteBuffer buffer) {
		super(buffer,Decompression.Type.DC);
	}
	
	protected Coefficient getCoefficient(int data, JpegInputStream stream) throws IOException {
		int value = 0;
		for (int i = 0; i < data; i++) {
			value = (coefficient << 1) + stream.readBit();
		}
		return new Coefficient(value,0,0);
	}	
}
