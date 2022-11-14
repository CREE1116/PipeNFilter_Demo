
package Components.DeleteFilter;

import java.io.IOException;
import Framework.CommonFilterImpl;

public class DeleteFilter extends CommonFilterImpl{
	private String targetString;
	private boolean checkByteList(byte[] buffer, String string) {
		boolean temp = true;
		for(int i = 0 ; i<string.length(); i++) {
			temp = temp&&(char)buffer[i] == string.charAt(i);
		}
		return temp;
	}
	 private void writeBuffer(byte[] buffer,int idx) throws IOException {
		 for(int i = 0; i < idx; i++) {
			if(33 > buffer[i]&& buffer[i]> 126)return;
				 out.write((char)buffer[i]);
				}
	 }
	 public DeleteFilter(String targetString) {
		 this.targetString = targetString;
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
      	byte_read = in.read();
          if(byte_read == ' ') numOfBlank++;
          if(byte_read != -1) buffer[idx++] = (byte)byte_read;
      }      
      writeBuffer(buffer,idx);
      idx = 0;
      while(31<byte_read) {
    	  byte_read = in.read();
    	  if(byte_read == ' ' || byte_read =='\n') {
    		  if(!checkByteList(buffer, targetString)) {
    			  writeBuffer(buffer,idx);
    			  out.write(' ');
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




