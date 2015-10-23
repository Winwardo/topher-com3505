package scenegraph;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import lighting.PointLight;
import math.Vector3;
import renderer.primitives.Axes;
import renderer.primitives.Teapot;
import renderer.primitives.TexturedCube;

public class DefaultSceneGraph extends SceneGraph {
    private SceneGraphNode majorCube;
    private SceneGraphNode minorCubeSpin;
    private float          rotate;

    public DefaultSceneGraph(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));

        SceneGraphNode root1 = root.createAttachedNode();

        majorCube = root1.createAttachedNode();
        majorCube.attachRenderable(new TexturedCube(gl, glut));

        minorCubeSpin = majorCube.createAttachedNode();
        minorCubeSpin.setPosition(new Vector3(0, 1, 0));
        minorCubeSpin.attachLight(new PointLight(gl, gl.GL_LIGHT0));

        SceneGraphNode cubeNode = minorCubeSpin.createAttachedNode();
        cubeNode.setRotation(new Vector3(0, 0, 1), 45.0f);
        cubeNode.attachRenderable(new Teapot(gl, glut));
        cubeNode.setScaling(new Vector3(0.5f, 0.5f, 0.5f));

        SceneGraphNode lightOffset = root.createAttachedNode();
        lightOffset.setPosition(new Vector3(0, -1, 1));
        lightOffset.attachLight(new PointLight(gl, gl.GL_LIGHT1));

        root1.attachRenderable(new Axes(gl));
    }

    @Override
    public void update() {
        rotate += 0.5f;
        minorCubeSpin.setRotation(new Vector3(0, 1, 0), rotate);
        majorCube.setRotation(new Vector3(0, 0, 1), rotate);
    }
}
