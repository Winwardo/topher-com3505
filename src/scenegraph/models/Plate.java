package scenegraph.models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.Materials;
import renderer.primitives.Cylinder;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Plate extends SceneGraph {
    private GL2  gl;
    private GLUT glut;

    public Plate(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));
        this.gl = gl;
        this.glut = glut;

        root
            .createAttachedNode()
            .setRotation(new Vector3(1, 0, 0), 90)
            .attachRenderable(
                new Cylinder(
                    gl,
                    glut,
                    1,
                    0.05f,
                    Materials.get().get("shinymetal")));
    }

    @Override
    public void update() {
    }
}
