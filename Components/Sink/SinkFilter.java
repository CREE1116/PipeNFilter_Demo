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
    public SinkFilter(String outputFile) {
        this.sinkFile = outputFile;
    }
    @Override
    public boolean specificComputationForFilter() throws IOException {
    	System.out.println("outputFile: "+sinkFile+"\n");
        int byte_read;
        fw = new FileWriter(this.sinkFile);
        while(true) {
            byte_read = in.read(); 
            if (byte_read == -1) {
            	 fw.close();
                 System.out.print( "\n::Filtering is finished; Output file is created." );  
                 return true;
            }else  System.out.print((char)byte_read);
            fw.write((char)byte_read);
        }   
    }
}
