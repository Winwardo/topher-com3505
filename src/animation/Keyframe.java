package animation;

import math.Vector3;

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
        Vector3 scale, int frame) {
        this.position = position;
        this.rotation = rotation;
        this.rotationAmount = rotationAmount;
        this.scale = scale;
        this.duration = frame;
    }

    public float lerpFromFrameId(int frameId) {
        return frameId / (float) duration;
    }
}
