package stego;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/*
	Create buffer size of 16kb for reading and 128kb for writing
*/


class BufferedStream {

	private static final int R_SIZE = 16*1024;
	private static final int W_SIZE = 128*1024+1;
	
	private byte buf[];
	private int pos, bitPos, countBits;
	private byte lastByte;
	
	private OutputStream ostream;
	private InputStream istream;
	private int count;
	
	BufferedStream(InputStream istream, OutputStream ostream) throws IOException {
		this.istream = istream;
		this.ostream = ostream;
		buf = new byte[W_SIZE];
		pos = 0;
		countBits = 0;
		count = 0;
	}
	
	BufferedStream(InputStream istream) throws IOException {
		this(istream,null);
	}	
	
	public byte read() throws IOException {
		processBuffer();
		return buf[pos++];		
	}
	
	public int readBit() throws IOException {
		if (bitPos == 8) bitPos = 0;
		if (bitPos == 0) read();
		int bit = (buf[pos-1] >> (7-bitPos)) & 1;
		bitPos++;
		return bit;
	}
	
	public void writeBit(int bit) throws IOException {
		lastByte = (byte) ((lastByte << 1) + bit);
		if (countBits++ == 8) {
			countBits = 0;
			ostream.write(lastByte);
		}
		
	}
		
	
	public byte getLastByte() {
		if (pos == 0) return lastByte;
		else return buf[pos-1];
	}
	 
	public void flush() throws IOException {
		ostream.write(buf,0,pos);
	}
	 
	private void processBuffer() throws IOException {
		if (pos % R_SIZE == 0) {
			if (pos == W_SIZE) {
				pos = 0;
				lastByte = buf[W_SIZE - 1];
				if (ostream != null) ostream.write(buf);
			}
			int c = istream.read(buf,pos,pos+R_SIZE);
			if (c == -1) throw new IOException(Errors.EndOfStream);
			else count += c;
		}
	}
	
	public void close() {
		try {
			ostream.close();
			istream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
