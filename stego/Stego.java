package stego;

import java.io.IOException;
import java.io.InputStream;

public class Stego {
			
	public static final int ID = -1;
	
	private JpegStream stream;
	private SOS sos;
	
	protected JpegStream getStream() {
		return stream;
	}
	
	public Stego(InputStream inputStream) throws IOException {
		stream = new JpegStream(inputStream);
	}
	
	public boolean encode(byte[] message) throws IOException {
		StegoMessage stegoMessage = new StegoMessage();
		stegoMessage.encodeMessage(message);
		decodeMetaData();
		return true;
		
	}
	
	public byte[] decode() throws IOException {
		decodeMetaData();
		StegoMessage stegoMessage = new StegoMessage();
			
		if (isUnCompressed()) decodeUncompressedDcs(stegoMessage, stream,sos.size());	
		else decode(stegoMessage,stream,sos.getComponents(),sos.end(), sos.size());
		
		if (stegoMessage.isComplete()) return stegoMessage.getMessage();
		else return null;
	}
	
	private boolean isUnCompressed() {
		return sos.start() == 0 && sos.end() == 0 && sos.getPreviousPointTransform() > 0;
	}
	
	private void decodeMetaData() throws IOException {
		JpegMetaData metaData = new JpegMetaData(stream);
		do {
			metaData.decodeMarkers();
			sos = metaData.getSOS();
		} while (!sos.isDecodable());
		
	}
	
	
	private void decodeUncompressedDcs(StegoMessage stegoMessage,JpegStream stream, int numOfBlocks) throws IOException {
		for (int i = 0; i < numOfBlocks / 8 && stegoMessage.isValid() 
					&& !stegoMessage.isComplete(); i++) {
			
			stegoMessage.addByte(stream.read());
		}
	}
	
	private void decode(StegoMessage stegoMessage,JpegStream stream,
				Component[] components, int end, int numOfBlocks) throws IOException {
				
		Decompression table;
		Component component;
		Coefficient coefficient;
		
		for (int i = 0; i < numOfBlocks && stegoMessage.isValid() 
					&& !stegoMessage.isComplete(); i++) {
			
			component = components[i % components.length];
			table = component.getDCTable();
			int value = table.decompress(stream).getValue();
			if (stream.isLastByteModifiable()) 
				stegoMessage.addBit(value & 1);
			
			for (int j = 1; j < end; j++) {
				table = component.getACTable();
				coefficient = table.decompress(stream);
				if (coefficient.getNumOfBlocksSkipped() > 0) break;
				if (coefficient.getValue() == 0) j--;				
				j += coefficient.getNumOfZerosSkipped();
			}
		}		
	
	}
	
	public static void main(String args[]) {
		System.out.println("success");
	}
	
}
