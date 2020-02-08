package stego;

import java.io.File;
import java.io.IOException;

class StegoDecoder extends Stego {


	public StegoDecoder(File file) {
		super(file,false);
	}

	public byte[] decode() throws IOException {
		CoefficientIterator iterator = getCoefficientIterator();
		int id;
		for (int i = 0; i < 32 && iterator.hasNext(); i++) {
			id = (id << 1) + (iterator.next() & 1);
		}
		if (id != ID) return null;
		int size;
		for (int i = 0; i < 32 && iterator.hasNext(); i++) {
			size = (size << 1) + (iterator.next() & 1);
		}
		byte[] result = new byte[size];
		for (int i = 0; i < size && iterator.hasNext(); i++) {
			for (int j = 0; j < 8; j++) {
				result[i] >>= 1; 
				result[i] ^= iterator.next() & 1;
			}

		}
		if (!iterator.hasNext()) return null;
		return result;
	}
}
