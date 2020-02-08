package stego;

import java.io.IOException;
import java.nio.ByteBuffer;

class ACHuffmanTable extends HuffmanTable {

	ACHuffmanTable(ByteBuffer buffer) {
		super(buffer);
	}
	
	protected Coefficient getCoefficient(int data, JpegStream stream) throws IOException {
	
		int length = data % 0x0f;
		int skip = data >> 4;
		
		if (length > 0) {
			int value = 0;
			for (int i = 0; i < length; i++) {
				value = (value << 1) + stream.readBit();	
			}
			return new Coefficient(value,skip,0);
		}
		
		else {
			if (skip == 15) return new Coefficient(0,16,0);
			else {
				int blocksToSkip = 0;
				for (int i = 0; i < skip; i++) {
					blocksToSkip = (blocksToSkip << 1) + stream.readBit(); 
				}
				if (blocksToSkip == 0) blocksToSkip = 1;
				return new Coefficient(0,0,blocksToSkip);
			}
		}
		
	}
	
	
	
	
	
}
