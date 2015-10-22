package scenegraph;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import lighting.PointLight;
import math.Vector3;
import scenegraph.models.robot.Head;

public class EditScene extends SceneGraph {
    private SceneGraphNode majorCube;
    private SceneGraphNode minorCubeSpin;
    private float          rotate;

    public EditScene(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));

        {
            SceneGraphNode frontLight = new SceneGraphNode(gl);
            frontLight.setPosition(new Vector3(0, 0, 4));
            frontLight.attachLight(new PointLight(gl, gl.GL_LIGHT0));
            root.attachNode(frontLight);
        }

        {
            SceneGraphNode backLight = new SceneGraphNode(gl);
            backLight.setPosition(new Vector3(0, 2, -1));
            backLight.attachLight(new PointLight(gl, gl.GL_LIGHT1));
            root.attachNode(backLight);
        }

        root.attachNode(new Head(gl, glut).root());
    }

    @Override
    public void update() {
    }
}
