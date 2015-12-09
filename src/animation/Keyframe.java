/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package animation;

import math.Vector3;

/**
 * A Keyframe holds position, rotation and scaling information at some given
 * point in time, as well as a length to hold for.
 * 
 * @author Topher
 *
 */
public class Keyframe {
    public final Vector3 position;
    public final Vector3 rotation;
    public final float   rotationAmount;
    public final Vector3 scale;

    public final int     duration;

    public Keyframe(int[] values) {
        this(
            new Vector3(values[0], values[1], values[2]),
            new Vector3(0, 1, 0),
            values[3],
            Vector3.one(),
            values[4]);
    }

    public Keyframe(Vector3 position, Vector3 rotation, float rotationAmount,
        Vector3 scale, int duration) {
        this.position = position;
        this.rotation = rotation;
        this.rotationAmount = rotationAmount;
        this.scale = scale;
        this.duration = duration;
    }

    public float lerpFromFrameId(int frameId) {
        return frameId / (float) duration;
    }
}
