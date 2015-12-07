package math;

public class Interpolation {
    public static float lerp(float from, float to, float percent) {
        return easeInOutSine(from, to, percent);
        // float difference = to - from;
        // return from + difference * lerp;
    }

    public static Vector3 lerp(Vector3 from, Vector3 to, float lerp) {
        return new Vector3(
            lerp(from.x(), to.x(), lerp),
            lerp(from.y(), to.y(), lerp),
            lerp(from.z(), to.z(), lerp));
    }

    public static float easeInOutSine(float from, float to,
        float percentThrough) {
        // http://gsgd.co.uk/sandbox/jquery/easing/jquery.easing.1.3.js

        final float difference = to - from;

        float d = 1;

        float result = (float) (-((float) 1) / 2
            * (Math.cos(Math.PI * percentThrough / d) - 1));
        return from + difference * result;
    }
}
