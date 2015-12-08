package scenegraph;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import lighting.SpotLight;
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
        frontLight.attachLight(new SpotLight(gl, new Vector3(0.75f, 1, 0.75f)));

        SceneGraphNode topLight = root.createAttachedNode();
        topLight.setPosition(new Vector3(0, 10, -2.5f));
        topLight.attachLight(new SpotLight(gl, new Vector3(1f, 0.95f, 0.95f)));

        SceneGraphNode backLight = root.createAttachedNode();
        backLight.setPosition(new Vector3(4, -2, -4));
        backLight.attachLight(new SpotLight(gl, new Vector3(0.75f, 0.75f, 1)));

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
}
