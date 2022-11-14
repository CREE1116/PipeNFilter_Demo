
package Components.AddFilter;

import java.io.IOException;
import Framework.CommonFilterImpl;

public class AddFilter extends CommonFilterImpl{
	private String targetString;
	private boolean checkByteList(byte[] buffer ,int startIndex, String string) {
		boolean temp = true;
		for(int i = startIndex ; i<startIndex+string.length(); i++) {
			temp = temp&&(char)buffer[i] == string.charAt(i-startIndex);
		}
		return temp;
	}
	 private void writeString(String string) throws IOException {
		 for(int i = 0; i<string.length(); i++) {
			 out.write(string.charAt(i));
		 }
	 }
	 private void writeBuffer(byte[] buffer,int idx) throws IOException {
		 for(int i = 0; i < idx; i++) {
			if(33 > buffer[i]&& buffer[i]> 126)return;
				 out.write((char)buffer[i]);
				}
	 }
	 public AddFilter(String targetString) {
		 this.targetString = targetString;
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
            	byte_read = in.read();
                if(byte_read == ' ') numOfBlank++;
                if(byte_read != -1) buffer[idx++] = (byte)byte_read;
            }      
            writeBuffer(buffer,idx);
            idx = 0;
            startIndex = 0;
            while(31<byte_read) {
            	byte_read = in.read();
                if(byte_read != ' '&& byte_read != '\n')buffer[idx++] = (byte)byte_read;
                else {
                	haveString = haveString|checkByteList(buffer,startIndex,targetString);
                	buffer[idx++] = ' ';
                	startIndex = idx;
                }
            }
            if (byte_read == -1) return true;
            if(!haveString) writeString(targetString+" ");
            writeBuffer(buffer,idx);
            haveString = false;
            idx = 0;
            numOfBlank = 0;
            byte_read = '\0';
        }
    }
}


