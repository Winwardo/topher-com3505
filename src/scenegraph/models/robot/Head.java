package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.TextureLoader;
import renderer.primitives.Sphere;
import renderer.primitives.Teapot;
import scenegraph.BallJoint;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Head extends SceneGraph {
    private static final float eyeOffset = 0.5f;

    private GL2  gl;
    private GLUT glut;

    private final BallJoint neck;
    private float           rotate = 0;

    public Head(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));
        this.gl = gl;
        this.glut = glut;

        neck = new BallJoint(root);
        SceneGraphNode head = neck.get();

        // Core head
        head.setScaling(new Vector3(0.6f, 1, 1));
        head.attachRenderable(
            new Teapot(gl, glut, TextureLoader.get().get("metal")));

        attachLeftEye(head);
        attachRightEye(head);
    }

    private void attachLeftEye(SceneGraphNode head) {
        attachEye(-eyeOffset, head, "eye_left");
    }

    private void attachRightEye(SceneGraphNode head) {
        attachEye(eyeOffset, head, "eye_right");
    }

    public void attachEye(float xOffset, SceneGraphNode head, String texture) {
        SceneGraphNode eye = head.createAttachedNode();
        eye.attachRenderable(
            new Sphere(gl, 0.5f, TextureLoader.get().get(texture)));
        eye.setScaling(Vector3.all(0.4f));
        eye.setPosition(new Vector3(0.7f, 0.2f, xOffset));
        eye.setRotation(new Vector3(0, 1, 1), 180);
    }

    @Override
    public void update() {
        rotate += 1;

        float ro = (float) Math.sin(rotate / 50);
        float p = ro * 30;
        neck.setYaw(p);

        float rro = (float) Math.sin(rotate / 76);
        float pp = rro * 5;
        neck.setPitch(pp);
    }
}
