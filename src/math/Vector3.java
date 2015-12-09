/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package math;

/**
 * Vector3 stores any 3 floats as a tuple with convenience functions to save on
 * having to use arbitrary length arrays
 * 
 * @author Topher
 *
 */
public class Vector3 {
    @Override
    public String toString() {
        return "Vector3 [x=" + x + ", y=" + y + ", z=" + z + "]";
    }

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

    public float distance(Vector3 other) {
        return (float) Math.sqrt(
            Math.pow(x() - other.x(), 2) + Math.pow(y() - other.y(), 2)
                + Math.pow(z() - other.z(), 2));
    }
}
