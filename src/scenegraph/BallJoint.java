package scenegraph;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;

public class BallJoint {
    private final SceneGraphNode pitch;
    private final SceneGraphNode yaw;
    private final SceneGraphNode roll;

    public BallJoint(GL2 gl, GLUT glut, SceneGraphNode toAttachTo) {
        pitch = toAttachTo.setRotation(new Vector3(0, 0, 1), 0);
        yaw = pitch.createAttachedNode().setRotation(new Vector3(0, 1, 0), 0);
        roll = yaw.createAttachedNode().setRotation(new Vector3(1, 0, 0), 0);
    }

    public SceneGraphNode pitch() {
        return pitch;
    }

    public SceneGraphNode yaw() {
        return yaw;
    }

    public SceneGraphNode roll() {
        return roll;
    }

    public SceneGraphNode get() {
        return roll;
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
