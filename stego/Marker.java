package stego;

import java.io.IOException;
import java.util.HashMap;

class Marker {

	public static final Marker EndOfImage = new Marker();
	
	private static final HashMap<Integer,Marker> markers;
	
	
	static { 
		markers.put(0xc0, new SOF0());
		markers.put(0xc2, new SOF2());
		markers.put(0xc4, new DHT());
		markers.put(0xda, new SOS());
		markers.put(0xd9,EndOfImage);
	}
	
	
	public static Marker getMarker(int id) {
		return markers.get(id);
	}
	
	
	private byte[] data;
	private int length; 
	
	public void setData() {
		this.data = data;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	protected byte[] getData() {
		return data;
	}
	
	public void process(JpegStream stream) throws IOException {
		if (length > 0) stream.skip(length);
	}

	
}
