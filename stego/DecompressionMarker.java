package stego;

class DecompressionMarker extends Marker {

	private HuffmanTable[] dcTables, acTables;
	
	public DecompressionMarker() {
		super();
	}

	public HuffmanTable getDcTable(int index) {
		return dcTables[index];
	}
	
	public HuffmanTable getAcTable(int index) {
		return acTables[index];
	}

	public void setDCTable(int index, DCHuffmanTable table) {
		dcTables[index] = table;
	}
	
	public void setACTable(int index, ACHuffmanTable table) {
		acTables[index] = table;
	}

}
