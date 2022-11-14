/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Framework;

import Components.AddFilter.AddFilter;
import Components.Middle.MiddleFilter;
import Components.Sink.SinkFilter;
import Components.Source.SourceFilter;

public class homework2 {
    public static void main(String[] args) {
        try {
            CommonFilter sourceFilter = new SourceFilter("Students.txt");
            CommonFilter sinkFilter = new SinkFilter("/Users/leejongmin/Desktop/output/Output2.txt");
            CommonFilter middleFilter = new MiddleFilter("EE");
            CommonFilter add23456Filter = new AddFilter("23456");
             
            
            sourceFilter.connectOutputTo(middleFilter);
            middleFilter.connectOutputTo(add23456Filter);
            add23456Filter.connectOutputTo(sinkFilter);
            
            Thread thread1 = new Thread(sourceFilter);
            Thread thread2 = new Thread(sinkFilter);
            Thread thread3 = new Thread(middleFilter);
            Thread thread4 = new Thread(add23456Filter);
            
            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
