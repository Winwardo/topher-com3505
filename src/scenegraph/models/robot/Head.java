package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.Sphere;
import renderer.Teapot;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Head extends SceneGraph {
    private static final float eyeOffset = 0.5f;

    private GL2  gl;
    private GLUT glut;

    private final SceneGraphNode pitch;
    private final SceneGraphNode yaw;
    private final SceneGraphNode roll;

    public Head(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));
        this.gl = gl;
        this.glut = glut;

        pitch = root.setRotation(new Vector3(0, 0, 1), 0);
        yaw = pitch.createAttachedNode().setRotation(new Vector3(0, 1, 0), 0);
        roll = yaw.createAttachedNode().setRotation(new Vector3(1, 0, 0), 0);

        SceneGraphNode wobblerNode = roll.createAttachedNode();

        // Core head
        wobblerNode.setScaling(new Vector3(0.6f, 1, 1));
        wobblerNode.attachRenderable(new Teapot(gl, glut));

        attachLeftEye(wobblerNode);
        attachRightEye(wobblerNode);
    }

    private void attachLeftEye(SceneGraphNode head) {
        attachEye(-eyeOffset, head);
    }

    private void attachRightEye(SceneGraphNode head) {
        attachEye(eyeOffset, head);
    }

    public void attachEye(float xOffset, SceneGraphNode head) {
        SceneGraphNode eye = head.createAttachedNode();
        eye.attachRenderable(new Sphere(gl, glut));
        eye.setScaling(Vector3.all(0.2f));
        eye.setPosition(new Vector3(0.7f, 0.2f, xOffset));

        {
            SceneGraphNode pupil = head.createAttachedNode();
            pupil.attachRenderable(new Sphere(gl, glut));
            pupil.setScaling(Vector3.all(0.25f));
            pupil.setPosition(new Vector3(0.5f, 0, 0));
        }
    }

    @Override
    public void update() {
        float now = System.currentTimeMillis() % 100000;
        float smaller = now * 0.001f;
        float rotate = (float) Math.sin(smaller);

        // wobbler.update();

        yaw.setRotationAmount((float) Math.sin(smaller) * 25);
        pitch.setRotationAmount((float) Math.cos(smaller * 1.5f) * 15);
    }
}
