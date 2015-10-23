package scenegraph;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import lighting.PointLight;
import math.Vector3;
import renderer.Axes;
import scenegraph.models.robot.Robot;

public class EditSceneGraph extends SceneGraph {
    private final SceneGraph     item;
    private final SceneGraphNode itemNode;
    private float                rotate;

    public EditSceneGraph(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));

        {
            SceneGraphNode frontLight = new SceneGraphNode(gl);
            frontLight.setPosition(new Vector3(0, 1, 4));
            frontLight.attachLight(new PointLight(gl, gl.GL_LIGHT0));
            root.attachNode(frontLight);
        }

        {
            SceneGraphNode topLight = new SceneGraphNode(gl);
            topLight.setPosition(new Vector3(0, 10, -2.5f));
            topLight.attachLight(new PointLight(gl, gl.GL_LIGHT1));
            root.attachNode(topLight);
        }

        {
            SceneGraphNode backLight = new SceneGraphNode(gl);
            backLight.setPosition(new Vector3(4, -2, -4));
            backLight.attachLight(new PointLight(gl, gl.GL_LIGHT2));
            root.attachNode(backLight);
        }

        item = new Robot(gl, glut);
        itemNode = item.root();
        root.attachNode(itemNode);

        root.attachRenderable(new Axes(gl));
    }

    @Override
    public void update() {
        rotate += 1f;
        itemNode.setRotation(new Vector3(0, 1, 0), rotate);

        item.update();
    }
}
