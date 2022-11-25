/**
2 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Components.Middle;

import java.io.IOException;
import Framework.CommonFilterImpl;

public class MajorsCourseFilter extends CommonFilterImpl{
	private String target;
	public MajorsCourseFilter(String target,int portNum) {
		this.target = target;
		setPortInfo(portNum);
	}
	 public String toString() {
		 return "MajorsCourseFilter--"+target+"--"+getPortNum(0);
	 }
	 /**
	  * 지정한 과목을 든는 학생들만 출력 
	  */
    @Override
    public boolean specificComputationForFilter() throws IOException {
    	int checkBlank = 4; 
        int numOfBlank = 0;
        int idx = 0;
        byte[] buffer = new byte[64];
        boolean haveString = false;    
        int byte_read = 0;
        
        while(true) {          
        	// check "CS" on byte_read from student information
            while(byte_read != '\n' && byte_read != -1) {
            	byte_read = in.get(getPortNum(0)).read();
                if(byte_read == ' ') numOfBlank++;
                if(byte_read != -1) buffer[idx++] = (byte)byte_read;
                if(numOfBlank == checkBlank && buffer[idx-3] == target.charAt(0) && buffer[idx-2] == target.charAt(1))
                	haveString = true;
            }      
            if(haveString == true) {
                for(int i = 0; i<idx; i++) 
                    out.get(getPortNum(0)).write((char)buffer[i]);
                haveString = false;
            }
            if (byte_read == -1) return true;
            idx = 0;
            numOfBlank = 0;
            byte_read = '\0';
        }
    }  
}
