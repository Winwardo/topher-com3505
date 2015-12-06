package scenegraph;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.util.gl2.GLUT;
import lighting.PointLight;
import math.Vector3;
import renderer.cameras.Cameras;
import renderer.cameras.RotateAroundPointCamera;
import renderer.cameras.SimpleCamera;
import renderer.primitives.Axes;

public class EditSceneGraph extends SceneGraph {
    private final SceneGraph        item;
    private final SceneGraphNode    itemNode;
    private float                   rotate;
    private RotateAroundPointCamera c;
    private SimpleCamera            c2;

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

        // c = new SimpleCamera(gl, new Vector3(1.2f, 2.0f, 2.0f), new
        // Vector3(0, 1, 0));
        // c = new RotateAroundPointCamera(gl, new Vector3(0, 0f, 0f), 10, 10,
        // 45);
        // int ci = Cameras.get().append(c);

        // c = (RotateAroundPointCamera) Cameras.get().get(0);

        c2 = new SimpleCamera(
            gl,
            new Vector3(0, 1.7f, 1),
            new Vector3(0, 1, 0));
        int ci2 = Cameras.get().append(c2);
    }

    @Override
    public void update() {
        rotate -= 0.5f;
        // itemNode.setRotation(new Vector3(0, 1, 0), rotate);

        // c.setCircleAngle(rotate);

        item.update();

        if (true) {
            return;
        }

        {
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

            // c.setPosition(new Vector3(array[12], array[13], array[14]));
            // c.setLookAt(new Vector3(0.0f, 1.0f, 0.0f));
        }

        {
            Matrix4 rot = new Matrix4();

            float pi = 3.141592f;
            float amt = rotate - 90;
            // amt = 0;
            float rad = amt * (pi / 180);

            rot.rotate(rad, 0, 1, 0);
            rot.translate(0, 1.7f, 1);
            // rot.translate(1.2f, 2.0f, 2.0f);
            // rot.translate(0, 0, 0);
            // rot.multVec(arg0, arg1, arg2, arg3);

            // Matrix4 m gl.glmodelv
            {
                float[] array = rot.getMatrix();
                c2.setPosition(new Vector3(array[12], array[13], array[14]));
            }
            {
                // rot.translate(0, 0, 0);
                // rot.translate(-1.2f, -2.0f, -2.0f);
                // rot.rotate(-rad, 0, 1, 0);

                rot.translate(0, 0, -1);

                // rot.rotate(rad, 0, 1, 0);
                // rot.translate(1.2f, 2.0f, 2.0f);

                float[] array = rot.getMatrix();
                c2.setLookAt(new Vector3(array[12], array[13], array[14]));
                // c2.setLookAt(new Vector3(0, 1.7f, 0));
            }
        }

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
