package stego;

class ScannedDataHeader {

	private int start, end, mcuCount;
	private boolean isCompressed;
	private Componet[] components;

	ScannedDataHeader(Component[] components,int start, int end, int mcuCount, boolean isCompressed) {	
		this.components = components;
		this.start = start;
		this.end = end;
		this.isCompressed = isCompressed;
		this.mcuCount = mcuCount;
	}
	
	public int getFirstIndex() {
		return start;
	}
	
	public int getLastIndex() {
		return end;
	}
	
	public int mcuCount() {
		return mcuCount;
	}
	
	public boolean isCompressed() {
		return isCompressed;
	}
	
	public Component[] getComponentsInScan() {
		return components;
	}
}
