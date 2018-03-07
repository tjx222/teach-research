import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

/**
 * <pre>
 *
 * </pre>
 *
 * @author 3020mt
 * @version $Id: ForkJoinTest.java, v 1.0 2016年8月12日 下午3:48:01 3020mt Exp $
 */
public class ForkJoinTest {
	
	public static void main(String[] args) throws InterruptedException {
		ForkJoinPool pool = new ForkJoinPool(); 
		for(int i=1;i<3;i++)
			pool.submit(new ForkJoinTest.Computor(69*(i-1), 69*i));
		pool.shutdown();
		//pool.awaitTermination(2000, TimeUnit.SECONDS);
		System.out.println("end .. and do some thing else");
		Thread.sleep(30000);
	}

  static class Computor extends RecursiveAction{
		
		/**
		 * <pre>
		 *
		 * </pre>
		 */
		private static final long serialVersionUID = 6541061740340730431L;

		private int start = 0;
		
		private int end;
		
		Computor(int start,int end){
			this.start = start;
			this.end = end;
		}
		
		/**
		 * @return
		 * @see java.util.concurrent.RecursiveTask#compute()
		 */
		@Override
		protected void compute() {
			int length = end - start;
			if(length < 11 ){
				for(int i=start;i<end;i++)
					System.out.println("##########"+i);
			}else{
				int split = start + length / 2;
				
				Computor startCompute = new Computor(start, split);
				Computor endCompute = new Computor(split,end);
				startCompute.fork();
				endCompute.fork();
			}
			
		}
	}
}
