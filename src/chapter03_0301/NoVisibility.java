package chapter03_0301;
/*
 * @author jamesmsw
 * @date 2017-03-01
 * 可见性是一种复杂的属性,因为可见性中的错误总是会违背我们的直觉。在单线程环境中，如果
 * 向某个变量先写入值，然后在没有其他写入操作的情况下读取这个变量，那么总能得到相同的值。
 * 这看起来很自然。然而，当读操作和写操作在不同的线程中执行时，情况却并非如此，这看起来或许有些
 * 难以接受。通常，我们无法确保执行读操作的线程能适时地看到其他线程写入的值，有时甚至是根本不可能
 * 的事情。为了确保多个线程之间对内存写入操作的可见性，必须使用同步机制。
 */

public class NoVisibility {
	private static boolean ready;
	private static int number;
	
	private static class ReaderThread extends Thread{
		public void run(){
			while(!ready)
				Thread.yield();
				System.out.println(number);
			
		}
	}
	public static void main(String[] args){
		new ReaderThread().start();
		number=42;
		ready=true;
	}

}
