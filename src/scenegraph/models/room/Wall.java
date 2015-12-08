package scenegraph.models.room;

import com.jogamp.opengl.GL2;
import math.Vector3;
import renderer.Materials;
import renderer.primitives.Cuboid;
import renderer.primitives.Plane;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Wall extends SceneGraph {
    private GL2 gl;

    public Wall(GL2 gl, float height, float depth) {
        super(new SceneGraphNode(gl));
        this.gl = gl;

        float wallBottomHeight = height * 0.32f;
        float wallTopHeight = height - wallBottomHeight;

        SceneGraphNode wall = root.createAttachedNode();

        // Wooden lower wall
        wall
            .createAttachedNode()
            .setScaling(new Vector3(depth, wallBottomHeight, 1))
            .attachRenderable(
                new Plane(gl, Materials.get().get("wood2"), 32, 32, 16, 1));

        // Upper red painted wall
        wall
            .createAttachedNode()
            .setScaling(new Vector3(depth, wallTopHeight, 1))
            .setPosition(new Vector3(0, wallBottomHeight, 0))
            .attachRenderable(
                new Plane(gl, Materials.get().get("redplastic"), 32, 32, 4, 1));

        // Plastic divider
        wall
            .createAttachedNode()
            .attachRenderable(
                new Cuboid(
                    gl,
                    new Vector3(depth, 0.25f, 0.25f),
                    Materials.get().get("glass"),
                    32,
                    1,
                    1,
                    1,
                    1,
                    1))
            .setPosition(new Vector3(depth / 2, wallBottomHeight, 0))
            .setRotation(new Vector3(0, 1, 0), 0);

        // Trim
        float trimHeight = 1;
        float trimDepth = 0.2f;

        float halfTrimDepth = trimDepth / 2;

        wall
            .createAttachedNode()
            .attachRenderable(
                new Cuboid(
                    gl,
                    new Vector3(depth, trimHeight, trimDepth),
                    Materials.get().get("wood"),
                    32,
                    1,
                    1,
                    16,
                    0.25f,
                    1))
            .setPosition(new Vector3(depth / 2, trimHeight / 2, halfTrimDepth))
            .setRotation(new Vector3(0, 1, 0), 0);
    }

    @Override
    public void update() {
    }
}
