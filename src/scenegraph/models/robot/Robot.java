package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Robot extends SceneGraph {
    private static final float ARM_OUT = 0.65f;
    private GL2                gl;
    private GLUT               glut;

    private final SceneGraph roller;
    private final SceneGraph head;
    private final SceneGraph leftArm;
    private final SceneGraph rightArm;

    public Robot(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));
        this.gl = gl;
        this.glut = glut;

        head = new Head(gl, glut);
        root.createAttachedNodeFromSceneGraph(head).setPosition(
            new Vector3(0, 3, 0));

        root.createAttachedNodeFromSceneGraph(new Body(gl, glut)).setPosition(
            new Vector3(0, 2.3f, 0));

        SceneGraphNode arms = root
            .createAttachedNode()
            .setPosition(new Vector3(0, 2.0f, 0));

        leftArm = new Arm(gl, glut);
        arms.createAttachedNodeFromSceneGraph(leftArm).setPosition(
            new Vector3(0, 0, ARM_OUT));

        rightArm = new Arm(gl, glut);
        arms
            .createAttachedNodeFromSceneGraph(rightArm)
            .setPosition(new Vector3(0, 0, -ARM_OUT))
            .setRotation(new Vector3(1, 0, 0), 180);

        roller = new Roller(gl, glut);
        root.createAttachedNodeFromSceneGraph(roller);
    }

    @Override
    public void update() {
        roller.update();
        head.update();
        leftArm.update();
        rightArm.update();
    }
}
