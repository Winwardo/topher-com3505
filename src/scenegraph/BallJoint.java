/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package scenegraph;

import math.Vector3;

/**
 * BallJoint is a helper class for easily giving any object an independent
 * pitch/yaw/roll.
 * 
 * Attach any future nodes to .get()
 * 
 * @author Topher
 *
 */
public class BallJoint {
    private final SceneGraphNode pitch;
    private final SceneGraphNode yaw;
    private final SceneGraphNode roll;
    private final SceneGraphNode get;

    public BallJoint(SceneGraphNode toAttachTo) {
        pitch = toAttachTo.setRotation(new Vector3(0, 0, 1), 0);
        yaw = pitch.createAttachedNode().setRotation(new Vector3(0, 1, 0), 0);
        roll = yaw.createAttachedNode().setRotation(new Vector3(1, 0, 0), 0);
        get = roll.createAttachedNode();
    }

    public SceneGraphNode get() {
        return get;
    }

    public BallJoint setPitch(float amount) {
        pitch.setRotationAmount(amount);
        return this;
    }

    public BallJoint setYaw(float amount) {
        yaw.setRotationAmount(amount);
        return this;
    }

    public BallJoint setRoll(float amount) {
        roll.setRotationAmount(amount);
        return this;
    }
}
