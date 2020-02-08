package stego;

import java.io.IOException;

abstract class SOF extends Marker {
	
	enum Mode { Baseline, Progressive }; 
	
	private int heigth, width;
	private Component[] components;
	private Mode mode;
	
	public void process(JpegStream stream) throws IOException {
		byte[] data = getData();
		heigth = (data[1] << 8) + data[2];
		width = (data[3] << 8) + data[4];
		int numOfComponents = data[5];
		components = new Component[numOfComponents];
		int hMax, vMax;
		int j = 6;
		for (int i = 0; i < numOfComponents; i++) {
			Component component = new Component();
			int id = data[j++];
			int factor = data[j++];
			h = factor >> 4;
			v = factor & 0xf;
			component.setFactor(h,v);
			components[id-1] = component;
		}
		setMode();
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
		
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return heigth;
	}
	
	public Component[] getComponents() {
		return components;
	}
	
	public Mode getMode() {
		return mode;
	}
	

}
