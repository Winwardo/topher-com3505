/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import math.Vector3;
import renderer.Materials;
import renderer.primitives.Sphere;
import renderer.primitives.Teapot;
import scenegraph.BallJoint;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Head extends SceneGraph {
    private static final float eyeOffset = 0.5f;

    private GL2                gl;

    private final BallJoint    neck;
    private float              rotate    = 0;

    public Head(GL2 gl) {
        super(new SceneGraphNode(gl));
        this.gl = gl;

        neck = new BallJoint(root);
        SceneGraphNode head = neck.get();

        // Core head
        head.setScaling(new Vector3(0.6f, 1, 1));

        head.attachRenderable(
            new Teapot(gl, Materials.get().get("shinymetal")));

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
            new Sphere(gl, 0.5f, Materials.get().get(texture)));
        eye.setScaling(Vector3.all(0.4f));
        eye.setPosition(new Vector3(0.7f, 0.2f, xOffset));
        eye.setRotation(new Vector3(0, 1, 1), 180);
    }

    @Override
    public void update() {
        // We want the head to look around as if surveying the restaurant
        rotate += 1;

        // These magic numbers have no meaning behind them other than they
        // produced good looking results
        float neckYaw = (float) Math.sin(rotate / 50) * 30;
        neck.setYaw(neckYaw);

        float neckPitch = (float) Math.sin(rotate / 76) * 5;
        neck.setPitch(neckPitch);
    }
}
