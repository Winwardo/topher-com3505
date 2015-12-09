/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import math.Vector3;
import renderer.Materials;
import renderer.primitives.Cylinder;
import scenegraph.BallJoint;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Body extends SceneGraph {

    public Body(GL2 gl) {
        super(new SceneGraphNode(gl));
        BallJoint rollerBallJoint = new BallJoint(root);

        root.setRotationAmount(90);

        root
            .createAttachedNode()
            .attachRenderable(
                new Cylinder(gl, 0.5f, 1.4f, Materials.get().get("shinymetal")))
            .setRotation(new Vector3(0, 1, 0), -90f)
            .setScaling(new Vector3(1.5f, 1, 1));

        root.createAttachedNodeFromSceneGraph(new ChestLight(gl));

    }

    @Override
    public void update() {
    }
}
