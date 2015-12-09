/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package scenegraph.models;

import com.jogamp.opengl.GL2;
import math.Vector3;
import renderer.Materials;
import renderer.primitives.Cuboid;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Table extends SceneGraph {
    private GL2 gl;

    public Table(GL2 gl) {
        super(new SceneGraphNode(gl));
        this.gl = gl;

        SceneGraphNode core = root.createAttachedNode();
        core.setPosition(new Vector3(0, -0.2f, 0));

        // Top
        core
            .createAttachedNode()
            .setPosition(new Vector3(0, 1, 0))
            .attachRenderable(
                new Cuboid(
                    gl,
                    new Vector3(2, 0.05f, 1),
                    Materials.get().get("wood"),
                    8,
                    8,
                    8,
                    1,
                    0.25f,
                    1));

        // Legs
        final float legOffset = 0.575f;
        final float legHeight = 0.8f;
        final float legWidth = 0.075f;
        final float legDistanceFromCentre = 0.9f;
        core
            .createAttachedNode()
            .setPosition(
                new Vector3(
                    legDistanceFromCentre,
                    legOffset,
                    legDistanceFromCentre / 2))
            .attachRenderable(
                new Cuboid(
                    gl,
                    new Vector3(legWidth, legHeight, legWidth),
                    Materials.get().get("wood")));
        core
            .createAttachedNode()
            .setPosition(
                new Vector3(
                    legDistanceFromCentre,
                    legOffset,
                    -legDistanceFromCentre / 2))
            .attachRenderable(
                new Cuboid(
                    gl,
                    new Vector3(legWidth, legHeight, legWidth),
                    Materials.get().get("wood")));
        core
            .createAttachedNode()
            .setPosition(
                new Vector3(
                    -legDistanceFromCentre,
                    legOffset,
                    -legDistanceFromCentre / 2))
            .attachRenderable(
                new Cuboid(
                    gl,
                    new Vector3(legWidth, legHeight, legWidth),
                    Materials.get().get("wood")));
        core
            .createAttachedNode()
            .setPosition(
                new Vector3(
                    -legDistanceFromCentre,
                    legOffset,
                    legDistanceFromCentre / 2))
            .attachRenderable(
                new Cuboid(
                    gl,
                    new Vector3(legWidth, legHeight, legWidth),
                    Materials.get().get("wood")));
    }

    @Override
    public void update() {
    }
}
