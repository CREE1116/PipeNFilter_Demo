/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Framework;

import Components.AddFilter.AddFilter;
import Components.DeleteFilter.DeleteFilter;
import Components.Middle.ExclusiveMiddleFilter;
import Components.Middle.MiddleFilter;
import Components.Sink.SinkFilter;
import Components.Source.SourceFilter;

public class homework3 {
    public static void main(String[] args) {
        try {
            CommonFilter sourceFilter = new SourceFilter("Students.txt");
            CommonFilter sinkFilter = new SinkFilter("/Users/leejongmin/Desktop/output/Output3.txt");
            CommonFilter middleFilter = new ExclusiveMiddleFilter("CS");
            CommonFilter Delete17651Filter = new DeleteFilter("17651");
            CommonFilter Delete17652Filter = new DeleteFilter("17652");
             
            
            sourceFilter.connectOutputTo(middleFilter);
            middleFilter.connectOutputTo(Delete17651Filter);
            Delete17651Filter.connectOutputTo(Delete17652Filter);
            Delete17652Filter.connectOutputTo(sinkFilter);
            
            Thread thread1 = new Thread(sourceFilter);
            Thread thread2 = new Thread(sinkFilter);
            Thread thread3 = new Thread(middleFilter);
            Thread thread4 = new Thread(Delete17651Filter);
            Thread thread5 = new Thread(Delete17652Filter);
           
            
            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();
            thread5.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}