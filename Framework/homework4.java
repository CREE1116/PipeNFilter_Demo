/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Framework;

import java.io.PipedInputStream;
import java.io.SequenceInputStream;

import Components.AddFilter.AddFilter;
import Components.DeleteFilter.DeleteFilter;
import Components.Middle.ExclusiveMajorsCourseFilter;
import Components.Middle.MajorsCourseFilter;
import Components.Sink.SinkFilter;
import Components.Source.SourceFilter;
import Componets.misinformationCheck.MisinformationFilter;

public class homework4 {
    public static void main(String[] args) {
        try {
            CommonFilter courseSourceFilter = new SourceFilter("Courses.txt",0);
            CommonFilter StudentsourceFilter = new SourceFilter("Students.txt",1);
            CommonFilter SinkFilterNonError = new SinkFilter("/Users/leejongmin/Desktop/output/Output4-1.txt",0);
            CommonFilter SinkFilterError = new SinkFilter("/Users/leejongmin/Desktop/output/Output4-2.txt",1);
            CommonFilter misinformationFilter = new MisinformationFilter(0,1);
            
            courseSourceFilter.connectOutputTo(misinformationFilter,0);
            StudentsourceFilter.connectOutputTo(misinformationFilter,1);
            misinformationFilter.connectOutputTo(SinkFilterNonError, 0);
            misinformationFilter.connectOutputTo(SinkFilterError, 1);
            
            Thread thread1 = new Thread(courseSourceFilter);
            Thread thread2 = new Thread(StudentsourceFilter);
            Thread thread3 = new Thread(misinformationFilter);
            Thread thread4 = new Thread(SinkFilterNonError);
            Thread thread5 = new Thread(SinkFilterError);
            
            
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
