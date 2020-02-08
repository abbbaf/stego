package stego;

import java.util.ArrayList;

class SOS extends Marker {

	/* 
	components is an array of components and factors 
	e.g []{component1, component1, component1, component1, component2, component3}
	*/
	
	private Component[] components;
	private int start, end, pointTransform, previousPointTransform, blocks;
	private DecompressionMarker decompressionMarker;
	private SOF sof;

	public void setDecompressionMarker(DecompressionMarker decompressionMarker) {
		this.decompressionMarker = decompressionMarker;
	}
	
	public void setSOF(SOF sof) {
		this.sof = sof;
	}
	
	public boolean isDecodable() {
		return pointTransform == 1 || (pointTransform == 0 && previousPointTransform == 0);
		
	}
	
	
	public void process(JpegInputStream stream) {
		byte data[] = getData();
		Components[] components = sof.getComponents();		
		ArrayList<Component> componentsList = new ArrayList();
		int numOfComponents = data[0];
		components = new Components[numOfComponents];
		int j = 1;
		Component component;
		for (int i = 0; i < numOfComponents; i++) {
			int index = data[j++] - 1;
			component = components[index];
			int table = data[j++];
			component.setDcTable(decompressionMarker.getDcTable(table >> 4));
			component.setAcTable(decompressionMarker.getAcTable(table & 0x0f));
			for (int k = 0; k < component.getFactor(); k++) 
				componentsList.add(component);
			
		}
		components = componentsList.toArray(new Component[componentsList.size()]);
		start = data[j++];
		end = data[j++];
		previousPointTransform = data[j] >> 4;
		pointTransform = data[j] & 0xf;
		setBlockCount(sof.getWidth(),sof.getHeight());
	}
	public int start() {
		return start;
	}
	
	public int end() {
		return end;
	}
	
	public int getPointTransform() {
		return pointTransform;
	}
	
	public int getPreviousPointTransform() {
		return previousPointTransform;
	}
	
	public Component[] getComponents() {
		return components;
	}
	
	private int seBlockCount(int width, int height) {
		if (components.length == 1) 
			blocks = Math.ceil(width*height/64);
		else {
			int hMax, vMax;
			for (Component component : componentsInScan) {
				int h = component.getHorizontalFactor();
				int v = component.getVerticalFactor();
				if (h > hMax) hMax = h;
				if (v > vMax) vMax = v;
			}
			int mcuCount = width*height/(64*hMax*vMax);
			int blocksInMcu = 0;
			for (Component component : components) {
				blocksInMcu += component.getFactor();
			}
			blocks = mcuCount * blocksInMcu;
		}
	}
	
	public int size() {
		return blocks;
	}
}
