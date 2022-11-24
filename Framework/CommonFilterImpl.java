/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Framework;

import java.io.EOFException;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;

public abstract class CommonFilterImpl implements CommonFilter {
	protected ArrayList<PipedInputStream> in = new ArrayList<PipedInputStream>();
	protected ArrayList<PipedOutputStream> out = new ArrayList<PipedOutputStream>();
	private ArrayList<Integer> portNum = new ArrayList<Integer>();

	public void connectOutputTo(CommonFilter nextFilter,int portNum) throws IOException {
		insertPipedOutputStream(portNum);
		out.get(portNum).connect(nextFilter.getPipedInputStream(portNum));
	}
	public void connectInputTo(CommonFilter previousFilter,int portNum) throws IOException {
		insertPipedInputStream(portNum);
		in.get(portNum).connect(previousFilter.getPipedOutputStream(portNum));
	}
	public PipedInputStream getPipedInputStream(int portNum) {
		insertPipedInputStream(portNum);
		return in.get(portNum);
	}
	public PipedOutputStream getPipedOutputStream(int portNum) {
		insertPipedOutputStream(portNum);
		return out.get(portNum);
	}
	public void setPortInfo(int portNum) {
		this.portNum.add(portNum);
	}
	public void insertPipedOutputStream(int portNum) {
		while(out.size()-1<portNum)
			out.add(null);
		out.set(portNum,new PipedOutputStream());
	}
	public void insertPipedInputStream(int portNum) {
		while(in.size()-1<portNum)
			in.add(null);
		in.add(portNum,new PipedInputStream());
	}
	public ArrayList<Integer> getPortList() {
		return this.portNum;
	}
	protected ArrayList<PipedInputStream> getPipedInputStreamList(){
		return this.in;
	}
	protected ArrayList<PipedOutputStream> getPipedOutputStreamList(){
		return this.out;
	}
	protected int getPortNum(int idx) {
		return portNum.get(idx);
	}
	
	abstract public boolean specificComputationForFilter() throws IOException;
	// Implementation defined in Runnable interface for thread
	public void run() {
		try {
			specificComputationForFilter();
		} catch (IOException e) {
			if (e instanceof EOFException) return;
			else System.out.println(e);
		} finally {
			closePorts();
		}
	}
	private void closePorts() {
		try {
			for(PipedInputStream pipedInputStream : in) 
				if(pipedInputStream !=null)
					pipedInputStream.close();
			for(PipedOutputStream pipedOutputStream : out)
				if(pipedOutputStream !=null)
					pipedOutputStream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
