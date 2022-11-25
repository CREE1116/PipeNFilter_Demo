package Componets.misinformationCheck;

import java.io.EOFException;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

import Framework.CommonFilter;
import Framework.CommonFilterImpl;
import Utility.BufferChecking;
import Utility.BufferOut;

public class MisinformationFilter extends CommonFilterImpl{
	private String[] courseLib;
	private BufferChecking bufferChecking;
	private BufferOut bufferOut;
	public MisinformationFilter(int CourseInputPort, int StudentInputPort) {
		bufferChecking = new BufferChecking();
		bufferOut = new BufferOut(getPipedOutputStreamList());
		setPortInfo(CourseInputPort);
		setPortInfo(StudentInputPort);
		
	}
	public String toString() {
		 return "MisinformationFilter--"+getPortNum(0)+", "+getPortNum(1);
	 }

	/** 
	 * 0번포트: 코스 
	 * 1번포트: 학생 
	 * 
	 */
	@Override
	public boolean specificComputationForFilter() throws IOException {
		String CourseBuffer ="";
		byte byte_read = '\0';
		int numOfBlank = 0;
		while(true){
			while (byte_read != '\n' && byte_read !=-1){
			byte_read = (byte)in.get(getPortNum(0)).read();
			if(byte_read == ' ')numOfBlank++;
			if(byte_read != -1 && (numOfBlank<1 || 2<numOfBlank))CourseBuffer = CourseBuffer+(char)byte_read;
			}
			if(byte_read == -1){
				courseLib = CourseBuffer.split("\n");
				break;
			}
			numOfBlank = 0;
			byte_read = '\0';
		}
		byte_read = '\0';
		while(true){
			byte[] buffer = new byte[64];
			int idx = 0;
			int checkBlank = 4;
			int startIdx = 0;
			numOfBlank = 0;
			while(byte_read != -1 && byte_read != '\n') {
				byte_read = (byte)in.get(getPortNum(1)).read();
				if(byte_read ==' ')numOfBlank++;
				if(byte_read != -1)buffer[idx++] = byte_read;
				if(numOfBlank < checkBlank)startIdx = idx;
			}
			if(idx>3) {
				try {
			if(bufferChecking.isNonErrorStudent(buffer,startIdx,idx,courseLib))bufferOut.writeBuffer(buffer, idx, getPortNum(0));
			else bufferOut.writeBuffer(buffer, idx, getPortNum(1));
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}
			if(byte_read == -1) 
				return true;
			idx = 0;
			byte_read = '\0';
			//return true;

			
		}
	}
}

