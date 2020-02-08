package stego;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.BitSet;


class StegoEncoder extends Stego {

	
	public StegoEncoder(InputStream	inputStream) {
		super(inputStream);
	}
	
	
	public boolean encode(byte[] input) {
		JpegStream stream = getStream();
		ByteBuffer buffer = ByteBuffer.allocate(4+4+input.length);
		buffer.putInt(Stego.ID);
		buffer.putInt(input.length);
		buffer.put(input);
		input = buffer.array();
		
		BitSet bitData = BitSet.valueOf(input);
		CoefficientIterator iterator = getCoefficientIterator();
		int i;
		Coefficient coefficient;
		for (i = 0; i < bitData.length() && iterator.hasNext(); i++) {
			coefficient = iterator.next();
			coefficient.setLastBit(bitData.get(i) ? 1 : 0);
		}
		if (!iterator.hasNext()) return false;
		stream.close();
		return true;
	}
	
}
