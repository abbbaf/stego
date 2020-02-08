package stego;

import java.io.IOException;

/*
Decodes metadata markers of a JPEG image
*/

class JpegMetaData {
	
	private JpegStream stream;
	private SOS sos;
	private SOF sof;
	
	JpegMetaData(JpegStream stream) {
		this.stream = stream;
	}
	
	public void decodeMarkers() throws IOException {
		Marker marker;
		DecompressionMarker decompressionMarker = null;
		sos = null;
		do {
			marker = stream.nextMarker();
			if (marker == Marker.EndOfImage) return;
			
			if (marker instanceof SOF) sof = marker;
			else if (marker instanceof DecompressionMarker) 
				decompressionMarker = marker;
			else if (marker instanceof SOS) {
				sos = marker;
				sos.setDecompressionMarker(decompressionMarker);
				sos.setSOF(sof);
			}
			marker.process(stream);
		} while (sos == null);
	}
	
	public SOS getSOS() {
		return sos;
	}
	
	
}
