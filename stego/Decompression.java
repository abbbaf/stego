package stego;

import java.io.IOException;

abstract class  Decompression  {
	
	enum Type { DC, AC };
	
	private Type type;
	
	Decompression(Decompression.Type type) {
		this.type = type;
	}
	
	abstract Coefficient decompress(JpegStream stream) throws IOException;
	
	public Type getType() {
		return type;
	}
}
