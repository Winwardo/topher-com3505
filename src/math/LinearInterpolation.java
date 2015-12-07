package math;

public class LinearInterpolation {
    public static float lerp(float from, float to, float lerp) {
        float difference = to - from;
        return from + difference * lerp;
    }

    public static Vector3 lerp(Vector3 from, Vector3 to, float lerp) {
        return new Vector3(
            lerp(from.x(), to.x(), lerp),
            lerp(from.y(), to.y(), lerp),
            lerp(from.z(), to.z(), lerp));
    }
}
