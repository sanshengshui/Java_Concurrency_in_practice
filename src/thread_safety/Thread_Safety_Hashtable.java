package thread_safety;

import java.util.Hashtable;

public class Thread_Safety_Hashtable {
	static Hashtable<Integer,Integer> table=new Hashtable<>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 new Thread(new Runnable() {

	            @Override
	            public void run() {
	                // TODO Auto-generated method stub
	                System.out.println("线程1执行");
	                for (int i = 0; i < 1000; i++) {
	                    table.put(i,i);
	                }
	                System.out.println("线程1---------" + table.get(500));
	            }
	        }).start();
	        new Thread(new Runnable() {

	            @Override
	            public void run() {
	                // TODO Auto-generated method stub
	                System.out.println("线程2执行");
	                for (int i = 1000; i < 2000; i++) {
	                    table.put(i, i);
	                }
	                System.out.println("线程2---------" + table.get(1500));
	            }
	        }).start();
	    }

}
