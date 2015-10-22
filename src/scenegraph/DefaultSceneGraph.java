package scenegraph;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import lighting.DirectionalLight;
import lighting.ILight;
import math.Vector3;
import renderer.Axes;
import renderer.Cube;

public class DefaultSceneGraph extends SceneGraph {
    private SceneGraphNode majorCube;
    private SceneGraphNode minorCubeSpin;
    private float          rotate;

    public DefaultSceneGraph(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));

        SceneGraphNode root1 = new SceneGraphNode(gl);

        SceneGraphNode cubeNode = new SceneGraphNode(gl);
        cubeNode.setRotation(new Vector3(0, 0, 1), 45.0f);
        cubeNode.attachRenderable(new Cube(gl, glut));
        cubeNode.setScaling(new Vector3(0.5f, 0.5f, 0.5f));

        minorCubeSpin = new SceneGraphNode(gl);
        minorCubeSpin.setPosition(new Vector3(0, 1, 0));
        minorCubeSpin.attachNode(cubeNode);

        majorCube = new SceneGraphNode(gl);
        majorCube.attachNode(minorCubeSpin);
        majorCube.attachRenderable(new Cube(gl, glut));

        root1.attachRenderable(new Axes(gl));
        root1.attachNode(majorCube);

        ILight light1 = new DirectionalLight(gl);
        root.attachLight(light1);

        root.attachNode(root1);
    }

    @Override
    public void update() {
        rotate += 2;
        minorCubeSpin.setRotation(new Vector3(0, 1, 0), rotate);
        majorCube.setRotation(new Vector3(0, 0, 1), rotate);
    }
}
