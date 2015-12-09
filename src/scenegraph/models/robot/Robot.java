/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import lighting.Lights;
import math.Vector3;
import scenegraph.BallJoint;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Robot extends SceneGraph {
    private static final float   ARM_OUT = 0.65f;
    private GL2                  gl;

    private final SceneGraph     roller;
    private final SceneGraph     head;
    private final SceneGraph     rightArm;
    private final SceneGraph     leftArm;
    private final SceneGraph     body;
    private final BallJoint      rollerBallJoint;

    private float                rotate  = 0;

    public static ChestLight     CHEST_LIGHT;
    public static SceneGraphNode RIGHT_ARM;
    public static SceneGraphNode RIGHT_CLAW;

    public Robot(GL2 gl) {
        super(new SceneGraphNode(gl));
        this.gl = gl;

        SceneGraphNode root1 = root.createAttachedNode();
        root1.setPosition(new Vector3(0, 1, 0));

        rollerBallJoint = new BallJoint(root1);

        head = new Head(gl);
        rollerBallJoint
            .get()
            .createAttachedNodeFromSceneGraph(head)
            .setPosition(new Vector3(0, 3, 0));

        body = new Body(gl);
        rollerBallJoint
            .get()
            .createAttachedNodeFromSceneGraph(body)
            .setPosition(new Vector3(0, 2.3f, 0));

        SceneGraphNode arms = rollerBallJoint
            .get()
            .createAttachedNode()
            .setPosition(new Vector3(0, 2.0f, 0));

        rightArm = new RightArm(gl);
        arms.createAttachedNodeFromSceneGraph(rightArm).setPosition(
            new Vector3(0, 0, ARM_OUT));

        leftArm = new LeftArm(gl);
        arms.createAttachedNodeFromSceneGraph(leftArm).setPosition(
            new Vector3(0, 0, -ARM_OUT));

        roller = new Roller(gl);
        rollerBallJoint.get().createAttachedNodeFromSceneGraph(roller);
    }

    @Override
    public void update() {
        roller.update();
        head.update();
        rightArm.update();
        leftArm.update();
        body.update();

        rotate += 1;

        // The robot should be constantly swaying backwards and forwards to show
        // balance
        float sway = (float) Math.sin(rotate / 76) * 10;
        rollerBallJoint.get().setRotation(new Vector3(0, 0, 1), sway);

        // The light on the robot's chest should also sway. We should have a
        // direct reference, rather than having to pick the number "6" based on
        // what we know about when lights were set up for this program
        Lights.get().get(6).setIncline(sway);
    }
}
