/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import math.Vector3;
import renderer.Materials;
import renderer.primitives.Cylinder;
import renderer.primitives.Sphere;
import scenegraph.BallJoint;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class LeftArm extends SceneGraph {
    private static final float   ELBOW_OFFSET     = 0.1f;
    private static final float   UPPER_ARM_LENGTH = 1.0f;
    private static final float   LOWER_ARM_LENGTH = 0.75f;
    private static final float   ARM_THICKNESS    = 0.25f;

    private GL2                  gl;

    private final SceneGraphNode elbowBall;
    private final SceneGraphNode forearm;

    private final BallJoint      shoulderJoint;
    private final BallJoint      elbowJoint;

    private float                rotateShoulder   = 0;
    private float                rotateElbow      = 0;

    public LeftArm(GL2 gl) {
        super(new SceneGraphNode(gl));
        this.gl = gl;

        SceneGraphNode base = root.createAttachedNode();
        base.setRotation(new Vector3(0, 0, 1), 180);

        shoulderJoint = new BallJoint(base);

        shoulderJoint.get().attachRenderable(
            new Cylinder(
                gl,
                ARM_THICKNESS,
                UPPER_ARM_LENGTH,
                Materials.get().get("shinymetal")));

        SceneGraphNode elbowOffset = shoulderJoint
            .get()
            .createAttachedNode()
            .setPosition(new Vector3(0, 0, UPPER_ARM_LENGTH + ELBOW_OFFSET));

        elbowJoint = new BallJoint(elbowOffset);

        elbowBall = elbowJoint.get().createAttachedNode();
        elbowBall.attachRenderable(new Sphere(gl, 0.25f));

        forearm = elbowBall.createAttachedNode();
        forearm.attachRenderable(
            new Cylinder(
                gl,
                ARM_THICKNESS / 2,
                LOWER_ARM_LENGTH,
                Materials.get().get("shinymetal")));

        forearm
            .createAttachedNodeFromSceneGraph(new Claw(gl))
            .setRotation(new Vector3(1, 0, 0), 90)
            .setPosition(new Vector3(0, 0, LOWER_ARM_LENGTH))
            .setScaling(Vector3.all(0.75f));
    }

    @Override
    public void update() {
        // The left arm will move around as if for balance
        rotateShoulder += 1;

        // These magic numbers have no meaning behind them other than they
        // produced good looking results
        float shoulderRoll = 35 + (float) Math.sin(rotateShoulder / 50) * 10;
        shoulderJoint.setRoll(shoulderRoll);

        float shoulderYaw = 180 + (float) Math.sin(rotateShoulder / 76) * 20;
        shoulderJoint.setYaw(shoulderYaw);

        rotateElbow += 1;
        elbowJoint.setYaw(20);
        elbowJoint.setPitch(rotateElbow);
    }
}
