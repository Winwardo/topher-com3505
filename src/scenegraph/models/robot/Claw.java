package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import math.Vector3;
import renderer.Materials;
import renderer.primitives.Cuboid;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Claw extends SceneGraph {
    private final GL2 gl;

    public Claw(GL2 gl) {
        super(new SceneGraphNode(gl));
        this.gl = gl;

        SceneGraphNode claw = root.createAttachedNode();

        claw
            .createAttachedNode()
            .attachRenderable(
                new Cuboid(
                    gl,
                    new Vector3(1, 0.1f, 1.2f),
                    Materials.get().get("shinymetal")))
            .setPosition(new Vector3(0.5f, 0, 0));

        claw
            .createAttachedNode()
            .attachRenderable(
                new Cuboid(
                    gl,
                    new Vector3(1, 0.1f, 1.2f),
                    Materials.get().get("shinymetal")))
            .setPosition(new Vector3(0, 0.5f, 0))
            .setRotation(new Vector3(0, 0, 1), 90);

        claw.setRotation(new Vector3(0, 0, 1), 45);
        claw.setScaling(Vector3.all(0.5f));
    }

    @Override
    public void update() {
    }
}
