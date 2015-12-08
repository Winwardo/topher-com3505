package scenegraph.models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.Materials;
import renderer.primitives.Cuboid;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Table extends SceneGraph {
    private GL2  gl;
    private GLUT glut;

    public Table(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));
        this.gl = gl;
        this.glut = glut;

        SceneGraphNode root1 = root.createAttachedNode();
        root1.setPosition(new Vector3(0, -0.2f, 0));

        // Top
        root1
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
        root1
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
        root1
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
        root1
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
        root1
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
