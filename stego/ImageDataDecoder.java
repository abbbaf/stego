package stego;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;

import stego.SOF.Mode;

abstract class ImageDataDecoder {

	private BufferedInputStream stream;
	private int start, end, blockCount;
	private Mode mode;
	private MessageOutputStream msgStream;

	ImageDataDecoder(BufferedInputStream stream, Component[] components, 
				int start, int end) {
			this.stream = stream;
			this.start = start;
			this.end = end;
			blockCount = 0;
			for (Component component : components) 
				blockCount += component.getBlockCount();
			msgStream = new DataOutputStream();
	}
	
	private MessageOutputStream getMsgStream() {
		return msgStream;
	}
	
	public void decode() {
		for (int block = 0; block < blockCount; block++) {
			for (Component component : components) {
				for (int i = 0; i < getFactor(); i++) {
					decodCoefficients(component,start,end);
				}
			}
		}
	}
	
	protected abstract void decodeCoefficients(Component component,int start, int end);
	

}
