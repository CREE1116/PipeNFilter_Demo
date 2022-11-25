package Framework;

import java.util.concurrent.TimeUnit;

import Framework.Constance.FilterSet;

public class Main {
	private static void WateSec(int waitingSec) {
		try {
			TimeUnit.MILLISECONDS.sleep(waitingSec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
			  LifeCycleManager LC = new LifeCycleManager();
			  try {
				  LC.run(FilterSet.Sysetm1);
				  WateSec(10);
				  LC.run(FilterSet.System2);
				  WateSec(10);
				  LC.run(FilterSet.System3);
				  WateSec(10);
				  LC.run(FilterSet.System4);
			  }catch(Exception e) {
				  e.printStackTrace();
		  }

	}

}
