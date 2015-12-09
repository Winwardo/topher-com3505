/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package math;

public class Interpolation {
    public static float interpolate(float from, float to, float percent) {
        return easeInOutSine(from, to, percent);
    }

    public static Vector3 interpolate(Vector3 from, Vector3 to, float lerp) {
        return new Vector3(
            interpolate(from.x(), to.x(), lerp),
            interpolate(from.y(), to.y(), lerp),
            interpolate(from.z(), to.z(), lerp));
    }

    public static float easeInOutSine(float from, float to,
        float percentThrough) {
        // http://gsgd.co.uk/sandbox/jquery/easing/jquery.easing.1.3.js

        final float difference = to - from;
        float result = (float) (0.5f
            * (Math.cos(Math.PI * percentThrough) - 1.0f));
        return from + difference * result;
    }
}
