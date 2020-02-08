package stego;

/*
The Y, Cb or Cr components of a JPEG image
*/

class Component {
	
	private int h, v;
	private Decompression dcTable, acTable;
	
	public void setFactor(int h, int v) {
		this.h = h;
		this.v = v;
	}
	
	public int getFactor() {
		return h*v;
	}
	
	public int getHorizontalFactor() {
		return h;
	}
	
	public int getVerticalFactor() {
		return v;
	}
	
	public void setAcTable(Decompression acTable) {
		this.acTable = acTable;
	}
	
	public void setDcTable(Decompression dcTable) {
		this.dcTable = dcTable;
	}
	
	public Decompression getDCTable() {
		return dcTable;
	}
	
	public Decompression getACTable() {
		return acTable;
	}
	
	
}
