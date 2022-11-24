package Utility;

import java.util.ArrayList;
import java.util.List;

public class BufferChecking {
	private String[] courseLib;
	public BufferChecking() {
		
	}
	
	public boolean isbufferEqualString(byte[] buffer,int startIdx,String string) {
		boolean temp = true;
		for(int i = startIdx ; i<string.length()+startIdx; i++) {
			temp = temp&&(char)buffer[i] == string.charAt(i-startIdx);
		}
		return temp;
	}
	private String bufferToString(byte[] buffer, int startIdx, int idx){
		String temp = "";
		for(int i = startIdx; i<idx;i++){
			temp = temp+(char)buffer[i];
		}
		return temp.trim();
	}
	public boolean isNonErrorStudent(byte[] buffer, int startIdx, int idx, String[] courseLib) throws Exception{
		if(courseLib == null)throw new Exception("Non CorseDataList");
		String temp = bufferToString(buffer,startIdx,idx);
		String [] bufferStringList = temp.trim().split(" ");
		ArrayList<String> list = new ArrayList<>(List.of(bufferStringList));
		for(String a : bufferStringList){
			if(!checkCourse(a,list,courseLib))return false;
		}
		return true;
	}
	private boolean checkCourse(String StudentCourse, ArrayList<String> StudentCourseList,String[] courseLib) {
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

}
