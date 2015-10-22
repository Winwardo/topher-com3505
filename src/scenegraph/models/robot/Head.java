package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import renderer.Sphere;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Head extends SceneGraph {
    public Head(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));

        SceneGraphNode face = new SceneGraphNode(gl);

        root.attachRenderable(new Sphere(gl, glut));

        root.attachNode(face);
    }

    @Override
    public void update() {
    }

}
