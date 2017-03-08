package chapter03_0301;

/*
 * @NotThreadSafe
 */
public class MutableInteger {
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
