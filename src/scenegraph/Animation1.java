package scenegraph;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.util.gl2.GLUT;
import lighting.PointLight;
import math.Vector3;
import renderer.Materials;
import renderer.cameras.Cameras;
import renderer.cameras.SimpleCamera;
import renderer.primitives.Axes;
import renderer.primitives.Teapot;
import renderer.primitives.TexturedCube;

public class Animation1 extends SceneGraph {
    private SceneGraphNode majorCube;
    private SceneGraphNode minorCubeSpin;
    private float          rotate;

    private SimpleCamera         c;
    private SimpleCamera         c2;

    SceneGraphNode         root1;

    public Animation1(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));

        root1 = root.createAttachedNode();

        majorCube = root1.createAttachedNode();
        majorCube.attachRenderable(
            new TexturedCube(gl, glut, Materials.get().get("shinymetal")));

        minorCubeSpin = majorCube.createAttachedNode();
        minorCubeSpin.setPosition(new Vector3(0, 1, 0));
        // minorCubeSpin.attachLight(new PointLight(gl, gl.GL_LIGHT0));

        SceneGraphNode cubeNode = minorCubeSpin.createAttachedNode();
        cubeNode.setRotation(new Vector3(0, 0, 1), 45.0f);
        cubeNode.attachRenderable(
            new Teapot(gl, glut, Materials.get().get("shinymetal")));
        cubeNode.setScaling(new Vector3(0.5f, 0.5f, 0.5f));

        SceneGraphNode lightOffset = root.createAttachedNode();
        lightOffset.setPosition(new Vector3(0, -1, 1));
        lightOffset.attachLight(new PointLight(gl, gl.GL_LIGHT1));

        root1.attachRenderable(new Axes(gl));

        c = new SimpleCamera(gl, new Vector3(1.2f, 2.0f, 2.0f), new Vector3(0, 1, 0));
        int ci = Cameras.get().append(c);

        c2 = new SimpleCamera(gl, new Vector3(0, 1.7f, 1), new Vector3(0, 1, 0));
        int ci2 = Cameras.get().append(c2);
    }

    @Override
    public void update() {
        rotate += 0.5f;
        minorCubeSpin.setRotation(new Vector3(0, 1, 0), rotate);
        majorCube.setRotation(new Vector3(0, 0, 1), rotate);

        root1.setRotation(new Vector3(0, 1, 0), rotate);

        if (false) {
            Matrix4 rot = new Matrix4();

            float pi = 3.141592f;
            float amt = rotate - 125 + 180;
            float rad = amt * (pi / 180);

            rot.rotate(rad, 0, 1, 0);
            rot.translate(1.2f, 2.0f, 2.0f);
            Vector3 pos = root.position();
            rot.translate(pos.x(), pos.y(), pos.z());
            // rot.multVec(arg0, arg1, arg2, arg3);

            // Matrix4 m gl.glmodelv

            float[] array = rot.getMatrix();
            // printmat(array);

            c.setPosition(new Vector3(array[12], array[13], array[14]));
            c.setLookAt(new Vector3(0.0f, 1.0f, 0.0f));
        }

    }
}
