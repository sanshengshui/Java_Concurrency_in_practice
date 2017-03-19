package chapter04;

public class SafePoint {
	/*
	 * @GuardedBy("this")
	 */
	private int x,y;
	
	private SafePoint(int[] a){
		this(a[0],a[1]);
	}
	public SafePoint(SafePoint p){
		this(p.get());
	}
	private synchronized int[] get() {
		// TODO Auto-generated method stub
		return new int[]{x,y};
	}
	public SafePoint(int x, int y) {
		// TODO Auto-generated constructor stub
		this.x=x;
		this.y=y;
	}
	
	public synchronized void set(int x,int y){
		this.x=x;
		this.y=y;
	}

}
