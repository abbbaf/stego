package stego;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class JpegStream extends BufferedStream {

	private static final int BUFF_SIZE = 8192;
	private byte lastByte;
	private int nextMarkerId;
	private boolean imageScanMode;

	JpegStream(InputStream istream, OutputStream ostream) throws IOException {
		super(istream, ostream);
	}
	
	JpegStream(InputStream istream) throws IOException {
		super(istream);
	}
	
	public byte read() throws IOException {
		byte b = super.read();
		if (b == 0 && getLastByte() == -1) b = super.read();
		return b;
	}
	
	
	public Marker nextMarker() throws IOException {
		int markerId;
		if (nextMarkerId == 0) 
			while (super.read() != 0xff && (markerId = super.read()) != 0);
		else { 
			markerId = nextMarkerId;
			nextMarkerId = 0;
		}
		Marker marker = Marker.get(markerId);
		
		int length = (((int) super.read() & 0xff) << 8) + ((int) super.read() & 0xff);
		if (length >= 0xff00) {
			nextMarkerId = length & 0xff;
			if (marker == null) marker = new Marker();
		}
		else {
			if (marker == null) { 
				marker = new Marker();
				marker.setLength(length);
			}
			else {
				byte[] data = new byte[length];
				read(data);
				marker.setData(data);
			}
		}
		return marker;
	}

	
	
	public boolean isLastByteModifiable() {
		byte b = getLastByte() & (byte) -1;
		int bitPos = getLastBitPos();
		
		if (b == 0 || (byte) 1 << (7-bitPos) == b) return false;
		else return true;		
		
				
	}	
	
}
