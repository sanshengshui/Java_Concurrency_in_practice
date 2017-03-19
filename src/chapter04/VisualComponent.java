package chapter04;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
/*
 * VisualComponent是一个图形组件，允许客户程序注册监控监控鼠标和键盘等事件的监听器。它为每种类型的事件都备有一个
 * 已注册监听器列表，因此当某个事件发生时，就会调用相应的监听器。然而，在鼠标事件监听器与键盘事件监听器之间不存在
 * 任何关联,二者是彼此独立的，因此VisualComponent可以将其线程安全性委托给这俩个线程安全的监听器列表
 */

public class VisualComponent {
	private final List<KeyListener> keyListeners=
			new CopyOnWriteArrayList<KeyListener>();
	private final List<MouseListener> mouseListeners=
			new CopyOnWriteArrayList<MouseListener>();
	
	public void addKeyListener(KeyListener listener){
		keyListeners.add(listener);
	}
	public void removeKeyListener(KeyListener listener){
		keyListeners.remove(listener);
	}
	public void addMouseListener(MouseListener listener){
		mouseListeners.add(listener);
	}
	public void removeMouseListener(MouseListener listener){
		mouseListeners.remove(listener);
	}
}
