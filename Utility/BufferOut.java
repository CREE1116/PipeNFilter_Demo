package Utility;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.ArrayList;

public class BufferOut {
	private ArrayList<PipedOutputStream> out;
	public BufferOut(ArrayList<PipedOutputStream> out) {
		this.out = out;
	}
	public void writeBuffer(byte[] buffer,int idx,int portNum) throws IOException {
		 for(int i = 0; i < idx; i++) {
			if(33 > buffer[i]&& buffer[i]> 126)return;
				 out.get(portNum).write((char)buffer[i]);
				}
	 }
	public void writeString(String targetString,int portNum) throws IOException {
		 for(int i = 0; i < targetString.length(); i++) {
				 out.get(portNum).write(targetString.charAt(i));
		 }
	 }
}
