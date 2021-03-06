/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.Materials;
import renderer.primitives.Cylinder;
import renderer.primitives.Sphere;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Roller extends SceneGraph {

    private GL2                  gl;
    private GLUT                 glut;

    private final SceneGraphNode ballNode;
    public static float          rotation = 0;

    public Roller(GL2 gl) {
        super(new SceneGraphNode(gl));
        this.gl = gl;

        ballNode = root.createAttachedNode();
        ballNode.attachRenderable(
            new Sphere(gl, 1.5f, Materials.get().get("shinymetal")));
        ballNode.setScaling(new Vector3(0.75f, 0.75f, 1f));

        SceneGraphNode axis = root.createAttachedNode();
        final float rollerLength = 3.25f;
        axis.attachRenderable(
            new Cylinder(
                gl,
                0.35f,
                rollerLength,
                Materials.get().get("shinymetal")));
        axis.setPosition(new Vector3(0, 0, -rollerLength / 2));
    }

    @Override
    public void update() {
        // Rotation is set externally based on the Robot's speed
        ballNode.setRotation(new Vector3(0, 0, -1), rotation);
    }
}
