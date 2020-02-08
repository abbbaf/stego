package stego;

import java.io.IOException;
import java.nio.ByteBuffer;


class DHT extends DecompressionMarker {

	private HuffmanTable[] acTables, dcTables;

	DHT() {
		super();
	}
	
	public void process(JpegStream stream) throws IOException {
		byte[] data = stream.readMarker();
		if (data != null) {
			int metaData, id, type;
			ByteBuffer buffer = ByteBuffer.wrap(data);
			while (buffer.remaining() > 0) {
				metaData = buffer.get();
				id = metaData >> 4;
				type = (metaData >> 3) & 1;
				HuffmanTable table;
				if (type == 0)  setDCTable(id-1, new DCHuffmanTable(buffer));
				else setACTable(id-1, new ACHuffmanTable(buffer));
			}
		}
		else {
			acTables = new HuffmanTable[4];
			dcTables = new HuffmanTable[4];	
		}
	}
	
	
}
