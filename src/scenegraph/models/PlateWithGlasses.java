package scenegraph.models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.Materials;
import renderer.primitives.Cylinder;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class PlateWithGlasses extends SceneGraph {
    private GL2  gl;
    private GLUT glut;

    public PlateWithGlasses(GL2 gl, GLUT glut) {
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
                    Materials.get().get("plastic_plate")));

        root
            .createAttachedNodeFromSceneGraph(new Wineglass(gl, glut))
            .setScaling(Vector3.all(0.2f))
            .setPosition(new Vector3(0.4f, 0.05f, 0.0f));

        root
            .createAttachedNodeFromSceneGraph(new Wineglass(gl, glut))
            .setScaling(Vector3.all(0.2f))
            .setPosition(new Vector3(-0.2f, 0.05f, 0.7f));

        root
            .createAttachedNodeFromSceneGraph(new Wineglass(gl, glut))
            .setScaling(Vector3.all(0.2f))
            .setRotation(new Vector3(1, 0, 0), 100)
            .setPosition(new Vector3(-0.3f, 0.2f, -0.5f));
    }

    @Override
    public void update() {
    }
}
