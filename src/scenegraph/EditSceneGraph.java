package scenegraph;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import lighting.PointLight;
import math.Vector3;
import renderer.primitives.Axes;

public class EditSceneGraph extends SceneGraph {
    private final SceneGraph     item;
    private final SceneGraphNode itemNode;
    private float                rotate;

    public EditSceneGraph(GL2 gl, GLUT glut, SceneGraph scene) {
        super(new SceneGraphNode(gl));

        SceneGraphNode frontLight = root.createAttachedNode();
        frontLight.setPosition(new Vector3(0, 1, 4));
        frontLight.attachLight(
            new PointLight(gl, gl.GL_LIGHT0, new Vector3(0.75f, 1, 0.75f)));

        SceneGraphNode topLight = root.createAttachedNode();
        topLight.setPosition(new Vector3(0, 10, -2.5f));
        topLight.attachLight(
            new PointLight(gl, gl.GL_LIGHT1, new Vector3(1f, 0.95f, 0.95f)));

        SceneGraphNode backLight = root.createAttachedNode();
        backLight.setPosition(new Vector3(4, -2, -4));
        backLight.attachLight(
            new PointLight(gl, gl.GL_LIGHT2, new Vector3(0.75f, 0.75f, 1)));

        item = scene;
        itemNode = root.createAttachedNodeFromSceneGraph(item);

        root.attachRenderable(new Axes(gl));
    }

    @Override
    public void update() {
        rotate -= 0.5f;
        itemNode.setRotation(new Vector3(0, 1, 0), rotate);

        item.update();
    }

    private void printmat(float[] q) {
        System.out.format(
            "%.2f %.2f %.2f %.2f\n%.2f %.2f %.2f %.2f\n%.2f %.2f %.2f %.2f\n%.2f %.2f %.2f %.2f\n",
            q[0],
            q[1],
            q[2],
            q[3],
            q[4],
            q[5],
            q[6],
            q[7],
            q[8],
            q[9],
            q[10],
            q[11],
            q[12],
            q[13],
            q[14],
            q[15]);
    }
}
