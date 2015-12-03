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

        // Top
        root
            .createAttachedNode()
            .setPosition(new Vector3(0, 1, 0))
            .attachRenderable(
                new Cuboid(
                    gl,
                    new Vector3(2, 0.05f, 1),
                    Materials.get().get("wood")));

        // Legs
        final float legOffset = 0.575f;
        final float legHeight = 0.8f;
        final float legWidth = 0.075f;
        final float legDistanceFromCentre = 0.9f;
        root
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
        root
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
        root
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
        root
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
