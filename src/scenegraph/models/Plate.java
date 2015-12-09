/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package scenegraph.models;

import com.jogamp.opengl.GL2;
import math.Vector3;
import renderer.Materials;
import renderer.primitives.Cylinder;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Plate extends SceneGraph {
    private GL2 gl;

    public Plate(GL2 gl) {
        super(new SceneGraphNode(gl));
        this.gl = gl;

        root
            .createAttachedNode()
            .setRotation(new Vector3(1, 0, 0), 90)
            .attachRenderable(
                new Cylinder(
                    gl,
                    1,
                    0.05f,
                    Materials.get().get("plastic_plate")));
    }

    @Override
    public void update() {
    }
}
