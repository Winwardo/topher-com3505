package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.Sphere;
import renderer.Teapot;
import scenegraph.BallJoint;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Head extends SceneGraph {
    private static final float eyeOffset = 0.5f;

    private GL2  gl;
    private GLUT glut;

    private final BallJoint neck;

    public Head(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));
        this.gl = gl;
        this.glut = glut;

        neck = new BallJoint(gl, glut, root);
        SceneGraphNode head = neck.get();

        // Core head
        head.setScaling(new Vector3(0.6f, 1, 1));
        head.attachRenderable(new Teapot(gl, glut));

        attachLeftEye(head);
        attachRightEye(head);
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

        neck.setYaw((float) Math.sin(smaller) * 25);
        neck.setPitch((float) Math.cos(smaller * 1.5f) * 15);
    }
}
