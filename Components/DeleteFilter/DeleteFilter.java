
package Components.DeleteFilter;

import java.io.IOException;
import Framework.CommonFilterImpl;
import Utility.BufferChecking;
import Utility.BufferOut;

public class DeleteFilter extends CommonFilterImpl{
	private String targetString;
	private BufferChecking bufferChecking;
	private BufferOut bufferOut;
	
	 public DeleteFilter(String targetString, int portNum) {
		 this.targetString = targetString;
		 setPortInfo(portNum);
		 bufferChecking = new BufferChecking();
		 bufferOut = new BufferOut(getPipedOutputStreamList());
	 }
	 public String toString() {
		 return "DeleteFilter--"+targetString+"--"+getPortNum(0);
	 }
    @Override
    public boolean specificComputationForFilter() throws IOException {
    	int byte_read = 0;
        int checkBlank = 3; 
        int numOfBlank = 0;
        int idx = 0;

  byte[] buffer = new byte[64];
        
  while(true) {          
      while(numOfBlank < checkBlank&& byte_read != -1) {
      	byte_read = in.get(getPortNum(0)).read();
          if(byte_read == ' ') numOfBlank++;
          if(byte_read != -1) buffer[idx++] = (byte)byte_read;
      }      
      bufferOut.writeBuffer(buffer,idx,getPortNum(0));
      idx = 0;
      while(31<byte_read) {
    	  byte_read = in.get(getPortNum(0)).read();
    	  if(byte_read == ' ' || byte_read =='\n') {
    		  if(!bufferChecking.isbufferEqualString(buffer,0,targetString)) {
    			  bufferOut.writeBuffer(buffer,idx,getPortNum(0));
    			  out.get(getPortNum(0)).write(' ');
    		  }
    		  idx = 0;
    	  }else buffer[idx++] = (byte) byte_read;
      }
      numOfBlank = 0;
      if (byte_read == -1) return true;
      byte_read = '\0';
    	}
    }
}




