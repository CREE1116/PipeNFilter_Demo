
package Components.AddFilter;

import java.io.IOException;
import Framework.CommonFilterImpl;
import Utility.*;

public class AddFilter extends CommonFilterImpl{
	private String targetString;
	private BufferChecking bufferChecking;
	private BufferOut bufferOut;
	 public AddFilter(String targetString,int portNum) {
		 this.targetString = targetString;
		 bufferChecking = new BufferChecking();
		 bufferOut = new BufferOut(getPipedOutputStreamList());
		 setPortInfo(portNum);
	 }
	 public String toString() {
		 return "AddFilter--"+targetString+"--"+getPortNum(0);
	 }
    @Override
    public boolean specificComputationForFilter() throws IOException {
    	int byte_read = 0;
        int checkBlank = 4; 
        int numOfBlank = 0;
        int idx = 0;
        int startIndex = 0;
        boolean haveString = false;
        byte[] buffer = new byte[64];
        
        while(true) {          
            while(numOfBlank < checkBlank&& byte_read != -1) {
            	byte_read = in.get(getPortNum(0)).read();
                if(byte_read == ' ') numOfBlank++;
                if(byte_read != -1) buffer[idx++] = (byte)byte_read;
            }      
            bufferOut.writeBuffer(buffer,idx,getPortNum(0));
            idx = 0;
            startIndex = 0;
            while(31<byte_read) {
            	byte_read = in.get(getPortNum(0)).read();
                if(byte_read != ' '&& byte_read != '\n')buffer[idx++] = (byte)byte_read;
                else {
                	haveString = haveString|bufferChecking.isbufferEqualString(buffer,startIndex,targetString);
                	buffer[idx++] = ' ';
                	startIndex = idx;
                }
            }
            if (byte_read == -1) return true;
            if(!haveString) bufferOut.writeString(targetString+" ",getPortNum(0));
            bufferOut.writeBuffer(buffer,idx,getPortNum(0));
            haveString = false;
            idx = 0;
            numOfBlank = 0;
            byte_read = '\0';
        }
    }
}


