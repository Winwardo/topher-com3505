package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.Materials;
import renderer.primitives.Plane;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Television extends SceneGraph {
    public Television(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));

        SceneGraphNode twister = root.createAttachedNode();

        twister
            .createAttachedNode()
            .attachRenderable(new Plane(gl, Materials.get().get("tvscreen")))
            .setRotation(new Vector3(1, 0, 0), 90)
            .setPosition(new Vector3(-1, -0.6f, -0.6f));
        twister.setRotation(new Vector3(0, 1, 0), 90);
        twister.setPosition(new Vector3(-0.5f, 0, -0.5f));
    }

    @Override
    public void update() {
    }
}
