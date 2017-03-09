package thread_safety;

import java.util.HashMap;

public class Thread_Unsafety_Hashmap {
	static HashMap<Integer,Integer> map=new HashMap<>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("线程１执行");
				for(int i=0;i<1000;i++){
					map.put(i, i);
				}
				System.out.println("线程1-------"+map.get(500));
			}
			
		}).start();
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("线程2执行");
				for(int i=1000;i<2000;i++){
					map.put(i, i);
				
				}
				System.out.println("线程2------"+map.get(1500));
			}
			
		}).start();

	}

}
