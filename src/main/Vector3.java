package main;

public class Vector3 {
	private final float x;
	private final float y;
	private final float z;

	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float x() {
		return x;
	}

	public float y() {
		return y;
	}

	public float z() {
		return z;
	}
}
