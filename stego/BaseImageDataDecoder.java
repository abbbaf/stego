package stego;

import java.io.BufferedInputStream;

class BaseImageDataDecoder extends ImageDataDecoder {

	BaseImageDataDecoder(BufferedInputStream stream, Component[] components, 
				int start, int end) {
		super(stream,components,start,end);			
	}
	
	protected void decodeCoefficients(Component component,int start, int end) {
		
	}
	
	private int decodeDC(int coefficientPrefix) {
		
	}
}
