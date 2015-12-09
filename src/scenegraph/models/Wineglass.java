/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package scenegraph.models;

import com.jogamp.opengl.GL2;
import math.Vector3;
import renderer.Materials;
import renderer.primitives.Cylinder;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Wineglass extends SceneGraph {
    private GL2 gl;

    public Wineglass(GL2 gl) {
        super(new SceneGraphNode(gl));
        this.gl = gl;

        final String material = "glass";

        SceneGraphNode base = root.createAttachedNode();
        base.setRotation(new Vector3(1, 0, 0), 90);

        // Bottom
        base.createAttachedNode().attachRenderable(
            new Cylinder(gl, 1, 0.05f, Materials.get().get(material)));

        // Leg
        base
            .createAttachedNode()
            .attachRenderable(
                new Cylinder(gl, 0.15f, 1f, Materials.get().get(material)))
            .setPosition(new Vector3(0, 0, -1));

        // Flare
        base
            .createAttachedNode()
            .attachRenderable(
                new Cylinder(
                    gl,
                    0.75f,
                    0.15f,
                    1f,
                    Materials.get().get(material)))
            .setPosition(new Vector3(0, 0, -2));

        // Top
        base
            .createAttachedNode()
            .attachRenderable(
                new Cylinder(gl, 0.75f, 1f, Materials.get().get(material)))
            .setPosition(new Vector3(0, 0, -3));
    }

    @Override
    public void update() {
    }
}
