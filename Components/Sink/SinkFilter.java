/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Components.Sink;

import java.io.FileWriter;
import java.io.IOException;

import Framework.CommonFilterImpl;

public class SinkFilter extends CommonFilterImpl{
    private String sinkFile;
  private  FileWriter fw;
    public SinkFilter(String outputFile, int portNum) {
        this.sinkFile = outputFile;
        setPortInfo(portNum);
    }
    public String toString() {
		 return "SinkFilter"+"--"+getPortNum(0);
	 }
    /** 
     * 지정된 파일 경로로 출력 
     */
    @Override
    public boolean specificComputationForFilter() throws IOException {
    	System.out.println("outputFile: "+sinkFile+"\n");
        int byte_read;
        fw = new FileWriter(this.sinkFile);
        while(true) {
            byte_read = in.get(getPortNum(0)).read(); 
            if (byte_read == -1) {
            	 fw.close();
                 System.out.print( "\n::Filtering is finished; Output file is created." );  
                 return true;
            }
            fw.write((char)byte_read);
        }   
    }
}
