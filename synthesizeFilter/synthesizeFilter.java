package synthesizeFilter;

import java.io.EOFException;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

import Framework.CommonFilter;
import Framework.CommonFilterImpl;

public class synthesizeFilter extends CommonFilterImpl{
	protected PipedInputStream CourseIn = new PipedInputStream();
	protected PipedInputStream StudnetIn = new PipedInputStream();
	protected PipedOutputStream NonError = new PipedOutputStream();
	protected PipedOutputStream Error = new PipedOutputStream();
	private String[] courseLib;

	private void writeBuffer(byte[] buffer,int idx,PipedOutputStream out) throws IOException {
		for(int i = 0; i < idx; i++) {
		   if(33 > buffer[i]&& buffer[i]> 126)return;
				out.write((char)buffer[i]);
				System.out.print((char)buffer[i]);
			   }
			   System.out.println();
		}
	private void printBuffer(byte[] buffer,int idx,String out) throws IOException {
		System.out.print(out+"---\t");
		for(int i = 0; i < idx; i++) {
		   if(33 > buffer[i]&& buffer[i]> 126)return;
				System.out.print((char)buffer[i]);
			   }
			   System.out.println();
		}
	
	private boolean checkBuffer(byte[] buffer, int startIdx, int idx){
		String temp = bufferToString(buffer,startIdx,idx);
		String [] bufferStringList = temp.trim().split(" ");
		ArrayList<String> list = new ArrayList<>(List.of(bufferStringList));
		for(String a : bufferStringList){
			if(!checkCourse(a,list))return false;
		}
		return true;
	}
	private boolean checkCourse(String StudentCourse, ArrayList<String> StudentCourseList) {
		for(String courseData : courseLib) {
			String[] temp = courseData.trim().split(" ");
			if(temp[0].equals(StudentCourse)) {
				if(temp.length<2)return true;
				for(String CourseId:temp) {
					if(!StudentCourseList.contains(CourseId))return false;
				}
			}
		}
		return true;
	}
	private String bufferToString(byte[] buffer, int startIdx, int idx){
		String temp = "";
		for(int i = startIdx; i<idx;i++){
			temp = temp+(char)buffer[i];
		}
		return temp.trim();
	}

	public void connectOutputTo(CommonFilter nextFilter1,CommonFilter nextFilter2) throws IOException {
		NonError.connect(nextFilter1.getPipedInputStream());
		Error.connect(nextFilter2.getPipedInputStream());
	}
	public void connectInputTo(CommonFilter CourseSource,CommonFilter StudentSource) throws IOException {
		CourseIn.connect(CourseSource.getPipedOutputStream());
		StudnetIn.connect(StudentSource.getPipedOutputStream());
	}
	public PipedInputStream getPipedInputStream() {
		return null;
	}
	public PipedOutputStream getNonErrorOutputStream() {
		return NonError;
	}
	public PipedOutputStream getErrorOutputStream() {
		return Error;
	}

	@Override
	public boolean specificComputationForFilter() throws IOException {
		String CourseBuffer ="";
		byte byte_read = '\0';
		int numOfBlank = 0;
		System.out.println("Course Data save start!!!");
		while(true){
			while (byte_read != '\n' && byte_read !=-1){
			byte_read = (byte)CourseIn.read();
			if(byte_read == ' ')numOfBlank++;
			if(byte_read != -1 && (numOfBlank<1 || 2<numOfBlank))CourseBuffer = CourseBuffer+(char)byte_read;
			}
			if(byte_read == -1){
				courseLib = CourseBuffer.split("\n");
				for(String a : courseLib){
					if(!a.trim().equals(""))
						System.out.println(a);
				}
				break;
			}
			numOfBlank = 0;
			byte_read = '\0';
		}
		byte_read = '\0';
		System.out.println("Course Data save end!!");
		System.out.println("Sudent Data call start!!");
		while(true){
			byte[] buffer = new byte[64];
			int idx = 0;
			int checkBlank = 4;
			int startIdx = 0;
			numOfBlank = 0;
			while(byte_read != -1 && byte_read != '\n') {
				byte_read = (byte)StudnetIn.read();
				if(byte_read ==' ')numOfBlank++;
				if(byte_read != -1)buffer[idx++] = byte_read;
				if(numOfBlank < checkBlank)startIdx = idx;
			}
			if(idx>3) {
			if(checkBuffer(buffer,startIdx,idx))printBuffer(buffer, idx, "NonError");
			else printBuffer(buffer, idx, "Error");
			}
			if(byte_read == -1)return true;
			idx = 0;
			byte_read = '\0';
			//return true;

			
		}
	}
}

