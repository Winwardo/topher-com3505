package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.Materials;
import renderer.primitives.Plane;
import renderer.primitives.TexturedCube;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Television extends SceneGraph {
    public Television(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));

        SceneGraphNode twister = root.createAttachedNode();

        twister
            .createAttachedNode()
            .attachRenderable(
                new TexturedCube(gl, glut, Materials.get().get("shinymetal")))
            .setPosition(new Vector3(-0.5f, -0.31f, -0.1f))
            .setScaling(new Vector3(1, 0.5f, 1));

        twister
            .createAttachedNode()
            .attachRenderable(new Plane(gl, Materials.get().get("chest_light")))
            .setRotation(new Vector3(1, 0, 0), 90)
            .setPosition(new Vector3(-1, -0.6f, -0.6f));

        twister.setRotation(new Vector3(0, 1, 0), 90);
        twister.setPosition(new Vector3(-0.5f, 0, -0.5f));
    }

    @Override
    public void update() {
    }
}
