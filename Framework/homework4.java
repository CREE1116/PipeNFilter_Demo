/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Framework;

import java.io.PipedInputStream;
import java.io.SequenceInputStream;

import Components.AddFilter.AddFilter;
import Components.DeleteFilter.DeleteFilter;
import Components.Middle.ExclusiveMiddleFilter;
import Components.Middle.MiddleFilter;
import Components.Sink.SinkFilter;
import Components.Source.SourceFilter;
import synthesizeFilter.synthesizeFilter;

public class homework4 {
    public static void main(String[] args) {
        try {
            CommonFilter courseSourceFilter = new SourceFilter("Courses.txt");
            CommonFilter StudentsourceFilter = new SourceFilter("Students.txt");
            CommonFilter SinkFilter1 = new SinkFilter("/Users/leejongmin/Desktop/output/Output4-1.txt");
            CommonFilter SinkFilter2 = new SinkFilter("/Users/leejongmin/Desktop/output/Output4-2.txt");
            synthesizeFilter synthesizeFilter = new synthesizeFilter();
            
            synthesizeFilter.connectInputTo(courseSourceFilter,StudentsourceFilter);
            SinkFilter1.getPipedInputStream().connect(synthesizeFilter.getNonErrorOutputStream());
            SinkFilter2.getPipedInputStream().connect(synthesizeFilter.getErrorOutputStream());
            
            Thread thread1 = new Thread(courseSourceFilter);
            Thread thread2 = new Thread(StudentsourceFilter);
            Thread thread3 = new Thread(synthesizeFilter);
            Thread thread4 = new Thread(SinkFilter1);
            Thread thread5 = new Thread(SinkFilter2);
            
            
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
