import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	/**
	 * 读线程
	 * @author wangjie
	 *
	 */
	private static class ReadTask implements Runnable {
		List<String> list;

		public ReadTask(List<String> list) {
			this.list = list;
		}

		@Override
		public void run() {
			for (String str : list) {
				System.out.println(str);
			}
		}
	}
	/**
	 * 写线程
	 * @author wangjie
	 *
	 */
	private static class WriteTask implements Runnable {
		List<String> list;
		int index;

		public WriteTask(List<String> list, int index) {
			this.list = list;
			this.index = index;
		}

		@Override
		public void run() {
			list.remove(index);
			list.add(index, "write_" + index);
		}
	}

	public void run() {
		final int NUM = 10;
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < NUM; i++) {
			list.add("main_" + i);
		}
		ExecutorService executorService = Executors.newFixedThreadPool(NUM);
		for (int i = 0; i < NUM; i++) {
			executorService.execute(new ReadTask(list));
			executorService.execute(new WriteTask(list, i));
		}
		executorService.shutdown();
	}

	public static void main(String[] args) {
		 // 删除/修改元素
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		for(String x:list){
			list.remove(x);
		}
		 System.out.println(list.size());
	}
}