package math;

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

    public static Vector3 zero() {
        return all(0);
    }

    public static Vector3 one() {
        return all(1);
    }

    public static Vector3 all(float f) {
        return new Vector3(f, f, f);
    }
}
