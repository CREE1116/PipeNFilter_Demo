package Framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import Components.AddFilter.AddFilter;
import Components.Sink.SinkFilter;
import Components.Source.SourceFilter;
import Componets.misinformationCheck.MisinformationFilter;
import Framework.Constance.FilterSet;

public class LifeCycleManager {
		
	public ArrayList<Thread> setFilterThread(FilterSet filterset) throws IOException{
		ArrayList<Thread> ThreadList = new ArrayList<>();
		for(CommonFilterImpl filter : setFilterConnection(filterset)) {
			ThreadList.add(new Thread(filter));
		}
		return ThreadList;
	}
	private List<CommonFilterImpl> setFilterConnection(FilterSet filterset) throws IOException{
		ArrayList<Integer> portNumList = new ArrayList<>();
		List<CommonFilterImpl> connctedFilterList = filterset.getFilterSet();
		for(CommonFilterImpl filter : connctedFilterList)
			for(int i : filter.getPortList()) 
				if(!portNumList.contains(i))portNumList.add(i);
		for(int i : portNumList) {
			CommonFilterImpl temp = null;
			for(CommonFilterImpl filter : filterset.getFilterSet()) {
				if(filter.getPortList().contains(i))
					if(temp == null) temp = filter;
					else { 
						System.out.println(temp.toString()+"\tConnect Out to\t"+filter+"\t--portNum: "+i);
						temp.connectOutputTo(filter, i);
						temp = filter;
					}
			}
		}
		return connctedFilterList;
	}
	
	  public void run(FilterSet filterset) throws IOException {
		  for(Thread thread : setFilterThread(filterset)) 
			  thread.start();
	  }
}
