package Framework;
import java.util.ArrayList;

import Components.AddFilter.AddFilter;
import Components.DeleteFilter.DeleteFilter;
import Components.Middle.ExclusiveMajorsCourseFilter;
import Components.Middle.MajorsCourseFilter;
import Components.Sink.SinkFilter;
import Components.Source.SourceFilter;
import Componets.misinformationCheck.MisinformationFilter;

import java.util.List;

public class Constance {
	
	public enum FilterSet {
		
		Sysetm1(List.of(
				new SourceFilter("Students.txt",0),
				new MajorsCourseFilter("CS",0),
				new AddFilter("12345",0),
				new AddFilter("23456",0),
				new SinkFilter("/Users/leejongmin/Desktop/output/Output1.txt",0))),
		
		System2(List.of(
				new SourceFilter("Students.txt",0),
				new MajorsCourseFilter("EE",0),
				new AddFilter("23456",0),
				new SinkFilter("/Users/leejongmin/Desktop/output/Output2.txt",0))),
		
		System3(List.of(
				new SourceFilter("Students.txt",0),
				new ExclusiveMajorsCourseFilter("CS",0),
				new DeleteFilter("17651",0),
				new DeleteFilter("17652",0),
				new SinkFilter("/Users/leejongmin/Desktop/output/Output3.txt",0))),
		
		System4(List.of(
				new SourceFilter("Courses.txt",0),
				new SourceFilter("Students.txt",1),
				new MisinformationFilter(0,1),
				new SinkFilter("/Users/leejongmin/Desktop/output/Output4-1.txt",0),
				new SinkFilter("/Users/leejongmin/Desktop/output/Output4-2.txt",1)
				));
		private List<CommonFilterImpl> FilterList;
		
		FilterSet(List<CommonFilterImpl> FilterList){
			this.FilterList = FilterList;
		}
		public List<CommonFilterImpl> getFilterSet() {
			return this.FilterList;
		}
	}
}
