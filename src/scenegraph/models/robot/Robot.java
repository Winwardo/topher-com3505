package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import lighting.Light;
import lighting.Lights;
import math.Vector3;
import scenegraph.BallJoint;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Robot extends SceneGraph {
    private static final float   ARM_OUT = 0.65f;
    private GL2                  gl;
    private GLUT                 glut;

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

    public Robot(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));
        this.gl = gl;
        this.glut = glut;

        SceneGraphNode root1 = root.createAttachedNode();
        root1.setPosition(new Vector3(0, 1, 0));

        rollerBallJoint = new BallJoint(root1);

        head = new Head(gl, glut);
        rollerBallJoint
            .get()
            .createAttachedNodeFromSceneGraph(head)
            .setPosition(new Vector3(0, 3, 0));

        body = new Body(gl, glut);
        rollerBallJoint
            .get()
            .createAttachedNodeFromSceneGraph(body)
            .setPosition(new Vector3(0, 2.3f, 0));

        SceneGraphNode arms = rollerBallJoint
            .get()
            .createAttachedNode()
            .setPosition(new Vector3(0, 2.0f, 0));

        rightArm = new RightArm(gl, glut);
        arms.createAttachedNodeFromSceneGraph(rightArm).setPosition(
            new Vector3(0, 0, ARM_OUT));

        leftArm = new LeftArm(gl, glut);
        arms.createAttachedNodeFromSceneGraph(leftArm).setPosition(
            new Vector3(0, 0, -ARM_OUT));

        roller = new Roller(gl, glut);
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
        float rro = (float) Math.sin(rotate / 76);
        float pp = rro * 10;

        rollerBallJoint.get().setRotation(new Vector3(0, 0, 1), pp);
        ((Light) Lights.get().get(4)).setIncline(pp);
    }
}
